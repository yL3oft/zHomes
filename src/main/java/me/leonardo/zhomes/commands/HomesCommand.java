package me.leonardo.zhomes.commands;

import me.leonardo.zhomes.api.events.ExecuteHomesCommandEvent;
import me.leonardo.zhomes.utils.LanguageUtils;
import me.leonardo.zhomes.utils.storage.HomesUtilsYAML;
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
        if(event.isCancelled()) return false;

        LanguageUtils.Homes lang = new LanguageUtils.Homes();

        lang.sendMsg(p, lang.getOutput(p));
    return false;
    }

}
