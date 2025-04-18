package me.yleoft.zHomes.listeners;

import com.zhomes.api.event.player.ExecuteHomesCommandEvent;
import com.zhomes.api.event.player.PreExecuteDelhomeCommandEvent;
import com.zhomes.api.event.player.PreExecuteHomeCommandEvent;
import com.zhomes.api.event.player.PreExecuteSethomeCommandEvent;
import me.yleoft.zHomes.utils.LanguageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import static me.yleoft.zHomes.utils.WorldGuardUtils.doUseHomes;

public class WorldGuardListeners implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerExecuteHomeCommand(PreExecuteSethomeCommandEvent e) {
        Player p = e.getPlayer();
        LanguageUtils.HooksMSG hooks = new LanguageUtils.HooksMSG();

        if (!doUseHomes(p)) {
            e.setCancelled(true);
            hooks.sendMsg(p, hooks.getWorldGuardCantUseHomes());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerExecuteHomeCommand(PreExecuteDelhomeCommandEvent e) {
        Player p = e.getPlayer();
        LanguageUtils.HooksMSG hooks = new LanguageUtils.HooksMSG();

        if (!doUseHomes(p)) {
            e.setCancelled(true);
            hooks.sendMsg(p, hooks.getWorldGuardCantUseHomes());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerExecuteHomeCommand(PreExecuteHomeCommandEvent e) {
        Player p = e.getPlayer();
        LanguageUtils.HooksMSG hooks = new LanguageUtils.HooksMSG();

        if (!doUseHomes(p)) {
            e.setCancelled(true);
            hooks.sendMsg(p, hooks.getWorldGuardCantUseHomes());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerExecuteHomeCommand(ExecuteHomesCommandEvent e) {
        Player p = e.getPlayer();
        LanguageUtils.HooksMSG hooks = new LanguageUtils.HooksMSG();

        if (!doUseHomes(p)) {
            e.setCancelled(true);
            hooks.sendMsg(p, hooks.getWorldGuardCantUseHomes());
        }
    }

}
