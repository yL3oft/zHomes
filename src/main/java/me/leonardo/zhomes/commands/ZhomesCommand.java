package me.leonardo.zhomes.commands;

import me.leonardo.zhomes.Main;
import me.leonardo.zhomes.api.events.ExecuteZhomesCommandEvent;
import me.leonardo.zhomes.utils.ConfigUtils;
import me.leonardo.zhomes.utils.LanguageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ZhomesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!(s instanceof Player)) {

            return false;
        }
        Player p = (Player)s;
        ExecuteZhomesCommandEvent event = new ExecuteZhomesCommandEvent(p);
        Bukkit.getPluginManager().callEvent(event);
        if(event.isCancelled()) return false;

        LanguageUtils.Zhomes lang = new LanguageUtils.Zhomes();
        LanguageUtils.Zhomes.ZhomesReload lang2 = new LanguageUtils.Zhomes.ZhomesReload();

        if(args.length >= 1) {
            String subcmd = args[0];

            if(
                    subcmd.equalsIgnoreCase("reload") ||
                    subcmd.equalsIgnoreCase("rl")
            ) {
                Main.main.reloadConfig();
                Main.fm.fu.reloadConfig();
                Main.fm.fu2.reloadConfig();
                Main.cfgu = new ConfigUtils();

                lang2.sendMsg(p, lang2.getOutput(p));
            }
        }else {
            lang.sendMsg(p, lang.getUsage());
        }
    return false;
    }

}
