package me.leonardo.zhomes.commands;

import me.leonardo.zhomes.Main;
import me.leonardo.zhomes.utils.ConfigUtils;
import me.leonardo.zhomes.utils.LanguageUtils;
import me.leonardo.zhomes.utils.storage.HomesUtilsYAML;
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
        LanguageUtils.Home lang = new LanguageUtils.Home();

        if(args.length >= 1) {
            String home = args[0];

            if(hasHome(p, home)) {
                lang.sendMsg(p, lang.getOutput(home));
                teleportPlayer(p, home);
            }else {
                // Message
            }
        }else {
            lang.sendMsg(p, lang.getUsage());
        }
    return false;
    }

}
