package me.leonardo.zhomes.mysql;

import org.bukkit.OfflinePlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseEditor extends DatabaseConnection {

    public String settingStr = "_settings";

    public void createTable(String table, String coluns) {
        Connection con = getConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS "
                    + table
                    + coluns
            );
            ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
    }

    public void setHome(OfflinePlayer p, String home, String location) {
        Connection con = getConnection();
        try {
            if(hasHomeSQL(p, home)) {
                PreparedStatement ps2 = con.prepareStatement("UPDATE "+databaseTable()+" SET HOME=?,LOCATION=? WHERE NAME=?");
                String nm = p.getName();
                if(isSaveAsTypeUuid()) {
                    ps2 = con.prepareStatement("UPDATE "+databaseTable()+" SET HOME=?,LOCATION=? WHERE UUID=?");
                    nm = p.getUniqueId().toString();
                }
                ps2.setString(1, home);
                ps2.setString(2, location);
                ps2.setString(3, nm);
                ps2.executeUpdate();

                return;
            }else {
                PreparedStatement ps2 = con.prepareStatement("INSERT IGNORE INTO "+databaseTable()
                        + " (NAME,HOME,LOCATION) VALUES (?,?,?)");
                String nm = p.getName();
                if(isSaveAsTypeUuid()) {
                    ps2 = con.prepareStatement("INSERT IGNORE INTO "+databaseTable()
                            + " (UUID,HOME,LOCATION) VALUES (?,?,?)");
                    nm = p.getUniqueId().toString();
                }
                ps2.setString(1, nm);
                ps2.setString(2, home);
                ps2.setString(3, location);
                ps2.executeUpdate();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
    }
    public void delHome(OfflinePlayer p, String home) {
        Connection con = getConnection();
        try {
            PreparedStatement ps2 = con.prepareStatement("DELETE FROM "+databaseTable()+" WHERE NAME=?,HOME=?");
            String nm = p.getName();
            if(isSaveAsTypeUuid()) {
                ps2 = con.prepareStatement("DELETE FROM "+databaseTable()+" WHERE NAME=?,HOME=?");
                nm = p.getUniqueId().toString();
            }
            ps2.setString(1, nm);
            ps2.setString(2, home);
            ps2.executeUpdate();

            return;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
    }
    public String getHomeLocation(OfflinePlayer p, String home) {
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT LOCATION from "+databaseTable()+" WHERE NAME=?,HOME=?");
            String nm = p.getName();
            if(isSaveAsTypeUuid()) {
                ps = con.prepareStatement("SELECT LOCATION from "+databaseTable()+" WHERE UUID=?,HOME=?");
                nm = p.getUniqueId().toString();
            }
            ps.setString(1, nm);
            ps.setString(2, home);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return rs.getString("LOCATION");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
        return "";
    }

    public List<String> getHomes(OfflinePlayer p) {
        List<String> list = new ArrayList<>();
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * from "+databaseTable()+" WHERE NAME=?");
            String nm = p.getName();
            if(isSaveAsTypeUuid()) {
                ps = con.prepareStatement("SELECT * from "+databaseTable()+" WHERE UUID=?");
                nm = p.getUniqueId().toString();
            }
            ps.setString(1, nm);
            ResultSet rs = ps.executeQuery();

            while ( rs.next()) {
                list.add(rs.getString("HOME"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
        return list;
    }

    public boolean hasHomeSQL(OfflinePlayer player, String home) {
        try {
            if(isSaveAsTypeUuid()) {
                String nm = player.getUniqueId().toString();
                if(existsTableColumnValueDouble(databaseTable(), "UUID", nm, "HOME", home)) return true;
            }
            String nm = player.getName();
            if(existsTableColumnValueDouble(databaseTable(), "NAME", nm, "HOME", home)) return true;
        }catch (Exception e) {
        }
        return false;
    }

}
