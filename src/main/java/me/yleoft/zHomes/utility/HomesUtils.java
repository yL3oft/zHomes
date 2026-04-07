package me.yleoft.zHomes.utility;

import com.zhomes.api.event.player.TeleportToHomeEvent;
import me.yleoft.zAPI.configuration.Path;
import me.yleoft.zAPI.libs.folialib.wrapper.task.WrappedTask;
import me.yleoft.zAPI.location.LocationHandler;
import me.yleoft.zAPI.util.TextFormatter;
import me.yleoft.zAPI.util.Version;
import me.yleoft.zAPI.zAPI;
import me.yleoft.zHomes.configuration.languages.LanguageBuilder;
import me.yleoft.zHomes.storage.DatabaseEditor;
import me.yleoft.zHomes.zHomes;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class HomesUtils extends DatabaseEditor {

    public static final ConcurrentHashMap<UUID, WrappedTask> warmups = new ConcurrentHashMap<>();

    public static boolean hasHome(OfflinePlayer p, String home) {
        return isInTable(p, home);
    }

    public static boolean inSameWorld(String w, Player p) {
        return !w.equals(p.getWorld().getName());
    }

    public static boolean inMaxLimit(Player p) {
        if (zHomes.getConfigYAML().isLimitsEnabled() && !p.hasPermission(zHomes.getConfigYAML().getBypassLimitPermission())) {
            return getLimit(p) >= getMaxLimit(p);
        }
        return false;
    }

    public static int getMaxLimit(OfflinePlayer p) {
        int l = -1;
        for (String str : zHomes.getConfigYAML().getConfigurationSection("limits").getKeys(false)) {
            if (str.equals("enabled") || str.equals("default")) continue;
            try {
                int i = Integer.parseInt(str);
                for (String perm : zHomes.getConfigYAML().getStringList(Path.formPath("limits", str))) {
                    if (p.isOnline() && requireNonNull(p.getPlayer()).hasPermission(perm) && i > l) l = i;
                }
            } catch (Exception e) {
                zHomes.getInstance().getLogger().warning("Invalid limit key in config: " + str + ". It should be an integer.");
            }
        }
        return (l == -1) ? zHomes.getConfigYAML().getDefaultHomeLimit() : l;
    }

    public static boolean isAllowedInWorld(Player p) {
        if (!zHomes.getConfigYAML().isRestrictedWorldsEnabled() || p.hasPermission(zHomes.getConfigYAML().getBypassRestrictedWorldsPermission())) return true;
        return isAllowedInWorld(p.getWorld());
    }

    public static boolean isAllowedInWorld(World world) {
        if (!zHomes.getConfigYAML().isRestrictedWorldsEnabled()) return true;
        List<String> restrictedWorlds = zHomes.getConfigYAML().getRestrictedWorldsList();
        String mode = zHomes.getConfigYAML().getRestrictedWorldsMode();
        if (mode.equalsIgnoreCase("blacklist")) return !restrictedWorlds.contains(world.getName());
        return restrictedWorlds.contains(world.getName());
    }

    public static boolean isAllowedInWorld(Player p, World world) {
        if (!zHomes.getConfigYAML().isRestrictedWorldsEnabled() || p.hasPermission(zHomes.getConfigYAML().getBypassRestrictedWorldsPermission())) return true;
        List<String> restrictedWorlds = zHomes.getConfigYAML().getRestrictedWorldsList();
        String mode = zHomes.getConfigYAML().getRestrictedWorldsMode();
        if (mode.equalsIgnoreCase("blacklist")) return !restrictedWorlds.contains(world.getName());
        return restrictedWorlds.contains(world.getName());
    }

    public static int getLimit(OfflinePlayer p) {
        return countHomes(p);
    }

    /**
     * @return {@code true} if the home was saved successfully, {@code false} on DB error.
     */
    public static boolean addHome(OfflinePlayer p, String home, Location loc) {
        return setHome(p, home, LocationHandler.serialize(loc));
    }

    /**
     * @return {@code true} if the home was deleted, {@code false} on DB error or not found.
     */
    public static boolean delHome(OfflinePlayer p, String home) {
        return deleteHome(p, home);
    }

    public static boolean canDimensionalTeleport(OfflinePlayer p) {
        if (p.isOnline()) {
            Player pl = (Player) p;
            if (pl.hasPermission(zHomes.getConfigYAML().getBypassDimensionalTeleportationPermission())) return true;
        }
        return zHomes.getConfigYAML().isDimensionalTeleportationEnabled();
    }

    public static void teleportPlayer(Player p, OfflinePlayer t, String home) {
        Location loc = getHomeLoc(t, home);
        String worldName = getHomeWorld(t, home);
        boolean isDimensionalTeleport = worldName != null && inSameWorld(worldName, p);
        TeleportToHomeEvent event = new TeleportToHomeEvent(p, home, p.getLocation(), loc, isDimensionalTeleport, p.getUniqueId() == t.getUniqueId(), t);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;
        if (isDimensionalTeleport && !canDimensionalTeleport(p)) {
            LanguageBuilder.sendMessage(p, zHomes.getLanguageYAML().getHomeCantDimensionalTeleport());
            return;
        }
        zAPI.getScheduler().runAtLocation(loc, task -> {
            Location tpLoc = (zHomes.getConfigYAML().isSafeTeleportEnabled() && !p.hasPermission(zHomes.getConfigYAML().getBypassSafeTeleportationPermission())) ? LocationHandler.findNearestSafeLocation(loc, 4, 50) : loc;
            if (tpLoc == null) {
                LanguageBuilder.sendMessage(p, zHomes.getLanguageYAML().getUnableToFindSafeLocation());
                return;
            }
            String homeString = p.getUniqueId() == t.getUniqueId() ? home : t.getName() + ":" + home;
            Runnable runnable = () -> {
                Sound sound = getTeleportSound();
                if (Version.isFolia()) {
                    zAPI.getScheduler().teleportAsync(p, tpLoc);
                    zAPI.getScheduler().runAtLocation(tpLoc, task2 -> {
                        if (sound != null && zHomes.getConfigYAML().isTeleportSoundEnabled()) p.playSound(p.getLocation(), sound, 1.0F, 1.0F);
                    });
                } else {
                    zAPI.getScheduler().runAtLocationLater(tpLoc, () -> {
                        p.teleport(tpLoc, PlayerTeleportEvent.TeleportCause.PLUGIN);
                        zAPI.getScheduler().runAtLocation(tpLoc, task2 -> {
                            if (sound != null && zHomes.getConfigYAML().isTeleportSoundEnabled()) p.playSound(p.getLocation(), sound, 1.0F, 1.0F);
                        });
                    }, 1L);
                }
                LanguageBuilder.sendMessage(p, zHomes.getLanguageYAML().getHomeOutput(homeString));
            };
            if (zHomes.getConfigYAML().isWarmupEnabled() && !p.hasPermission(zHomes.getConfigYAML().getBypassWarmupPermission()) && zHomes.getConfigYAML().getWarmupTime() > 0) {
                LanguageBuilder.sendMessage(p, zHomes.getLanguageYAML().getWarmup(zHomes.getConfigYAML().getWarmupTime()));
                startWarmup(p, homeString, runnable);
                return;
            }
            runnable.run();
        });
    }

    public static void teleportPlayer(Player p, String home) {
        teleportPlayer(p, p, home);
    }

    public static void startWarmup(Player p, String home, Runnable runnable) {
        UUID uuid = p.getUniqueId();
        WrappedTask previous = warmups.remove(uuid);
        if (previous != null) {
            try { previous.cancel(); } catch (Throwable ignored) {}
        }

        AtomicReference<WrappedTask> taskRef = new AtomicReference<>();

        Runnable task = new Runnable() {
            int counter = zHomes.getConfigYAML().getWarmupTime();

            @Override
            public void run() {
                if (!p.isOnline()) {
                    warmups.remove(uuid);
                    taskRef.get().cancel();
                    return;
                }
                if (counter >= 1) {
                    if (zHomes.getConfigYAML().isWarmupShowOnActionBar()) {
                        p.sendActionBar(TextFormatter.transform(p, zHomes.getLanguageYAML().getWarmupActionbar(counter)));
                    }
                    counter--;
                } else {
                    if (zHomes.getConfigYAML().isWarmupShowOnActionBar()) {
                        p.sendActionBar(TextFormatter.transform(p, zHomes.getLanguageYAML().getHomeOutput(home)));
                    }
                    runnable.run();
                    warmups.remove(uuid);
                    taskRef.get().cancel();
                }
            }
        };

        WrappedTask scheduledTask = zAPI.getScheduler().runAtEntityTimer(p, task, 1L, 20L);
        taskRef.set(scheduledTask);
        warmups.put(uuid, scheduledTask);
    }

    public static Sound getTeleportSound() {
        try {
            return Sound.ENTITY_ENDERMAN_TELEPORT;
        } catch (Throwable ignored) {}
        return null;
    }

    public static String homes(OfflinePlayer p, boolean other) {
        try {
            List<String> homes = getHomes(p);
            if (homes.isEmpty()) return "None";
            return homes.stream()
                    .map(h -> other
                            ? zHomes.getLanguageYAML().getHomesHomeStringOthers(h, p.getName())
                            : zHomes.getLanguageYAML().getHomesHomeString(h))
                    .collect(Collectors.joining(", "));
        } catch (Exception e) {
            return "None";
        }
    }

    public static String homes(OfflinePlayer p) {
        return homes(p, false);
    }

    public static List<String> homesW(OfflinePlayer p) {
        return getHomes(p);
    }

    public static List<String> homesWDD(OfflinePlayer p) {
        return getHomes(p).stream()
                .map(home -> p.getName() + ":" + home)
                .collect(Collectors.toList());
    }

    public static int numberOfHomes(OfflinePlayer p) {
        return countHomes(p);
    }

    public static Location getHomeLoc(OfflinePlayer p, String home) {
        return LocationHandler.deserialize(getHome(p, home));
    }

    public static String getHomeWorld(OfflinePlayer p, String home) {
        String locS = getHome(p, home);
        String[] locP = locS.split(";");
        try {
            return locP[0];
        } catch (Exception exception) {
            return null;
        }
    }

}
