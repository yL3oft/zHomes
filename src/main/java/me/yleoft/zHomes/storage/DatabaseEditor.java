package me.yleoft.zHomes.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.yleoft.zAPI.location.LocationHandler;
import me.yleoft.zAPI.player.PlayerHandler;
import me.yleoft.zAPI.utility.TextFormatter;
import me.yleoft.zHomes.configuration.languages.LanguageBuilder;
import me.yleoft.zHomes.zHomes;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class DatabaseEditor extends DatabaseConnection {

    public static void createTable(String table, String coluns) {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS " + table + coluns)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            zHomes.getInstance().getLoggerInstance().error("Error creating table: " + table, e);
        }
    }
    
    public static String databaseTable() {
        return zHomes.getConfigYAML().getDatabaseTable();
    }

    public static void setHome(OfflinePlayer p, String home, String location) {
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
            zHomes.getInstance().getLoggerInstance().error("Error setting home for player: " + p.getName(), e);
        }
    }

    public static void deleteHome(OfflinePlayer p, String home) {
        try (Connection con = getConnection();
            PreparedStatement ps2 = con.prepareStatement("DELETE FROM " + databaseTable() + " WHERE UUID=? AND LOWER(HOME)=LOWER(?)")) {
            String uuid = p.getUniqueId().toString();
            ps2.setString(1, uuid);
            ps2.setString(2, home);
            ps2.executeUpdate();
        } catch (SQLException e) {
            zHomes.getInstance().getLoggerInstance().error("Error deleting home for player: " + p.getName(), e);
        }
    }

    public static String getHome(OfflinePlayer p, String home) {
        try (Connection con = getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT LOCATION from " + databaseTable() + " WHERE UUID=? AND LOWER(HOME)=LOWER(?)")) {
                String uuid = p.getUniqueId().toString();
                ps.setString(1, uuid);
                ps.setString(2, home);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("LOCATION");
                    }
                }
            }
        } catch (SQLException e) {
            zHomes.getInstance().getLoggerInstance().error("Error getting homes for player: " + p.getName(), e);
        }
        return "";
    }

    public static List<String> getHomes(OfflinePlayer p) {
        List<String> list = new ArrayList<>();
        if(p == null) return list;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM " + databaseTable() + " WHERE UUID=?")) {
            ps.setString(1, p.getUniqueId().toString());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getString("HOME"));
                }
            }
        } catch (SQLException e) {
            zHomes.getInstance().getLoggerInstance().error("Error getting homes for player: " + p.getName(), e);
        }
        return list;
    }

    public static HashMap<OfflinePlayer, List<String>> getNearHomes(Location loc, double radius) {
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
                Location homeLoc = LocationHandler.deserialize(locStr);
                if (!Objects.equals(homeLoc.getWorld(), world)) continue;
                if (homeLoc.distanceSquared(loc) <= radiusSq) {
                    OfflinePlayer owner = PlayerHandler.getOfflinePlayer(UUID.fromString(uuid));
                    result.computeIfAbsent(owner, k -> new ArrayList<>()).add(home);
                }
            }
        } catch (SQLException e) {
            zHomes.getInstance().getLoggerInstance().error("Error getting near homes", e);
        } catch (Exception ignored) {}
        return result;
    }

    public static long getTotalHomes() {
        long count = 0;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) AS total FROM " + databaseTable());
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getLong("total");
            }
        } catch (SQLException e) {
            zHomes.getInstance().getLoggerInstance().error("Error getting total homes", e);
        }
        return count;
    }

    public static long getTotalUsers() {
        long count = 0;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT COUNT(DISTINCT UUID) AS total FROM " + databaseTable());
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getLong("total");
            }
        } catch (SQLException e) {
            zHomes.getInstance().getLoggerInstance().error("Error getting total users", e);
        }
        return count;
    }

    public static boolean isInTable(OfflinePlayer p, String home) {
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

    public static File exportDatabase(CommandSender sender) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");
        String filename = "zhomes-" + dtf.format(LocalDateTime.now()) + ".json.gz";
        File outFile = new File(zHomes.getInstance().getDataFolder(), filename);

        List<ExportEntry> entries = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT UUID, HOME, LOCATION FROM " + databaseTable());
             ResultSet rs = ps.executeQuery()) {

            int count = 0;
            while (rs.next()) {
                entries.add(new ExportEntry(rs.getString("UUID"), rs.getString("HOME"), rs.getString("LOCATION")));
                count++;
                if (sender != null && (count % 50 == 0)) {
                    // periodic progress update to player every 50 rows
                    String message = "Exporting data... [" + count + " records]";
                    LanguageBuilder.sendActionBar(sender, message);
                }
            }

            // Write JSON.gz
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (GZIPOutputStream gos = new GZIPOutputStream(Files.newOutputStream(outFile.toPath()));
                 Writer writer = new OutputStreamWriter(gos, StandardCharsets.UTF_8)) {
                gson.toJson(entries, writer);
                writer.flush();
            }

            if (sender != null) {
                String message = "Export complete! " + entries.size() + " records exported.";
                LanguageBuilder.sendActionBar(sender, message);
                try {
                    if(sender instanceof Player player) player.playSound(player.getLocation(), Objects.requireNonNull(getEntityLevelUP()), 100.0F, 1.0F);
                }catch (Exception ignored) {}
            }
            zHomes.getInstance().getLogger().info("Exported " + entries.size() + " records to " + outFile.getAbsolutePath());
            return outFile;
        } catch (Exception e) {
            zHomes.getInstance().getLoggerInstance().error("Error exporting database to " + outFile.getAbsolutePath(), e);
            if (sender != null) {
                LanguageBuilder.sendMessage(sender, "<red>Failed to export database. Check console for details.");
            }
            return null;
        }
    }

    public static Sound getEntityLevelUP() {
        try {
            return Sound.ENTITY_PLAYER_LEVELUP;
        } catch (Throwable ignored) {}
        return null;
    }

    public static int importDatabase(File gzJsonFile, CommandSender sender) {
        if (gzJsonFile == null || !gzJsonFile.exists()) {
            zHomes.getInstance().getLogger().severe("Import file does not exist: " + (gzJsonFile == null ? "null" : gzJsonFile.getAbsolutePath()));
            return -1;
        }

        List<ExportEntry> entries;
        try (GZIPInputStream gis = new GZIPInputStream(Files.newInputStream(gzJsonFile.toPath()));
             Reader reader = new InputStreamReader(gis, StandardCharsets.UTF_8)) {
            Gson gson = new Gson();
            entries = gson.fromJson(reader, new TypeToken<List<ExportEntry>>() {}.getType());
            if (entries == null) entries = Collections.emptyList();
        } catch (Exception e) {
            zHomes.getInstance().getLoggerInstance().error("Error reading import file: " + gzJsonFile.getAbsolutePath(), e);
            if (sender != null) LanguageBuilder.sendMessage(sender, "<red>Failed to import database. Check console for details.");
            return -1;
        }

        if (entries.isEmpty()) {
            zHomes.getInstance().getLogger().info("Import file contained no entries: " + gzJsonFile.getAbsolutePath());
            if (sender != null) LanguageBuilder.sendMessage(sender, "<red>Import file contained no entries.");
            return 0;
        }

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
                    updateImportProgressToPlayer(sender, imported, entries.size());
                }
            }
            if (batchCount > 0) {
                pstmt.executeBatch();
                con.commit();
                imported += batchCount;
            }
            con.setAutoCommit(true);

            if (sender != null) {
                String message = "Import complete! " + imported + " records imported.";
                LanguageBuilder.sendActionBar(sender, message);
                try {
                    if(sender instanceof Player player) player.playSound(player.getLocation(), Objects.requireNonNull(getEntityLevelUP()), 100.0F, 1.0F);
                }catch (Exception ignored) {}
            }
            zHomes.getInstance().getLogger().info("Imported " + imported + " records from " + gzJsonFile.getAbsolutePath());
            return imported;
        } catch (SQLException e) {
            zHomes.getInstance().getLoggerInstance().error("Error importing database from " + gzJsonFile.getAbsolutePath(), e);
            if (sender != null) LanguageBuilder.sendMessage(sender, "<red>Failed to import database. Check console for details.");
            return -1;
        }
    }

    private static void updateImportProgressToPlayer(CommandSender sender, int count, int total) {
        if (sender != null) {
            String message = "§aImporting data... §8[§7" + count + " / " + total + "§8]";
            LanguageBuilder.sendActionBar(sender, message);
        }
    }

}
