package me.yleoft.zHomes.tabcompleters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import me.yleoft.zHomes.utils.ConfigUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class MainCompleter extends ConfigUtils implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender s, Command cmd, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();
        if (!(s instanceof Player))
            return completions;
        Player p = (Player)s;
        if (args.length == 1) {
            if (p.hasPermission(CmdMainReloadPermission()))
                commands.add("reload");
            if (p.hasPermission(CmdMainVersionPermission()))
                commands.add("version");
            if (p.hasPermission(CmdMainVersionPermission()))
                commands.add("migrate");
            StringUtil.copyPartialMatches(args[0], commands, completions);
        } else if (args.length == 2) {
            if ((args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) &&
                    p.hasPermission(CmdMainReloadPermission())) {
                commands.add("all");
                commands.add("commands");
                commands.add("config");
                commands.add("languages");
            }
            StringUtil.copyPartialMatches(args[1], commands, completions);
        }
        Collections.sort(commands);
        return completions;
    }

}
