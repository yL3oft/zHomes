package me.yleoft.zHomes.commands;

import me.yleoft.zAPI.command.SubCommand;
import me.yleoft.zHomes.storage.DatabaseEditor;
import me.yleoft.zHomes.zHomes;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainImportSubCommand implements SubCommand {

    @Override
    public @NotNull String name() {
        return "import";
    }

    @Override
    public List<String> aliases() {
        return List.of("importar");
    }

    @Override
    public String permission() {
        return zHomes.getConfigYAML().getMainCommandImportExportPermission();
    }

    @Override
    public int minArgs() {
        return 1;
    }

    @Override
    public String usage(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String[] args) {
        return zHomes.getLanguageYAML().getMainImportUsage();
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String @NotNull [] args) {
        String fileName = args[0];
        File file = new File(zHomes.getInstance().getDataFolder(), fileName+".json.gz");
        if(fileName.endsWith("json.gz")) file = new File(zHomes.getInstance().getDataFolder(), fileName);
        if(!file.exists()) {
            message(sender, zHomes.getLanguageYAML().getMainImportFileNotFound(file.getAbsolutePath()));
        }
        DatabaseEditor.importDatabase(file, sender);
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        File[] files = zHomes.getInstance().getDataFolder().listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String name = file.getName();
                    if(!name.endsWith(".json.gz")) continue;
                    int dotIndex = name.lastIndexOf(".json.gz");
                    if (dotIndex > 0) {
                        completions.add(name.substring(0, dotIndex));
                    }
                }
            }
        }
        return completions;
    }
}
