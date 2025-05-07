package me.yleoft.zHomes.commands;

import me.yleoft.zHomes.Main;
import com.zhomes.api.event.player.ExecuteHomeCommandEvent;
import com.zhomes.api.event.player.PreExecuteHomeCommandEvent;
import me.yleoft.zHomes.utils.ConfigUtils;
import me.yleoft.zHomes.utils.HomesUtils;
import me.yleoft.zHomes.utils.LanguageUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HomeCommand extends HomesUtils implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(s instanceof Player))
            return false;
        Player p = (Player)s;
        LanguageUtils.CommandsMSG cmdm = new LanguageUtils.CommandsMSG();

        if (!p.hasPermission(CmdHomePermission())) {
            cmdm.sendMsg(p, cmdm.getNoPermission());
            return false;
        }

        PreExecuteHomeCommandEvent preevent = new PreExecuteHomeCommandEvent(p);
        Bukkit.getPluginManager().callEvent(preevent);
        if (preevent.isCancelled()) return false;

        LanguageUtils.Home lang = new LanguageUtils.Home();

        if (args.length >= 1) {
            String home = args[0];
            if (home.contains(":")) {
                if (p.hasPermission(CmdHomeOthersPermission())) {
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
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        home = event.getHome();

        if (hasHome(p, home)) {
            if (cfguExtras.canAfford(p, CmdHomePermission(), CmdHomeCost())) {
                teleportPlayer(p, home);
            }
        } else {
            lang.sendMsg(p, cmdm.getHomeDoesntExist());
        }
    }

    public void code2(Player p, String home, LanguageUtils.Home lang, LanguageUtils.CommandsMSG cmdm) {
        ExecuteHomeCommandEvent event = new ExecuteHomeCommandEvent(p, home);
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
            return;
        }
        if (hasHome(t, home)) {
            if (cfguExtras.canAfford(p, CmdHomePermission(), CmdHomeCost())) {
                teleportPlayer(p, t, home);
            }
        } else {
            lang.sendMsg(p, cmdm.getHomeDoesntExistOthers(t));
        }
    }

}
