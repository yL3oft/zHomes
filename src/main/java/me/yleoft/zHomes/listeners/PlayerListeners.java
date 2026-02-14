package me.yleoft.zHomes.listeners;

import com.zhomes.api.event.player.*;
import me.yleoft.zAPI.libs.folialib.wrapper.task.WrappedTask;
import me.yleoft.zAPI.zAPI;
import me.yleoft.zHomes.configuration.languages.LanguageBuilder;
import me.yleoft.zHomes.storage.DatabaseEditor;
import me.yleoft.zHomes.utility.HomesUtils;
import me.yleoft.zHomes.utility.UpdateUtils;
import me.yleoft.zHomes.zHomes;
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

public class PlayerListeners extends HomesUtils implements Listener {

    private final ConcurrentHashMap<UUID, Long> lastAnnounced = new ConcurrentHashMap<>();

    public PlayerListeners() {
        zAPI.getScheduler().runTimerAsync(task -> {
            long cutoff = System.currentTimeMillis() - (1000L * 60L * 30L); // 30 minutes
            lastAnnounced.entrySet().removeIf(entry -> entry.getValue() < cutoff);

            for (Map.Entry<UUID, WrappedTask> entry : HomesUtils.warmups.entrySet()) {
                UUID uuid = entry.getKey();
                WrappedTask wrappedTask = entry.getValue();
                Player player = Bukkit.getPlayer(uuid);
                if (player == null || !player.isOnline()) {
                    if (wrappedTask != null) {
                        try {
                            wrappedTask.cancel();
                        } catch (Throwable ignored) {}
                    }
                    HomesUtils.warmups.remove(uuid);
                }
            }
        }, 20L * 60L * 10L, 20L * 60L * 10L);
    }


    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        DatabaseEditor.updatePlayerName(p);
        UUID uuid = p.getUniqueId();
        if(lastAnnounced.containsKey(uuid)) {
            long lastTime = lastAnnounced.get(uuid);
            long currentTime = System.currentTimeMillis();
            if(currentTime - lastTime <= 1000L * 60L * 30L) { // 30 minutes
                return;
            }
        }
        if(zHomes.updateUtils.needsUpdate && zHomes.getConfigYAML().isAnnounceUpdateEnabled() && (p.isOp() || p.hasPermission(zHomes.getConfigYAML().getMainCommandVersionUpdatePermission()))) {
            lastAnnounced.put(uuid, System.currentTimeMillis());
            zAPI.getScheduler().runLater(() -> {
                if(!p.isOnline()) return;
                LanguageBuilder.sendMessage(p, "%prefix% <gold>You are using an outdated version of zHomes! Please update to the latest version.");
                LanguageBuilder.sendMessage(p, "%prefix% <gold>New version: <green>" + zHomes.updateUtils.updateVersion);
                LanguageBuilder.sendMessage(p, "%prefix% <gold>Your version: <red>" + zHomes.getInstance().getPluginMeta().getVersion());
                LanguageBuilder.sendMessage(p, "%prefix% <gold>You can update your plugin here: <yellow>" + UpdateUtils.site);
            }, 60L);
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        WrappedTask wrappedTask = HomesUtils.warmups.remove(uuid);
        if (wrappedTask != null) {
            try {
                wrappedTask.cancel();
            } catch (Throwable ignored) {}
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        if(HomesUtils.warmups.containsKey(uuid) && zHomes.getConfigYAML().isWarmupCancelOnMove()) {
            Location from = e.getFrom();
            Location to = e.getTo();
            if(to == null) return;
            if (from.getBlockX() != to.getBlockX()
                    || from.getBlockY() != to.getBlockY()
                    || from.getBlockZ() != to.getBlockZ()) {
                WrappedTask wrappedTask = HomesUtils.warmups.remove(uuid);
                if (wrappedTask != null) {
                    wrappedTask.cancel();
                    LanguageBuilder.sendMessage(p, zHomes.getLanguageYAML().getWarmupCancelled());
                    LanguageBuilder.sendActionBar(p, zHomes.getLanguageYAML().getWarmupCancelledActionbar());
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerExecuteSethomeCommand(PreExecuteSethomeCommandEvent e) {
        Player p = e.getPlayer();

        if(!isAllowedInWorld(p)) {
            e.setCancelled(true);
            LanguageBuilder.sendMessage(p, zHomes.getLanguageYAML().getWorldRestrictedSethome());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerExecuteHomeCommand(ExecuteHomeCommandEvent e) {
        Player p = e.getPlayer();

        if(hasHome(p, e.getHome()) && !isAllowedInWorld(p, HomesUtils.getHomeLoc(p, e.getHome()).getWorld())) {
            e.setCancelled(true);
            LanguageBuilder.sendMessage(p, zHomes.getLanguageYAML().getWorldRestrictedHome());
        }
    }

}
