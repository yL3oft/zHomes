package me.yleoft.zHomes.commands;

import me.yleoft.zAPI.command.SubCommand;
import me.yleoft.zHomes.utility.UpdateUtils;
import me.yleoft.zHomes.zHomes;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainVersionSubCommand implements SubCommand {

    public MainVersionSubCommand() {
        addSubCommand(new MainVersionUpdateSubCommand());
    }

    @Override
    public @NotNull String name() {
        return "version";
    }

    @Override
    public List<String> aliases() {
        return List.of("ver");
    }

    @Override
    public String permission() {
        return zHomes.getConfigYAML().getMainCommandVersionPermission();
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String @NotNull [] args) {
        if (sender.hasPermission(zHomes.getConfigYAML().getMainCommandVersionUpdatePermission())) {
            if(zHomes.updateUtils.needsUpdate) {
                message(sender, "%prefix% <gold>You are using an outdated version of zHomes! Please update to the latest version.");
                message(sender, "%prefix% <gold>New version: <green>" + zHomes.updateUtils.updateVersion);
                message(sender, "%prefix% <gold>Your version: <red>" + zHomes.getInstance().getPluginMeta().getVersion());
                message(sender, "%prefix% <gold>You can update your plugin here: <yellow>" + UpdateUtils.site);
            }else {
                message(sender, zHomes.getLanguageYAML().getMainVersionOutput());
                message(sender, zHomes.getLanguageYAML().getMainVersionNoUpdate());
            }
            return;
        }
        message(sender, zHomes.getLanguageYAML().getMainVersionOutput());
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String[] args) {
        return sender.hasPermission(zHomes.getConfigYAML().getMainCommandVersionUpdatePermission()) ?
                List.of("update") : List.of();
    }

    public static class MainVersionUpdateSubCommand implements SubCommand {

        @Override
        public @NotNull String name() {
            return "update";
        }

        @Override
        public List<String> aliases() {
            return List.of("--update");
        }

        @Override
        public String permission() {
            return zHomes.getConfigYAML().getMainCommandVersionUpdatePermission();
        }

        @Override
        public void execute(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String @NotNull [] args) {
            if(!zHomes.updateUtils.needsUpdate) {
                message(sender, zHomes.getLanguageYAML().getMainVersionNoUpdate());
                return;
            }
            try {
                zHomes.updateUtils.checker.update();
                message(sender, zHomes.getLanguageYAML().getMainVersionUpdateOutput());
            } catch (Exception e) {
                zHomes.getInstance().getLoggerInstance().warn("An error occurred while updating the plugin", e);
            }
        }

    }

}
