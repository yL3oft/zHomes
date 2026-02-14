package me.yleoft.zHomes.utility;

import com.zhomes.api.event.player.TeleportToHomeEvent;
import me.yleoft.zAPI.configuration.Path;
import me.yleoft.zAPI.libs.folialib.wrapper.task.WrappedTask;
import me.yleoft.zAPI.location.LocationHandler;
import me.yleoft.zAPI.utility.TextFormatter;
import me.yleoft.zAPI.utility.Version;
import me.yleoft.zAPI.zAPI;
import me.yleoft.zHomes.configuration.languages.LanguageBuilder;
import me.yleoft.zHomes.storage.DatabaseEditor;
import me.yleoft.zHomes.zHomes;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.requireNonNull;

public class HomesUtils extends DatabaseEditor {

    public static final ConcurrentHashMap<UUID, WrappedTask> warmups = new ConcurrentHashMap<>();

    public static boolean hasHome(OfflinePlayer p, String home) {
        return isInTable(p, home);
    }

    public static boolean inSameWorld(String w, Player p) {
        String w2 = p.getWorld().getName();
        return !w.equals(w2);
    }

    public static boolean inMaxLimit(Player p) {
        if(zHomes.getConfigYAML().isLimitsEnabled() && !p.hasPermission(zHomes.getConfigYAML().getBypassLimitPermission())) {
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
        if(!zHomes.getConfigYAML().isRestrictedWorldsEnabled() || p.hasPermission(zHomes.getConfigYAML().getBypassRestrictedWorldsPermission())) return true;
        return isAllowedInWorld(p.getWorld());
    }

    public static boolean isAllowedInWorld(World world) {
        if(!zHomes.getConfigYAML().isRestrictedWorldsEnabled()) return true;
        List<String> restrictedWorlds = zHomes.getConfigYAML().getRestrictedWorldsList();
        String mode = zHomes.getConfigYAML().getRestrictedWorldsMode();
        if(mode.equalsIgnoreCase("blacklist")) {
            return !restrictedWorlds.contains(world.getName());
        }else {
            return restrictedWorlds.contains(world.getName());
        }
    }

    public static boolean isAllowedInWorld(Player p, World world) {
        if(!zHomes.getConfigYAML().isRestrictedWorldsEnabled() || p.hasPermission(zHomes.getConfigYAML().getBypassRestrictedWorldsPermission())) return true;
        List<String> restrictedWorlds = zHomes.getConfigYAML().getRestrictedWorldsList();
        String mode = zHomes.getConfigYAML().getRestrictedWorldsMode();
        if(mode.equalsIgnoreCase("blacklist")) {
            return !restrictedWorlds.contains(world.getName());
        }else {
            return restrictedWorlds.contains(world.getName());
        }
    }

    public static int getLimit(OfflinePlayer p) {
        return getHomes(p).size();
    }

    public static void addHome(OfflinePlayer p, String home, Location loc) {
        setHome(p, home, LocationHandler.serialize(loc));
    }

    public static void delHome(OfflinePlayer p, String home) {
        deleteHome(p, home);
    }

    public static void renameHome(OfflinePlayer p, String home, String newName) {
        if (hasHome(p, home)) {
            if (!hasHome(p, newName)) {
                String locS = getHome(p, home);
                deleteHome(p, home);
                setHome(p, newName, locS);
            }
        }
    }

    public static boolean canDimensionalTeleport(OfflinePlayer p) {
        if (p.isOnline()) {
            Player pl = (Player)p;
            if (pl.hasPermission(zHomes.getConfigYAML().getBypassDimensionalTeleportationPermission()))
                return true;
        }
        return zHomes.getConfigYAML().isDimensionalTeleportationEnabled();
    }

    public static void teleportPlayer(Player p, OfflinePlayer t, String home) {
        Location loc = getHomeLoc(t, home);
        boolean isDimensionalTeleport = inSameWorld(getHomeWorld(t, home), p);
        TeleportToHomeEvent event = new TeleportToHomeEvent(p, home, p.getLocation(), loc, isDimensionalTeleport, p.getUniqueId()==t.getUniqueId(), t);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        if (isDimensionalTeleport && !canDimensionalTeleport(p)) {
            LanguageBuilder.sendMessage(p, zHomes.getLanguageYAML().getHomeCantDimensionalTeleport());
            return;
        }
        zAPI.getScheduler().runAtEntity(p, task -> {
            Location tpLoc = (zHomes.getConfigYAML().isSafeTeleportEnabled() && !p.hasPermission(zHomes.getConfigYAML().getBypassSafeTeleportationPermission())) ? LocationHandler.findNearestSafeLocation(loc, 4, 50) : loc;
            if(tpLoc == null) {
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
                }else {
                    zAPI.getScheduler().runAtLocationLater(tpLoc, () -> {
                        p.teleport(tpLoc, PlayerTeleportEvent.TeleportCause.PLUGIN);
                        zAPI.getScheduler().runAtLocation(tpLoc, task2 -> {
                            if (sound != null && zHomes.getConfigYAML().isTeleportSoundEnabled()) p.playSound(p.getLocation(), sound, 1.0F, 1.0F);
                        });
                    }, 1L);
                }
                LanguageBuilder.sendMessage(p, zHomes.getLanguageYAML().getHomeOutput(homeString));
            };
            if(zHomes.getConfigYAML().isWarmupEnabled() && !p.hasPermission(zHomes.getConfigYAML().getBypassWarmupPermission()) && zHomes.getConfigYAML().getWarmupTime() > 0) {
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
            try {
                previous.cancel();
            } catch (Throwable ignored) {}
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
        StringBuilder returned = new StringBuilder();
        try {
            List<String> homes = getHomes(p);
            if(!homes.isEmpty()) {
                for (String home : homes) {
                    home = other ? zHomes.getLanguageYAML().getHomesHomeStringOthers(home, p.getName()) : zHomes.getLanguageYAML().getHomesHomeString(home);
                    if (returned.isEmpty()) {
                        returned = new StringBuilder(home);
                        continue;
                    }
                    returned.append(", ").append(home);
                }
            }else {
                returned = new StringBuilder("None");
            }
        }catch (Exception e) {
            returned = new StringBuilder("None");
        }

        return returned.toString();
    }
    public static String homes(OfflinePlayer p) {
        return homes(p, false);
    }

    public static List<String> homesW(OfflinePlayer p) {
        return getHomes(p);
    }

    public static List<String> homesWDD(OfflinePlayer p) {
        List<String> list = new ArrayList<>();
        getHomes(p).forEach(home -> list.add(p.getName()+":"+home));
        return list;
    }

    public static int numberOfHomes(OfflinePlayer p) {
        return getHomes(p).size();
    }

    public static Location getHomeLoc(OfflinePlayer p, String home) {
        String locS = getHome(p, home);
        return LocationHandler.deserialize(locS);
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
