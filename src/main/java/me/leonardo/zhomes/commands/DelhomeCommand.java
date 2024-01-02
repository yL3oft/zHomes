package me.leonardo.zhomes.commands;

import me.leonardo.zhomes.api.events.ExecuteDelhomeCommandEvent;
import me.leonardo.zhomes.api.events.PreExecuteDelhomeCommandEvent;
import me.leonardo.zhomes.utils.LanguageUtils;
import me.leonardo.zhomes.utils.storage.HomesUtilsYAML;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelhomeCommand extends HomesUtilsYAML implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!(s instanceof Player)) {

            return false;
        }
        Player p = (Player)s;
        PreExecuteDelhomeCommandEvent preevent = new PreExecuteDelhomeCommandEvent(p);
        Bukkit.getPluginManager().callEvent(preevent);
        if(preevent.isCancelled()) return false;

        LanguageUtils.Delhome lang = new LanguageUtils.Delhome();
        LanguageUtils.CommandsMSG cmdm = new LanguageUtils.CommandsMSG();

        if(args.length >= 1) {
            String home = args[0];
            ExecuteDelhomeCommandEvent event = new ExecuteDelhomeCommandEvent(p, home);
            Bukkit.getPluginManager().callEvent(event);
            if(event.isCancelled()) return false;
            home = event.getHome();

            if(hasHome(p, home)) {
                delHome(p, home);
                lang.sendMsg(p, lang.getOutput(home));
            }else {
                lang.sendMsg(p, cmdm.getHomeDoesntExist());
            }
        }else {
            lang.sendMsg(p, lang.getUsage());
        }
    return false;
    }

}
