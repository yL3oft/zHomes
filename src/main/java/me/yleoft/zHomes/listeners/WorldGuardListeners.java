package me.yleoft.zHomes.listeners;

import com.zhomes.api.event.player.ExecuteHomesCommandEvent;
import com.zhomes.api.event.player.PreExecuteDelhomeCommandEvent;
import com.zhomes.api.event.player.PreExecuteHomeCommandEvent;
import com.zhomes.api.event.player.PreExecuteSethomeCommandEvent;
import me.yleoft.zHomes.utils.LanguageUtils;
import me.yleoft.zHomes.utils.WorldGuardUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import static me.yleoft.zHomes.Main.setHomesFlag;
import static me.yleoft.zHomes.Main.useHomesFlag;
public class WorldGuardListeners extends WorldGuardUtils implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerExecuteSethomeCommand(PreExecuteSethomeCommandEvent e) {
        Player p = e.getPlayer();
        LanguageUtils.HooksMSG hooks = new LanguageUtils.HooksMSG();

        if (!getFlagStateAtPlayer(p, useHomesFlag)) {
            e.setCancelled(true);
            hooks.sendMsg(p, hooks.getWorldGuardCantUseHomes());
        }else if (!getFlagStateAtPlayer(p, setHomesFlag)) {
            e.setCancelled(true);
            hooks.sendMsg(p, hooks.getWorldGuardCantSetHomes());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerExecuteDelhomeCommand(PreExecuteDelhomeCommandEvent e) {
        Player p = e.getPlayer();
        LanguageUtils.HooksMSG hooks = new LanguageUtils.HooksMSG();

        if (!getFlagStateAtPlayer(p, useHomesFlag)) {
            e.setCancelled(true);
            hooks.sendMsg(p, hooks.getWorldGuardCantUseHomes());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerExecuteHomeCommand(PreExecuteHomeCommandEvent e) {
        Player p = e.getPlayer();
        LanguageUtils.HooksMSG hooks = new LanguageUtils.HooksMSG();

        if (!getFlagStateAtPlayer(p, useHomesFlag)) {
            e.setCancelled(true);
            hooks.sendMsg(p, hooks.getWorldGuardCantUseHomes());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerExecuteHomesCommand(ExecuteHomesCommandEvent e) {
        Player p = e.getPlayer();
        LanguageUtils.HooksMSG hooks = new LanguageUtils.HooksMSG();

        if (!getFlagStateAtPlayer(p, useHomesFlag)) {
            e.setCancelled(true);
            hooks.sendMsg(p, hooks.getWorldGuardCantUseHomes());
        }
    }

}
