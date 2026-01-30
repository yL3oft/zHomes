package me.yleoft.zHomes.storage;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.yleoft.zAPI.location.LocationHandler;
import me.yleoft.zAPI.player.PlayerHandler;
import me.yleoft.zAPI.zAPI;
import me.yleoft.zHomes.configuration.languages.LanguageBuilder;
import me.yleoft.zHomes.zHomes;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.sql.DataSource;
import java.io.File;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

public class DatabaseConnection {

    public static HikariDataSource dataSource = null;
    public static database_type type = database_type.H2;

    public static Class<?> mariadbdriverClass;
    public static Class<?> mysqldriverClass;
    public static Class<?> h2driverClass;
    public static Class<?> sqlitedriverClass;

    public static ClassLoader mysqlDriverLoader;
    public static ClassLoader mariadbDriverLoader;
    public static ClassLoader h2DriverLoader;
    public static ClassLoader sqliteDriverLoader;

    public static Driver mysqlDriver = null;
    public static Driver mariadbDriver = null;
    public static Driver h2Driver = null;
    public static Driver sqliteDriver = null;

    public static void connect() {
        try {
            if (dataSource == null || dataSource.isClosed()) {
                long start = System.currentTimeMillis();

                HikariConfig config = new HikariConfig();

                if (mariadbDriver == null) {
                    mariadbDriver = (Driver) mariadbdriverClass.getDeclaredConstructor().newInstance();
                }
                if (mysqlDriver == null) {
                    mysqlDriver = (Driver) mysqldriverClass.getDeclaredConstructor().newInstance();
                }
                if (sqliteDriver == null) {
                    sqliteDriver = (Driver) sqlitedriverClass.getDeclaredConstructor().newInstance();
                }
                if (h2Driver == null) {
                    h2Driver = (Driver) h2driverClass.getDeclaredConstructor().newInstance();
                }

                switch (zHomes.getConfigYAML().getDatabaseType().toLowerCase()) {
                    case "mariadb": {
                        type = database_type.EXTERNAL;
                        config.setDataSource(new DirectDriverDataSource(
                                mariadbDriver,
                                mariadbDriverLoader,
                                mariadbUrl(),
                                zHomes.getConfigYAML().getDatabaseUsername(),
                                zHomes.getConfigYAML().getDatabasePassword()
                        ));
                        break;
                    }
                    case "mysql": {
                        type = database_type.EXTERNAL;
                        config.setDataSource(new DirectDriverDataSource(
                                mysqlDriver,
                                mysqlDriverLoader,
                                mysqlUrl(),
                                zHomes.getConfigYAML().getDatabaseUsername(),
                                zHomes.getConfigYAML().getDatabasePassword()
                        ));
                        break;
                    }
                    case "sqlite": {
                        type = database_type.SQLITE;
                        config.setDataSource(new DirectDriverDataSource(
                                sqliteDriver,
                                sqliteDriverLoader,
                                sqliteUrl(),
                                null,
                                null
                        ));
                        break;
                    }
                    default: {
                        type = database_type.H2;
                        config.setDataSource(new DirectDriverDataSource(
                                h2Driver,
                                h2DriverLoader,
                                h2Url(),
                                null,
                                null
                        ));
                        break;
                    }
                }

                config.setMaximumPoolSize(zHomes.getConfigYAML().getDatabasePoolSize());
                dataSource = new HikariDataSource(config);

                long end = System.currentTimeMillis();
                zHomes.getInstance().getLoggerInstance().info("<yellow>HikariCP started in " + (end - start) + "ms");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error setting up HikariCP connection pool", e);
        }
    }

    public static String mariadbUrl() {
        return "jdbc:mariadb://" + zHomes.getConfigYAML().getDatabaseHost() + ":" + zHomes.getConfigYAML().getDatabasePort() + "/" + zHomes.getConfigYAML().getDatabaseName() +
                "?allowPublicKeyRetrieval=" + zHomes.getConfigYAML().isDatabaseAllowPublicKeyRetrieval() +
                "&useSSL=" + zHomes.getConfigYAML().isDatabaseUseSSL();
    }
    public static String mysqlUrl() {
        return "jdbc:mysql://" + zHomes.getConfigYAML().getDatabaseHost() + ":" + zHomes.getConfigYAML().getDatabasePort() + "/" + zHomes.getConfigYAML().getDatabaseName() +
                "?allowPublicKeyRetrieval=" + zHomes.getConfigYAML().isDatabaseAllowPublicKeyRetrieval() +
                "&useSSL=" + zHomes.getConfigYAML().isDatabaseUseSSL();
    }
    public static String h2Url() {
        return "jdbc:h2:" + zHomes.getInstance().getDataFolder().getAbsolutePath() + "/database-h2";
    }
    public static String sqliteUrl() {
        return "jdbc:sqlite:" + zHomes.getInstance().getDataFolder().getAbsolutePath() + "/database-sqlite.db";
    }

    public static void disconnect() {
        if (dataSource != null) {
            closePool();
        }
    }
    public static Connection getConnection() {
        try {
            if (dataSource != null) {
                return dataSource.getConnection();
            }
            disconnect();
            connect();
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to access database", e);
        }
    }
    public static void closePool() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

    public static void migrateData(@Nullable CommandSender sender, @NotNull String type) {
        zAPI.getScheduler().runAsync(task -> {
            final String op = type.toLowerCase(Locale.ROOT);
            switch (op) {
                case "sqlitetoh2" -> migrateUrlToUrl(sender, sqliteUrl(), DbEngine.SQLITE, h2Url(), DbEngine.H2);
                case "sqlitetomysql" -> migrateUrlToUrl(sender, sqliteUrl(), DbEngine.SQLITE, mysqlUrl(), DbEngine.MYSQL);
                case "sqlitetomariadb" -> migrateUrlToUrl(sender, sqliteUrl(), DbEngine.SQLITE, mariadbUrl(), DbEngine.MYSQL);

                case "mysqltosqlite" -> migrateUrlToUrl(sender, mysqlUrl(), DbEngine.MYSQL, sqliteUrl(), DbEngine.SQLITE);
                case "mysqltoh2" -> migrateUrlToUrl(sender, mysqlUrl(), DbEngine.MYSQL, h2Url(), DbEngine.H2);

                case "mariadbtosqlite" -> migrateUrlToUrl(sender, mariadbUrl(), DbEngine.MYSQL, sqliteUrl(), DbEngine.SQLITE);
                case "mariadbtoh2" -> migrateUrlToUrl(sender, mariadbUrl(), DbEngine.MYSQL, h2Url(), DbEngine.H2);

                case "h2tosqlite" -> migrateUrlToUrl(sender, h2Url(), DbEngine.H2, sqliteUrl(), DbEngine.SQLITE);
                case "h2tomysql" -> migrateUrlToUrl(sender, h2Url(), DbEngine.H2, mysqlUrl(), DbEngine.MYSQL);
                case "h2tomariadb" -> migrateUrlToUrl(sender, h2Url(), DbEngine.H2, mariadbUrl(), DbEngine.MYSQL);

                case "essentials" -> migrateFromEssentials(sender);
                case "sethome" -> migrateFromSetHome(sender);
                case "ultimatehomes" -> migrateFromUltimateHomes(sender);
                case "xhomes" -> migrateFromXHomes(sender);
                case "zhome" -> migrateFromZHome(sender);

                default -> {
                    if (sender != null) LanguageBuilder.sendMessage(sender, zHomes.getLanguageYAML().getMainConverterUsage());
                }
            }
        });
    }

    private enum DbEngine { SQLITE, H2, MYSQL }
    private static DbEngine engineFromUrl(String url) {
        String u = url.toLowerCase(Locale.ROOT);
        if (u.startsWith("jdbc:sqlite:")) return DbEngine.SQLITE;
        if (u.startsWith("jdbc:h2:")) return DbEngine.H2;
        if (u.startsWith("jdbc:mysql:") || u.startsWith("jdbc:mariadb:")) return DbEngine.MYSQL;
        return DbEngine.MYSQL;
    }

    private static String upsertSql(DbEngine engine) {
        String table = DatabaseEditor.databaseTable();

        return switch (engine) {
            case SQLITE ->
                    "INSERT INTO " + table + " (UUID, HOME, LOCATION) VALUES (?, ?, ?) " +
                            "ON CONFLICT(UUID, HOME) DO UPDATE SET LOCATION = excluded.LOCATION";

            case H2 ->
                // H2 MERGE is portable for H2. Requires unique key on (UUID, HOME).
                    "MERGE INTO " + table + " (UUID, HOME, LOCATION) KEY(UUID, HOME) VALUES (?, ?, ?)";

            case MYSQL ->
                // MySQL/MariaDB
                    "INSERT INTO " + table + " (UUID, HOME, LOCATION) VALUES (?, ?, ?) " +
                            "ON DUPLICATE KEY UPDATE LOCATION = VALUES(LOCATION)";
        };
    }

    private static String selectAllSql() {
        return "SELECT UUID, HOME, LOCATION FROM " + DatabaseEditor.databaseTable();
    }
    private static String countSql() {
        return "SELECT COUNT(*) AS total FROM " + DatabaseEditor.databaseTable();
    }

    private static void migrateUrlToUrl(@Nullable CommandSender sender, @NotNull String sourceUrl, @NotNull DbEngine sourceEngine, @NotNull String targetUrl, @NotNull DbEngine targetEngine) {

        connect();
        String user = zHomes.getConfigYAML().getDatabaseUsername();
        String pass = zHomes.getConfigYAML().getDatabasePassword();

        boolean sourceNeedsAuth = sourceEngine == DbEngine.MYSQL;
        boolean targetNeedsAuth = targetEngine == DbEngine.MYSQL;

        try (Connection sourceConn = openConnectionForUrl(sourceUrl, sourceNeedsAuth ? user : null, sourceNeedsAuth ? pass : null);
             Connection targetConn = openConnectionForUrl(targetUrl, targetNeedsAuth ? user : null, targetNeedsAuth ? pass : null)) {

            zHomes.getInstance().getLoggerInstance().info("<yellow>Connected to both source and target databases.");

            int totalRows = countRows(sourceConn);
            if (totalRows <= 0) {
                zHomes.getInstance().getLoggerInstance().info("<red>No data to migrate.");
                return;
            }

            zHomes.getInstance().getLoggerInstance().info("<yellow>Starting migration of " + totalRows + " records...");

            final String upsert = upsertSql(targetEngine);

            final int batchSize = 1000;
            final int progressEvery = 250;

            boolean oldAutoCommit = targetConn.getAutoCommit();
            targetConn.setAutoCommit(false);

            int migrated = 0;

            try (Statement stmt = sourceConn.createStatement();
                 ResultSet rs = stmt.executeQuery(selectAllSql());
                 PreparedStatement ps = targetConn.prepareStatement(upsert)) {

                while (rs.next()) {
                    String uuid = rs.getString("UUID");
                    String home = truncateHome(rs.getString("HOME"));
                    String location = rs.getString("LOCATION");

                    ps.setString(1, uuid);
                    ps.setString(2, home);
                    ps.setString(3, location);
                    ps.addBatch();

                    migrated++;

                    if (migrated % batchSize == 0) {
                        ps.executeBatch();
                        targetConn.commit();
                    }

                    if (migrated % progressEvery == 0) {
                        updateProgress(sender, migrated, totalRows);
                    }
                }

                ps.executeBatch();
                targetConn.commit();
            } catch (SQLException e) {
                try { targetConn.rollback(); } catch (SQLException ignored) {}
                throw e;
            } finally {
                try { targetConn.setAutoCommit(oldAutoCommit); } catch (SQLException ignored) {}
            }

            completeMigration(sender, migrated, totalRows);

        } catch (SQLException e) {
            zHomes.getInstance().getLoggerInstance().error("Failed to migrate database", e);
        }
    }

    private static int countRows(Connection sourceConn) throws SQLException {
        try (Statement stmt = sourceConn.createStatement();
             ResultSet countResult = stmt.executeQuery(countSql())) {
            return countResult.next() ? countResult.getInt("total") : 0;
        }
    }

    private static DbEngine currentEngine() {
        try (Connection c = getConnection()) {
            String url = c.getMetaData().getURL();
            return engineFromUrl(url);
        } catch (Exception e) {
            return DbEngine.MYSQL;
        }
    }

    private static String currentUpsertSql() {
        return upsertSql(currentEngine());
    }

    private static void migrateFromEssentials(@Nullable CommandSender sender) {
        File directory = new File(zHomes.getInstance().getDataFolder() + "/../Essentials/userdata");
        if (!directory.exists() || !directory.isDirectory()) {
            zHomes.getInstance().getLoggerInstance().error("Invalid directory: " + directory.getPath());
            return;
        }

        final String sql = currentUpsertSql();
        migrateYamlDirectory(sender, directory, sql, YamlHomeFormat.ESSENTIALS);
    }
    private static void migrateFromSetHome(@Nullable CommandSender sender) {
        File directory = new File(zHomes.getInstance().getDataFolder() + "/../SetHome/homes");
        if (!directory.exists() || !directory.isDirectory()) {
            zHomes.getInstance().getLoggerInstance().error("Invalid directory: " + directory.getPath());
            return;
        }

        final String sql = currentUpsertSql();
        migrateYamlDirectory(sender, directory, sql, YamlHomeFormat.SETHOME);
    }
    private static void migrateFromUltimateHomes(@Nullable CommandSender sender) {
        File directory = new File(zHomes.getInstance().getDataFolder() + "/../UltimateHomes/playerdata");
        if (!directory.exists() || !directory.isDirectory()) {
            zHomes.getInstance().getLoggerInstance().error("Invalid directory: " + directory.getPath());
            return;
        }

        final String sql = currentUpsertSql();
        migrateYamlDirectory(sender, directory, sql, YamlHomeFormat.ULTIMATEHOMES);
    }
    private static void migrateFromZHome(@Nullable CommandSender sender) {
        File directory = new File(zHomes.getInstance().getDataFolder() + "/../zHome/homes");
        if (!directory.exists() || !directory.isDirectory()) {
            zHomes.getInstance().getLoggerInstance().error("Invalid directory: " + directory.getPath());
            return;
        }

        final String sql = currentUpsertSql();
        migrateYamlDirectory(sender, directory, sql, YamlHomeFormat.ZHOME);
    }
    private static void migrateFromXHomes(@Nullable CommandSender sender) {
        File file = new File(zHomes.getInstance().getDataFolder() + "/../Xhomes/playerhomes.yml");
        if (!file.exists()) {
            zHomes.getInstance().getLoggerInstance().error("Invalid file: " + file.getPath());
            return;
        }

        final String sql = currentUpsertSql();

        try (Connection conn = getConnection()) {
            int totalUsers;
            YamlConfiguration fYaml = YamlConfiguration.loadConfiguration(file);
            totalUsers = fYaml.getKeys(false).size();

            zHomes.getInstance().getLoggerInstance().info("<yellow>Starting migration for " + totalUsers + " users...");

            boolean oldAuto = conn.getAutoCommit();
            conn.setAutoCommit(false);

            int userCount = 0;
            int homeCount = 0;

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                for (String player : fYaml.getKeys(false)) {
                    OfflinePlayer offplayer = PlayerHandler.getOfflinePlayer(player);
                    userCount++;

                    if (offplayer == null) {
                        updateUserProgress(sender, userCount, totalUsers, "<green>Conversion failed, skipping user...");
                        continue;
                    }

                    String uuid = offplayer.getUniqueId().toString();
                    ConfigurationSection homesSection = fYaml.getConfigurationSection(player);
                    if (homesSection == null) {
                        updateUserProgress(sender, userCount, totalUsers, "<green>Converting Data...");
                        continue;
                    }

                    for (String homeName : homesSection.getKeys(false)) {
                        String homeRaw = homesSection.getString(homeName);
                        if (homeRaw == null) continue;

                        String[] homeS = homeRaw.split(",");
                        if (homeS.length != 11) continue;

                        String worldName = homeS[0];
                        double x = Double.parseDouble(homeS[1] + "." + homeS[2]);
                        double y = Double.parseDouble(homeS[3] + "." + homeS[4]);
                        double z = Double.parseDouble(homeS[5] + "." + homeS[6]);
                        float yaw = Float.parseFloat(homeS[7] + "." + homeS[8]);
                        float pitch = Float.parseFloat(homeS[9] + "." + homeS[10]);

                        String location = LocationHandler.serialize(worldName, x, y, z, yaw, pitch);

                        ps.setString(1, uuid);
                        ps.setString(2, truncateHome(homeName));
                        ps.setString(3, location);
                        ps.addBatch();
                        homeCount++;
                    }

                    if (userCount % 200 == 0) {
                        ps.executeBatch();
                        conn.commit();
                    }

                    updateUserProgress(sender, userCount, totalUsers, "<green>Converting Data...");
                }

                ps.executeBatch();
                conn.commit();
            } catch (SQLException e) {
                try { conn.rollback(); } catch (SQLException ignored) {}
                throw e;
            } finally {
                try { conn.setAutoCommit(oldAuto); } catch (SQLException ignored) {}
            }

            if (sender != null) {
                LanguageBuilder.sendMessage(sender, "<green>Converted Data! <dark_gray>[<gray>" + userCount + " users/" + totalUsers + " users<dark_gray>]");
                if (sender instanceof Player player) player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100.0F, 1.0F);
            }
            zHomes.getInstance().getLoggerInstance().info("<green>Migration completed! " + userCount + " users and " + homeCount + " homes transferred from XHomes.");

        } catch (SQLException e) {
            zHomes.getInstance().getLoggerInstance().error("Unable to migrate data from XHomes", e);
        }
    }

    private enum YamlHomeFormat { ESSENTIALS, SETHOME, ULTIMATEHOMES, ZHOME }
    private static void migrateYamlDirectory(@Nullable CommandSender sender, File directory, String upsertSql, YamlHomeFormat format) {
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files == null) {
            LanguageBuilder.sendMessage(sender, zHomes.getLanguageYAML().getMainConverterError());
            return;
        }

        int totalUsers = files.length;
        zHomes.getInstance().getLoggerInstance().info("<yellow>Starting migration for " + totalUsers + " users...");

        try (Connection conn = getConnection()) {
            boolean oldAuto = conn.getAutoCommit();
            conn.setAutoCommit(false);

            int userCount = 0;
            int homeCount = 0;

            try (PreparedStatement ps = conn.prepareStatement(upsertSql)) {
                for (File file : files) {
                    userCount++;

                    String uuid = file.getName().replace(".yml", "");
                    YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);

                    int insertedThisUser = switch (format) {
                        case ESSENTIALS -> insertHomesFromSection(ps, uuid, yaml.getConfigurationSection("homes"), EssentialsKeys.INSTANCE);
                        case SETHOME -> insertHomesFromSection(ps, uuid, yaml.getConfigurationSection("Homes"), SetHomeKeys.INSTANCE);
                        case ULTIMATEHOMES -> insertHomesFromSection(ps, uuid, yaml.getConfigurationSection("homes"), UltimateHomesKeys.INSTANCE);
                        case ZHOME -> insertHomesFromZHome(ps, uuid, yaml);
                    };

                    homeCount += insertedThisUser;

                    if (userCount % 200 == 0) {
                        ps.executeBatch();
                        conn.commit();
                    }

                    updateUserProgress(sender, userCount, totalUsers, "<green>Converting Data...");
                }

                ps.executeBatch();
                conn.commit();

            } catch (SQLException e) {
                try { conn.rollback(); } catch (SQLException ignored) {}
                throw e;
            } finally {
                try { conn.setAutoCommit(oldAuto); } catch (SQLException ignored) {}
            }

            if (sender != null) {
                LanguageBuilder.sendMessage(sender, "<green>Converted Data! <dark_gray>[<gray>" + userCount + " users/" + totalUsers + " users<dark_gray>]");
                if (sender instanceof Player player) player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100.0F, 1.0F);
            }

            zHomes.getInstance().getLoggerInstance().info("<green>Migration completed! " + userCount + " users and " + homeCount + " homes transferred from " + format + ".");

        } catch (SQLException e) {
            zHomes.getInstance().getLoggerInstance().error("Unable to migrate YAML data: " + format, e);
        }
    }

    private interface HomeKeys {
        String world(ConfigurationSection home);
    }
    private static final class EssentialsKeys implements HomeKeys {
        static final EssentialsKeys INSTANCE = new EssentialsKeys();
        public String world(ConfigurationSection home) { return home.getString("world-name", ""); }
    }
    private static final class SetHomeKeys implements HomeKeys {
        static final SetHomeKeys INSTANCE = new SetHomeKeys();
        public String world(ConfigurationSection home) { return home.getString("world", ""); }
    }
    private static final class UltimateHomesKeys implements HomeKeys {
        static final UltimateHomesKeys INSTANCE = new UltimateHomesKeys();
        public String world(ConfigurationSection home) { return home.getString("world", ""); }
    }

    private static int insertHomesFromSection(PreparedStatement ps, String uuid, @Nullable ConfigurationSection homesSection, HomeKeys keys) throws SQLException {
        if (homesSection == null) return 0;

        int count = 0;
        for (String homeName : homesSection.getKeys(false)) {
            ConfigurationSection home = homesSection.getConfigurationSection(homeName);
            if (home == null) continue;

            String worldName = keys.world(home);
            double x = home.getDouble("x");
            double y = home.getDouble("y");
            double z = home.getDouble("z");
            float yaw = (float) home.getDouble("yaw");
            float pitch = (float) home.getDouble("pitch");

            String location = LocationHandler.serialize(worldName, x, y, z, yaw, pitch);

            ps.setString(1, uuid);
            ps.setString(2, truncateHome(homeName));
            ps.setString(3, location);
            ps.addBatch();
            count++;
        }
        return count;
    }
    private static int insertHomesFromZHome(PreparedStatement ps, String uuid, YamlConfiguration yaml) throws SQLException {
        if (yaml.getKeys(false).isEmpty()) return 0;

        int count = 0;
        for (String homeName : yaml.getKeys(false)) {
            ConfigurationSection home = yaml.getConfigurationSection(homeName);
            if (home == null) continue;

            String worldName = home.getString("world", "");
            double x = home.getDouble("x");
            double y = home.getDouble("y");
            double z = home.getDouble("z");
            float yaw = (float) home.getDouble("yaw");
            float pitch = (float) home.getDouble("pitch");

            String location = LocationHandler.serialize(worldName, x, y, z, yaw, pitch);

            ps.setString(1, uuid);
            ps.setString(2, truncateHome(homeName));
            ps.setString(3, location);
            ps.addBatch();
            count++;
        }
        return count;
    }

    private static void updateUserProgress(CommandSender sender, int count, int totalUsers, String prefix) {
        if (sender != null) {
            String message = prefix + " <dark_gray>[<gray>" + count + " users/" + totalUsers + " users<dark_gray>]";
            LanguageBuilder.sendActionBar(sender, message);
        }
    }
    private static void updateProgress(CommandSender sender, int count, int totalRows) {
        if (sender != null) {
            String message = "<green>Converting Data... <dark_gray>[<gray>" + count + "/" + totalRows + "<dark_gray>]";
            LanguageBuilder.sendActionBar(sender, message);
        }
    }
    private static void completeMigration(CommandSender sender, int count, int totalRows) {
        if (sender != null) {
            String message = "<green>Converted Data! <dark_gray>[<gray>" + count + "/" + totalRows + "<dark_gray>]";
            LanguageBuilder.sendActionBar(sender, message);
            LanguageBuilder.sendMessage(sender, zHomes.getLanguageYAML().getMainConverterOutput());
            try {
                if (sender instanceof Player player) {
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100.0F, 1.0F);
                }
            } catch (Exception ignored) {}
        }
        zHomes.getInstance().getLoggerInstance().info("<green>Migration completed! " + count + " records transferred.");
    }

    private static String truncateHome(String home) {
        if (home == null) return "";
        return home.length() <= 100 ? home : home.substring(0, 100);
    }

    private static Connection openConnectionForUrl(String jdbcUrl, String username, String password) throws SQLException {
        if (jdbcUrl == null) {
            throw new SQLException("jdbcUrl is null");
        }

        final String u = jdbcUrl.toLowerCase();

        Driver driver;
        ClassLoader loader;

        if (u.startsWith("jdbc:mysql:")) {
            driver = mysqlDriver;
            loader = mysqlDriverLoader;
            if (driver == null) {
                throw new SQLException("MySQL driver is not initialized (mysqlDriver == null)");
            }
        } else if (u.startsWith("jdbc:mariadb:")) {
            driver = mariadbDriver;
            loader = mariadbDriverLoader;
            if (driver == null) {
                throw new SQLException("MariaDB driver is not initialized (mariadbDriver == null)");
            }
        } else if (u.startsWith("jdbc:h2:")) {
            driver = h2Driver;
            loader = h2DriverLoader;
            if (driver == null) {
                throw new SQLException("H2 driver is not initialized (h2Driver == null)");
            }
        } else if (u.startsWith("jdbc:sqlite:")) {
            driver = sqliteDriver;
            loader = sqliteDriverLoader;
            if (driver == null) {
                throw new SQLException("SQLite driver is not initialized (SQLite == null)");
            }
        } else {
            throw new SQLException("Unsupported JDBC url for migration: " + jdbcUrl);
        }

        DirectDriverDataSource ds = new DirectDriverDataSource(driver, loader, jdbcUrl, username, password);
        return ds.getConnection();
    }

    public static boolean existsTableColumnValueDoubleLower(String table, String columnName, String value, String columnName2, String value2) {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM " + table + " WHERE " + columnName + "=? AND LOWER(" + columnName2 + ")=LOWER(?)")) {
            ps.setString(1, value);
            ps.setString(2, value2);
            try (ResultSet results = ps.executeQuery()) {
                return results.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * DataSource that calls Driver#connect directly, while temporarily setting
     * the thread context classloader to the driver's classloader.
     *
     * This avoids DriverManager classloader issues and fixes NoClassDefFoundError
     * for drivers loaded from URLClassLoader (like H2 in your case).
     */
    public static final class DirectDriverDataSource implements DataSource {
        private final Driver driver;
        private final ClassLoader driverLoader;
        private final String jdbcUrl;
        private final Properties props = new Properties();
        private final AtomicReference<PrintWriter> logWriter = new AtomicReference<>(null);

        public DirectDriverDataSource(Driver driver, ClassLoader driverLoader, String jdbcUrl, String username, String password) {
            this.driver = driver;
            this.driverLoader = driverLoader;
            this.jdbcUrl = jdbcUrl;
            if (username != null) props.setProperty("user", username);
            if (password != null) props.setProperty("password", password);
        }

        @Override
        public Connection getConnection() throws SQLException {
            return connectWithTccl();
        }

        @Override
        public Connection getConnection(String username, String password) throws SQLException {
            Properties p = new Properties();
            p.putAll(this.props);
            if (username != null) p.setProperty("user", username);
            if (password != null) p.setProperty("password", password);
            return connectWithTccl(p);
        }

        private Connection connectWithTccl() throws SQLException {
            return connectWithTccl(this.props);
        }

        private Connection connectWithTccl(Properties p) throws SQLException {
            ClassLoader prev = Thread.currentThread().getContextClassLoader();
            try {
                if (driverLoader != null) {
                    Thread.currentThread().setContextClassLoader(driverLoader);
                }
                Connection c = driver.connect(jdbcUrl, p);
                if (c == null) {
                    throw new SQLException("Driver returned null connection for url=" + jdbcUrl + " (acceptsURL=" + driver.acceptsURL(jdbcUrl) + ")");
                }
                return c;
            } finally {
                Thread.currentThread().setContextClassLoader(prev);
            }
        }

        @Override
        public PrintWriter getLogWriter() {
            return logWriter.get();
        }

        @Override
        public void setLogWriter(PrintWriter out) {
            logWriter.set(out);
        }

        @Override
        public void setLoginTimeout(int seconds) {
            // not used
        }

        @Override
        public int getLoginTimeout() {
            return 0;
        }

        @Override
        public Logger getParentLogger() throws SQLFeatureNotSupportedException {
            throw new SQLFeatureNotSupportedException();
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            if (iface.isInstance(this)) return iface.cast(this);
            throw new SQLException("Not a wrapper for " + iface.getName());
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) {
            return iface.isInstance(this);
        }
    }
}