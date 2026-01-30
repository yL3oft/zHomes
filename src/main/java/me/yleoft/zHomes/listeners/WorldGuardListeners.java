package me.yleoft.zHomes.listeners;

import com.zhomes.api.event.player.ExecuteHomesCommandEvent;
import com.zhomes.api.event.player.PreExecuteDelhomeCommandEvent;
import com.zhomes.api.event.player.PreExecuteHomeCommandEvent;
import com.zhomes.api.event.player.PreExecuteSethomeCommandEvent;
import me.yleoft.zHomes.configuration.languages.LanguageBuilder;
import me.yleoft.zHomes.hooks.HookRegistry;
import me.yleoft.zHomes.zHomes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class WorldGuardListeners implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerExecuteSethomeCommand(PreExecuteSethomeCommandEvent e) {
        Player p = e.getPlayer();
        if (!HookRegistry.WORLDGUARD.getFlagStateAtPlayer(p, HookRegistry.WORLDGUARD.useHomesFlag)) {
            e.setCancelled(true);
            LanguageBuilder.sendMessage(p, zHomes.getLanguageYAML().getWGCantUseHomes());
        }else if (!HookRegistry.WORLDGUARD.getFlagStateAtPlayer(p, HookRegistry.WORLDGUARD.setHomesFlag)) {
            e.setCancelled(true);
            LanguageBuilder.sendMessage(p, zHomes.getLanguageYAML().getWGCantSetHomes());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerExecuteDelhomeCommand(PreExecuteDelhomeCommandEvent e) {
        Player p = e.getPlayer();

        if (!HookRegistry.WORLDGUARD.getFlagStateAtPlayer(p, HookRegistry.WORLDGUARD.useHomesFlag)) {
            e.setCancelled(true);
            LanguageBuilder.sendMessage(p, zHomes.getLanguageYAML().getWGCantUseHomes());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerExecuteHomeCommand(PreExecuteHomeCommandEvent e) {
        Player p = e.getPlayer();

        if (!HookRegistry.WORLDGUARD.getFlagStateAtPlayer(p, HookRegistry.WORLDGUARD.useHomesFlag)) {
            e.setCancelled(true);
            LanguageBuilder.sendMessage(p, zHomes.getLanguageYAML().getWGCantUseHomes());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerExecuteHomesCommand(ExecuteHomesCommandEvent e) {
        Player p = e.getPlayer();

        if (!HookRegistry.WORLDGUARD.getFlagStateAtPlayer(p, HookRegistry.WORLDGUARD.useHomesFlag)) {
            e.setCancelled(true);
            LanguageBuilder.sendMessage(p, zHomes.getLanguageYAML().getWGCantUseHomes());
        }
    }

}
