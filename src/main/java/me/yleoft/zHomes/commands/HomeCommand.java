package me.yleoft.zHomes.commands;

import com.zhomes.api.event.player.ExecuteHomeCommandEvent;
import com.zhomes.api.event.player.PreExecuteHomeCommandEvent;
import me.yleoft.zAPI.command.Command;
import me.yleoft.zAPI.player.PlayerHandler;
import me.yleoft.zHomes.configuration.languages.LanguageBuilder;
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

public class HomeCommand extends HomesUtils implements Command {

    public HomeCommand() {
        addSubCommand(new HomeRenameSubCommand());
    }

    @Override
    public @NotNull String name() {
        return zHomes.getConfigYAML().getHomeCommand();
    }

    @Override
    public String description() {
        return zHomes.getConfigYAML().getHomeCommandDescription();
    }

    @Override
    public List<String> aliases() {
        return zHomes.getConfigYAML().getHomeCommandAliases();
    }

    @Override
    public int minArgs() {
        return 1;
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public String permission() {
        return zHomes.getConfigYAML().getHomeCommandPermission();
    }

    @Override
    public double cooldownTime() {
        return zHomes.getConfigYAML().getHomeCommandCooldown();
    }

    @Override
    public String bypassCooldownPermission() {
        return zHomes.getConfigYAML().getBypassCommandCooldownPermission(zHomes.getConfigYAML().getHomeCommandPermission());
    }

    @Override
    public String usage(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String[] args) {
        return zHomes.getLanguageYAML().getHomeUsage();
    }

    @Override
    public boolean prexecute(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String @NotNull [] args) {
        PreExecuteHomeCommandEvent preevent = new PreExecuteHomeCommandEvent((Player) sender);
        Bukkit.getPluginManager().callEvent(preevent);
        return preevent.isCancelled();
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String @NotNull [] args) {
        Player player = (Player) sender;

        String home = args[0];
        if (home.contains(":")) {
            if (player.hasPermission(zHomes.getConfigYAML().getHomeOthersPermission())) {
                code2(player, home);
            } else {
                message(player, zHomes.getLanguageYAML().getCantUse2Dot());
            }
        }else {
            code1(player, home);
        }
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] fullArgs, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        if (args[0].contains(":")) {
            if (sender.hasPermission(zHomes.getConfigYAML().getHomeOthersPermission())) {
                String[] as = args[0].split(":");
                OfflinePlayer t = Bukkit.getOfflinePlayer(as[0]);
                if (t != null) completions.addAll(homesWDD(t));
            }
        }
        if (completions.isEmpty() && sender instanceof Player player) {
            completions.addAll(homesW(player));
            if (player.hasPermission(zHomes.getConfigYAML().getHomeOthersPermission()))
                Bukkit.getOnlinePlayers().forEach((on -> completions.add(on.getName() + ":")));
        }
        if(sender.hasPermission(zHomes.getConfigYAML().getHomeRenamePermission())) completions.add("rename");
        return completions;
    }

    public void code1(Player p, String home) {
        ExecuteHomeCommandEvent event = new ExecuteHomeCommandEvent(p, home);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        home = event.getHome();

        if (hasHome(p, home)) {
            if (HookRegistry.VAULT.canAfford(p, zHomes.getConfigYAML().getHomeCommandPermission(), zHomes.getConfigYAML().getHomeCommandCost())) {
                teleportPlayer(p, home);
            }
        } else {
            LanguageBuilder.sendMessage(p, zHomes.getLanguageYAML().getHomeDoesntExist());
        }
    }

    public void code2(Player p, String home) {
        ExecuteHomeCommandEvent event = new ExecuteHomeCommandEvent(p, home);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        String ofchome = event.getHome();
        if (ofchome == null || ofchome.isEmpty()) return;
        String[] homeS = ofchome.split(":");
        if(homeS.length < 2) return;
        String player = homeS[0];
        home = homeS[1];

        OfflinePlayer t = PlayerHandler.getOfflinePlayer(player);
        if (t == null) {
            return;
        }
        if (hasHome(t, home)) {
            if (HookRegistry.VAULT.canAfford(p, zHomes.getConfigYAML().getHomeCommandPermission(), zHomes.getConfigYAML().getHomeCommandCost())) {
                teleportPlayer(p, t, home);
            }
        } else {
            LanguageBuilder.sendMessage(p, zHomes.getLanguageYAML().getHomeDoesntExistOthers(t.getName()));
        }
    }

}
