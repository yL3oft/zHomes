package me.yleoft.zHomes.storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.OfflinePlayer;

public class DatabaseEditor extends DatabaseConnection {

    public void createTable(String table, String coluns) {
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS " + table + coluns);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
    }

    public void setHome(OfflinePlayer p, String home, String location) {
        Connection con = getConnection();
        try {
            if (isInTable(p, home)) {
                PreparedStatement ps2 = con.prepareStatement("UPDATE " + databaseTable() + " SET HOME=?,LOCATION=? WHERE UUID=?");
                String uuid = p.getUniqueId().toString();
                ps2.setString(1, home);
                ps2.setString(2, location);
                ps2.setString(3, uuid);
                ps2.executeUpdate();
                ps2.close();
            } else {
                PreparedStatement ps2 = con.prepareStatement("INSERT OR IGNORE INTO " + databaseTable() + " (UUID,HOME,LOCATION) VALUES (?,?,?)");
                if (databaseEnabled().booleanValue())
                    ps2 = con.prepareStatement("INSERT IGNORE INTO " + databaseTable() + " (UUID,HOME,LOCATION) VALUES (?,?,?)");
                String uuid = p.getUniqueId().toString();
                ps2.setString(1, uuid);
                ps2.setString(2, home);
                ps2.setString(3, location);
                ps2.executeUpdate();
                ps2.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
    }

    public void deleteHome(OfflinePlayer p, String home) {
        Connection con = getConnection();
        try {
            PreparedStatement ps2 = con.prepareStatement("DELETE FROM " + databaseTable() + " WHERE UUID=? AND HOME=?");
            String uuid = p.getUniqueId().toString();
            ps2.setString(1, uuid);
            ps2.setString(2, home);
            ps2.executeUpdate();
            ps2.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
    }

    public String getHome(OfflinePlayer p, String home) {
        Connection con = getConnection();
        try {
            if (isInTable(p, home)) {
                PreparedStatement ps = con.prepareStatement("SELECT LOCATION from " + databaseTable() + " WHERE UUID=? AND HOME=?");
                String uuid = p.getUniqueId().toString();
                ps.setString(1, uuid);
                ps.setString(2, home);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String returned = rs.getString("LOCATION");
                    rs.close();
                    ps.close();
                    disconnect();
                    return returned;
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
        return "";
    }

    public List<String> getHomes(OfflinePlayer p) {
        List<String> list = new ArrayList<>();
        Connection con = getConnection();
        try {
            if (isInTable(p)) {
                PreparedStatement ps = con.prepareStatement("SELECT * from " + databaseTable() + " WHERE UUID=?");
                String uuid = p.getUniqueId().toString();
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                    list.add(rs.getString("HOME"));
                rs.close();
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
        return list;
    }

    public boolean isInTable(OfflinePlayer p) {
        try {
            String uuid = p.getUniqueId().toString();
            if (existsTableColumnValue(databaseTable(), "UUID", uuid))
                return true;
        } catch (Exception exception) {}
        return false;
    }

    public boolean isInTable(OfflinePlayer p, String home) {
        try {
            String uuid = p.getUniqueId().toString();
            if (existsTableColumnValueDouble(databaseTable(), "UUID", uuid, "HOME", home))
                return true;
        } catch (Exception exception) {}
        return false;
    }

}
