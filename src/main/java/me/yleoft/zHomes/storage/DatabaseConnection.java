package me.yleoft.zHomes.storage;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.yleoft.zHomes.Main;
import me.yleoft.zHomes.utils.ConfigUtils;
import me.yleoft.zHomes.utils.LanguageUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DatabaseConnection extends ConfigUtils {

    boolean retry = false;

    public void connect() {
        try {
            if (Main.dataSource == null || Main.dataSource.isClosed()) {
                long start = System.currentTimeMillis();
                HikariConfig config = new HikariConfig();
                switch (databaseType().toLowerCase()) {
                    case "mariadb":
                        if (Main.mariadbDriver == null) {
                            File mariadbjarFile = new File(Main.getInstance().libsFolder, Main.getInstance().mariadbJar);
                            URL mariadbjarURL = mariadbjarFile.toURI().toURL();
                            URLClassLoader mariadbclassLoader = new URLClassLoader(new URL[]{mariadbjarURL}, Main.class.getClassLoader());
                            Class<?> mariadbdriverClass = Class.forName("org.mariadb.jdbc.Driver", true, mariadbclassLoader);
                            Main.mariadbDriver = (Driver) mariadbdriverClass.getDeclaredConstructor().newInstance();
                            DriverManager.registerDriver(new DriverShim(Main.mariadbDriver));
                        }
                        Main.type = database_type.EXTERNAL;
                        config.setJdbcUrl(mariadbUrl());
                        config.setUsername(databaseUsername());
                        config.setPassword(databasePassword());
                        break;
                    case "mysql":
                        if (Main.mysqlDriver == null) {
                            try {
                                if (DriverManager.getDriver("jdbc:mysql://") != null) {
                                    DriverManager.deregisterDriver(DriverManager.getDriver("jdbc:mysql://"));
                                }
                            }catch (Exception ignored) {}
                            File mysqljarFile = new File(Main.getInstance().libsFolder, Main.getInstance().mysqlJar);
                            URL mysqljarURL = mysqljarFile.toURI().toURL();
                            URLClassLoader mysqlclassLoader = new URLClassLoader(new URL[]{mysqljarURL}, Main.class.getClassLoader());
                            Class<?> mysqldriverClass = Class.forName("com.mysql.cj.jdbc.Driver", true, mysqlclassLoader);
                            Main.mysqlDriver = (Driver) mysqldriverClass.getDeclaredConstructor().newInstance();
                            DriverManager.registerDriver(new DriverShim(Main.mysqlDriver));
                        }
                        Main.type = database_type.EXTERNAL;
                        config.setJdbcUrl(mysqlUrl());
                        config.setUsername(databaseUsername());
                        config.setPassword(databasePassword());
                        break;
                    case "h2":
                        if (Main.h2Driver == null) {
                            File h2jarFile = new File(Main.getInstance().libsFolder, Main.getInstance().h2Jar);
                            URL h2jarURL = h2jarFile.toURI().toURL();
                            URLClassLoader h2classLoader = new URLClassLoader(new URL[]{h2jarURL}, Main.class.getClassLoader());
                            Class<?> h2driverClass = Class.forName("org.h2.Driver", true, h2classLoader);
                            Main.h2Driver = (Driver) h2driverClass.getDeclaredConstructor().newInstance();
                            DriverManager.registerDriver(new DriverShim(Main.h2Driver));
                        }
                        Main.type = database_type.H2;
                        config.setJdbcUrl(h2Url());
                        break;
                    default:
                        Main.type = database_type.SQLITE;
                        config.setJdbcUrl(sqliteUrl());
                        break;
                }

                config.setMaximumPoolSize(databasePoolsize());
                Main.dataSource = new HikariDataSource(config);
                long end = System.currentTimeMillis();
                Main.getInstance().getLogger().info("HikariCP startup took " + (end - start) + "ms");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error setting up HikariCP connection pool", e);
        }
    }

    public String mariadbUrl() {
        return "jdbc:mariadb://" + databaseHost() + ":" + databasePort() + "/" + databaseDatabase()+"?allowPublicKeyRetrieval="+databaseAllowPublicKeyRetrieval()+"&useSSL="+databaseUseSSL();
    }
    public String mysqlUrl() {
        return "jdbc:mysql://" + databaseHost() + ":" + databasePort() + "/" + databaseDatabase()+"?allowPublicKeyRetrieval="+databaseAllowPublicKeyRetrieval()+"&useSSL="+databaseUseSSL();
    }
    public String h2Url() {
        return "jdbc:h2:" + Main.getInstance().getDataFolder().getAbsolutePath() + "/database-h2";
    }
    public String sqliteUrl() {
        File old = new File(Main.getInstance().getDataFolder(), "database.db");
        if(old.exists()) {
            old.renameTo(new File(Main.getInstance().getDataFolder(), "database-sqlite.db"));
        }
        return "jdbc:sqlite:" + Main.getInstance().getDataFolder().getAbsolutePath() + "/database-sqlite.db";
    }

    public void disconnect() {
        HikariDataSource dataSource = Main.dataSource;
        if (dataSource != null) {
            closePool();
        }
    }

    public Connection getConnection() {
        HikariDataSource dataSource = Main.dataSource;
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

    public void closePool() {
        HikariDataSource dataSource = Main.dataSource;
        if (dataSource != null) {
            dataSource.close();
        }
    }

    public void migrateData(@Nullable Player p, @NotNull String type) {new BukkitRunnable() {
        final database_type dbType = Main.type;
        @Override
            public void run() {
            LanguageUtils.MainCMD.MainConverter lang = new LanguageUtils.MainCMD.MainConverter();
            switch(type) {
                case "sqlitetoh2": {
                    migrateLocalDatabase(p, sqliteUrl(), h2Url(),
                            "MERGE INTO " + databaseTable() + " (UUID, HOME, LOCATION) " +
                                    "KEY(UUID, HOME) VALUES (?, LEFT(?, 100), ?)"
                    );
                    break;
                }
                case "sqlitetomysql": {
                    migrateDatabase(p, sqliteUrl(), mysqlUrl(), "INSERT IGNORE INTO " + databaseTable() + " (UUID, HOME, LOCATION) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE LOCATION = VALUES(LOCATION)");
                    break;
                }
                case "sqlitetomariadb": {
                    migrateDatabase(p, sqliteUrl(), mariadbUrl(), "INSERT IGNORE INTO " + databaseTable() + " (UUID, HOME, LOCATION) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE LOCATION = VALUES(LOCATION)");
                    break;
                }
                case "mysqltosqlite": {
                    migrateDatabase2(p, mysqlUrl(), sqliteUrl(), "INSERT OR REPLACE INTO " + databaseTable() + " (UUID, HOME, LOCATION) VALUES (?, ?, ?)");
                    break;
                }
                case "mysqltoh2": {
                    migrateDatabase2(p, mysqlUrl(), h2Url(),
                            "MERGE INTO " + databaseTable() + " (UUID, HOME, LOCATION) " +
                                    "KEY(UUID, HOME) VALUES (?, LEFT(?, 100), ?)"
                    );
                    break;
                }
                case "mariadbtosqlite": {
                    migrateDatabase2(p, mariadbUrl(), sqliteUrl(), "INSERT OR REPLACE INTO " + databaseTable() + " (UUID, HOME, LOCATION) VALUES (?, ?, ?)");
                    break;
                }
                case "mariadbtoh2": {
                    migrateDatabase2(p, mariadbUrl(), h2Url(),
                            "MERGE INTO " + databaseTable() + " (UUID, HOME, LOCATION) " +
                                    "KEY(UUID, HOME) VALUES (?, LEFT(?, 100), ?)"
                    );
                    break;
                }
                case "h2tosqlite": {
                    migrateLocalDatabase(p, h2Url(), sqliteUrl(), "INSERT OR REPLACE INTO " + databaseTable() + " (UUID, HOME, LOCATION) VALUES (?, ?, ?)");
                    break;
                }
                case "h2tomysql": {
                    migrateDatabase(p, h2Url(), mysqlUrl(), "INSERT INTO " + databaseTable() + " (UUID, HOME, LOCATION) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE LOCATION = VALUES(LOCATION)");
                    break;
                }
                case "h2tomariadb": {
                    migrateDatabase(p, h2Url(), mariadbUrl(), "INSERT INTO " + databaseTable() + " (UUID, HOME, LOCATION) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE LOCATION = VALUES(LOCATION)");
                    break;
                }
                case "essentials": {
                    File directory = new File(Main.getInstance().getDataFolder()+"/../Essentials/userdata");
                    if (!directory.exists() || !directory.isDirectory()) {
                        System.out.println("Invalid directory: " + directory.getPath());
                        return;
                    }

                    try (Connection conn = getConnection()) {
                        String insertQuery;
                        if (dbType.equals(database_type.H2)) {
                            insertQuery = "MERGE INTO " + databaseTable() + " (UUID, HOME, LOCATION) " +
                                    "KEY(UUID, HOME) VALUES (?, LEFT(?, 100), ?)";
                        } else if (dbType.equals(database_type.SQLITE)) {
                            insertQuery = "INSERT OR REPLACE INTO "+databaseTable()+" (UUID, HOME, LOCATION) VALUES (?, SUBSTR(?, 1, 100), ?)";
                        } else {
                            insertQuery = "INSERT INTO "+databaseTable()+" (UUID, HOME, LOCATION) VALUES (?, LEFT(?, 100), ?) "
                                    + "ON DUPLICATE KEY UPDATE LOCATION = VALUES(LOCATION)";
                        }
                        try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                            File[] files = directory.listFiles((dir, name) -> name.endsWith(".yml"));
                            if (files == null)
                                lang.sendMsg(Main.getInstance().getServer().getConsoleSender(), lang.getError());

                            assert files != null;
                            int totalUsers = files.length;
                            System.out.println("Starting migration for " + totalUsers + " users...");

                            int count = 0;
                            int countH = 0;
                            for (File file : files) {
                                YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
                                String uuid = file.getName().replace(".yml", "");

                                if (!yaml.contains("homes")) {
                                    count++;
                                    if (p != null) {
                                        String message = ChatColor.translateAlternateColorCodes('&', "&aConverting Data... &8[&7" + count + " users/" + totalUsers + " users&8]");
                                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                                    }
                                    continue;
                                }

                                ConfigurationSection homesSection = yaml.getConfigurationSection("homes");
                                assert homesSection != null;
                                for (String homeName : homesSection.getKeys(false)) {
                                    ConfigurationSection home = homesSection.getConfigurationSection(homeName);

                                    if (home == null) continue;

                                    String worldName = home.getString("world-name", "");
                                    double x = home.getDouble("x");
                                    double y = home.getDouble("y");
                                    double z = home.getDouble("z");
                                    float yaw = (float) home.getDouble("yaw");
                                    float pitch = (float) home.getDouble("pitch");
                                    String location = Main.getInstance().serialize(worldName, x, y, z, yaw, pitch);

                                    pstmt.setString(1, uuid);
                                    pstmt.setString(2, homeName);
                                    pstmt.setString(3, location);
                                    pstmt.executeUpdate();
                                    countH++;
                                }
                                count++;
                                if (p != null) {
                                    String message = ChatColor.translateAlternateColorCodes('&', "&aConverting Data... &8[&7" + count + " users/" + totalUsers + " users&8]");
                                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                                }
                            }
                            if (p != null) {
                                String message = ChatColor.translateAlternateColorCodes('&', "&aConverted Data! &8[&7" + count + " users/" + totalUsers + " users&8]");
                                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100.0F, 1.0F);
                            }
                            System.out.println("Migration completed! " + count + " users and " + countH + " homes transferred from Essentials.");
                        }
                    } catch (SQLException e) {
                        Main.getInstance().getLogger().log(Level.SEVERE, "Unable to migrate data from Essentials", e);
                    }
                    break;
                }
                case "sethome": {
                    File directory = new File(Main.getInstance().getDataFolder()+"/../SetHome/homes");
                    if (!directory.exists() || !directory.isDirectory()) {
                        System.out.println("Invalid directory: " + directory.getPath());
                        return;
                    }

                    try (Connection conn = getConnection()) {
                        String insertQuery;
                        if (dbType.equals(database_type.H2)) {
                            insertQuery = "MERGE INTO " + databaseTable() + " (UUID, HOME, LOCATION) " +
                                    "KEY(UUID, HOME) VALUES (?, LEFT(?, 100), ?)";
                        } else if (dbType.equals(database_type.SQLITE)) {
                            insertQuery = "INSERT OR REPLACE INTO "+databaseTable()+" (UUID, HOME, LOCATION) VALUES (?, SUBSTR(?, 1, 100), ?)";
                        } else {
                            insertQuery = "INSERT INTO "+databaseTable()+" (UUID, HOME, LOCATION) VALUES (?, LEFT(?, 100), ?) "
                                    + "ON DUPLICATE KEY UPDATE LOCATION = VALUES(LOCATION)";
                        }
                        try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                            File[] files = directory.listFiles((dir, name) -> name.endsWith(".yml"));
                            if (files == null)
                                lang.sendMsg(Main.getInstance().getServer().getConsoleSender(), lang.getError());

                            assert files != null;
                            int totalUsers = files.length;
                            System.out.println("Starting migration for " + totalUsers + " users...");

                            int count = 0;
                            int countH = 0;
                            for (File file : files) {
                                YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
                                String uuid = file.getName().replace(".yml", "");

                                if (!yaml.contains("Homes")) {
                                    count++;
                                    if (p != null) {
                                        String message = ChatColor.translateAlternateColorCodes('&', "&aConverting Data... &8[&7" + count + " users/" + totalUsers + " users&8]");
                                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                                    }
                                    continue;
                                }

                                ConfigurationSection homesSection = yaml.getConfigurationSection("Homes");
                                assert homesSection != null;
                                for (String homeName : homesSection.getKeys(false)) {
                                    ConfigurationSection home = homesSection.getConfigurationSection(homeName);

                                    if (home == null) continue;

                                    String worldName = home.getString("world", "");
                                    double x = home.getDouble("x");
                                    double y = home.getDouble("y");
                                    double z = home.getDouble("z");
                                    float yaw = (float) home.getDouble("yaw");
                                    float pitch = (float) home.getDouble("pitch");
                                    String location = Main.getInstance().serialize(worldName, x, y, z, yaw, pitch);

                                    pstmt.setString(1, uuid);
                                    pstmt.setString(2, homeName);
                                    pstmt.setString(3, location);
                                    pstmt.executeUpdate();
                                    countH++;
                                }
                                count++;
                                if (p != null) {
                                    String message = ChatColor.translateAlternateColorCodes('&', "&aConverting Data... &8[&7" + count + " users/" + totalUsers + " users&8]");
                                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                                }
                            }
                            if (p != null) {
                                String message = ChatColor.translateAlternateColorCodes('&', "&aConverted Data! &8[&7" + count + " users/" + totalUsers + " users&8]");
                                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100.0F, 1.0F);
                            }
                            System.out.println("Migration completed! " + count + " users and " + countH + " homes transferred from SetHome.");
                        }
                    } catch (SQLException e) {
                        Main.getInstance().getLogger().log(Level.SEVERE, "Unable to migrate data from SetHome", e);
                    }
                    break;
                }
                case "ultimatehomes": {
                    File directory = new File(Main.getInstance().getDataFolder()+"/../UltimateHomes/playerdata");
                    if (!directory.exists() || !directory.isDirectory()) {
                        System.out.println("Invalid directory: " + directory.getPath());
                        return;
                    }

                    try (Connection conn = getConnection()) {
                        String insertQuery;
                        if (dbType.equals(database_type.H2)) {
                            insertQuery = "MERGE INTO " + databaseTable() + " (UUID, HOME, LOCATION) " +
                                    "KEY(UUID, HOME) VALUES (?, LEFT(?, 100), ?)";
                        } else if (dbType.equals(database_type.SQLITE)) {
                            insertQuery = "INSERT OR REPLACE INTO "+databaseTable()+" (UUID, HOME, LOCATION) VALUES (?, SUBSTR(?, 1, 100), ?)";
                        } else {
                            insertQuery = "INSERT INTO "+databaseTable()+" (UUID, HOME, LOCATION) VALUES (?, LEFT(?, 100), ?) "
                                    + "ON DUPLICATE KEY UPDATE LOCATION = VALUES(LOCATION)";
                        }
                        try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                            File[] files = directory.listFiles((dir, name) -> name.endsWith(".yml"));
                            if (files == null)
                                lang.sendMsg(Main.getInstance().getServer().getConsoleSender(), lang.getError());

                            assert files != null;
                            int totalUsers = files.length;
                            System.out.println("Starting migration for " + totalUsers + " users...");

                            int count = 0;
                            int countH = 0;
                            for (File file : files) {
                                YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
                                String uuid = file.getName().replace(".yml", "");

                                if (!yaml.contains("homes")) {
                                    count++;
                                    if (p != null) {
                                        String message = ChatColor.translateAlternateColorCodes('&', "&aConverting Data... &8[&7" + count + " users/" + totalUsers + " users&8]");
                                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                                    }
                                    continue;
                                }

                                ConfigurationSection homesSection = yaml.getConfigurationSection("homes");
                                assert homesSection != null;
                                for (String homeName : homesSection.getKeys(false)) {
                                    ConfigurationSection home = homesSection.getConfigurationSection(homeName);

                                    if (home == null) continue;

                                    String worldName = home.getString("world", "");
                                    double x = home.getDouble("x");
                                    double y = home.getDouble("y");
                                    double z = home.getDouble("z");
                                    float yaw = (float) home.getDouble("yaw");
                                    float pitch = (float) home.getDouble("pitch");
                                    String location = Main.getInstance().serialize(worldName, x, y, z, yaw, pitch);

                                    pstmt.setString(1, uuid);
                                    pstmt.setString(2, homeName);
                                    pstmt.setString(3, location);
                                    pstmt.executeUpdate();
                                    countH++;
                                }
                                count++;
                                if (p != null) {
                                    String message = ChatColor.translateAlternateColorCodes('&', "&aConverting Data... &8[&7" + count + " users/" + totalUsers + " users&8]");
                                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                                }
                            }
                            if (p != null) {
                                String message = ChatColor.translateAlternateColorCodes('&', "&aConverted Data! &8[&7" + count + " users/" + totalUsers + " users&8]");
                                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100.0F, 1.0F);
                            }
                            System.out.println("Migration completed! " + count + " users and " + countH + " homes transferred from UltimateHomes.");
                        }
                    } catch (SQLException e) {
                        Main.getInstance().getLogger().log(Level.SEVERE, "Unable to migrate data from UltimateHomes", e);
                    }
                    break;
                }
                case "xhomes": {
                    File file = new File(Main.getInstance().getDataFolder()+"/../Xhomes/playerhomes.yml");
                    if (!file.exists()) {
                        System.out.println("Invalid file: " + file.getPath());
                        return;
                    }

                    try (Connection conn = getConnection()) {
                        String insertQuery;
                        if (dbType.equals(database_type.H2)) {
                            insertQuery = "MERGE INTO " + databaseTable() + " (UUID, HOME, LOCATION) " +
                                    "KEY(UUID, HOME) VALUES (?, LEFT(?, 100), ?)";
                        } else if (dbType.equals(database_type.SQLITE)) {
                            insertQuery = "INSERT OR REPLACE INTO "+databaseTable()+" (UUID, HOME, LOCATION) VALUES (?, SUBSTR(?, 1, 100), ?)";
                        } else {
                            insertQuery = "INSERT INTO "+databaseTable()+" (UUID, HOME, LOCATION) VALUES (?, LEFT(?, 100), ?) "
                                    + "ON DUPLICATE KEY UPDATE LOCATION = VALUES(LOCATION)";
                        }
                        try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                            YamlConfiguration fYaml = YamlConfiguration.loadConfiguration(file);
                            int totalUsers = fYaml.getKeys(false).size();
                            System.out.println("Starting migration for " + totalUsers + " users...");

                            int count = 0;
                            int countH = 0;
                            for (String player : fYaml.getKeys(false)) {
                                YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
                                OfflinePlayer offplayer = Bukkit.getOfflinePlayer(player);
                                if (offplayer == null) {
                                    count++;
                                    if (p != null) {
                                        String message = ChatColor.translateAlternateColorCodes('&', "&aConvertion failed, skipping user... &8[&7" + count + " users/" + totalUsers + " users&8]");
                                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                                    }
                                    continue;
                                }
                                String uuid = offplayer.getUniqueId().toString();

                                ConfigurationSection homesSection = yaml.getConfigurationSection(player);
                                assert homesSection != null;
                                for (String homeName : homesSection.getKeys(false)) {
                                    String[] homeS = Objects.requireNonNull(homesSection.getString(homeName)).split(",");
                                    String worldName = homeS[0];
                                    double x = Double.parseDouble(homeS[1]);
                                    double y = Double.parseDouble(homeS[2]);
                                    double z = Double.parseDouble(homeS[3]);
                                    float yaw = Float.parseFloat(homeS[4]);
                                    float pitch = Float.parseFloat(homeS[5]);
                                    String location = Main.getInstance().serialize(worldName, x, y, z, yaw, pitch);

                                    pstmt.setString(1, uuid);
                                    pstmt.setString(2, homeName);
                                    pstmt.setString(3, location);
                                    pstmt.executeUpdate();
                                    countH++;
                                }
                                count++;
                                if (p != null) {
                                    String message = ChatColor.translateAlternateColorCodes('&', "&aConverting Data... &8[&7" + count + " users/" + totalUsers + " users&8]");
                                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                                }
                            }
                            if (p != null) {
                                String message = ChatColor.translateAlternateColorCodes('&', "&aConverted Data! &8[&7" + count + " users/" + totalUsers + " users&8]");
                                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100.0F, 1.0F);
                            }
                            System.out.println("Migration completed! " + count + " users and " + countH + " homes transferred from XHomes.");
                        }
                    } catch (SQLException e) {
                        Main.getInstance().getLogger().log(Level.SEVERE, "Unable to migrate data from XHomes", e);
                    }
                    break;
                }
                default: {
                    if (p != null) {
                        lang.sendMsg(p, lang.getUsage());
                    }
                }
            }
    }}.runTaskAsynchronously(Main.getInstance());}

    private void migrateDatabase(Player p, String sourceUrl, String targetUrl, String insertQuery) {
        disconnect();
        try (Connection sourceConn = DriverManager.getConnection(sourceUrl);
             Connection targetConn = DriverManager.getConnection(targetUrl, databaseUsername(), databasePassword())) {
            System.out.println("Connected to both source and target databases.");
            try (Statement stmt = sourceConn.createStatement();
                 ResultSet countResult = stmt.executeQuery("SELECT COUNT(*) AS total FROM " + databaseTable())) {
                countResult.next();
                int totalRows = countResult.getInt("total");
                if (totalRows == 0) {
                    System.out.println("No data to migrate.");
                    return;
                }
                System.out.println("Starting migration of " + totalRows + " records...");
                try (ResultSet resultSet = stmt.executeQuery("SELECT UUID, HOME, LOCATION FROM " + databaseTable())) {
                    try (PreparedStatement pstmt = targetConn.prepareStatement(insertQuery)) {
                        int count = 0;
                        while (resultSet.next()) {
                            pstmt.setString(1, resultSet.getString("UUID"));
                            pstmt.setString(2, resultSet.getString("HOME"));
                            pstmt.setString(3, resultSet.getString("LOCATION"));
                            pstmt.executeUpdate();
                            count++;
                            updateProgress(p, count, totalRows);
                        }
                        completeMigration(p, count, totalRows);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connect();
    }
    private void migrateDatabase2(Player p, String sourceUrl, String targetUrl, String insertQuery) {
        disconnect();
        try (Connection sourceConn = DriverManager.getConnection(sourceUrl, databaseUsername(), databasePassword());
             Connection targetConn = DriverManager.getConnection(targetUrl)) {
            System.out.println("Connected to both source and target databases.");
            try (Statement stmt = sourceConn.createStatement();
                 ResultSet countResult = stmt.executeQuery("SELECT COUNT(*) AS total FROM " + databaseTable())) {
                countResult.next();
                int totalRows = countResult.getInt("total");
                if (totalRows == 0) {
                    System.out.println("No data to migrate.");
                    return;
                }
                System.out.println("Starting migration of " + totalRows + " records...");
                try (ResultSet resultSet = stmt.executeQuery("SELECT UUID, HOME, LOCATION FROM " + databaseTable())) {
                    try (PreparedStatement pstmt = targetConn.prepareStatement(insertQuery)) {
                        int count = 0;
                        while (resultSet.next()) {
                            pstmt.setString(1, resultSet.getString("UUID"));
                            pstmt.setString(2, resultSet.getString("HOME"));
                            pstmt.setString(3, resultSet.getString("LOCATION"));
                            pstmt.executeUpdate();
                            count++;
                            updateProgress(p, count, totalRows);
                        }
                        completeMigration(p, count, totalRows);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connect();
    }
    private void migrateLocalDatabase(Player p, String sourceUrl, String targetUrl, String insertQuery) {
        disconnect();
        try (Connection sourceConn = DriverManager.getConnection(sourceUrl);
             Connection targetConn = DriverManager.getConnection(targetUrl)) {
            System.out.println("Connected to both source and target databases.");
            try (Statement stmt = sourceConn.createStatement();
                 ResultSet countResult = stmt.executeQuery("SELECT COUNT(*) AS total FROM " + databaseTable())) {
                countResult.next();
                int totalRows = countResult.getInt("total");
                if (totalRows == 0) {
                    System.out.println("No data to migrate.");
                    return;
                }
                System.out.println("Starting migration of " + totalRows + " records...");
                try (ResultSet resultSet = stmt.executeQuery("SELECT UUID, HOME, LOCATION FROM " + databaseTable())) {
                    try (PreparedStatement pstmt = targetConn.prepareStatement(insertQuery)) {
                        int count = 0;
                        while (resultSet.next()) {
                            pstmt.setString(1, resultSet.getString("UUID"));
                            pstmt.setString(2, resultSet.getString("HOME"));
                            pstmt.setString(3, resultSet.getString("LOCATION"));
                            pstmt.executeUpdate();
                            count++;
                            updateProgress(p, count, totalRows);
                        }
                        completeMigration(p, count, totalRows);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connect();
    }
    private void updateProgress(Player p, int count, int totalRows) {
        if (p != null) {
            String message = ChatColor.translateAlternateColorCodes('&', "&aConverting Data... &8[&7" + count + "/" + totalRows + "&8]");
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
        }
    }
    private void completeMigration(Player p, int count, int totalRows) {
        if (p != null) {
            String message = ChatColor.translateAlternateColorCodes('&', "&aConverted Data! &8[&7" + count + "/" + totalRows + "&8]");
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100.0F, 1.0F);
        }
        System.out.println("\nMigration completed! " + count + " records transferred.");
    }

    public boolean existsTableColumnValue(String table, String columnName, String value) {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM " + table + " WHERE " + columnName + "=?")) {
            ps.setString(1, value);
            try (ResultSet results = ps.executeQuery()) {
                if (results.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existsTableColumnValueDouble(String table, String columnName, String value, String columnName2, String value2) {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM " + table + " WHERE " + columnName + "=? AND " + columnName2 + "=?")) {
            ps.setString(1, value);
            ps.setString(2, value2);
            try (ResultSet results = ps.executeQuery()) {
                if (results.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static class DriverShim implements Driver {
        private final Driver driver;

        public DriverShim(Driver driver) {
            this.driver = driver;
        }

        @Override
        public boolean acceptsURL(String u) throws SQLException {
            return driver.acceptsURL(u);
        }

        @Override
        public Connection connect(String u, Properties p) throws SQLException {
            return driver.connect(u, p);
        }

        @Override
        public int getMajorVersion() {
            return driver.getMajorVersion();
        }

        @Override
        public int getMinorVersion() {
            return driver.getMinorVersion();
        }

        @Override
        public DriverPropertyInfo[] getPropertyInfo(String u, Properties p) throws SQLException {
            return driver.getPropertyInfo(u, p);
        }

        @Override
        public boolean jdbcCompliant() {
            return driver.jdbcCompliant();
        }

        @Override
        public Logger getParentLogger() throws SQLFeatureNotSupportedException {
            return driver.getParentLogger();
        }
    }

}