package me.leonardo.zhomes.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.leonardo.zhomes.Main;
import me.leonardo.zhomes.utils.storage.HomesUtilsYAML;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class LanguageUtils extends ConfigUtils {

    public static Main main = Main.main;
    public static String cmds = "commands";
    public static File f = new File(main.getDataFolder(), "languages/en.yml");
    public static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);

    public String lang = langType();

    public LanguageUtils() {
        File f = new File(main.getDataFolder(), "languages/"+lang+".yml");
        if(f.exists()) this.f = f;
        cfg = YamlConfiguration.loadConfiguration(this.f);
    }

    public static String formPath(String... strs) {
        String path = "";

        try {
            for(String str : strs) {
                if(path.equals("")) {
                    path = str;
                    continue;
                }
                path += "."+str;
            }
        }catch (Exception e) {
        }

        return path;
    }

    public static class Sethome implements Commands {

        @Override
        public String getCmd() {
            return "sethome";
        }

        @Override
        public String getUsage() {
            String path = formPath(cmds, getCmd(), "usage");
            return cfg.getString(path);
        }

        @Override
        public String getOutput(String home) {
            String path = formPath(cmds, getCmd(), "output");
            return cfg.getString(path)
                    .replace("%home%", home);
        }

        @Override
        public String getOutput(OfflinePlayer home) {
            return null;
        }

    }

    public static class Delhome implements Commands {

        @Override
        public String getCmd() {
            return "delhome";
        }

        @Override
        public String getUsage() {
            String path = formPath(cmds, getCmd(), "usage");
            return cfg.getString(path);
        }

        @Override
        public String getOutput(String home) {
            String path = formPath(cmds, getCmd(), "output");
            return cfg.getString(path)
                    .replace("%home%", home);
        }

        @Override
        public String getOutput(OfflinePlayer home) {
            return null;
        }

    }

    public static class Home implements Commands {

        @Override
        public String getCmd() {
            return "home";
        }

        @Override
        public String getUsage() {
            String path = formPath(cmds, getCmd(), "usage");
            return cfg.getString(path);
        }

        @Override
        public String getOutput(String home) {
            String path = formPath(cmds, getCmd(), "output");
            return cfg.getString(path)
                    .replace("%home%", home);
        }

        @Override
        public String getOutput(OfflinePlayer home) {
            return null;
        }

    }

    public static class Homes implements Commands {

        @Override
        public String getCmd() {
            return "homes";
        }

        @Override
        public String getUsage() {
            return null;
        }

        @Override
        public String getOutput(String home) {
            return null;
        }

        @Override
        public String getOutput(OfflinePlayer p) {
            String path = formPath(cmds, getCmd(), "output");
            return cfg.getString(path)
                    .replace("%homes%", new HomesUtilsYAML().homes(p));
        }

        public String getCantDimensionalTeleport() {
            String path = formPath(cmds, getCmd(), "cant-dimensional-teleport");
            return cfg.getString(path);
        }

    }

    public static class CommandsMSG implements Helper {

        public String getHomeAlreadyExist() {
            String path = formPath(cmds, "home-already-exist");
            return cfg.getString(path);
        }

        public String getHomeDoesntExist() {
            String path = formPath(cmds, "home-doesnt-exist");
            return cfg.getString(path);
        }

    }

    public interface Commands extends Helper {
        
        public String getCmd();

        public String getUsage();

        public String getOutput(String home);
        public String getOutput(OfflinePlayer home);

    }

    public interface Helper {

        public default void sendMsg(Player p, String text) {
            text = ChatColor.translateAlternateColorCodes('&', text);
            text = PlaceholderAPI.setPlaceholders(p, text);
            p.sendMessage(text);
        }

    }

}
