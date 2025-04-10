package me.yleoft.zHomes.commands;

import com.zhomes.api.event.player.ExecuteDelhomeCommandEvent;
import com.zhomes.api.event.player.PreExecuteDelhomeCommandEvent;
import me.yleoft.zHomes.utils.HomesUtils;
import me.yleoft.zHomes.utils.LanguageUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DelhomeCommand extends HomesUtils implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(s instanceof Player))
            return false;
        Player p = (Player)s;
        PreExecuteDelhomeCommandEvent preevent = new PreExecuteDelhomeCommandEvent(p);
        Bukkit.getPluginManager().callEvent(preevent);
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
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        home = event.getHome();

        if (hasHome(p, home)) {
            if (cfguExtras.canAfford(p, CmdDelhomeCost())) {
                delHome(p, home);
                lang.sendMsg(p, lang.getOutput(home));
            }
        } else {
            lang.sendMsg(p, cmdm.getHomeDoesntExist());
        }
    }

    public void code2(Player p, String home, LanguageUtils.Delhome lang, LanguageUtils.CommandsMSG cmdm) {
        ExecuteDelhomeCommandEvent event = new ExecuteDelhomeCommandEvent(p, home);
        Bukkit.getPluginManager().callEvent(event);
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
            if (cfguExtras.canAfford(p, CmdDelhomeCost())) {
                delHome(t, home);
                lang.sendMsg(p, lang.getOutput(ofchome));
            }
        } else {
            lang.sendMsg(p, cmdm.getHomeDoesntExistOthers(t));
        }
    }

}
