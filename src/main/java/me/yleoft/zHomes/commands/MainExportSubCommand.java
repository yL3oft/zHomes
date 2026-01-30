package me.yleoft.zHomes.commands;

import me.yleoft.zAPI.command.SubCommand;
import me.yleoft.zHomes.storage.DatabaseEditor;
import me.yleoft.zHomes.zHomes;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainExportSubCommand implements SubCommand {

    @Override
    public @NotNull String name() {
        return "export";
    }

    @Override
    public List<String> aliases() {
        return List.of("exportar");
    }

    @Override
    public String permission() {
        return zHomes.getConfigYAML().getMainCommandImportExportPermission();
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String @NotNull [] args) {
        DatabaseEditor.exportDatabase(sender);
    }

}
