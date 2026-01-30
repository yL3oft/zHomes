package me.yleoft.zHomes.commands;

import me.yleoft.zAPI.command.SubCommand;
import me.yleoft.zHomes.storage.DatabaseConnection;
import me.yleoft.zHomes.zHomes;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainConverterSubCommand implements SubCommand {

    @Override
    public @NotNull String name() {
        return "converter";
    }

    @Override
    public int minArgs() {
        return 1;
    }

    @Override
    public String permission() {
        return zHomes.getConfigYAML().getMainCommandConverterPermission();
    }

    @Override
    public String usage(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String[] args) {
        return zHomes.getLanguageYAML().getMainConverterUsage();
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String @NotNull [] args) {
        DatabaseConnection.migrateData(isPlayer(sender) ? (Player) sender : sender, args[0]);
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String[] args) {
        return List.of(
                "sqlitetoh2",
                "sqlitetomysql",
                "sqlitetomariadb",
                "mysqltosqlite",
                "mysqltoh2",
                "mariadbtosqlite",
                "mariadbtoh2",
                "h2tosqlite",
                "h2tomysql",
                "h2tomariadb",
                "essentials",
                "sethome",
                "ultimatehomes",
                "xhomes",
                "zhome"
        );
    }

}
