package me.leonardo.yhomes.commands;

import me.leonardo.yhomes.Main;
import me.leonardo.yhomes.utils.ConfigUtils;
import me.leonardo.yhomes.utils.storage.HomesUtilsYAML;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
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

        if(args.length >= 1) {
            String home = args[0];

            if(hasHome(p, home)) {
                teleportPlayer(p, home);
            }else {
                // Message
            }
        }else {
            // Message
        }
    return false;
    }

}
