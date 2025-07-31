package me.yleoft.zHomes.listeners;

import com.zhomes.api.event.player.PreExecuteSethomeCommandEvent;
import me.yleoft.zHomes.utils.GriefPreventionUtils;
import me.yleoft.zHomes.utils.LanguageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class GriefPreventionListeners extends GriefPreventionUtils implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerExecuteSethomeCommand(PreExecuteSethomeCommandEvent e) {
        Player p = e.getPlayer();
        LanguageUtils.HooksMSG hooks = new LanguageUtils.HooksMSG();

        if(!hasAccessTrust(p)) {
            e.setCancelled(true);
            hooks.sendMsg(p, hooks.getGriefPreventionCantSetHomes());
        }
    }

}
