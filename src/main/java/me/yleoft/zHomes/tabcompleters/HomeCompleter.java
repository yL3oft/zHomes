package me.yleoft.zHomes.tabcompleters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import me.yleoft.zHomes.utils.HomesUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

public class HomeCompleter extends HomesUtils implements TabCompleter {

    @Override
    public List<String> onTabComplete(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, String[] args) {
        Player p = (Player)s;
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();

        if (args.length == 1) {
            if (args[0].contains(":")) {
                if (p.hasPermission(CmdHomeOthersPermission())) {
                    String[] as = args[0].split(":");
                    OfflinePlayer t = Bukkit.getOfflinePlayer(as[0]);
                    if (t != null) commands.addAll(homesWDD(t));
                }
            }
            if (commands.isEmpty()) {
                commands.addAll(homesW(p));
                if (p.hasPermission(CmdHomeOthersPermission()))
                    Bukkit.getOnlinePlayers().forEach((on -> commands.add(on.getName() + ":")));
            }

            StringUtil.copyPartialMatches(args[0], commands, completions);
        }else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("rename")) {
                if (p.hasPermission(CmdHomeRenamePermission())) {
                    commands.addAll(homesW(p));
                }
            }

            StringUtil.copyPartialMatches(args[1], commands, completions);
        }
        Collections.sort(commands);
        return completions;
    }

}
