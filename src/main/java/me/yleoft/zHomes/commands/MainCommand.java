package me.yleoft.zHomes.commands;

import me.yleoft.zAPI.command.Command;
import me.yleoft.zHomes.zHomes;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainCommand implements Command {

    public MainCommand() {
        addSubCommand(new MainInfoSubCommand());
        addSubCommand(new MainReloadSubCommand());
        addSubCommand(new MainVersionSubCommand());
        addSubCommand(new MainNearhomesSubCommand());
        addSubCommand(new MainParseSubCommand());
        addSubCommand(new MainConverterSubCommand());
        addSubCommand(new MainExportSubCommand());
        addSubCommand(new MainImportSubCommand());
    }

    @Override
    public @NotNull String name() {
        return zHomes.getConfigYAML().getMainCommand();
    }

    @Override
    public String description() {
        return zHomes.getConfigYAML().getMainCommandDescription();
    }

    @Override
    public List<String> aliases() {
        return zHomes.getConfigYAML().getMainCommandAliases();
    }

    @Override
    public String permission() {
        return zHomes.getConfigYAML().getMainCommandPermission();
    }

    @Override
    public String usage(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String[] args) {
        return sender.hasPermission(zHomes.getConfigYAML().getMainCommandHelpPermission()) ?
                zHomes.getLanguageYAML().getMainHelpPerm() :
                zHomes.getLanguageYAML().getMainHelpNoPerm();
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        completions.add("help");
        if(sender.hasPermission(zHomes.getConfigYAML().getMainCommandReloadPermission())) {
            completions.add("reload");
        }
        if(sender.hasPermission(zHomes.getConfigYAML().getMainCommandInfoPermission())) {
            completions.add("info");
        }
        if(sender.hasPermission(zHomes.getConfigYAML().getMainCommandVersionPermission())) {
            completions.add("version");
        }
        if(sender.hasPermission(zHomes.getConfigYAML().getMainCommandNearHomesPermission())) {
            completions.add("nearhomes");
        }
        if(sender.hasPermission(zHomes.getConfigYAML().getMainCommandParsePermission())) {
            completions.add("parse");
        }
        if(sender.hasPermission(zHomes.getConfigYAML().getMainCommandConverterPermission())) {
            completions.add("converter");
        }
        if(sender.hasPermission(zHomes.getConfigYAML().getMainCommandImportExportPermission())) {
            completions.add("export");
            completions.add("import");
        }
        return completions;
    }

    @Override
    public int minArgs() {
        return 1;
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String @NotNull [] args) {
        message(sender, usage(sender, fullArgs, args));
    }

}
