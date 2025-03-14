package me.yleoft.zHomes.commands;

import me.yleoft.zHomes.api.events.ExecuteDelhomeCommandEvent;
import me.yleoft.zHomes.api.events.PreExecuteDelhomeCommandEvent;
import me.yleoft.zHomes.utils.HomesUtils;
import me.yleoft.zHomes.utils.LanguageUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class DelhomeCommand extends HomesUtils implements CommandExecutor {

    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!(s instanceof Player))
            return false;
        Player p = (Player)s;
        PreExecuteDelhomeCommandEvent preevent = new PreExecuteDelhomeCommandEvent(p);
        Bukkit.getPluginManager().callEvent((Event)preevent);
        if (preevent.isCancelled()) return false;

        LanguageUtils.Delhome lang = new LanguageUtils.Delhome();
        LanguageUtils.CommandsMSG cmdm = new LanguageUtils.CommandsMSG();

        if (args.length >= 1) {
            String home = args[0];
            if (home.contains(":")) {
                if (p.hasPermission(CmdDelhomeOthersPermission())) {
                    code2(p, home, lang, cmdm);
                } else {
                    lang.sendMsg(p, cmdm.getCantUse2Dot());
                }
            } else {
                code1(p, home, lang, cmdm);
            }
        } else {
            lang.sendMsg(p, lang.getUsage());
        }
        return false;
    }

    public void code1(Player p, String home, LanguageUtils.Delhome lang, LanguageUtils.CommandsMSG cmdm) {
        ExecuteDelhomeCommandEvent event = new ExecuteDelhomeCommandEvent(p, home);
        Bukkit.getPluginManager().callEvent((Event)event);
        if (event.isCancelled())
            return;
        home = event.getHome();

        if (hasHome(p, home)) {
            delHome(p, home);
            lang.sendMsg(p, lang.getOutput(home));
        } else {
            lang.sendMsg(p, cmdm.getHomeDoesntExist());
        }
    }

    public void code2(Player p, String home, LanguageUtils.Delhome lang, LanguageUtils.CommandsMSG cmdm) {
        ExecuteDelhomeCommandEvent event = new ExecuteDelhomeCommandEvent(p, home);
        Bukkit.getPluginManager().callEvent((Event)event);
        if (event.isCancelled())
            return;
        String ofchome = event.getHome();
        String[] homeS = ofchome.split(":");
        if(homeS.length < 2) return;
        String player = homeS[0];
        home = homeS[1];

        OfflinePlayer t = Bukkit.getOfflinePlayer(player);
        if (t == null) {
            lang.sendMsg(p, cmdm.getCantFindPlayer());
            return;
        }
        if (hasHome(t, home)) {
            delHome(t, home);
            lang.sendMsg(p, lang.getOutput(ofchome));
        } else {
            lang.sendMsg(p, cmdm.getHomeDoesntExistOthers(t));
        }
    }

}
