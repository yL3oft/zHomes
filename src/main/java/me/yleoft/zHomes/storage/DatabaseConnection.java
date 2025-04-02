package me.yleoft.zHomes.storage;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.yleoft.zHomes.Main;
import me.yleoft.zHomes.utils.ConfigUtils;
import me.yleoft.zHomes.utils.LanguageUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DatabaseConnection extends ConfigUtils {

    private static HikariDataSource dataSource = null;
    protected static database_type type = database_type.SQLITE;

    public void connect() {
        try {
            if (dataSource == null) {
                HikariConfig config = new HikariConfig();
                File jarFile;
                URL jarURL;
                URLClassLoader classLoader;
                Class<?> driverClass;
                Driver driverInstance;
                switch (databaseType().toLowerCase()) {
                    case "mariadb":
                        type = database_type.EXTERNAL;
                        jarFile = new File(Main.getInstance().libsFolder, Main.getInstance().mariadbJar);
                        jarURL = jarFile.toURI().toURL();
                        classLoader = new URLClassLoader(new URL[]{jarURL}, Main.class.getClassLoader());
                        driverClass = Class.forName("org.mariadb.jdbc.Driver", true, classLoader);
                        driverInstance = (Driver) driverClass.getDeclaredConstructor().newInstance();
                        DriverManager.registerDriver(new DriverShim(driverInstance));
                        config.setJdbcUrl(mariadbUrl());
                        config.setUsername(databaseUsername());
                        config.setPassword(databasePassword());
                        break;
                    case "mysql":
                        type = database_type.EXTERNAL;
                        jarFile = new File(Main.getInstance().libsFolder, Main.getInstance().mysqlJar);
                        jarURL = jarFile.toURI().toURL();
                        classLoader = new URLClassLoader(new URL[]{jarURL}, Main.class.getClassLoader());
                        driverClass = Class.forName("com.mysql.jdbc.Driver", true, classLoader);
                        driverInstance = (Driver) driverClass.getDeclaredConstructor().newInstance();
                        DriverManager.registerDriver(new DriverShim(driverInstance));
                        config.setJdbcUrl(mysqlUrl());
                        config.setUsername(databaseUsername());
                        config.setPassword(databasePassword());
                        break;
                    case "h2":
                        type = database_type.H2;
                        jarFile = new File(Main.getInstance().libsFolder, Main.getInstance().h2Jar);
                        jarURL = jarFile.toURI().toURL();
                        classLoader = new URLClassLoader(new URL[]{jarURL}, Main.class.getClassLoader());
                        driverClass = Class.forName("org.h2.Driver", true, classLoader);
                        driverInstance = (Driver) driverClass.getDeclaredConstructor().newInstance();
                        DriverManager.registerDriver(new DriverShim(driverInstance));
                        config.setJdbcUrl(h2Url());
                        break;
                    default:
                        type = database_type.SQLITE;
                        config.setJdbcUrl(sqliteUrl());
                        config.setDriverClassName("org.sqlite.JDBC");
                        break;
                }

                config.setMaximumPoolSize(databasePoolsize());
                config.setConnectionTimeout(30000);
                config.setIdleTimeout(600000);
                config.setLeakDetectionThreshold(2000);
                dataSource = new HikariDataSource(config);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error setting up HikariCP connection pool", e);
        }
    }

    public String mariadbUrl() {
        return "jdbc:mariadb://" + databaseHost() + ":" + databasePort() + "/" + databaseDatabase();
    }
    public String mysqlUrl() {
        return "jdbc:mysql://" + databaseHost() + ":" + databasePort() + "/" + databaseDatabase()+"?useSSL=false";
    }
    public String h2Url() {
        return "jdbc:h2:" + Main.getInstance().getDataFolder().getAbsolutePath() + "/database-h2;AUTO_SERVER=TRUE";
    }
    public String sqliteUrl() {
        File old = new File(Main.getInstance().getDataFolder(), "database.db");
        if(old.exists()) {
            return "jdbc:sqlite:" + Main.getInstance().getDataFolder().getAbsolutePath() + "/database.db";
        }
        return "jdbc:sqlite:" + Main.getInstance().getDataFolder().getAbsolutePath() + "/database-sqlite.db";
    }

    public void disconnect() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    public Connection getConnection() {
        try {
            if (dataSource != null) {
                return dataSource.getConnection();
            }
            disconnect();
            connect();
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closePool() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

    public void migrateData(@Nullable Player p, @NotNull String type) {new BukkitRunnable() {
        @Override
            public void run() {
            LanguageUtils.MainCMD.MainConverter lang = new LanguageUtils.MainCMD.MainConverter();
            Connection sqliteConn = null;
            Connection mysqlConn = null;
            Connection mariadbConn = null;
            PreparedStatement pstmt = null;
            Statement stmt = null;
            ResultSet resultSet = null;
            disconnect();
            switch(type) {
                case "sqlitetomysql": {
                    try {
                        sqliteConn = DriverManager.getConnection(sqliteUrl());
                        mysqlConn = DriverManager.getConnection(mysqlUrl(), databaseUsername(), databasePassword());
                        System.out.println("Connected to both SQLite and MySQL.");
                        stmt = sqliteConn.createStatement();
                        ResultSet countResult = stmt.executeQuery("SELECT COUNT(*) AS total FROM " + databaseTable());
                        countResult.next();
                        int totalRows = countResult.getInt("total");
                        countResult.close();
                        if (totalRows == 0) {
                            System.out.println("No data to migrate.");
                            return;
                        }
                        System.out.println("Starting migration of " + totalRows + " records...");
                        resultSet = stmt.executeQuery("SELECT UUID, HOME, LOCATION FROM " + databaseTable());
                        String insertQuery = "INSERT IGNORE INTO " + databaseTable() + " (UUID, HOME, LOCATION) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE LOCATION = VALUES(LOCATION)";
                        pstmt = mysqlConn.prepareStatement(insertQuery);
                        int count = 0;
                        while (resultSet.next()) {
                            String uuid = resultSet.getString("UUID");
                            String home = resultSet.getString("HOME");
                            String location = resultSet.getString("LOCATION");
                            pstmt.setString(1, uuid);
                            pstmt.setString(2, home);
                            pstmt.setString(3, location);
                            pstmt.executeUpdate();
                            count++;
                            if (p != null) {
                                String message = ChatColor.translateAlternateColorCodes('&', "&aConverting Data... &8[&7" + count + "/" + totalRows + "&8]");
                                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                            }
                        }
                        if (p != null) {
                            String message = ChatColor.translateAlternateColorCodes('&', "&aConverted Data! &8[&7" + count + "/" + totalRows + "&8]");
                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100.0F, 1.0F);
                        }
                        System.out.println("\nMigration completed! " + count + " records transferred.");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "sqlitetomariadb": {
                    try {
                        sqliteConn = DriverManager.getConnection(sqliteUrl());
                        mariadbConn = DriverManager.getConnection(mariadbUrl(), databaseUsername(), databasePassword());
                        System.out.println("Connected to both SQLite and MariaDB.");
                        stmt = sqliteConn.createStatement();
                        ResultSet countResult = stmt.executeQuery("SELECT COUNT(*) AS total FROM " + databaseTable());
                        countResult.next();
                        int totalRows = countResult.getInt("total");
                        countResult.close();
                        if (totalRows == 0) {
                            System.out.println("No data to migrate.");
                            return;
                        }
                        System.out.println("Starting migration of " + totalRows + " records...");
                        resultSet = stmt.executeQuery("SELECT UUID, HOME, LOCATION FROM " + databaseTable());
                        String insertQuery = "INSERT IGNORE INTO " + databaseTable() + " (UUID, HOME, LOCATION) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE LOCATION = VALUES(LOCATION)";
                        pstmt = mariadbConn.prepareStatement(insertQuery);
                        int count = 0;
                        while (resultSet.next()) {
                            String uuid = resultSet.getString("UUID");
                            String home = resultSet.getString("HOME");
                            String location = resultSet.getString("LOCATION");
                            pstmt.setString(1, uuid);
                            pstmt.setString(2, home);
                            pstmt.setString(3, location);
                            pstmt.executeUpdate();
                            count++;
                            if (p != null) {
                                String message = ChatColor.translateAlternateColorCodes('&', "&aConverting Data... &8[&7" + count + "/" + totalRows + "&8]");
                                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                            }
                        }
                        if (p != null) {
                            String message = ChatColor.translateAlternateColorCodes('&', "&aConverted Data! &8[&7" + count + "/" + totalRows + "&8]");
                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100.0F, 1.0F);
                        }
                        System.out.println("\nMigration completed! " + count + " records transferred.");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "mysqltosqlite": {
                    try {
                        sqliteConn = DriverManager.getConnection(sqliteUrl());
                        mysqlConn = DriverManager.getConnection(mysqlUrl(), databaseUsername(), databasePassword());
                        System.out.println("Connected to both SQLite and MySQL.");
                        stmt = mysqlConn.createStatement();
                        ResultSet countResult = stmt.executeQuery("SELECT COUNT(*) AS total FROM " + databaseTable());
                        countResult.next();
                        int totalRows = countResult.getInt("total");
                        countResult.close();
                        if (totalRows == 0) {
                            System.out.println("No data to migrate.");
                            return;
                        }
                        System.out.println("Starting migration of " + totalRows + " records...");
                        resultSet = stmt.executeQuery("SELECT UUID, HOME, LOCATION FROM " + databaseTable());
                        String insertQuery = "INSERT OR REPLACE INTO " + databaseTable() + " (UUID, HOME, LOCATION) VALUES (?, ?, ?)";
                        pstmt = sqliteConn.prepareStatement(insertQuery);
                        int count = 0;
                        while (resultSet.next()) {
                            String uuid = resultSet.getString("UUID");
                            String home = resultSet.getString("HOME");
                            String location = resultSet.getString("LOCATION");
                            pstmt.setString(1, uuid);
                            pstmt.setString(2, home);
                            pstmt.setString(3, location);
                            pstmt.executeUpdate();
                            count++;
                            if (p != null) {
                                String message = ChatColor.translateAlternateColorCodes('&', "&aConverting Data... &8[&7" + count + "/" + totalRows + "&8]");
                                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                            }
                        }
                        if (p != null) {
                            String message = ChatColor.translateAlternateColorCodes('&', "&aConverted Data! &8[&7" + count + "/" + totalRows + "&8]");
                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100.0F, 1.0F);
                        }
                        System.out.println("\nMigration completed! " + count + " records transferred.");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "mariadbtosqlite": {
                    try {
                        sqliteConn = DriverManager.getConnection(sqliteUrl());
                        mariadbConn = DriverManager.getConnection(mariadbUrl(), databaseUsername(), databasePassword());
                        System.out.println("Connected to both SQLite and MariaDB.");
                        stmt = mariadbConn.createStatement();
                        ResultSet countResult = stmt.executeQuery("SELECT COUNT(*) AS total FROM " + databaseTable());
                        countResult.next();
                        int totalRows = countResult.getInt("total");
                        countResult.close();
                        if (totalRows == 0) {
                            System.out.println("No data to migrate.");
                            return;
                        }
                        System.out.println("Starting migration of " + totalRows + " records...");
                        resultSet = stmt.executeQuery("SELECT UUID, HOME, LOCATION FROM " + databaseTable());
                        String insertQuery = "INSERT OR REPLACE INTO " + databaseTable() + " (UUID, HOME, LOCATION) VALUES (?, ?, ?)";
                        pstmt = sqliteConn.prepareStatement(insertQuery);
                        int count = 0;
                        while (resultSet.next()) {
                            String uuid = resultSet.getString("UUID");
                            String home = resultSet.getString("HOME");
                            String location = resultSet.getString("LOCATION");
                            pstmt.setString(1, uuid);
                            pstmt.setString(2, home);
                            pstmt.setString(3, location);
                            pstmt.executeUpdate();
                            count++;
                            if (p != null) {
                                String message = ChatColor.translateAlternateColorCodes('&', "&aConverting Data... &8[&7" + count + "/" + totalRows + "&8]");
                                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                            }
                        }
                        if (p != null) {
                            String message = ChatColor.translateAlternateColorCodes('&', "&aConverted Data! &8[&7" + count + "/" + totalRows + "&8]");
                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100.0F, 1.0F);
                        }
                        System.out.println("\nMigration completed! " + count + " records transferred.");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "essentials": {
                    File directory = new File(Main.getInstance().getDataFolder()+"/../Essentials/userdata");
                    if (!directory.exists() || !directory.isDirectory()) {
                        System.out.println("Invalid directory: " + directory.getPath());
                        return;
                    }

                    Connection dbConn = null;

                    try {
                        dbConn = getConnection();
                        String insertQuery;
                        if (!type.equalsIgnoreCase("sqlite")) {
                            insertQuery = "INSERT INTO "+databaseTable()+" (UUID, HOME, LOCATION) VALUES (?, LEFT(?, 100), ?) "
                                    + "ON DUPLICATE KEY UPDATE LOCATION = VALUES(LOCATION)";
                        } else {
                            insertQuery = "INSERT OR REPLACE INTO "+databaseTable()+" (UUID, HOME, LOCATION) VALUES (?, SUBSTR(?, 1, 100), ?)";
                        }

                        pstmt = dbConn.prepareStatement(insertQuery);
                        File[] files = directory.listFiles((dir, name) -> name.endsWith(".yml"));
                        if (files == null) lang.sendMsg(Main.getInstance().getServer().getConsoleSender(), lang.getError());

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
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "sethome": {
                    File directory = new File(Main.getInstance().getDataFolder()+"/../SetHome/homes");
                    if (!directory.exists() || !directory.isDirectory()) {
                        System.out.println("Invalid directory: " + directory.getPath());
                        return;
                    }

                    Connection dbConn = null;

                    try {
                        dbConn = getConnection();
                        String insertQuery;
                        if (!type.equalsIgnoreCase("sqlite")) {
                            insertQuery = "INSERT IGNORE INTO "+databaseTable()+" (UUID, HOME, LOCATION) VALUES (?, LEFT(?, 100), ?) "
                                    + "ON DUPLICATE KEY UPDATE LOCATION = VALUES(LOCATION)";
                        } else {
                            insertQuery = "INSERT OR REPLACE INTO "+databaseTable()+" (UUID, HOME, LOCATION) VALUES (?, SUBSTR(?, 1, 100), ?)";
                        }

                        pstmt = dbConn.prepareStatement(insertQuery);
                        File[] files = directory.listFiles((dir, name) -> name.endsWith(".yml"));
                        if (files == null) lang.sendMsg(Main.getInstance().getServer().getConsoleSender(), lang.getError());

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
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "ultimatehomes": {
                    File directory = new File(Main.getInstance().getDataFolder()+"/../UltimateHomes/playerdata");
                    if (!directory.exists() || !directory.isDirectory()) {
                        System.out.println("Invalid directory: " + directory.getPath());
                        return;
                    }

                    Connection dbConn = null;

                    try {
                        dbConn = getConnection();
                        String insertQuery;
                        if (!type.equalsIgnoreCase("sqlite")) {
                            insertQuery = "INSERT INTO "+databaseTable()+" (UUID, HOME, LOCATION) VALUES (?, LEFT(?, 100), ?) "
                                    + "ON DUPLICATE KEY UPDATE LOCATION = VALUES(LOCATION)";
                        } else {
                            insertQuery = "INSERT OR REPLACE INTO "+databaseTable()+" (UUID, HOME, LOCATION) VALUES (?, SUBSTR(?, 1, 100), ?)";
                        }

                        pstmt = dbConn.prepareStatement(insertQuery);
                        File[] files = directory.listFiles((dir, name) -> name.endsWith(".yml"));
                        if (files == null) lang.sendMsg(Main.getInstance().getServer().getConsoleSender(), lang.getError());

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
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                default: {
                    if (p != null) {
                        lang.sendMsg(p, lang.getUsage());
                        return;
                    }
                }
            }
            connect();
            try {
                if (resultSet != null)
                    resultSet.close();
                if (stmt != null)
                    stmt.close();
                if (pstmt != null)
                    pstmt.close();
                if (sqliteConn != null)
                    sqliteConn.close();
                if (mysqlConn != null)
                    mysqlConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }}.runTaskAsynchronously(Main.getInstance());}

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