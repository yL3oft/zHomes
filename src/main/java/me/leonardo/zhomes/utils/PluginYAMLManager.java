package me.leonardo.zhomes.utils;

import me.leonardo.zhomes.Main;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;


public class PluginYAMLManager {

    private PluginDescriptionFile file;
    public Main main = Main.main;

    public PluginYAMLManager() {
        this.file = main.getDescription();
    }

    public void registerCommand(String command, CommandExecutor ce){
        if(file.getCommands().containsKey(command)) {
            main.getCommand(command).setExecutor(ce);
        }else {
            main.getLogger().severe("§7[§9z§cHomes§7] §4Couldn't load command §e"+command);
        }
    }

    public void registerCommandTabCompleter(String command, TabCompleter tc){
        if(file.getCommands().containsKey(command)) {
            main.getCommand(command).setTabCompleter(tc);
        }else {
            main.getLogger().severe("§4Couldn't load tab completer of command §e"+command);
        }
    }

    public void registerEvent(Listener l) {
        main.getServer().getPluginManager().registerEvents(l, main);
    }

}
