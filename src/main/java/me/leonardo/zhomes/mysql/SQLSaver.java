package me.leonardo.zhomes.mysql;

import me.leonardo.zhomes.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLSaver {

    // PreparedStatement ps = Main.main.SQL.getConnection().prepareStatement();
    public void createServerTable() {
        Main.Getter.createTable("servers", "(" +
                "IP VARCHAR(100)," +
                "PORT VARCHAR(100)," +
                "VERSION VARCHAR(100)," +
                "PLVERSION VARCHAR(100)," +
                "STATUS VARCHAR(100)" +
                ")");
    }

    public void createServer(String ip, String port, String version, String plversion) {
        try {
            if(!existsServer(ip, port)) {
                PreparedStatement ps2 = Main.SQL.getConnection().prepareStatement("INSERT IGNORE INTO servers"
                        + " (IP,PORT,VERSION,PLVERSION) VALUES (?,?,?,?)");
                ps2.setString(1, ip);
                ps2.setString(2, port);
                ps2.setString(3, version);
                ps2.setString(4, plversion);
                ps2.executeUpdate();

                return;
            }
        }catch (SQLException e) {
        }
    }

    public void setStatus(String ip, String port, String status) {
        try {
            if(existsServer(ip, port)) {
                PreparedStatement ps2 = Main.main.SQL.getConnection().prepareStatement("UPDATE servers SET STATUS=? WHERE IP=? AND PORT=?");
                ps2.setString(1, status);
                ps2.setString(2, ip);
                ps2.setString(3, port);
                ps2.executeUpdate();

                return;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setVersion(String ip, String port, String ver) {
        try {
            if(existsServer(ip, port)) {
                PreparedStatement ps2 = Main.main.SQL.getConnection().prepareStatement("UPDATE servers SET PLVERSION=? WHERE IP=? AND PORT=?");
                ps2.setString(1, ver);
                ps2.setString(2, ip);
                ps2.setString(3, port);
                ps2.executeUpdate();

                return;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existsServer(String ip, String port) {
        try {
            if(Main.Getter.existsTableColumnValue("servers", "IP", "PORT", ip, port)) return true;
        }catch (Exception e) {
        }
        return false;
    }

}
