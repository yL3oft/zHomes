package me.yleoft.zHomes.commands;

import com.zhomes.api.event.player.RenameHomeEvent;
import me.yleoft.zAPI.command.SubCommand;
import me.yleoft.zHomes.hooks.HookRegistry;
import me.yleoft.zHomes.utility.HomesUtils;
import me.yleoft.zHomes.zHomes;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeRenameSubCommand extends HomesUtils implements SubCommand {

    @Override
    public @NotNull String name() {
        return "rename";
    }

    @Override
    public List<String> aliases() {
        return List.of("renomear");
    }

    @Override
    public int minArgs() {
        return 2;
    }

    @Override
    public int maxArgs() {
        return 2;
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public String permission() {
        return zHomes.getConfigYAML().getHomeRenamePermission();
    }

    @Override
    public String usage(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String[] args) {
        return zHomes.getLanguageYAML().getHomeRenameUsage();
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String @NotNull [] args) {
        Player player = (Player) sender;
        String home = args[0];
        String newName = args[1];
        if(home.equals(newName)) {
            message(player, zHomes.getLanguageYAML().getHomeRenameSameName());
            return;
        }
        if (!hasHome(player, home)) {
            message(player, zHomes.getLanguageYAML().getHomeDoesntExist());
            return;
        }
        if(hasHome(player, newName)) {
            message(player, zHomes.getLanguageYAML().getHomeAlreadyExist());
            return;
        }

        RenameHomeEvent event = new RenameHomeEvent(player, home, newName);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;
        home = event.getHome();
        newName = event.getNewName();

        if (HookRegistry.VAULT.canAfford(player, zHomes.getConfigYAML().getHomeRenamePermission(), zHomes.getConfigYAML().getHomeRenameCommandCost())) {
            renameHome(player, home, newName);
            message(player, zHomes.getLanguageYAML().getHomeRenameOutput(home, newName));
        }
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String[] args) {
        return isPlayer(sender) ? homesW((Player) sender) : List.of();
    }
}
