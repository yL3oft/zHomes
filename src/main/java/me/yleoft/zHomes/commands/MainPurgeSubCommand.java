package me.yleoft.zHomes.commands;

import me.yleoft.zAPI.command.Parameter;
import me.yleoft.zAPI.command.SubCommand;
import me.yleoft.zAPI.player.PlayerHandler;
import me.yleoft.zHomes.storage.DatabaseEditor;
import me.yleoft.zHomes.utility.HomesUtils;
import me.yleoft.zHomes.zHomes;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainPurgeSubCommand implements SubCommand {

    private static final String deleteAllSymbol = "*";

    private final Parameter playerParameter = new Parameter() {
        @Override
        public @NotNull String name() {
            return "player";
        }

        @Override
        public @NotNull List<String> aliases() {
            return List.of("u", "user", "p");
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
        public Set<String> whitelist() {
            return Arrays.stream(Bukkit.getOfflinePlayers()).map(offlinePlayer -> offlinePlayer.getName() == null ? "" : offlinePlayer.getName()).collect(Collectors.toSet());
        }

        @Override
        public @Nullable String whitelistMessage() {
            return zHomes.getLanguageYAML().getCantFindPlayer();
        }

        @Override
        public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String[] parameterArgs) {
            return Arrays.stream(Bukkit.getOfflinePlayers()).map(offlinePlayer -> offlinePlayer.getName() == null ? "" : offlinePlayer.getName()).toList();
        }
    };
    private final Parameter worldParameter = new Parameter() {
        @Override
        public @NotNull String name() {
            return "world";
        }

        @Override
        public @NotNull List<String> aliases() {
            return List.of("w");
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
        public Set<String> whitelist() {
            return Bukkit.getWorlds().stream().map(WorldInfo::getName).collect(Collectors.toSet());
        }

        @Override
        public @Nullable String whitelistMessage() {
            // TODO: ADD LANGUAGE
            return Parameter.super.whitelistMessage();
        }

        @Override
        public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String[] parameterArgs) {
            return Bukkit.getWorlds().stream().map(WorldInfo::getName).toList();
        }
    };
    private final Parameter startswithParameter = new Parameter() {
        @Override
        public @NotNull String name() {
            return "startswith";
        }

        @Override
        public @NotNull List<String> aliases() {
            return List.of("sw", "startwith");
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
            String target = fullArgs[1];
            OfflinePlayer targetPlayer = null;
            if (!target.equals(deleteAllSymbol)) {
                targetPlayer = PlayerHandler.getOfflinePlayer(target);
            }
            String[] playerArgs = getParameter(sender, playerParameter);
            if(playerArgs != null) {
                targetPlayer = PlayerHandler.getOfflinePlayer(playerArgs[0]);
            }

            if(targetPlayer != null) {
                return HomesUtils.homesW(targetPlayer);
            }
            return List.of();
        }
    };
    private final Parameter endswithParameter = new Parameter() {
        @Override
        public @NotNull String name() {
            return "endswith";
        }

        @Override
        public @NotNull List<String> aliases() {
            return List.of("ew", "endwith");
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
            String target = fullArgs[1];
            OfflinePlayer targetPlayer = null;
            if (!target.equals(deleteAllSymbol)) {
                targetPlayer = PlayerHandler.getOfflinePlayer(target);
            }
            String[] playerArgs = getParameter(sender, playerParameter);
            if(playerArgs != null) {
                targetPlayer = PlayerHandler.getOfflinePlayer(playerArgs[0]);
            }

            if(targetPlayer != null) {
                return HomesUtils.homesW(targetPlayer);
            }
            return List.of();
        }
    };

    public MainPurgeSubCommand() {
        addParameter(playerParameter);
        addParameter(worldParameter);
        addParameter(startswithParameter);
        addParameter(endswithParameter);
    }

    @Override
    public @NotNull String name() {
        return "purge";
    }

    @Override
    public List<String> aliases() {
        return List.of("purgedata");
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
    public String permission() {
        return zHomes.getConfigYAML().getMainCommandPurgePermission();
    }

    @Override
    public String usage(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String[] args) {
        return zHomes.getLanguageYAML().getMainPurgeUsage();
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String @NotNull [] args) {
        String target = args[0];
        DatabaseEditor.PurgeFilter.Builder filterBuilder = DatabaseEditor.PurgeFilter.builder();

        if (!target.equals(deleteAllSymbol)) {
            OfflinePlayer targetPlayer = PlayerHandler.getOfflinePlayer(target);
            if(targetPlayer == null) {
                message(sender, zHomes.getLanguageYAML().getCantFindPlayer());
                return;
            }
            filterBuilder.player(targetPlayer);
        }

        String[] playerArgs = getParameter(sender, playerParameter);
        if(playerArgs != null) {
            OfflinePlayer playerArg = PlayerHandler.getOfflinePlayer(playerArgs[0]);
            if (playerArg != null) filterBuilder.player(playerArg);
        }

        String[] worldArgs = getParameter(sender, worldParameter);
        if(worldArgs != null && Bukkit.getWorld(worldArgs[0]) != null) filterBuilder.world(Bukkit.getWorld(worldArgs[0]));

        String[] startswithArgs = getParameter(sender, startswithParameter);
        if(startswithArgs != null) filterBuilder.homeStartsWith(startswithArgs[0]);

        String[] endswithArgs = getParameter(sender, endswithParameter);
        if(endswithArgs != null) filterBuilder.homeEndsWith(endswithArgs[0]);

        int affected = DatabaseEditor.purgeHomes(filterBuilder.build());
        message(sender, zHomes.getLanguageYAML().getMainPurgeOutput(affected));
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String[] args) {
        return args.length > 1 ? List.of("-") : Stream.concat(
                Arrays.stream(Bukkit.getOfflinePlayers()).map(player -> player.getName() != null ? player.getName() : ""),
                Stream.of("*")
        ).toList();
    }

}
