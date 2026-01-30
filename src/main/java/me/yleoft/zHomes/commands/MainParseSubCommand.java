package me.yleoft.zHomes.commands;

import me.yleoft.zAPI.command.SubCommand;
import me.yleoft.zAPI.player.PlayerHandler;
import me.yleoft.zAPI.utility.TextFormatter;
import me.yleoft.zHomes.hooks.HookRegistry;
import me.yleoft.zHomes.zHomes;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class MainParseSubCommand implements SubCommand {

    @Override
    public @NotNull String name() {
        return "parse";
    }

    @Override
    public int minArgs() {
        return 2;
    }

    @Override
    public String permission() {
        return zHomes.getConfigYAML().getMainCommandParsePermission();
    }

    @Override
    public String usage(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String[] args) {
        return zHomes.getLanguageYAML().getMainParseUsage();
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String @NotNull [] args) {
        String targetName = args[0];
        OfflinePlayer target = PlayerHandler.getOfflinePlayer(targetName);
        if(target == null) {
            sender.sendMessage(zHomes.getLanguageYAML().getCantFindPlayer());
            return;
        }
        String parseString = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        message(sender, zHomes.getLanguageYAML().getMainParseOutput(parseString));
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String[] args) {
        if(args.length == 1) {
            return Arrays.stream(Bukkit.getOfflinePlayers()).map(player -> player.getName() != null ? player.getName() : "").toList();
        }else {
            String currentArg = args[args.length - 1];

            if(currentArg.startsWith("%") && !currentArg.endsWith("%")) {
                return HookRegistry.PLUGIN_PAPI.getPlaceholderSuggestions(currentArg);
            }
        }
        return List.of();
    }

}
