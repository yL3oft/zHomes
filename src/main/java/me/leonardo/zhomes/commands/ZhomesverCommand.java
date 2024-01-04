package me.leonardo.zhomes.commands;

import me.leonardo.zhomes.Main;
import me.leonardo.zhomes.api.events.ExecuteZhomesCommandEvent;
import me.leonardo.zhomes.utils.ConfigUtils;
import me.leonardo.zhomes.utils.LanguageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ZhomesverCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        s.sendMessage(Main.main.getDescription().getVersion());
        return false;
    }

}
