package me.yleoft.zHomes.commands;

import com.zhomes.api.event.player.ExecuteHomesCommandEvent;
import me.yleoft.zAPI.inventory.CustomInventory;
import me.yleoft.zHomes.Main;
import me.yleoft.zHomes.utils.HomesUtils;
import me.yleoft.zHomes.utils.LanguageUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static me.yleoft.zAPI.inventory.CustomInventory.configPathInventory;
import static me.yleoft.zAPI.utils.ConfigUtils.formPath;
import static me.yleoft.zHomes.Main.homesMenuPath;

public class HomesCommand extends HomesUtils implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(s instanceof Player))
            return false;
        Player p = (Player)s;
        ExecuteHomesCommandEvent event = new ExecuteHomesCommandEvent(p);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        LanguageUtils.Homes lang = new LanguageUtils.Homes();
        LanguageUtils.CommandsMSG cmdm = new LanguageUtils.CommandsMSG();

        if (args.length >= 1) {
            String player = args[0];
            if (p.hasPermission(CmdHomesOthersPermission())) {
                OfflinePlayer t = Bukkit.getOfflinePlayer(player);
                if (t == null) {
                    lang.sendMsg(p, cmdm.getCantFindPlayer());
                    return false;
                }
                code(p, t, lang, cmdm);
                return true;
            }
        }
        if (cfguExtras.canAfford(p, CmdHomesPermission(), CmdHomesCost())) {
            code(p, p, lang, cmdm);
        }
        return false;
    }

    public void code(Player p, OfflinePlayer t, LanguageUtils.Homes lang, LanguageUtils.CommandsMSG cmdm) {
        if (cfguExtras.canAfford(p, CmdHomesPermission(), CmdHomesCost())) {
            switch (CmdHomesType()) {
                case "menu":
                    p.openInventory(getInventory(p, t));
                    break;
                case "text":
                    if (p != t) {
                        lang.sendMsg(p, lang.getOutput(t));
                        break;
                    }
                    lang.sendMsg(p, lang.getOutput());
                    break;
                default:
                    lang.sendMsg(p, cmdm.getCantFindPlayer());
                    break;
            }
        }
    }

    public Inventory getInventory(Player p, OfflinePlayer t) {
        YamlConfiguration config = Main.fm.getFile(homesMenuPath);
        String path = formPath("Config", "homes-item");
        CustomInventory inv = new CustomInventory(p, config);
        List<String> homes = Main.hu.homesW(t);
        List<String> finalHomes;
        if(p != t) {
            finalHomes = new ArrayList<>();
            inv.setInventoryName(requireNonNull(requireNonNull(config.getString(formPath(configPathInventory, "title-other")))
                    .replace("%player%", requireNonNull(t.getName()))));
            Main.hu.homesW(t).forEach(home -> finalHomes.add(t.getName()+":"+home));
        }else finalHomes = homes;
        inv.setItem(p, config, path, "%home%", finalHomes);
        return inv.getInventory();
    }

}
