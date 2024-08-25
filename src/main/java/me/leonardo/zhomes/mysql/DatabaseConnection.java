package me.leonardo.zhomes.mysql;

import me.leonardo.zhomes.Main;
import me.leonardo.zhomes.utils.ConfigUtils;

import java.sql.*;

public class DatabaseConnection extends ConfigUtils {

    private static Connection conn = null;
    public static String url = "jdbc:sqlite:"+ Main.main.getDataFolder().getPath()+"/database.db";

    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            if(!isLocal()) {
                url = "jdbc:mysql://"+databaseHost()+":"+databasePort()+"/"+databaseDatabase();
                conn = DriverManager.getConnection(url, databaseUsername(), databasePassword());
            }else {
                conn = DriverManager.getConnection(url);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }   

    public void disconnect() {
        try {
            if(conn != null) return;
            if(conn.isClosed()) return;
            conn.close();
            conn = null;
        } catch (SQLException | NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        if(conn != null) return conn;
        try {
            connect();
        }catch (Exception e) {
        }
        return conn;
    }

    public boolean existsTableColumnValue(String table, String columnName, String value) {
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM "
                    + table
                    + " WHERE "
                    + columnName
                    + "=?");

            ps.setString(1, value);
            ResultSet results = ps.executeQuery();
            if(results.next()) {
                return true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
        return false;
    }
    public boolean existsTableColumnValueDouble(String table, String columnName, String value, String columnName2, String value2) {
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM "
                    + table
                    + " WHERE "
                    + columnName
                    + "=?,"
                    + columnName2
                    + "=?");

            ps.setString(  1, value);
            ps.setString(  2, value2);
            ResultSet results = ps.executeQuery();
            if(results.next()) {
                return true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
        return false;
    }
    public boolean existsTableColumnValue(String table, String columnName, int value) {
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM "
                    + table
                    + " WHERE "
                    + columnName
                    + "=?");

            ps.setInt(  1, value);
            ResultSet results = ps.executeQuery();
            if(results.next()) {
                return true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
        return false;
    }
    public boolean existsTableColumnValueDouble(String table, String columnName, int value, String columnName2, int value2) {
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM "
                    + table
                    + " WHERE "
                    + columnName
                    + "=?,"
                    + columnName2
                    + "=?");

            ps.setInt(  1, value);
            ps.setInt(  2, value2);
            ResultSet results = ps.executeQuery();
            if(results.next()) {
                return true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
        return false;
    }

}
