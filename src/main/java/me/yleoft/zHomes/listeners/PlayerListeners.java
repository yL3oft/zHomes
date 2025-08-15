package me.yleoft.zHomes.listeners;

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
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

import static me.yleoft.zHomes.Main.needsUpdate;

public class PlayerListeners extends HomesUtils implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(needsUpdate && doAnnounceUpdate() && (p.isOp() || p.hasPermission(CmdMainVersionUpdatePermission()))) {
            LanguageUtils.CommandsMSG cmdm = new LanguageUtils.CommandsMSG();
            SchedulerUtils.runTaskLater(p.getLocation(), () -> {
                cmdm.sendMsg(p, "%prefix%&6You are using an outdated version of zHomes! Please update to the latest version.");
                cmdm.sendMsg(p, "%prefix%&6New version: &a" + Main.getInstance().updateVersion);
                cmdm.sendMsg(p, "%prefix%&6Your version: &c" + Main.getInstance().getDescription().getVersion());
                cmdm.sendMsg(p, "%prefix%&6You can update your plugin here: &e" + Main.getInstance().site);
            }, 60L);
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
                BukkitRunnable runnable = HomesUtils.warmups.remove(uuid);
                if (runnable != null) {
                    runnable.cancel();
                    lang.sendMsg(p, lang.getCancelled());
                    ActionbarUtils.send(p, LanguageUtils.Helper.getText(p, lang.getCancelledActionbar()));
                }
            }
        }
    }

}
