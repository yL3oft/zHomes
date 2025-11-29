package me.yleoft.zHomes.listeners;

import com.zhomes.api.event.player.*;
import me.yleoft.zAPI.utils.ActionbarUtils;
import me.yleoft.zAPI.utils.SchedulerUtils;
import me.yleoft.zHomes.Main;
import me.yleoft.zHomes.utils.HomesUtils;
import me.yleoft.zHomes.utils.LanguageUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static me.yleoft.zHomes.Main.needsUpdate;

public class PlayerListeners extends HomesUtils implements Listener {

    private final ConcurrentHashMap<UUID, Long> lastAnnounced = new ConcurrentHashMap<>();

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
    public void onPlayerQuit(PlayerQuitEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        
        // Limpar warmup pendente para prevenir memory leak
        if (HomesUtils.warmups.containsKey(uuid)) {
            BukkitRunnable runnable = HomesUtils.warmups.remove(uuid);
            if (runnable != null) {
                runnable.cancel();
            }
        }
        
        // Limpar cache de anúncio de atualização (não essencial, mas bom para manutenção)
        lastAnnounced.remove(uuid);
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
                // Remover do HashMap antes de cancelar para garantir limpeza
                BukkitRunnable runnable = HomesUtils.warmups.remove(uuid);
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
    public void onPlayerExecuteDelhomeCommand(PreExecuteDelhomeCommandEvent e) {
        Player p = e.getPlayer();

        if(!isAllowedInWorld(p)) {
            e.setCancelled(true);
            LanguageUtils.CommandsMSG cmdm = new LanguageUtils.CommandsMSG();
            cmdm.sendMsg(p, cmdm.getWorldRestricted());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerExecuteHomeCommand(PreExecuteHomeCommandEvent e) {
        Player p = e.getPlayer();

        if(!isAllowedInWorld(p)) {
            e.setCancelled(true);
            LanguageUtils.CommandsMSG cmdm = new LanguageUtils.CommandsMSG();
            cmdm.sendMsg(p, cmdm.getWorldRestricted());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerExecuteHomesCommand(ExecuteHomesCommandEvent e) {
        Player p = e.getPlayer();

        if(!isAllowedInWorld(p)) {
            e.setCancelled(true);
            LanguageUtils.CommandsMSG cmdm = new LanguageUtils.CommandsMSG();
            cmdm.sendMsg(p, cmdm.getWorldRestricted());
        }
    }

}
