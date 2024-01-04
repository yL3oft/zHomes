package me.leonardo.zhomes.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.leonardo.zhomes.FileManager;
import me.leonardo.zhomes.Main;
import me.leonardo.zhomes.utils.storage.HomesUtilsYAML;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LanguageUtils extends ConfigUtils {

    public static Main main = Main.main;
    public static String cmds = "commands";
    public static File f = new File(main.getDataFolder(), "languages/en.yml");
    public static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);

    public LanguageUtils() {
        cfg = getConfigFile();
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

    public static YamlConfiguration getConfigFile() {
        List<FileUtils> list = new ArrayList<>();
        list.add(Main.fm.fu3);
        list.add(Main.fm.fu4);
        list.add(Main.fm.fuBACKUP);
        boolean found = false;
        YamlConfiguration returned = cfg;
        String lang = langType();

        for(FileUtils fu : list) {
            if(fu != null) {
                File f = fu.getFile();
                if(f.exists()) {
                    String name = f.getName();
                    String[] nameS = name.split("\\.");
                    String langtype = nameS[0];
                    if (langtype.equals(lang)) {
                        returned = (YamlConfiguration) fu.getConfig();
                        found = true;
                        break;
                    }
                }
            }
        }
        if(!found) {
            String resource = "languages/"+lang+".yml";
            File f = new File(Main.main.getDataFolder(), resource);
            if(f.exists()) {
                Main.fm.fBACKUP = f;
                Main.fm.fuBACKUP = new FileUtils(f, resource);
                returned = (YamlConfiguration) Main.fm.fuBACKUP.getConfig();
            }
        }

        return returned;
    }

    public static class Sethome extends HomesUtilsYAML implements Commands {

        public File f = new File(main.getDataFolder(), "languages/en.yml");
        public YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);

        public Sethome() {
            cfg = getConfigFile();
        }

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

        public String getLimitReached(Player p) {
            String path = formPath(cmds, getCmd(), "limit-reached");
            return cfg.getString(path)
                    .replace("%limit%", String.valueOf(getMaxLimit(p)));
        }

    }

    public static class Delhome implements Commands {

        public File f = new File(main.getDataFolder(), "languages/en.yml");
        public YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);

        public Delhome() {
            cfg = getConfigFile();
        }

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

        public File f = new File(main.getDataFolder(), "languages/en.yml");
        public YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);

        public Home() {
            cfg = getConfigFile();
        }

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

        public String getCantDimensionalTeleport() {
            String path = formPath(cmds, getCmd(), "cant-dimensional-teleport");
            return cfg.getString(path);
        }

    }

    public static class Homes implements Commands {

        public File f = new File(main.getDataFolder(), "languages/en.yml");
        public YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);

        public Homes() {
            cfg = getConfigFile();
        }

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

    }

    public static class Zhomes implements Commands {

        public File f = new File(main.getDataFolder(), "languages/en.yml");
        public YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);

        public Zhomes() {
            cfg = getConfigFile();
        }

        @Override
        public String getCmd() {
            return "zhomes";
        }

        @Override
        public String getUsage() {
            String path = formPath(cmds, getCmd(), "usage");
            return cfg.getString(path);
        }

        @Override
        public String getOutput(String home) {
            return null;
        }

        @Override
        public String getOutput(OfflinePlayer p) {
            return null;
        }


        public static class ZhomesReload implements Commands {

            public File f = new File(main.getDataFolder(), "languages/en.yml");
            public YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);

            public ZhomesReload() {
                cfg = getConfigFile();
            }

            @Override
            public String getCmd() {
                return "zhomes.reload";
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
                return null;
            }

            public String getOutput() {
                String path = formPath(cmds, getCmd(), "output");
                return cfg.getString(path);
            }

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

        public String getNoPermission() {
            String path = formPath(cmds, "no-permission");
            return cfg.getString(path);
        }

    }

    public interface Commands extends Helper {

        public File f = new File(main.getDataFolder(), "languages/en.yml");
        public YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
        
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

        public default void sendMsg(CommandSender s, String text) {
            text = ChatColor.translateAlternateColorCodes('&', text);
            if(s instanceof Player) {
                Player p = (Player)s;
                text = PlaceholderAPI.setPlaceholders(p, text);
            }
            s.sendMessage(text);
        }

    }

}
