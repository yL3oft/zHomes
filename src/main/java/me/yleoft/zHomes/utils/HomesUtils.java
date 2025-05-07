package me.yleoft.zHomes.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import me.yleoft.zHomes.Main;
import com.zhomes.api.event.player.TeleportToHomeEvent;
import me.yleoft.zHomes.storage.DatabaseEditor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static me.yleoft.zAPI.utils.LocationUtils.findNearestSafeLocation;

public class HomesUtils extends DatabaseEditor {

    Main main = Main.getInstance();

    public static HashMap<UUID, BukkitRunnable> warmups = new HashMap<>();

    public boolean hasHome(OfflinePlayer p, String home) {
        return isInTable(p, home);
    }

    public boolean inSameWorld(String w, Player p) {
        String w2 = p.getWorld().getName();
        return !w.equals(w2);
    }

    public boolean inMaxLimit(Player p) {
        if(needsLimit() && !p.hasPermission(PermissionBypassLimit())) {
            return getLimit(p) >= getMaxLimit(p);
        }
        return false;
    }

    public int getLimit(OfflinePlayer p) {
        return getHomes(p).size();
    }

    public void addHome(OfflinePlayer p, String home, Location loc) {
        setHome(p, home, main.serialize(loc));
    }

    public void delHome(OfflinePlayer p, String home) {
        deleteHome(p, home);
    }

    public boolean canDimensionalTeleport(OfflinePlayer p) {
        if (p.isOnline()) {
            Player pl = (Player)p;
            if (pl.hasPermission(PermissionBypassDT()))
                return true;
        }
        return canDimensionalTeleport();
    }

    public void teleportPlayer(Player p, OfflinePlayer t, String home) {
        Location loc = getHomeLoc(t, home);
        boolean isDimensionalTeleport = inSameWorld(getHomeWorld(t, home), p);
        TeleportToHomeEvent event = new TeleportToHomeEvent(p, home, p.getLocation(), loc, isDimensionalTeleport, p.getUniqueId()==t.getUniqueId(), t);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        LanguageUtils.Home lang = new LanguageUtils.Home();
        if (isDimensionalTeleport && !canDimensionalTeleport(p)) {
            lang.sendMsg(p, lang.getCantDimensionalTeleport());
            return;
        }
        Location tpLoc = findNearestSafeLocation(loc, 4, 50);
        if(tpLoc == null) {
            LanguageUtils.CommandsMSG cmdm = new LanguageUtils.CommandsMSG();
            cmdm.sendMsg(p, cmdm.getUnableToFindSafeLocation());
            return;
        }
        String homeString = p.getUniqueId() == t.getUniqueId() ? home : t.getName() + ":" + home;
        Runnable task = () -> {
            Sound sound = getTeleportSound();
            if (Main.getInstance().getServer().getName().contains("Folia")) {
                try {
                    Method teleportAsyncMethod = Player.class.getMethod("teleportAsync", Location.class);
                    teleportAsyncMethod.invoke(p, tpLoc);
                    lang.sendMsg(p, lang.getOutput(homeString));
                    if (playSound()) p.playSound(p.getLocation(), sound, 1.0F, 1.0F);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException("Unable to teleport player to home", e);
                }
                return;
            }
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                p.teleport(tpLoc);
                lang.sendMsg(p, lang.getOutput(homeString));
                if (playSound()) p.playSound(p.getLocation(), sound, 1.0F, 1.0F);
            }, 1L);
        };
        if(doWarmup() && !p.hasPermission(PermissionBypassWarmup()) && warmupTime() > 0) {
            LanguageUtils.TeleportWarmupMSG langWarmup = new LanguageUtils.TeleportWarmupMSG();
            lang.sendMsg(p, langWarmup.getWarmup(warmupTime()));
            startWarmup(p, langWarmup, lang, homeString, task);
            return;
        }
        task.run();
    }
    public void teleportPlayer(Player p, String home) {
        teleportPlayer(p, p, home);
    }

    public void startWarmup(Player p, LanguageUtils.TeleportWarmupMSG lang, LanguageUtils.Home lang2, String home, Runnable task) {
        UUID uuid = p.getUniqueId();
        if (warmups.containsKey(uuid)) {
            warmups.get(uuid).cancel();
        }

        BukkitRunnable runnable = new BukkitRunnable() {
            int counter = warmupTime();

            @Override
            public void run() {
                if (!p.isOnline()) {
                    cancel();
                    warmups.remove(uuid);
                    return;
                }

                if (counter >= 1) {
                    if(warmupShowOnActionbar()) {
                        ActionbarUtils.send(p, LanguageUtils.Helper.getText(p, lang.getWarmupActionbar(counter)));
                    }
                    counter--;
                } else {
                    if(warmupShowOnActionbar()) {
                        ActionbarUtils.send(p, LanguageUtils.Helper.getText(p, lang2.getOutput(home)));
                    }
                    task.run();
                    warmups.remove(uuid);
                    cancel();
                }
            }
        };

        warmups.put(uuid, runnable);
        runnable.runTaskTimer(Main.getInstance(), 0L, 20L);
    }

    public static Sound getTeleportSound() {
        try {
            return Sound.valueOf("ENTITY_ENDERMAN_TELEPORT");
        } catch (IllegalArgumentException e) {
            return Sound.valueOf("ENDERMAN_TELEPORT");
        }
    }

    public String homes(OfflinePlayer p) {
        StringBuilder returned = new StringBuilder();
        try {
            List<String> homes = getHomes(p);
            if(!homes.isEmpty()) {
                for (String home : homes) {
                    if (returned.length() == 0) {
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

    public List<String> homesW(OfflinePlayer p) {
        return getHomes(p);
    }

    public List<String> homesWDD(OfflinePlayer p) {
        List<String> list = new ArrayList<>();
        getHomes(p).forEach(home -> list.add(p.getName()+":"+home));
        return list;
    }

    public int numberOfHomes(OfflinePlayer p) {
        return getHomes(p).size();
    }

    public Location getHomeLoc(OfflinePlayer p, String home) {
        String locS = getHome(p, home);
        return this.main.deserialize(locS);
    }

    public String getHomeWorld(OfflinePlayer p, String home) {
        String locS = getHome(p, home);
        String[] locP = locS.split(";");
        try {
            return locP[0];
        } catch (Exception exception) {
            return null;
        }
    }
}
