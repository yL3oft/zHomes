package me.leonardo.zhomes.events;

import me.leonardo.zhomes.api.events.*;
import me.leonardo.zhomes.utils.LanguageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PluginCommandsEvents implements Listener {

    @EventHandler
    public void onPlayerPreExecuteSethomeCommandEvent(PreExecuteSethomeCommandEvent e) {
        Player p = e.getPlayer();
        LanguageUtils.CommandsMSG lang = new LanguageUtils.CommandsMSG();
        if(!p.hasPermission("zhomes.commands.sethome")) {
            e.setCancelled(true);
            lang.sendMsg(p, lang.getNoPermission());
        }
    }

    @EventHandler
    public void onPlayerPreExecuteDelhomeCommandEvent(PreExecuteDelhomeCommandEvent e) {
        Player p = e.getPlayer();
        LanguageUtils.CommandsMSG lang = new LanguageUtils.CommandsMSG();
        if(!p.hasPermission("zhomes.commands.delhome")) {
            e.setCancelled(true);
            lang.sendMsg(p, lang.getNoPermission());
        }
    }

    @EventHandler
    public void onPlayerPreExecuteHomeCommandEvent(PreExecuteHomeCommandEvent e) {
        Player p = e.getPlayer();
        LanguageUtils.CommandsMSG lang = new LanguageUtils.CommandsMSG();
        if(!p.hasPermission("zhomes.commands.home")) {
            e.setCancelled(true);
            lang.sendMsg(p, lang.getNoPermission());
        }
    }

    @EventHandler
    public void onPlayerExecuteHomesCommandEvent(ExecuteHomesCommandEvent e) {
        Player p = e.getPlayer();
        LanguageUtils.CommandsMSG lang = new LanguageUtils.CommandsMSG();
        if(!p.hasPermission("zhomes.commands.homes")) {
            e.setCancelled(true);
            lang.sendMsg(p, lang.getNoPermission());
        }
    }

    @EventHandler
    public void onPlayerExecuteZhomesCommandEvent(ExecuteZhomesCommandEvent e) {
        Player p = e.getPlayer();
        LanguageUtils.CommandsMSG lang = new LanguageUtils.CommandsMSG();
        if(!p.hasPermission("zhomes.commands.zhomes")) {
            e.setCancelled(true);
            lang.sendMsg(p, lang.getNoPermission());
        }
    }

}
