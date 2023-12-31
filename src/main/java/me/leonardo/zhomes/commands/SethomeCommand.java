package me.leonardo.zhomes.commands;

import me.leonardo.zhomes.utils.storage.HomesUtilsYAML;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SethomeCommand extends HomesUtilsYAML implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!(s instanceof Player)) {

            return false;
        }
        Player p = (Player)s;

        if(args.length >= 1) {
            String home = args[0];

            if(true /**homes limit */) {
                if(hasHome(p, home)) {
                    addHome(p, home, p.getLocation());
                }else {
                    // Message
                }
            }else {
                // Message
            }
        }else {
            // Message
        }
    return false;
    }

}
