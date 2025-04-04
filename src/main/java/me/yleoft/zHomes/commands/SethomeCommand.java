package me.yleoft.zHomes.commands;

import com.zhomes.api.event.player.ExecuteSethomeCommandEvent;
import com.zhomes.api.event.player.PreExecuteSethomeCommandEvent;
import me.yleoft.zHomes.utils.HomesUtils;
import me.yleoft.zHomes.utils.LanguageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class SethomeCommand extends HomesUtils implements CommandExecutor {

    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!(s instanceof Player))
            return false;
        Player p = (Player)s;
        PreExecuteSethomeCommandEvent preevent = new PreExecuteSethomeCommandEvent(p);
        Bukkit.getPluginManager().callEvent((Event)preevent);
        if (preevent.isCancelled()) return false;

        LanguageUtils.Sethome lang = new LanguageUtils.Sethome();
        LanguageUtils.CommandsMSG cmdm = new LanguageUtils.CommandsMSG();

        if (args.length >= 1) {
            String home = args[0];
            ExecuteSethomeCommandEvent event = new ExecuteSethomeCommandEvent(p, home);
            Bukkit.getPluginManager().callEvent((Event)event);
            if (event.isCancelled())
                return false;
            home = event.getHome();

            if (!inMaxLimit(p)) {
                if (!hasHome(p, home)) {
                    if (cfguExtras.canAfford(p, CmdSethomeCost())) {
                        addHome(p, home, p.getLocation());
                        lang.sendMsg(p, lang.getOutput(home));
                    }
                } else {
                    lang.sendMsg(p, cmdm.getHomeAlreadyExist());
                }
            } else {
                lang.sendMsg(p, lang.getLimitReached(p));
            }
        } else {
            lang.sendMsg(p, lang.getUsage());
        }
        return false;
    }

}
