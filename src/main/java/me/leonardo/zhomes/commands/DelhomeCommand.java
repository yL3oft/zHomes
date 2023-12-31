package me.leonardo.zhomes.commands;

import me.leonardo.zhomes.utils.storage.HomesUtilsYAML;
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

        if(args.length >= 1) {
            String home = args[0];

            if(hasHome(p, home)) {
                delHome(p, home);
            }else {
                // Message
            }
        }else {
            // Message
        }
    return false;
    }

}
