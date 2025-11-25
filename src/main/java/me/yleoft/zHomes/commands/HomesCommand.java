package me.yleoft.zHomes.commands;

import com.zhomes.api.event.player.ExecuteHomesCommandEvent;
import me.yleoft.zAPI.inventory.CustomInventory;
import me.yleoft.zAPI.managers.FileManager;
import me.yleoft.zAPI.utils.PlayerUtils;
import me.yleoft.zAPI.utils.StringUtils;
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
import java.util.Collections;
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
        LanguageUtils.CommandsMSG cmdm = new LanguageUtils.CommandsMSG();

        if (!p.hasPermission(CmdHomesPermission())) {
            cmdm.sendMsg(p, cmdm.getNoPermission());
            return false;
        }

        ExecuteHomesCommandEvent event = new ExecuteHomesCommandEvent(p);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        LanguageUtils.Homes lang = new LanguageUtils.Homes();

        if (args.length >= 1) {
            if(StringUtils.isInteger(args[0])) {
                int page = Integer.parseInt(args[0]);
                if (page < 1) {
                    lang.sendMsg(p, lang.getInvalidPage());
                    return false;
                }
                code(p, p, page, lang, cmdm);
                return true;
            }
            String player = args[0];
            if (p.hasPermission(CmdHomesOthersPermission())) {
                OfflinePlayer t = PlayerUtils.getOfflinePlayer(player);
                if (t == null) {
                    lang.sendMsg(p, cmdm.getCantFindPlayer());
                    return false;
                }
                if(args.length >= 2) {
                    if(StringUtils.isInteger(args[1])) {
                        int page = Integer.parseInt(args[1]);
                        if (page < 1) {
                            lang.sendMsg(p, lang.getInvalidPage());
                            return false;
                        }
                        code(p, t, page, lang, cmdm);
                        return true;
                    }
                }
                code(p, t, lang, cmdm);
                return true;
            }
        }
        code(p, p, lang, cmdm);
        return false;
    }

    public void code(String type, Player p, OfflinePlayer t, int page, LanguageUtils.Homes lang, LanguageUtils.CommandsMSG cmdm) {
        if (cfguExtras.canAfford(p, CmdHomesPermission(), CmdHomesCost())) {
            switch (type) {
                case "menu":
                    try {
                        p.openInventory(getInventory(p, t, page));
                    }catch (Exception e) {
                        code("text", p, t, page, lang, cmdm);
                        return;
                    }
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
    public void code(Player p, OfflinePlayer t, int page, LanguageUtils.Homes lang, LanguageUtils.CommandsMSG cmdm) {
        code(CmdHomesType(), p, t, page, lang, cmdm);
    }
    public void code(Player p, OfflinePlayer t, LanguageUtils.Homes lang, LanguageUtils.CommandsMSG cmdm) {
        code(CmdHomesType(), p, t, 1, lang, cmdm);
    }

    public Inventory getInventory(Player p, OfflinePlayer t, int page) {
        YamlConfiguration config = FileManager.getFile(homesMenuPath);
        String path = formPath("Config", "homes-item");
        String pathPP = formPath("Config", "previous-page-item");
        String pathNP = formPath("Config", "next-page-item");
        CustomInventory inv = new CustomInventory(p, config);
        List<String> homes = Main.hu.homesW(t);
        List<String> finalHomes;
        if(p != t) {
            finalHomes = new ArrayList<>();
            inv.setInventoryName(t, requireNonNull(requireNonNull(config.getString(formPath(configPathInventory, "title-other")))
                    .replace("%player%", requireNonNull(t.getName()))));
            homes.forEach(home -> finalHomes.add(t.getName()+":"+home));
        }else finalHomes = homes;
        String slotString = config.getString(formPath(path, "slot"));
        int slots;
        if (slotString.matches("\\d+-\\d+")) {
            String[] parts = slotString.split("-");
            int start = Integer.parseInt(parts[0]);
            int end = Integer.parseInt(parts[1]);
            slots = end - start + 1;
        }else slots = Integer.parseInt(slotString)+1;
        if (page > 1) {
            int startIndex = 0;
            int endIndex = Math.min(startIndex + (slots*(page-1)), finalHomes.size());
            finalHomes.subList(startIndex, endIndex).clear();
            inv.setItem(p, config, pathPP, "%previous-page%", Collections.singletonList(String.valueOf(page - 1)));
        }
        if(!finalHomes.isEmpty()) {
            if (finalHomes.size() > slots) {
                inv.setItem(p, config, pathNP, "%next-page%", Collections.singletonList(String.valueOf(page + 1)));
            }
            inv.setItem(p, config, path, "%home%", finalHomes);
        }
        return inv.getInventory();
    }

}
