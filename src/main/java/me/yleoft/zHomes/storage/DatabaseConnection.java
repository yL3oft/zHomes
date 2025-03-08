package me.yleoft.zHomes.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import me.yleoft.zHomes.Main;
import me.yleoft.zHomes.utils.ConfigUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
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

    public void migrateData(@Nullable Player p) {
        Connection sqliteConn = null;
        Connection mysqlConn = null;
        PreparedStatement mysqlStmt = null;
        Statement sqliteStmt = null;
        ResultSet resultSet = null;
        try {
            disconnect();
            sqliteConn = DriverManager.getConnection(url);
            mysqlConn = DriverManager.getConnection(mysqlUrl(), databaseUsername(), databasePassword());
            System.out.println("Connected to both SQLite and MySQL.");
            sqliteStmt = sqliteConn.createStatement();
            ResultSet countResult = sqliteStmt.executeQuery("SELECT COUNT(*) AS total FROM " + databaseTable());
            countResult.next();
            int totalRows = countResult.getInt("total");
            countResult.close();
            if (totalRows == 0) {
                System.out.println("No data to migrate.");
                return;
            }
            System.out.println("Starting migration of " + totalRows + " records...");
            resultSet = sqliteStmt.executeQuery("SELECT UUID, HOME, LOCATION FROM " + databaseTable());
            String insertQuery = "INSERT IGNORE INTO " + databaseTable() + " (UUID, HOME, LOCATION) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE LOCATION = VALUES(LOCATION)";
            mysqlStmt = mysqlConn.prepareStatement(insertQuery);
            int count = 0;
            while (resultSet.next()) {
                String uuid = resultSet.getString("UUID");
                String home = resultSet.getString("HOME");
                String location = resultSet.getString("LOCATION");
                mysqlStmt.setString(1, uuid);
                mysqlStmt.setString(2, home);
                mysqlStmt.setString(3, location);
                mysqlStmt.executeUpdate();
                count++;
                if (p != null) {
                    String message = ChatColor.translateAlternateColorCodes('&', "&aMigrating Data... &8[&7" + count + "/" + totalRows + "&8]");
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                }
            }
            if (p != null) {
                String message = ChatColor.translateAlternateColorCodes('&', "&aMigration Complete! &8[&7" + count + "/" + totalRows + "&8]");
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100.0F, 1.0F);
            }
            System.out.println("\nMigration completed! " + count + " records transferred.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (sqliteStmt != null)
                    sqliteStmt.close();
                if (mysqlStmt != null)
                    mysqlStmt.close();
                if (sqliteConn != null)
                    sqliteConn.close();
                if (mysqlConn != null)
                    mysqlConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

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
