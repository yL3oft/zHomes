package me.leonardo.zhomes.tabcompleters;

import me.leonardo.zhomes.utils.storage.HomesUtilsYAML;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeCommandsTabCompleter extends HomesUtilsYAML implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender s, Command cmd, String label, String[] args) {
        Player p = (Player)s;

        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();

        commands.addAll(homesW(p));

        StringUtil.copyPartialMatches(args[0], commands, completions);

        Collections.sort(commands);
        return completions;
    }

}
