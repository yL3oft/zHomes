package me.yleoft.zHomes.commands;

import me.yleoft.zHomes.Main;
import me.yleoft.zHomes.api.events.ExecuteHomeCommandEvent;
import me.yleoft.zHomes.api.events.PreExecuteHomeCommandEvent;
import me.yleoft.zHomes.utils.ConfigUtils;
import me.yleoft.zHomes.utils.HomesUtils;
import me.yleoft.zHomes.utils.LanguageUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class HomeCommand extends HomesUtils implements CommandExecutor {

    ConfigUtils cfgu = Main.cfgu;

    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!(s instanceof Player))
            return false;
        Player p = (Player)s;
        PreExecuteHomeCommandEvent preevent = new PreExecuteHomeCommandEvent(p);
        Bukkit.getPluginManager().callEvent((Event)preevent);
        if (preevent.isCancelled()) return false;

        LanguageUtils.Home lang = new LanguageUtils.Home();
        LanguageUtils.CommandsMSG cmdm = new LanguageUtils.CommandsMSG();

        if (args.length >= 1) {
            String home = args[0];
            if (home.contains(":")) {
                if (p.hasPermission(this.cfgu.CmdHomeOthersPermission())) {
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

    public void code1(Player p, String home, LanguageUtils.Home lang, LanguageUtils.CommandsMSG cmdm) {
        ExecuteHomeCommandEvent event = new ExecuteHomeCommandEvent(p, home);
        Bukkit.getPluginManager().callEvent((Event)event);
        if (event.isCancelled())
            return;
        home = event.getHome();

        if (hasHome((OfflinePlayer)p, home)) {
            lang.sendMsg(p, lang.getOutput(home));
            teleportPlayer(p, home);
        } else {
            lang.sendMsg(p, cmdm.getHomeDoesntExist());
        }
    }

    public void code2(Player p, String home, LanguageUtils.Home lang, LanguageUtils.CommandsMSG cmdm) {
        ExecuteHomeCommandEvent event = new ExecuteHomeCommandEvent(p, home);
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
            lang.sendMsg(p, lang.getOutput(ofchome));
            teleportPlayer(p, t, home);
        } else {
            lang.sendMsg(p, cmdm.getHomeDoesntExistOthers(t));
        }
    }

}
