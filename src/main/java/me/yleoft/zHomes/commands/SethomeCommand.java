package me.yleoft.zHomes.commands;

import com.zhomes.api.event.player.ExecuteSethomeCommandEvent;
import com.zhomes.api.event.player.PreExecuteSethomeCommandEvent;
import me.yleoft.zAPI.command.Command;
import me.yleoft.zHomes.hooks.HookRegistry;
import me.yleoft.zHomes.utility.HomesUtils;
import me.yleoft.zHomes.zHomes;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SethomeCommand extends HomesUtils implements Command {

    @Override
    public @NotNull String name() {
        return zHomes.getConfigYAML().getSetHomeCommand();
    }

    @Override
    public String description() {
        return zHomes.getConfigYAML().getSetHomeCommandDescription();
    }

    @Override
    public List<String> aliases() {
        return zHomes.getConfigYAML().getSetHomeCommandAliases();
    }

    @Override
    public int minArgs() {
        return 1;
    }

    @Override
    public String permission() {
        return zHomes.getConfigYAML().getSetHomeCommandPermission();
    }

    @Override
    public double cooldownTime() {
        return zHomes.getConfigYAML().getSetHomeCommandCooldown();
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public String usage(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String[] args) {
        return zHomes.getLanguageYAML().getSethomeUsage();
    }

    @Override
    public boolean prexecute(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String @NotNull [] args) {
        PreExecuteSethomeCommandEvent preevent = new PreExecuteSethomeCommandEvent((Player) sender);
        Bukkit.getPluginManager().callEvent(preevent);
        return preevent.isCancelled();
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String @NotNull [] args) {
        Player player = (Player) sender;
        String home = args[0];
        ExecuteSethomeCommandEvent event = new ExecuteSethomeCommandEvent(player, home);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;
        home = event.getHome();

        if (!inMaxLimit(player)) {
            if(home.contains(":")) {
                message(player, zHomes.getLanguageYAML().getCantUse2Dot());
                return;
            }
            if(!hasHome(player, home)) {
                if (HookRegistry.VAULT.canAfford(player, zHomes.getConfigYAML().getSetHomeCommandPermission(), zHomes.getConfigYAML().getSetHomeCommandCost())) {
                    addHome(player, home, player.getLocation());
                    message(player, zHomes.getLanguageYAML().getSethomeOutput(home));
                }
            } else {
                message(player, zHomes.getLanguageYAML().getHomeAlreadyExist());
            }
        } else {
            message(player, zHomes.getLanguageYAML().getSethomeLimitReached(player));
        }
    }

}
