package me.yleoft.zHomes.commands;

import me.yleoft.zAPI.command.SubCommand;
import me.yleoft.zAPI.utility.PluginYAML;
import me.yleoft.zHomes.utility.LanguageUtils;
import me.yleoft.zHomes.zHomes;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class MainReloadSubCommand implements SubCommand {

    @Override
    public @NotNull String name() {
        return "reload";
    }

    @Override
    public List<String> aliases() {
        return List.of("rl");
    }

    @Override
    public int maxArgs() {
        return 1;
    }

    @Override
    public String permission() {
        return zHomes.getConfigYAML().getMainCommandReloadPermission();
    }

    @Override
    public String usage(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String[] args) {
        return zHomes.getLanguageYAML().getMainReloadUsage();
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String @NotNull [] args) {
        String reloadType = "all";
        if(args.length == 1) {
            reloadType = args[0];
        }
        switch (reloadType) {
            case "all": {
                long time = reload("all");
                message(sender, zHomes.getLanguageYAML().getMainReloadOutput(time));
                return;
            }
            case "commands": {
                long time = reload("commands");
                message(sender, zHomes.getLanguageYAML().getMainReloadCommandsOutput(time));
                return;
            }
            case "config": {
                long time = reload("config");
                message(sender, zHomes.getLanguageYAML().getMainReloadConfigOutput(time));
                return;
            }
            case "languages": {
                long time = reload("languages");
                message(sender, zHomes.getLanguageYAML().getMainReloadLanguagesOutput(time));
                return;
            }
        }
        message(sender, usage(sender, fullArgs, args));
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String[] args) {
        return List.of("all", "commands", "config", "languages");
    }

    private static long reload(String reloadType) {
        long now = System.currentTimeMillis();
        if(!Objects.equals(reloadType, "config")) reload("config");
        switch (reloadType.toLowerCase()) {
            case "all":
                reload("commands");
                reload("languages");
                break;
            case "commands":
                zHomes.getInstance().loadCommands();
                PluginYAML.syncCommands();
                break;
            case "config":
                zHomes.getInstance().reloadConfig();
                boolean debugMode = zHomes.getConfigYAML().isDebugModeEnabled();
                zHomes.getInstance().getLoggerInstance().setDebugMode(debugMode);
                break;
            case "languages":
                LanguageUtils.loadLanguages();
                zHomes.getInstance().reloadConfig();
                break;
        }
        return System.currentTimeMillis() - now;
    }

}
