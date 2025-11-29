package me.yleoft.zHomes.listeners;

import com.zhomes.api.event.player.*;
import me.yleoft.zAPI.folia.FoliaRunnable;
import me.yleoft.zAPI.utils.ActionbarUtils;
import me.yleoft.zAPI.utils.SchedulerUtils;
import me.yleoft.zHomes.Main;
import me.yleoft.zHomes.utils.HomesUtils;
import me.yleoft.zHomes.utils.LanguageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static me.yleoft.zHomes.Main.needsUpdate;

public class PlayerListeners extends HomesUtils implements Listener {

    private final ConcurrentHashMap<UUID, Long> lastAnnounced = new ConcurrentHashMap<>();

    public PlayerListeners() {
        FoliaRunnable runnable = new FoliaRunnable() {
            @Override
            public void run() {
                long cutoff = System.currentTimeMillis() - (1000L * 60L * 30L); // 30 minutes
                lastAnnounced.entrySet().removeIf(entry -> entry.getValue() < cutoff);

                for (Map.Entry<UUID, FoliaRunnable> entry : HomesUtils.warmups.entrySet()) {
                    UUID uuid = entry.getKey();
                    FoliaRunnable runnable = entry.getValue();
                    Player player = Bukkit.getPlayer(uuid);
                    if (player == null || !player.isOnline()) {
                        if (runnable != null) {
                            try {
                                runnable.cancel();
                            } catch (Throwable ignored) {}
                        }
                        HomesUtils.warmups.remove(uuid);
                    }
                }
            }
        };
        SchedulerUtils.runTaskTimer(null, runnable, 20L * 60L * 10L, 20L * 60L * 10L);
    }


    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        if(lastAnnounced.containsKey(uuid)) {
            long lastTime = lastAnnounced.get(uuid);
            long currentTime = System.currentTimeMillis();
            if(currentTime - lastTime <= 1000L * 60L * 30L) { // 30 minutes
                return;
            }
        }
        if(needsUpdate && doAnnounceUpdate() && (p.isOp() || p.hasPermission(CmdMainVersionUpdatePermission()))) {
            LanguageUtils.CommandsMSG cmdm = new LanguageUtils.CommandsMSG();
            lastAnnounced.put(uuid, System.currentTimeMillis());
            SchedulerUtils.runTaskLater(p.getLocation(), () -> {
                cmdm.sendMsg(p, "%prefix%&6You are using an outdated version of zHomes! Please update to the latest version.");
                cmdm.sendMsg(p, "%prefix%&6New version: &a" + Main.getInstance().updateVersion);
                cmdm.sendMsg(p, "%prefix%&6Your version: &c" + Main.getInstance().getDescription().getVersion());
                cmdm.sendMsg(p, "%prefix%&6You can update your plugin here: &e" + Main.getInstance().site);
            }, 60L);
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        FoliaRunnable runnable = HomesUtils.warmups.remove(uuid);
        if (runnable != null) {
            try {
                runnable.cancel();
            } catch (Throwable ignored) {}
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        if(HomesUtils.warmups.containsKey(uuid) && warmupCancelOnMove()) {
            LanguageUtils.TeleportWarmupMSG lang = new LanguageUtils.TeleportWarmupMSG();

            Location from = e.getFrom();
            Location to = e.getTo();
            if(to == null) return;
            if (from.getBlockX() != to.getBlockX()
                    || from.getBlockY() != to.getBlockY()
                    || from.getBlockZ() != to.getBlockZ()) {
                FoliaRunnable runnable = HomesUtils.warmups.remove(uuid);
                if (runnable != null) {
                    runnable.cancel();
                    lang.sendMsg(p, lang.getCancelled());
                    ActionbarUtils.send(p, LanguageUtils.Helper.getText(p, lang.getCancelledActionbar()));
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerExecuteSethomeCommand(PreExecuteSethomeCommandEvent e) {
        Player p = e.getPlayer();

        if(!isAllowedInWorld(p)) {
            e.setCancelled(true);
            LanguageUtils.CommandsMSG cmdm = new LanguageUtils.CommandsMSG();
            cmdm.sendMsg(p, cmdm.getWorldRestricted());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerExecuteHomeCommand(ExecuteHomeCommandEvent e) {
        Player p = e.getPlayer();

        if(!isAllowedInWorld(Main.hu.getHomeLoc(p, e.getHome()).getWorld())) {
            e.setCancelled(true);
            LanguageUtils.CommandsMSG cmdm = new LanguageUtils.CommandsMSG();
            cmdm.sendMsg(p, cmdm.getWorldRestricted());
        }
    }

}
