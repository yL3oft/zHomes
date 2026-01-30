package me.yleoft.zHomes.listeners;

import com.zhomes.api.event.player.PreExecuteSethomeCommandEvent;
import me.yleoft.zHomes.configuration.languages.LanguageBuilder;
import me.yleoft.zHomes.hooks.HookRegistry;
import me.yleoft.zHomes.zHomes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class GriefPreventionListeners implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerExecuteSethomeCommand(PreExecuteSethomeCommandEvent e) {
        Player p = e.getPlayer();

        if(!HookRegistry.GRIEF_PREVENTION.hasAccessTrust(p)) {
            e.setCancelled(true);
            LanguageBuilder.sendMessage(p, zHomes.getLanguageYAML().getGFCantSetHomes());
        }
    }

}
