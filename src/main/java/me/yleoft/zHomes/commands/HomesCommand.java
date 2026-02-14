package me.yleoft.zHomes.commands;

import com.zhomes.api.event.player.ExecuteHomesCommandEvent;
import me.yleoft.zAPI.command.Command;
import me.yleoft.zAPI.command.Parameter;
import me.yleoft.zAPI.configuration.Path;
import me.yleoft.zAPI.inventory.InventoryBuilder;
import me.yleoft.zAPI.player.PlayerHandler;
import me.yleoft.zAPI.utility.TextFormatter;
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

public class HomesCommand extends HomesUtils implements Command {

    private final Parameter typeParameter = new Parameter() {
        @Override
        public @NotNull String name() {
            return "type";
        }

        @Override
        public @NotNull List<String> aliases() {
            return List.of("t", "displaytype", "dt");
        }

        @Override
        public int minArgs() {
            return 1;
        }

        @Override
        public int maxArgs() {
            return 1;
        }

        @Override
        public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String[] parameterArgs) {
            return List.of("menu", "text");
        }
    };

    public HomesCommand() {
        addParameter(typeParameter);
    }

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

        String displaytype = zHomes.getConfigYAML().getHomesDisplayType();
        String[] displayArgs = getParameter(sender, typeParameter);
        if (displayArgs != null && displayArgs.length > 0) {
            displaytype = displayArgs[0];
        }
        if (args.length >= 1) {
            if(TextFormatter.isInteger(args[0])) {
                int page = Integer.parseInt(args[0]);
                if (page < 1) {
                    message(player, zHomes.getLanguageYAML().getHomesInvalidPage());
                    return;
                }
                code(player, player, displaytype, page);
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
                        code(player, t, displaytype, page);
                        return;
                    }
                }
                code(player, t, displaytype);
                return;
            }
        }
        code(player, player, displaytype);
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String[] args) {
        if(fullArgs.length >= 2 && fullArgs.length <= 3) {
            return List.of("-type");
        }
        List<String> completions = new ArrayList<>();
        if (sender.hasPermission(zHomes.getConfigYAML().getHomesOthersPermission())) {
            Bukkit.getOnlinePlayers().forEach(online -> completions.add(online.getName()));
        }
        return completions;
    }

    public void code(String type, Player p, OfflinePlayer t, int page) {
        if (HookRegistry.VAULT.canAfford(p, zHomes.getConfigYAML().getHomesCommandPermission(), zHomes.getConfigYAML().getHomesCommandCost())) {
            switch (type.toLowerCase()) {
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
    public void code(Player p, OfflinePlayer t, String displaytype, int page) {
        code(displaytype, p, t, page);
    }
    public void code(Player p, OfflinePlayer t, String displaytype) {
        code(displaytype, p, t, 1);
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