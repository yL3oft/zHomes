package me.yleoft.zHomes.storage;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import me.yleoft.zHomes.Main;
import me.yleoft.zHomes.utils.ConfigUtils;
import me.yleoft.zHomes.utils.LanguageUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DatabaseConnection extends ConfigUtils {

    private static Connection conn = null;

    public static String url = "jdbc:sqlite:" + Main.getInstance().getDataFolder().getPath() + "/database.db";

    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            if (databaseEnabled().booleanValue()) {
                conn = DriverManager.getConnection(mysqlUrl(), databaseUsername(), databasePassword());
            } else {
                conn = DriverManager.getConnection(url);
            }
        } catch (SQLException|ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String mysqlUrl() {
        return "jdbc:mysql://" + databaseHost() + ":" + databasePort() + "/" + databaseDatabase();
    }

    public void disconnect() {
        try {
            if (conn != null)
                return;
            if (conn.isClosed())
                return;
            conn.close();
            conn = null;
        } catch (SQLException|NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        try {
            if (conn != null && !conn.isClosed()) return conn;
            connect();
        } catch (Exception exception) {}
        return conn;
    }

    public void migrateData(@Nullable Player p, @NotNull String type) {new BukkitRunnable() {
        @Override
        public void run() {
        LanguageUtils.MainCMD.MainConverter lang = new LanguageUtils.MainCMD.MainConverter();
        Connection sqliteConn = null;
        Connection mysqlConn = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet resultSet = null;
        switch(type) {
            case "sqlitetomysql": {
                try {
                    disconnect();
                    sqliteConn = DriverManager.getConnection(url);
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
            case "mysqltosqlite": {
                try {
                    disconnect();
                    sqliteConn = DriverManager.getConnection(url);
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
                    if (databaseEnabled()) {
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
                } finally {
                    try {
                        if (dbConn != null) dbConn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
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
                    if (databaseEnabled()) {
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
                } finally {
                    try {
                        if (dbConn != null) dbConn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
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
                    if (databaseEnabled()) {
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
                } finally {
                    try {
                        if (dbConn != null) dbConn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
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
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM " + table + " WHERE " + columnName + "=?");
            ps.setString(1, value);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                results.close();
                ps.close();
                disconnect();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
        return false;
    }

    public boolean existsTableColumnValueDouble(String table, String columnName, String value, String columnName2, String value2) {
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM " + table + " WHERE " + columnName + "=? AND " + columnName2 + "=?");
            ps.setString(1, value);
            ps.setString(2, value2);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                results.close();
                ps.close();
                disconnect();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
        return false;
    }

}
