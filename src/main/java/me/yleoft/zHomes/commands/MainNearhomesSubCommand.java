package me.yleoft.zHomes.commands;

import me.yleoft.zAPI.command.Parameter;
import me.yleoft.zAPI.command.SubCommand;
import me.yleoft.zAPI.player.PlayerHandler;
import me.yleoft.zHomes.utility.HomesUtils;
import me.yleoft.zHomes.zHomes;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class MainNearhomesSubCommand implements SubCommand {

    private final Parameter userParameter = new Parameter() {
        @Override
        public @NotNull String name() {
            return "user";
        }

        @Override
        public @NotNull List<String> aliases() {
            return List.of("u", "player", "p");
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
            return Arrays.stream(Bukkit.getOfflinePlayers()).map(offlinePlayer -> offlinePlayer.getName() == null ? "" : offlinePlayer.getName()).toList();
        }
    };
    private final Parameter homeParameter = new Parameter() {
        @Override
        public @NotNull String name() {
            return "home";
        }

        @Override
        public @NotNull List<String> aliases() {
            return List.of("h");
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
            String[] userArgs = getParameter(sender, userParameter);
            return userArgs == null ? List.of() : HomesUtils.homesW(PlayerHandler.getOfflinePlayer(userArgs[0]));
        }
    };

    public MainNearhomesSubCommand() {
        addParameter(userParameter);
        addParameter(homeParameter);
    }

    @Override
    public @NotNull String name() {
        return "nearhomes";
    }

    @Override
    public int minArgs() {
        return 1;
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public String permission() {
        return zHomes.getConfigYAML().getMainCommandNearHomesPermission();
    }

    @Override
    public String usage(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String[] args) {
        return zHomes.getLanguageYAML().getMainNearhomesUsage();
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String @NotNull [] args) {
        Player player = (Player) sender;
        double radius = 0D;
        try {
            radius = Double.parseDouble(args[0]);
            if (radius <= 0) {
                message(player, usage(player, fullArgs, args));
                return;
            }
        }catch (Exception e) {
            message(player, usage(player, fullArgs, args));
            return;
        }
        if(radius > zHomes.getConfigYAML().getMainCommandNearHomesLimit()) {
            radius = zHomes.getConfigYAML().getMainCommandNearHomesLimit();
        }
        String[] userArgs = getParameter(sender, userParameter);
        String[] homeArgs = getParameter(sender, homeParameter);
        message(player, zHomes.getLanguageYAML().getMainNearhomesOutput(radius, HomesUtils.getNearHomes(player.getLocation(), radius), userArgs == null ? null : userArgs[0], homeArgs == null ? null : homeArgs[0]));
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String[] args) {
        return args.length > 1 ? List.of("-") : List.of();
    }

}
