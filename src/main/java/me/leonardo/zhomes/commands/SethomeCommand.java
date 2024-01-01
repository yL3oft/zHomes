package me.leonardo.zhomes.commands;

import me.leonardo.zhomes.api.events.ExecuteSethomeCommandEvent;
import me.leonardo.zhomes.api.events.PreExecuteSethomeCommandEvent;
import me.leonardo.zhomes.utils.LanguageUtils;
import me.leonardo.zhomes.utils.storage.HomesUtilsYAML;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SethomeCommand extends HomesUtilsYAML implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!(s instanceof Player)) {

            return false;
        }
        Player p = (Player)s;
        PreExecuteSethomeCommandEvent preevent = new PreExecuteSethomeCommandEvent(p);
        if(preevent.isCancelled()) return false;

        LanguageUtils.Sethome lang = new LanguageUtils.Sethome();
        LanguageUtils.CommandsMSG cmdm = new LanguageUtils.CommandsMSG();

        if(args.length >= 1) {
            String home = args[0];
            ExecuteSethomeCommandEvent event = new ExecuteSethomeCommandEvent(p, home);
            if(event.isCancelled()) return false;
            home = event.getHome();

            if(true /**homes limit */) {
                if(!hasHome(p, home)) {
                    addHome(p, home, p.getLocation());
                    lang.sendMsg(p, lang.getOutput(home));
                }else {
                    lang.sendMsg(p, cmdm.getHomeAlreadyExist());
                }
            }else {
                // Message
            }
        }else {
            lang.sendMsg(p, lang.getUsage());
        }
    return false;
    }

}
