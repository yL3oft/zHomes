package me.yleoft.zHomes.storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zaxxer.hikari.HikariDataSource;
import me.yleoft.zHomes.Main;
import org.bukkit.OfflinePlayer;

public class DatabaseEditor extends DatabaseConnection {

    public void createTable(String table, String coluns) {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS " + table + coluns)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void renameTable(String oldtable, String newtable) {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("ALTER TABLE " + oldtable + " RENAME TO " + newtable)) {
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public void setHome(OfflinePlayer p, String home, String location) {
        database_type type = Main.type;
        try (Connection con = getConnection()) {
            if (isInTable(p, home)) {
                try (PreparedStatement ps = con.prepareStatement("UPDATE " + databaseTable() + " SET LOCATION=? WHERE UUID=? AND HOME=?")) {
                    String uuid = p.getUniqueId().toString();
                    ps.setString(1, location);
                    ps.setString(2, uuid);
                    ps.setString(3, home);
                    ps.executeUpdate();
                }
            } else {
                String query = "INSERT OR IGNORE INTO " + databaseTable() + " (UUID,HOME,LOCATION) VALUES (?,?,?)";
                switch (type) {
                    case SQLITE:
                        query = "INSERT OR IGNORE INTO " + databaseTable() + " (UUID,HOME,LOCATION) VALUES (?,?,?)";
                        break;
                    case H2:
                        query = "MERGE INTO " + databaseTable() + " KEY(UUID, HOME) VALUES (?, ?, ?)";
                        break;
                    case EXTERNAL:
                        query = "INSERT IGNORE INTO " + databaseTable() + " (UUID,HOME,LOCATION) VALUES (?,?,?)";
                        break;
                }
                try (PreparedStatement ps = con.prepareStatement(query)) {
                    String uuid = p.getUniqueId().toString();
                    ps.setString(1, uuid);
                    ps.setString(2, home);
                    ps.setString(3, location);
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteHome(OfflinePlayer p, String home) {
        try (Connection con = getConnection();
            PreparedStatement ps2 = con.prepareStatement("DELETE FROM " + databaseTable() + " WHERE UUID=? AND HOME=?")) {
            String uuid = p.getUniqueId().toString();
            ps2.setString(1, uuid);
            ps2.setString(2, home);
            ps2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getHome(OfflinePlayer p, String home) {
        try (Connection con = getConnection()) {
            if (isInTable(p, home)) {
                try (PreparedStatement ps = con.prepareStatement("SELECT LOCATION from " + databaseTable() + " WHERE UUID=? AND HOME=?")) {
                    String uuid = p.getUniqueId().toString();
                    ps.setString(1, uuid);
                    ps.setString(2, home);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            String returned = rs.getString("LOCATION");
                            rs.close();
                            ps.close();
                            return returned;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public List<String> getHomes(OfflinePlayer p) {
        List<String> list = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM " + databaseTable() + " WHERE UUID=?")) {
            ps.setString(1, p.getUniqueId().toString());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getString("HOME"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
