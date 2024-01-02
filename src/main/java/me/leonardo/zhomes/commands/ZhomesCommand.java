package me.leonardo.zhomes.commands;

import me.leonardo.zhomes.Main;
import me.leonardo.zhomes.api.events.ExecuteZhomesCommandEvent;
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

        if(args.length >= 1) {
            String subcmd = args[0];

            if(
                    subcmd.equalsIgnoreCase("reload") ||
                    subcmd.equalsIgnoreCase("rl")
            ) {
                Main.main.reloadConfig();
                Main.fm.reloadCfg();
                Main.fm.reloadCfg2();
            }
        }
    return false;
    }

}
