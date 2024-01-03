package me.leonardo.zhomes.mysql;

import me.leonardo.zhomes.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SQLGetter {

    // PreparedStatement ps = Main.main.SQL.getConnection().prepareStatement();

    public void createTable(String table, String coluns) {
        PreparedStatement ps;
        try {
            ps = Main.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS "
                    + table
                    + coluns
            );
            ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearDatabase() {
        PreparedStatement ps;
        try {
            ps = Main.main.SQL.getConnection().prepareStatement("DROP TABLE arenas");
            ps.executeUpdate();

            ps = Main.main.SQL.getConnection().prepareStatement("DROP TABLE spawns");
            ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existsTableColumnValue(String table, String columnName, String value) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("SELECT * FROM "
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
        return false;
    }
    public boolean existsTableColumnValue(String table, String columnName, String columnName2, String value, String value2) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("SELECT * FROM "
                    + table
                    + " WHERE "
                    + columnName
                    + "=?"
                    + "AND "
                    + columnName2
                    + "=?");

            ps.setString(1, value);
            ps.setString(2, value2);
            ResultSet results = ps.executeQuery();
            if(results.next()) {
                return true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existsTableColumnValue(String table, String columnName, int value) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("SELECT * FROM "
                    + table
                    + " WHERE "
                    + columnName
                    + "=?");

            ps.setInt(1, value);
            ResultSet results = ps.executeQuery();
            if(results.next()) {
                return true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existsTableColumnValue(String table, String columnName, boolean value) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("SELECT * FROM "
                    + table
                    + " WHERE "
                    + columnName
                    + "=?");

            ps.setBoolean(1, value);
            ResultSet results = ps.executeQuery();
            if(results.next()) {
                return true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setDefaultValue(String table, String columnName, Object value) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("ALTER TABLE "
                    + table
                    + " ALTER COLUMN "
                    + columnName
                    + " SET DEFAULT "
                    + value);

            ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
