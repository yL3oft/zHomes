package me.leonardo.zhomes.commands;

import me.leonardo.zhomes.api.events.ExecuteHomesCommandEvent;
import me.leonardo.zhomes.utils.LanguageUtils;
import me.leonardo.zhomes.utils.storage.HomesUtilsYAML;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomesCommand extends HomesUtilsYAML implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!(s instanceof Player)) {

            return false;
        }
        Player p = (Player)s;
        ExecuteHomesCommandEvent event = new ExecuteHomesCommandEvent(p);
        Bukkit.getPluginManager().callEvent(event);
        if(event.isCancelled()) return false;

        LanguageUtils.Homes lang = new LanguageUtils.Homes();
        LanguageUtils.CommandsMSG cmdm = new LanguageUtils.CommandsMSG();

        if(args.length >= 1) {
            String player = args[0];
            if(p.hasPermission("zhomes.commands.homes.others")) {
                OfflinePlayer t = Bukkit.getOfflinePlayer(player);
                if(t == null) {
                    lang.sendMsg(p, cmdm.getCantFindPlayer());
                    return false;
                }

                lang.sendMsg(p, lang.getOutput(t));
                return true;
            }
        }

        lang.sendMsg(p, lang.getOutput(p));
    return false;
    }

}
