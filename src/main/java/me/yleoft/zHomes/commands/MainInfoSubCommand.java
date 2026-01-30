package me.yleoft.zHomes.commands;

import me.yleoft.zAPI.command.SubCommand;
import me.yleoft.zHomes.zHomes;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class MainInfoSubCommand implements SubCommand {

    @Override
    public @NotNull String name() {
        return "info";
    }

    @Override
    public String permission() {
        return zHomes.getConfigYAML().getMainCommandInfoPermission();
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String @NotNull [] args) {
        message(sender, zHomes.getLanguageYAML().getMainInfoOutput());
    }

}
