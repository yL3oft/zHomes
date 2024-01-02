package me.leonardo.zhomes.commands;

import me.leonardo.zhomes.Main;
import me.leonardo.zhomes.api.events.ExecuteHomeCommandEvent;
import me.leonardo.zhomes.api.events.PreExecuteHomeCommandEvent;
import me.leonardo.zhomes.utils.ConfigUtils;
import me.leonardo.zhomes.utils.LanguageUtils;
import me.leonardo.zhomes.utils.storage.HomesUtilsYAML;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand  extends HomesUtilsYAML implements CommandExecutor {

    ConfigUtils cfgu = Main.cfgu;

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!(s instanceof Player)) {

            return false;
        }
        Player p = (Player)s;
        PreExecuteHomeCommandEvent preevent = new PreExecuteHomeCommandEvent(p);
        Bukkit.getPluginManager().callEvent(preevent);
        if(preevent.isCancelled()) return false;

        LanguageUtils.Home lang = new LanguageUtils.Home();
        LanguageUtils.CommandsMSG cmdm = new LanguageUtils.CommandsMSG();

        if(args.length >= 1) {
            String home = args[0];
            ExecuteHomeCommandEvent event = new ExecuteHomeCommandEvent(p, home);
            Bukkit.getPluginManager().callEvent(event);
            if(event.isCancelled()) return false;
            home = event.getHome();

            if(hasHome(p, home)) {
                lang.sendMsg(p, lang.getOutput(home));
                teleportPlayer(p, home);
            }else {
                lang.sendMsg(p, cmdm.getHomeDoesntExist());
            }
        }else {
            lang.sendMsg(p, lang.getUsage());
        }
    return false;
    }

}
