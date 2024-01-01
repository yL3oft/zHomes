package me.leonardo.zhomes.events;

import me.leonardo.zhomes.api.events.ExecuteHomesCommandEvent;
import me.leonardo.zhomes.api.events.PreExecuteDelhomeCommandEvent;
import me.leonardo.zhomes.api.events.PreExecuteHomeCommandEvent;
import me.leonardo.zhomes.api.events.PreExecuteSethomeCommandEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PluginCommandsEvents implements Listener {

    @EventHandler
    public void onPlayerPreExecuteSethomeCommandEvent(PreExecuteSethomeCommandEvent e) {
        Player p = e.getPlayer();
        if(!p.hasPermission("zhomes.commands.sethome")) e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerPreExecuteDelhomeCommandEvent(PreExecuteDelhomeCommandEvent e) {
        Player p = e.getPlayer();
        if(!p.hasPermission("zhomes.commands.delhome")) e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerPreExecuteHomeCommandEvent(PreExecuteHomeCommandEvent e) {
        Player p = e.getPlayer();
        if(!p.hasPermission("zhomes.commands.home")) e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerExecuteHomesCommandEvent(ExecuteHomesCommandEvent e) {
        Player p = e.getPlayer();
        if(!p.hasPermission("zhomes.commands.home")) e.setCancelled(true);
    }

}
