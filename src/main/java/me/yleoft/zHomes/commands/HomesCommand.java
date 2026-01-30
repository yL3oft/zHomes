package me.yleoft.zHomes.commands;

import com.zhomes.api.event.player.ExecuteHomesCommandEvent;
import me.yleoft.zAPI.command.Command;
import me.yleoft.zAPI.configuration.Path;
import me.yleoft.zAPI.inventory.InventoryBuilder;
import me.yleoft.zAPI.player.PlayerHandler;
import me.yleoft.zAPI.utility.TextFormatter;
import me.yleoft.zHomes.configuration.menus.menuhomesYAML;
import me.yleoft.zHomes.hooks.HookRegistry;
import me.yleoft.zHomes.utility.HomesUtils;
import me.yleoft.zHomes.zHomes;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static java.util.Objects.requireNonNull;

public class HomesCommand extends HomesUtils implements Command {

    @Override
    public @NotNull String name() {
        return zHomes.getConfigYAML().getHomesCommand();
    }

    @Override
    public String description() {
        return zHomes.getConfigYAML().getHomesCommandDescription();
    }

    @Override
    public List<String> aliases() {
        return zHomes.getConfigYAML().getHomesCommandAliases();
    }

    @Override
    public String permission() {
        return zHomes.getConfigYAML().getHomesCommandPermission();
    }

    @Override
    public double cooldownTime() {
        return zHomes.getConfigYAML().getHomesCommandCooldown();
    }

    @Override
    public boolean prexecute(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String @NotNull [] args) {
        if(!isPlayer(sender)) return false;
        ExecuteHomesCommandEvent preevent = new ExecuteHomesCommandEvent((Player) sender);
        Bukkit.getPluginManager().callEvent(preevent);
        return preevent.isCancelled();
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player player)) {
            if (args.length != 1) {
                message(sender, zHomes.getLanguageYAML().getCantFindPlayer());
                return;
            }
            OfflinePlayer t = PlayerHandler.getOfflinePlayer(args[0]);
            if (t == null) {
                message(sender, zHomes.getLanguageYAML().getCantFindPlayer());
                return;
            }
            message(sender, zHomes.getLanguageYAML().getHomesOutputOthers(t));
            return;
        }

        if (args.length >= 1) {
            if(TextFormatter.isInteger(args[0])) {
                int page = Integer.parseInt(args[0]);
                if (page < 1) {
                    message(player, zHomes.getLanguageYAML().getHomesInvalidPage());
                    return;
                }
                code(player, player, page);
                return;
            }
            String target = args[0];
            if (player.hasPermission(zHomes.getConfigYAML().getHomesOthersPermission())) {
                OfflinePlayer t = PlayerHandler.getOfflinePlayer(target);
                if (t == null) {
                    message(player, zHomes.getLanguageYAML().getCantFindPlayer());
                    return;
                }
                if(args.length >= 2) {
                    if(TextFormatter.isInteger(args[1])) {
                        int page = Integer.parseInt(args[1]);
                        if (page < 1) {
                            message(player, zHomes.getLanguageYAML().getHomesInvalidPage());
                            return;
                        }
                        code(player, t, page);
                        return;
                    }
                }
                code(player, t);
                return;
            }
        }
        code(player, player);
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        if (sender.hasPermission(zHomes.getConfigYAML().getHomesOthersPermission())) {
            Bukkit.getOnlinePlayers().forEach(online -> completions.add(online.getName()));
        }
        return completions;
    }

    public void code(String type, Player p, OfflinePlayer t, int page) {
        if (HookRegistry.VAULT.canAfford(p, zHomes.getConfigYAML().getHomesCommandPermission(), zHomes.getConfigYAML().getHomesCommandCost())) {
            switch (type) {
                case "menu":
                    try {
                        p.openInventory(getInventory(p, t, page));
                    }catch (Exception e) {
                        zHomes.getInstance().getLoggerInstance().warn("Failed to open homes menu for player " + p.getName() + ". Falling back to text display.", e);
                        code("text", p, t, page);
                        return;
                    }
                    break;
                case "text":
                    if (p != t) {
                        message(p, zHomes.getLanguageYAML().getHomesOutputOthers(t));
                        break;
                    }
                    message(p, zHomes.getLanguageYAML().getHomesOutputSelf(p));
                    break;
                default:
                    message(p, zHomes.getLanguageYAML().getCantFindPlayer());
                    break;
            }
        }
    }
    public void code(Player p, OfflinePlayer t, int page) {
        code(zHomes.getConfigYAML().getHomesDisplayType(), p, t, page);
    }
    public void code(Player p, OfflinePlayer t) {
        code(zHomes.getConfigYAML().getHomesDisplayType(), p, t, 1);
    }

    public Inventory getInventory(Player p, OfflinePlayer t, int page) {
        YamlConfiguration config = (YamlConfiguration) zHomes.menuHomes.getConfig();

        Map<String, String> customPlaceholders = new HashMap<>();
        if(t != null) {
            customPlaceholders.put("%targetplayer%", t.getName());
            customPlaceholders.put("%target%", t.getName());
        }
        customPlaceholders.put("%page%", String.valueOf(page));

        InventoryBuilder inv = new InventoryBuilder(p, config, customPlaceholders);

        if(p != t) {
            String titleOther = zHomes.menuHomes.getString(Path.formPath(InventoryBuilder.KEY_INVENTORY, "title-other"));
            titleOther = titleOther.replace("%target%", t.getName());
            inv.setTitle(p, titleOther);
        }

        return inv.getInventory();
    }

}