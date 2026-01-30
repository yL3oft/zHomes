package me.yleoft.zHomes.commands;

import com.zhomes.api.event.player.ExecuteDelhomeCommandEvent;
import com.zhomes.api.event.player.PreExecuteDelhomeCommandEvent;
import me.yleoft.zAPI.command.Command;
import me.yleoft.zAPI.player.PlayerHandler;
import me.yleoft.zHomes.hooks.HookRegistry;
import me.yleoft.zHomes.utility.HomesUtils;
import me.yleoft.zHomes.zHomes;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DelhomeCommand extends HomesUtils implements Command {

    @Override
    public @NotNull String name() {
        return zHomes.getConfigYAML().getDelHomeCommand();
    }

    @Override
    public String description() {
        return zHomes.getConfigYAML().getDelHomeCommandDescription();
    }

    @Override
    public List<String> aliases() {
        return zHomes.getConfigYAML().getDelHomeCommandAliases();
    }

    @Override
    public int minArgs() {
        return 1;
    }

    @Override
    public String permission() {
        return zHomes.getConfigYAML().getDelHomeCommandPermission();
    }

    @Override
    public double cooldownTime() {
        return zHomes.getConfigYAML().getDelHomeCommandCooldown();
    }

    @Override
    public String usage(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String[] args) {
        return zHomes.getLanguageYAML().getDelhomeUsage();
    }

    @Override
    public boolean prexecute(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String @NotNull [] args) {
        if(!isPlayer(sender)) return false;
        PreExecuteDelhomeCommandEvent preevent = new PreExecuteDelhomeCommandEvent((Player) sender);
        Bukkit.getPluginManager().callEvent(preevent);
        return preevent.isCancelled();
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String @NotNull [] args) {
        String home = args[0];
        if (home.contains(":")) {
            if (sender.hasPermission(zHomes.getConfigYAML().getHomesOthersPermission())) {
                code2(sender, home);
            } else {
                message(sender, zHomes.getLanguageYAML().getCantUse2Dot());
            }
        } else {
            if(!isPlayer(sender)) {
                message(sender, playerOnlyMessage());
                return;
            }
            code1((Player) sender, home);
        }
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        if (args[0].contains(":")) {
            if (sender.hasPermission(zHomes.getConfigYAML().getDelHomeOthersPermission())) {
                String[] as = args[0].split(":");
                OfflinePlayer t = Bukkit.getOfflinePlayer(as[0]);
                if (t != null) completions.addAll(homesWDD(t));
            }
        }
        if (completions.isEmpty() && sender instanceof Player player) {
            completions.addAll(homesW(player));
            if (sender.hasPermission(zHomes.getConfigYAML().getDelHomeOthersPermission()))
                Bukkit.getOnlinePlayers().forEach((on -> completions.add(on.getName() + ":")));
        }
        return completions;
    }

    public void code1(Player p, String home) {
        ExecuteDelhomeCommandEvent event = new ExecuteDelhomeCommandEvent(p, home);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        home = event.getHome();

        if (hasHome(p, home)) {
            if (HookRegistry.VAULT.canAfford(p, zHomes.getConfigYAML().getDelHomeCommandPermission(), zHomes.getConfigYAML().getDelHomeCommandCost())) {
                delHome(p, home);
                message(p, zHomes.getLanguageYAML().getDelhomeOutput(home));
            }
        } else {
            message(p, zHomes.getLanguageYAML().getHomeDoesntExist());
        }
    }

    public void code2(CommandSender sender, String home) {
        String ofchome = home;
        if(isPlayer(sender)) {
            ExecuteDelhomeCommandEvent event = new ExecuteDelhomeCommandEvent((Player) sender, home);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled())
                return;
            ofchome = event.getHome();
        }
        if (ofchome == null || ofchome.isEmpty()) return;
        String[] homeS = ofchome.split(":");
        if(homeS.length < 2) return;
        String player = homeS[0];
        home = homeS[1];

        OfflinePlayer t = PlayerHandler.getOfflinePlayer(player);
        if (t == null) {
            message(sender, zHomes.getLanguageYAML().getCantFindPlayer());
            return;
        }
        if (hasHome(t, home)) {
            if (!isPlayer(sender) || HookRegistry.VAULT.canAfford((Player) sender, zHomes.getConfigYAML().getDelHomeCommandPermission(), zHomes.getConfigYAML().getDelHomeCommandCost())) {
                delHome(t, home);
                message(sender, zHomes.getLanguageYAML().getDelhomeOutput(home));
            }
        } else {
            message(sender, zHomes.getLanguageYAML().getHomeDoesntExistOthers(t.getName()));
        }
    }

}
