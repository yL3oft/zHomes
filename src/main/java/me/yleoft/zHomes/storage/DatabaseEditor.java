package me.yleoft.zHomes.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.yleoft.zAPI.location.LocationHandler;
import me.yleoft.zAPI.player.PlayerHandler;
import me.yleoft.zAPI.zAPI;
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

    public static void addNameColumn() {
        boolean justCreated = false;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "ALTER TABLE " + databaseTable() + " ADD COLUMN NAME VARCHAR(45)")) {
            ps.executeUpdate();
            justCreated = true;
        } catch (SQLException e) {
            // Column already exists — safe to ignore on all three DB types
        }

        if (!justCreated) return;

        zAPI.getScheduler().runAsync(task -> {
            zHomes.getInstance().getLoggerInstance().info("NAME column added to database, backfilling player names...");
            List<String> uuids = new ArrayList<>();
            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT DISTINCT UUID FROM " + databaseTable());
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) uuids.add(rs.getString("UUID"));
            } catch (SQLException e) {
                zHomes.getInstance().getLoggerInstance().error("Error fetching UUIDs for name backfill", e);
                return;
            }

            int success = 0, failed = 0;
            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(
                         "UPDATE " + databaseTable() + " SET NAME=? WHERE UUID=?")) {
                con.setAutoCommit(false);
                for (String uuidStr : uuids) {
                    try {
                        OfflinePlayer p = PlayerHandler.getOfflinePlayer(UUID.fromString(uuidStr));
                        if (p.getName() != null) {
                            ps.setString(1, p.getName());
                            ps.setString(2, uuidStr);
                            ps.addBatch();
                            success++;
                        } else {
                            failed++;
                        }
                    } catch (Exception e) {
                        failed++;
                    }
                }
                ps.executeBatch();
                con.commit();
                con.setAutoCommit(true);
            } catch (SQLException e) {
                zHomes.getInstance().getLoggerInstance().error("Error during name backfill batch", e);
                return;
            }
            zHomes.getInstance().getLoggerInstance().info("Database Name backfill complete: " + success + " resolved, " + failed + " unresolved (will populate on next players login).");
        });
    }

    public static String databaseTable() {
        return zHomes.getConfigYAML().getDatabaseTable();
    }

    /**
     * Builds the upsert SQL for the current database type.
     * All four columns (UUID, NAME, HOME, LOCATION) are included.
     */
    private static String upsertSql() {
        String table = databaseTable();
        return switch (type) {
            case SQLITE -> "INSERT INTO " + table + " (UUID,NAME,HOME,LOCATION) VALUES (?,?,?,?) " +
                    "ON CONFLICT(UUID,HOME) DO UPDATE SET LOCATION=excluded.LOCATION, NAME=excluded.NAME";
            case H2 -> "MERGE INTO " + table + " (UUID,NAME,HOME,LOCATION) KEY(UUID,HOME) VALUES (?,?,?,?)";
            default -> "INSERT INTO " + table + " (UUID,NAME,HOME,LOCATION) VALUES (?,?,?,?) " +
                    "ON DUPLICATE KEY UPDATE LOCATION=VALUES(LOCATION), NAME=VALUES(NAME)";
        };
    }

    //<editor-fold desc="CRUD operations">
    /**
     * Inserts or updates a home for the given player using an atomic upsert.
     *
     * @return {@code true} if the row was written successfully, {@code false} on error.
     */
    public static boolean setHome(OfflinePlayer p, String home, String location) {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(upsertSql())) {
            ps.setString(1, p.getUniqueId().toString());
            ps.setString(2, p.getName());
            ps.setString(3, home);
            ps.setString(4, location);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            zHomes.getInstance().getLoggerInstance().error("Error setting home for player: " + p.getName(), e);
            return false;
        }
    }

    /**
     * Deletes a specific home for the given player.
     *
     * @return {@code true} if at least one row was deleted, {@code false} on error or not found.
     */
    public static boolean deleteHome(OfflinePlayer p, String home) {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "DELETE FROM " + databaseTable() + " WHERE UUID=? AND LOWER(HOME)=LOWER(?)")) {
            ps.setString(1, p.getUniqueId().toString());
            ps.setString(2, home);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            zHomes.getInstance().getLoggerInstance().error("Error deleting home for player: " + p.getName(), e);
            return false;
        }
    }

    /**
     * Renames a home atomically within a single transaction.
     * If any step fails the transaction is rolled back, preserving the original home.
     *
     * @return {@code true} on success, {@code false} if the home was not found or a DB error occurred.
     */
    public static boolean renameHome(OfflinePlayer p, String home, String newName) {
        String locS = getHome(p, home);
        if (locS == null || locS.isEmpty()) return false;
        try (Connection con = getConnection()) {
            con.setAutoCommit(false);
            try {
                try (PreparedStatement del = con.prepareStatement(
                        "DELETE FROM " + databaseTable() + " WHERE UUID=? AND LOWER(HOME)=LOWER(?)")) {
                    del.setString(1, p.getUniqueId().toString());
                    del.setString(2, home);
                    del.executeUpdate();
                }
                try (PreparedStatement ins = con.prepareStatement(upsertSql())) {
                    ins.setString(1, p.getUniqueId().toString());
                    ins.setString(2, p.getName());
                    ins.setString(3, newName);
                    ins.setString(4, locS);
                    ins.executeUpdate();
                }
                con.commit();
                return true;
            } catch (SQLException e) {
                try { con.rollback(); } catch (SQLException ignored) {}
                zHomes.getInstance().getLoggerInstance().error("Error renaming home for player: " + p.getName(), e);
                return false;
            } finally {
                try { con.setAutoCommit(true); } catch (SQLException ignored) {}
            }
        } catch (SQLException e) {
            zHomes.getInstance().getLoggerInstance().error("Error renaming home for player: " + p.getName(), e);
            return false;
        }
    }

    public static String getHome(OfflinePlayer p, String home) {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT LOCATION FROM " + databaseTable() + " WHERE UUID=? AND LOWER(HOME)=LOWER(?)")) {
            ps.setString(1, p.getUniqueId().toString());
            ps.setString(2, home);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("LOCATION");
            }
        } catch (SQLException e) {
            zHomes.getInstance().getLoggerInstance().error("Error getting home for player: " + p.getName(), e);
        }
        return "";
    }

    public static List<String> getHomes(OfflinePlayer p) {
        List<String> list = new ArrayList<>();
        if (p == null) return list;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT HOME FROM " + databaseTable() + " WHERE UUID=?")) {
            ps.setString(1, p.getUniqueId().toString());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(rs.getString("HOME"));
            }
        } catch (SQLException e) {
            zHomes.getInstance().getLoggerInstance().error("Error getting homes for player: " + p.getName(), e);
        }
        return list;
    }

    /**
     * Returns the number of homes the player currently has, using a COUNT query
     * instead of fetching all home names.
     */
    public static int countHomes(OfflinePlayer p) {
        if (p == null) return 0;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT COUNT(*) AS total FROM " + databaseTable() + " WHERE UUID=?")) {
            ps.setString(1, p.getUniqueId().toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("total");
            }
        } catch (SQLException e) {
            zHomes.getInstance().getLoggerInstance().error("Error counting homes for player: " + p.getName(), e);
        }
        return 0;
    }

    public static void updatePlayerName(OfflinePlayer p) {
        if (p.getName() == null) return;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE " + databaseTable() + " SET NAME=? WHERE UUID=?")) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getUniqueId().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            zHomes.getInstance().getLoggerInstance().error("Error updating name for: " + p.getName(), e);
        }
    }

    public static UUID getUUIDByName(String name) {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT UUID FROM " + databaseTable() + " WHERE LOWER(NAME)=LOWER(?) LIMIT 1")) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return UUID.fromString(rs.getString("UUID"));
            }
        } catch (Exception ignored) {}
        return null;
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
            if (rs.next()) count = rs.getLong("total");
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
            if (rs.next()) count = rs.getLong("total");
        } catch (SQLException e) {
            zHomes.getInstance().getLoggerInstance().error("Error getting total users", e);
        }
        return count;
    }
    //</editor-fold>

    //<editor-fold desc="Purge">
    public static class PurgeFilter {
        private UUID playerUuid;
        private String worldName;
        private String homeStartsWith;
        private String homeEndsWith;
        private Map<UUID, List<String>> customFilter;

        private PurgeFilter() {}

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private final PurgeFilter filter = new PurgeFilter();

            public Builder player(OfflinePlayer player) {
                if (player != null) filter.playerUuid = player.getUniqueId();
                return this;
            }

            public Builder playerUuid(UUID uuid) {
                filter.playerUuid = uuid;
                return this;
            }

            public Builder world(World world) {
                if (world != null) filter.worldName = world.getName();
                return this;
            }

            public Builder worldName(String worldName) {
                filter.worldName = worldName;
                return this;
            }

            public Builder homeStartsWith(String prefix) {
                filter.homeStartsWith = prefix;
                return this;
            }

            public Builder homeEndsWith(String suffix) {
                filter.homeEndsWith = suffix;
                return this;
            }

            public Builder customFilter(Map<UUID, List<String>> customFilter) {
                filter.customFilter = customFilter;
                return this;
            }

            public PurgeFilter build() {
                return filter;
            }
        }

        boolean hasInMemoryFilters() {
            return worldName != null || customFilter != null;
        }

        boolean matchesCustomFilter(UUID uuid, String homeName) {
            if (customFilter == null) return true;
            List<String> allowedHomes = customFilter.get(uuid);
            return allowedHomes != null && allowedHomes.contains(homeName);
        }
    }

    public static int purgeHomes(PurgeFilter filter) {
        if (filter == null) return 0;
        try (Connection con = getConnection()) {
            if (filter.hasInMemoryFilters()) {
                return purgeWithInMemoryFilter(con, filter);
            } else {
                return purgeWithSqlFilter(con, filter);
            }
        } catch (SQLException e) {
            zHomes.getInstance().getLoggerInstance().error("Error purging homes with filter", e);
            return -1;
        }
    }

    private static int purgeWithSqlFilter(Connection con, PurgeFilter filter) throws SQLException {
        StringBuilder sql = new StringBuilder("DELETE FROM ").append(databaseTable()).append(" WHERE 1=1");
        List<String> params = new ArrayList<>();

        if (filter.playerUuid != null) {
            sql.append(" AND UUID=?");
            params.add(filter.playerUuid.toString());
        }
        if (filter.homeStartsWith != null && !filter.homeStartsWith.isEmpty()) {
            sql.append(" AND LOWER(HOME) LIKE LOWER(?)");
            params.add(filter.homeStartsWith + "%");
        }
        if (filter.homeEndsWith != null && !filter.homeEndsWith.isEmpty()) {
            sql.append(" AND LOWER(HOME) LIKE LOWER(?)");
            params.add("%" + filter.homeEndsWith);
        }

        try (PreparedStatement ps = con.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) ps.setString(i + 1, params.get(i));
            int deleted = ps.executeUpdate();
            zHomes.getInstance().getLoggerInstance().info("Purged " + deleted + " homes with SQL filter");
            return deleted;
        }
    }

    private static int purgeWithInMemoryFilter(Connection con, PurgeFilter filter) throws SQLException {
        record HomeKey(String uuid, String home) {}
        List<HomeKey> toDelete = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT UUID, HOME, LOCATION FROM ").append(databaseTable()).append(" WHERE 1=1");
        List<String> params = new ArrayList<>();

        if (filter.playerUuid != null) {
            sql.append(" AND UUID=?");
            params.add(filter.playerUuid.toString());
        }
        if (filter.homeStartsWith != null && !filter.homeStartsWith.isEmpty()) {
            sql.append(" AND LOWER(HOME) LIKE LOWER(?)");
            params.add(filter.homeStartsWith + "%");
        }
        if (filter.homeEndsWith != null && !filter.homeEndsWith.isEmpty()) {
            sql.append(" AND LOWER(HOME) LIKE LOWER(?)");
            params.add("%" + filter.homeEndsWith);
        }

        try (PreparedStatement ps = con.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) ps.setString(i + 1, params.get(i));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String uuidStr = rs.getString("UUID");
                    String homeName = rs.getString("HOME");
                    String locationStr = rs.getString("LOCATION");
                    try {
                        UUID uuid = UUID.fromString(uuidStr);
                        if (filter.customFilter != null && !filter.matchesCustomFilter(uuid, homeName)) continue;
                        if (filter.worldName != null) {
                            Location loc = LocationHandler.deserialize(locationStr);
                            if (loc == null || loc.getWorld() == null || !loc.getWorld().getName().equalsIgnoreCase(filter.worldName)) continue;
                        }
                        toDelete.add(new HomeKey(uuidStr, homeName));
                    } catch (Exception ignored) {}
                }
            }
        }

        if (toDelete.isEmpty()) return 0;

        try (PreparedStatement deletePs = con.prepareStatement(
                "DELETE FROM " + databaseTable() + " WHERE UUID=? AND HOME=?")) {
            con.setAutoCommit(false);
            try {
                for (HomeKey key : toDelete) {
                    deletePs.setString(1, key.uuid());
                    deletePs.setString(2, key.home());
                    deletePs.addBatch();
                }
                int[] results = deletePs.executeBatch();
                con.commit();
                int deleted = 0;
                for (int result : results) deleted += Math.max(0, result);
                zHomes.getInstance().getLoggerInstance().info("Purged " + deleted + " homes with in-memory filter");
                return deleted;
            } catch (SQLException e) {
                try { con.rollback(); } catch (SQLException ignored) {}
                throw e;
            } finally {
                try { con.setAutoCommit(true); } catch (SQLException ignored) {}
            }
        }
    }
    //</editor-fold>

    public static boolean isInTable(OfflinePlayer p, String home) {
        try {
            return existsTableColumnValueDoubleLower(databaseTable(), "UUID", p.getUniqueId().toString(), "HOME", home);
        } catch (Exception ignored) {
            return false;
        }
    }

    //<editor-fold desc="Database export/import">
    private static class ExportEntry {
        String uuid;
        String name;
        String home;
        String location;

        ExportEntry() {}

        ExportEntry(String uuid, String name, String home, String location) {
            this.uuid = uuid;
            this.name = name;
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
             PreparedStatement ps = con.prepareStatement("SELECT UUID, NAME, HOME, LOCATION FROM " + databaseTable());
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                entries.add(new ExportEntry(rs.getString("UUID"), rs.getString("NAME"), rs.getString("HOME"), rs.getString("LOCATION")));
                if (sender != null && entries.size() % 50 == 0) {
                    LanguageBuilder.sendActionBar(sender, "Exporting data... [" + entries.size() + " records]");
                }
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (GZIPOutputStream gos = new GZIPOutputStream(Files.newOutputStream(outFile.toPath()));
                 Writer writer = new OutputStreamWriter(gos, StandardCharsets.UTF_8)) {
                gson.toJson(entries, writer);
                writer.flush();
            }

            if (sender != null) {
                LanguageBuilder.sendActionBar(sender, "Export complete! " + entries.size() + " records exported.");
                try {
                    if (sender instanceof Player player) player.playSound(player.getLocation(), Objects.requireNonNull(getEntityLevelUP()), 100.0F, 1.0F);
                } catch (Exception ignored) {}
            }
            zHomes.getInstance().getLoggerInstance().info("Exported " + entries.size() + " records to " + outFile.getAbsolutePath());
            return outFile;
        } catch (Exception e) {
            zHomes.getInstance().getLoggerInstance().error("Error exporting database to " + outFile.getAbsolutePath(), e);
            if (sender != null) LanguageBuilder.sendMessage(sender, "<red>Failed to export database. Check console for details.");
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
            zHomes.getInstance().getLoggerInstance().error("Import file does not exist: " + (gzJsonFile == null ? "null" : gzJsonFile.getAbsolutePath()));
            return -1;
        }

        List<ExportEntry> entries;
        try (GZIPInputStream gis = new GZIPInputStream(Files.newInputStream(gzJsonFile.toPath()));
             Reader reader = new InputStreamReader(gis, StandardCharsets.UTF_8)) {
            entries = new Gson().fromJson(reader, new TypeToken<List<ExportEntry>>() {}.getType());
            if (entries == null) entries = Collections.emptyList();
        } catch (Exception e) {
            zHomes.getInstance().getLoggerInstance().error("Error reading import file: " + gzJsonFile.getAbsolutePath(), e);
            if (sender != null) LanguageBuilder.sendMessage(sender, "<red>Failed to import database. Check console for details.");
            return -1;
        }

        if (entries.isEmpty()) {
            zHomes.getInstance().getLoggerInstance().info("Import file contained no entries: " + gzJsonFile.getAbsolutePath());
            if (sender != null) LanguageBuilder.sendMessage(sender, "<red>Import file contained no entries.");
            return 0;
        }

        final int BATCH_SIZE = 500;
        int imported = 0;
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(upsertSql())) {
            con.setAutoCommit(false);
            try {
                for (ExportEntry e : entries) {
                    if (e.name == null) {
                        try {
                            OfflinePlayer op = PlayerHandler.getOfflinePlayer(UUID.fromString(e.uuid));
                            e.name = op.getName();
                        } catch (Exception ignored) {}
                    }
                    pstmt.setString(1, e.uuid);
                    pstmt.setString(2, e.name);
                    pstmt.setString(3, e.home);
                    pstmt.setString(4, e.location);
                    pstmt.addBatch();
                    imported++;

                    if (imported % BATCH_SIZE == 0) {
                        pstmt.executeBatch();
                        con.commit();
                        if (sender != null) LanguageBuilder.sendActionBar(sender, "Importing data... [" + imported + "/" + entries.size() + " records]");
                    }
                }
                pstmt.executeBatch();
                con.commit();
            } catch (SQLException e) {
                try { con.rollback(); } catch (SQLException ignored) {}
                zHomes.getInstance().getLoggerInstance().error("Error during database import", e);
                if (sender != null) LanguageBuilder.sendMessage(sender, "<red>Failed to import database. Check console for details.");
                return -1;
            } finally {
                try { con.setAutoCommit(true); } catch (SQLException ignored) {}
            }
        } catch (SQLException e) {
            zHomes.getInstance().getLoggerInstance().error("Error getting DB connection during import", e);
            if (sender != null) LanguageBuilder.sendMessage(sender, "<red>Failed to import database. Check console for details.");
            return -1;
        }

        if (sender != null) {
            LanguageBuilder.sendActionBar(sender, "Import complete! " + imported + " records imported.");
            try {
                if (sender instanceof Player player) player.playSound(player.getLocation(), Objects.requireNonNull(getEntityLevelUP()), 100.0F, 1.0F);
            } catch (Exception ignored) {}
        }
        zHomes.getInstance().getLoggerInstance().info("Imported " + imported + " records from " + gzJsonFile.getAbsolutePath());
        return imported;
    }
}

