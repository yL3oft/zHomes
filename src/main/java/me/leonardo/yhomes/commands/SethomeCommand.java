package me.leonardo.yhomes.commands;

import me.leonardo.yhomes.Main;
import me.leonardo.yhomes.utils.storage.HomesUtilsYAML;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SethomeCommand implements CommandExecutor{

    HomesUtilsYAML hu = Main.hu;

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!(s instanceof Player)) {

            return false;
        }
        Player p = (Player)s;
        OfflinePlayer ofp = (OfflinePlayer)p;

        if(args.length >= 1) {
            String home = args[0];

            if(true /**homes limit */) {
                if(hu.hasHome(ofp, home)) {
                    hu.addHome(ofp, home, p.getLocation());
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
