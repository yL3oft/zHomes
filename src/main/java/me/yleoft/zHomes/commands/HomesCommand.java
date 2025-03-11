package me.yleoft.zHomes.commands;

import me.yleoft.zHomes.api.events.ExecuteHomesCommandEvent;
import me.yleoft.zHomes.utils.HomesUtils;
import me.yleoft.zHomes.utils.LanguageUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class HomesCommand extends HomesUtils implements CommandExecutor {

    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!(s instanceof Player))
            return false;
        Player p = (Player)s;
        ExecuteHomesCommandEvent event = new ExecuteHomesCommandEvent(p);
        Bukkit.getPluginManager().callEvent((Event)event);
        if (event.isCancelled()) return false;

        LanguageUtils.Homes lang = new LanguageUtils.Homes();
        LanguageUtils.CommandsMSG cmdm = new LanguageUtils.CommandsMSG();

        if (args.length >= 1) {
            String player = args[0];
            if (p.hasPermission(CmdHomesOthersPermission())) {
                OfflinePlayer t = Bukkit.getOfflinePlayer(player);
                if (t == null) {
                    lang.sendMsg(p, cmdm.getCantFindPlayer());
                    return false;
                }
                lang.sendMsg(p, lang.getOutput(t));
                return true;
            }
        }
        lang.sendMsg(p, lang.getOutput());
        return false;
    }

}
