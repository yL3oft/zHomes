package me.yleoft.zHomes.storage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.yleoft.zAPI.utils.ActionbarUtils;
import me.yleoft.zAPI.utils.LocationUtils;
import me.yleoft.zAPI.utils.PlayerUtils;
import me.yleoft.zHomes.Main;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class DatabaseEditor extends DatabaseConnection {

    public void createTable(String table, String coluns) {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS " + table + coluns)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Error creating table: " + table, e);
        }
    }

    public void setHome(OfflinePlayer p, String home, String location) {
        database_type type = Main.type;
        try (Connection con = getConnection()) {
            if (isInTable(p, home)) {
                try (PreparedStatement ps = con.prepareStatement("UPDATE " + databaseTable() + " SET LOCATION=? WHERE UUID=? AND LOWER(HOME)=LOWER(?)")) {
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
            Main.getInstance().getLogger().log(Level.SEVERE, "Error setting home for player: " + p.getName(), e);
        }
    }

    public void deleteHome(OfflinePlayer p, String home) {
        try (Connection con = getConnection();
            PreparedStatement ps2 = con.prepareStatement("DELETE FROM " + databaseTable() + " WHERE UUID=? AND LOWER(HOME)=LOWER(?)")) {
            String uuid = p.getUniqueId().toString();
            ps2.setString(1, uuid);
            ps2.setString(2, home);
            ps2.executeUpdate();
        } catch (SQLException e) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Error deleting home for player: " + p.getName(), e);
        }
    }

    public String getHome(OfflinePlayer p, String home) {
        try (Connection con = getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT LOCATION from " + databaseTable() + " WHERE UUID=? AND LOWER(HOME)=LOWER(?)")) {
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
        } catch (SQLException e) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Error getting homes for player: " + p.getName(), e);
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
            Main.getInstance().getLogger().log(Level.SEVERE, "Error getting homes for player: " + p.getName(), e);
        }
        return list;
    }

    public HashMap<OfflinePlayer, List<String>> getNearHomes(Location loc, double radius) {
        HashMap<OfflinePlayer, List<String>> result = new HashMap<>();
        if (loc == null || loc.getWorld() == null) return result;
        double radiusSq = radius * radius;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT UUID, HOME, LOCATION FROM " + databaseTable());
             ResultSet rs = ps.executeQuery()) {
            World world = loc.getWorld();
            while (rs.next()) {
                String uuid = rs.getString("UUID");
                String home = rs.getString("HOME");
                String locStr = rs.getString("LOCATION");
                Location homeLoc = LocationUtils.deserialize(locStr);
                if (!Objects.equals(homeLoc.getWorld(), world)) continue;
                if (homeLoc.distanceSquared(loc) <= radiusSq) {
                    OfflinePlayer owner = PlayerUtils.getOfflinePlayer(UUID.fromString(uuid));
                    result.computeIfAbsent(owner, k -> new ArrayList<>()).add(home);
                }
            }
        } catch (SQLException e) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Error getting near homes", e);
        } catch (Exception ignored) {}
        return result;
    }

    public long getTotalHomes() {
        long count = 0;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) AS total FROM " + databaseTable());
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getLong("total");
            }
        } catch (SQLException e) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Error getting total homes", e);
        }
        return count;
    }

    public long getTotalUsers() {
        long count = 0;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT COUNT(DISTINCT UUID) AS total FROM " + databaseTable());
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getLong("total");
            }
        } catch (SQLException e) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Error getting total users", e);
        }
        return count;
    }

    public boolean isInTable(OfflinePlayer p, String home) {
        try {
            String uuid = p.getUniqueId().toString();
            if (existsTableColumnValueDoubleLower(databaseTable(), "UUID", uuid, "HOME", home))
                return true;
        } catch (Exception ignored) {}
        return false;
    }

    private static class ExportEntry {
        String uuid;
        String home;
        String location;

        ExportEntry() {}

        ExportEntry(String uuid, String home, String location) {
            this.uuid = uuid;
            this.home = home;
            this.location = location;
        }
    }

    public File exportDatabase(Player p) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");
        String filename = "zhomes-" + dtf.format(LocalDateTime.now()) + ".json.gz";
        File outFile = new File(Main.getInstance().getDataFolder(), filename);

        List<ExportEntry> entries = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT UUID, HOME, LOCATION FROM " + databaseTable());
             ResultSet rs = ps.executeQuery()) {

            int count = 0;
            while (rs.next()) {
                entries.add(new ExportEntry(rs.getString("UUID"), rs.getString("HOME"), rs.getString("LOCATION")));
                count++;
                if (p != null && (count % 50 == 0)) {
                    // periodic progress update to player every 50 rows
                    String message = "Exporting data... [" + count + " records]";
                    ActionbarUtils.send(p, message);
                }
            }

            // Write JSON.gz
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (GZIPOutputStream gos = new GZIPOutputStream(Files.newOutputStream(outFile.toPath()));
                 Writer writer = new OutputStreamWriter(gos, StandardCharsets.UTF_8)) {
                gson.toJson(entries, writer);
                writer.flush();
            }

            if (p != null) {
                String message = "Export complete! " + entries.size() + " records exported.";
                ActionbarUtils.send(p, message);
                try {
                    p.playSound(p.getLocation(), org.bukkit.Sound.ENTITY_PLAYER_LEVELUP, 100.0F, 1.0F);
                }catch (Exception ignored) {}
            }
            Main.getInstance().getLogger().info("Exported " + entries.size() + " records to " + outFile.getAbsolutePath());
            return outFile;
        } catch (Exception e) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Error exporting database to " + outFile.getAbsolutePath(), e);
            if (p != null) {
                p.sendMessage("§cFailed to export database: " + e.getMessage());
            }
            return null;
        }
    }

    public int importDatabase(File gzJsonFile, Player p) {
        if (gzJsonFile == null || !gzJsonFile.exists()) {
            Main.getInstance().getLogger().severe("Import file does not exist: " + (gzJsonFile == null ? "null" : gzJsonFile.getAbsolutePath()));
            return -1;
        }

        List<ExportEntry> entries;
        try (GZIPInputStream gis = new GZIPInputStream(Files.newInputStream(gzJsonFile.toPath()));
             Reader reader = new InputStreamReader(gis, StandardCharsets.UTF_8)) {
            Gson gson = new Gson();
            entries = gson.fromJson(reader, new TypeToken<List<ExportEntry>>() {}.getType());
            if (entries == null) entries = Collections.emptyList();
        } catch (Exception e) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Error reading import file: " + gzJsonFile.getAbsolutePath(), e);
            if (p != null) p.sendMessage("§cFailed to read import file: " + e.getMessage());
            return -1;
        }

        if (entries.isEmpty()) {
            Main.getInstance().getLogger().info("Import file contained no entries: " + gzJsonFile.getAbsolutePath());
            if (p != null) p.sendMessage("§eImport file contained no entries.");
            return 0;
        }

        database_type type = Main.type;
        String insertQuery;
        switch (type) {
            case H2:
                insertQuery = "MERGE INTO " + databaseTable() + " KEY(UUID, HOME) VALUES (?, ?, ?)";
                break;
            case SQLITE:
                insertQuery = "INSERT OR REPLACE INTO " + databaseTable() + " (UUID, HOME, LOCATION) VALUES (?, ?, ?)";
                break;
            case EXTERNAL:
            default:
                // MySQL/MariaDB
                insertQuery = "INSERT INTO " + databaseTable() + " (UUID, HOME, LOCATION) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE LOCATION = VALUES(LOCATION)";
                break;
        }

        int imported = 0;
        final int BATCH_SIZE = 500;
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(insertQuery)) {
            con.setAutoCommit(false);
            int batchCount = 0;
            for (int i = 0; i < entries.size(); i++) {
                ExportEntry e = entries.get(i);
                pstmt.setString(1, e.uuid);
                pstmt.setString(2, e.home);
                pstmt.setString(3, e.location);
                pstmt.addBatch();
                batchCount++;

                if (batchCount >= BATCH_SIZE) {
                    pstmt.executeBatch();
                    con.commit();
                    pstmt.clearBatch();
                    imported += batchCount;
                    batchCount = 0;
                    updateImportProgressToPlayer(p, imported, entries.size());
                }
            }
            if (batchCount > 0) {
                pstmt.executeBatch();
                con.commit();
                imported += batchCount;
            }
            con.setAutoCommit(true);

            if (p != null) {
                String message = "Import complete! " + imported + " records imported.";
                ActionbarUtils.send(p, message);
                try {
                    p.playSound(p.getLocation(), org.bukkit.Sound.ENTITY_PLAYER_LEVELUP, 100.0F, 1.0F);
                }catch (Exception ignored) {}
            }
            Main.getInstance().getLogger().info("Imported " + imported + " records from " + gzJsonFile.getAbsolutePath());
            return imported;
        } catch (SQLException e) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Error importing database from " + gzJsonFile.getAbsolutePath(), e);
            if (p != null) p.sendMessage("§cFailed to import database: " + e.getMessage());
            return -1;
        }
    }

    private void updateImportProgressToPlayer(Player p, int count, int total) {
        if (p != null) {
            String message = "§aImporting data... §8[§7" + count + " / " + total + "§8]";
            ActionbarUtils.send(p, message);
        }
    }

}
