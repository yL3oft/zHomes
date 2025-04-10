package me.yleoft.zHomes.tabcompleters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import me.yleoft.zHomes.utils.HomesUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

public class HomesCompleter extends HomesUtils implements TabCompleter {

    @Override
    public List<String> onTabComplete(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, String[] args) {
        Player p = (Player)s;
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();

        if (p.hasPermission(CmdHomesOthersPermission())) {
            Bukkit.getOnlinePlayers().forEach(online -> commands.add(online.getName()));
        }

        StringUtil.copyPartialMatches(args[0], commands, completions);
        Collections.sort(commands);
        return completions;
    }

}
