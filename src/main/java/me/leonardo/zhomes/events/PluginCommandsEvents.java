package me.leonardo.zhomes.events;

import me.leonardo.zhomes.Main;
import me.leonardo.zhomes.api.events.*;
import me.leonardo.zhomes.utils.LanguageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.text.DecimalFormat;

public class PluginCommandsEvents implements Listener {

    @EventHandler
    public void onPlayerPreExecuteSethomeCommandEvent(PreExecuteSethomeCommandEvent e) {
        Player p = e.getPlayer();
        LanguageUtils.CommandsMSG lang = new LanguageUtils.CommandsMSG();
        LanguageUtils.Sethome lang2 = new LanguageUtils.Sethome();
        if(!p.hasPermission("zhomes.commands.sethome")) {
            e.setCancelled(true);
            lang.sendMsg(p, lang.getNoPermission());
            return;
        }

        long now = System.currentTimeMillis();
        long need = (long) (lang2.getCooldown()*1000L);
        if(Main.SethomeCooldown.containsKey(p)) {
            long then = Main.SethomeCooldown.get(p);
            long thenAdd = then+need;
            long dif = thenAdd-now;

            if(thenAdd >= now) {
                e.setCancelled(true);

                double gottawait = dif/1000D;
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(lang.getDecimalPoints());
                String gottawaitS = df.format(gottawait);

                lang.sendMsg(p, lang.getCooldown(gottawaitS));
                return;
            }

            Main.SethomeCooldown.remove(p);
        }

        Main.SethomeCooldown.put(p, now);
    }

    @EventHandler
    public void onPlayerPreExecuteDelhomeCommandEvent(PreExecuteDelhomeCommandEvent e) {
        Player p = e.getPlayer();
        LanguageUtils.CommandsMSG lang = new LanguageUtils.CommandsMSG();
        LanguageUtils.Delhome lang2 = new LanguageUtils.Delhome();
        if(!p.hasPermission("zhomes.commands.delhome")) {
            e.setCancelled(true);
            lang.sendMsg(p, lang.getNoPermission());
            return;
        }

        long now = System.currentTimeMillis();
        long need = (long) (lang2.getCooldown()*1000L);
        if(Main.DelhomeCooldown.containsKey(p)) {
            long then = Main.DelhomeCooldown.get(p);
            long thenAdd = then+need;
            long dif = thenAdd-now;

            if(thenAdd >= now) {
                e.setCancelled(true);

                double gottawait = dif/1000D;
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(lang.getDecimalPoints());
                String gottawaitS = df.format(gottawait);

                lang.sendMsg(p, lang.getCooldown(gottawaitS));
                return;
            }

            Main.DelhomeCooldown.remove(p);
        }

        Main.DelhomeCooldown.put(p, now);
    }

    @EventHandler
    public void onPlayerPreExecuteHomeCommandEvent(PreExecuteHomeCommandEvent e) {
        Player p = e.getPlayer();
        LanguageUtils.CommandsMSG lang = new LanguageUtils.CommandsMSG();
        LanguageUtils.Home lang2 = new LanguageUtils.Home();
        if(!p.hasPermission("zhomes.commands.home")) {
            e.setCancelled(true);
            lang.sendMsg(p, lang.getNoPermission());
            return;
        }

        long now = System.currentTimeMillis();
        long need = (long) (lang2.getCooldown()*1000L);
        if(Main.HomeCooldown.containsKey(p)) {
            long then = Main.HomeCooldown.get(p);
            long thenAdd = then+need;
            long dif = thenAdd-now;

            if(thenAdd >= now) {
                e.setCancelled(true);

                double gottawait = dif/1000D;
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(lang.getDecimalPoints());
                String gottawaitS = df.format(gottawait);

                lang.sendMsg(p, lang.getCooldown(gottawaitS));
                return;
            }

            Main.HomeCooldown.remove(p);
        }

        Main.HomeCooldown.put(p, now);
    }

    @EventHandler
    public void onPlayerExecuteHomesCommandEvent(ExecuteHomesCommandEvent e) {
        Player p = e.getPlayer();
        LanguageUtils.CommandsMSG lang = new LanguageUtils.CommandsMSG();
        LanguageUtils.Homes lang2 = new LanguageUtils.Homes();
        if(!p.hasPermission("zhomes.commands.homes")) {
            e.setCancelled(true);
            lang.sendMsg(p, lang.getNoPermission());
            return;
        }

        long now = System.currentTimeMillis();
        long need = (long) (lang2.getCooldown()*1000L);
        if(Main.HomesCooldown.containsKey(p)) {
            long then = Main.HomesCooldown.get(p);
            long thenAdd = then+need;
            long dif = thenAdd-now;

            if(thenAdd >= now) {
                e.setCancelled(true);

                double gottawait = dif/1000D;
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(lang.getDecimalPoints());
                String gottawaitS = df.format(gottawait);

                lang.sendMsg(p, lang.getCooldown(gottawaitS));
                return;
            }

            Main.HomesCooldown.remove(p);
        }

        Main.HomesCooldown.put(p, now);
    }

    @EventHandler
    public void onPlayerExecuteZhomesCommandEvent(ExecuteZhomesCommandEvent e) {
        Player p = e.getPlayer();
        LanguageUtils.CommandsMSG lang = new LanguageUtils.CommandsMSG();
        LanguageUtils.Zhomes lang2 = new LanguageUtils.Zhomes();
        if(!p.hasPermission("zhomes.commands.zhomes")) {
            e.setCancelled(true);
            lang.sendMsg(p, lang.getNoPermission());
            return;
        }

        long now = System.currentTimeMillis();
        long need = (long) (lang2.getCooldown()*1000L);
        if(Main.ZhomesCooldown.containsKey(p)) {
            long then = Main.ZhomesCooldown.get(p);
            long thenAdd = then+need;
            long dif = thenAdd-now;

            if(thenAdd >= now) {
                e.setCancelled(true);

                double gottawait = dif/1000D;
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(lang.getDecimalPoints());
                String gottawaitS = df.format(gottawait);

                lang.sendMsg(p, lang.getCooldown(gottawaitS));
                return;
            }

            Main.ZhomesCooldown.remove(p);
        }

        Main.ZhomesCooldown.put(p, now);
    }

}
