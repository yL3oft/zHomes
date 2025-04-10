package me.yleoft.zHomes.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.clip.placeholderapi.PlaceholderAPI;
import me.yleoft.zAPI.utils.FileUtils;
import me.yleoft.zHomes.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class LanguageUtils extends ConfigUtils {

    private static final Main main = Main.getInstance();

    public static String cmds = "commands";

    public static File f = new File(main.getDataFolder(), "languages/en.yml");

    public static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);

    public LanguageUtils() {
        cfg = getConfigFile();
    }

    public static String formPath(String... strs) {
        StringBuilder path = new StringBuilder();

        try {
            for(String str : strs) {
                if(path.isEmpty()) {
                    path = new StringBuilder(str);
                    continue;
                }
                path.append(".").append(str);
            }
        }catch (Exception ignored) {
        }

        return path.toString();
    }

    public static YamlConfiguration getConfigFile() {
        List<FileUtils> list = new ArrayList<>();
        list.add(Main.fm.fuLang);
        list.add(Main.fm.fuLang2);
        list.add(Main.fm.fuBACKUP);
        boolean found = false;
        YamlConfiguration returned = cfg;
        String lang = langType();
        for (FileUtils fu : list) {
            if (fu != null) {
                File f = fu.getFile();
                if (f.exists()) {
                    String name = f.getName();
                    String[] nameS = name.split("\\.");
                    String langtype = nameS[0];
                    if (langtype.equals(lang)) {
                        returned = (YamlConfiguration)fu.getConfig();
                        found = true;
                        break;
                    }
                }
            }
        }
        if (!found) {
            String resource = "languages/" + lang + ".yml";
            File f = new File(main.getDataFolder(), resource);
            if (f.exists()) {
                Main.fm.fBACKUP = f;
                Main.fm.fuBACKUP = new FileUtils(f, resource);
                returned = (YamlConfiguration)Main.fm.fuBACKUP.getConfig();
            }
        }
        return returned;
    }

    public static class Sethome extends HomesUtils implements Commands {
        public YamlConfiguration cfg;

        public Sethome() {
            this.cfg = LanguageUtils.getConfigFile();
        }

        public String getCmd() {
            return "sethome";
        }

        public String getUsage() {
            String path = LanguageUtils.formPath(cmds, getCmd(), "usage");
            return this.cfg.getString(path)
                    .replace("%command%", Main.cfgu.CmdSethomeCommand());
        }

        public String getOutput() {
            return null;
        }

        public String getOutput(String home) {
            String path = LanguageUtils.formPath(cmds, getCmd(), "output");
            return this.cfg.getString(path)
                    .replace("%command%", Main.cfgu.CmdSethomeCommand())
                    .replace("%home%", home);
        }

        public String getLimitReached(Player p) {
            String path = LanguageUtils.formPath(cmds, getCmd(), "limit-reached");
            return this.cfg.getString(path)
                    .replace("%command%", Main.cfgu.CmdSethomeCommand())
                    .replace("%limit%", String.valueOf(Main.hu.getMaxLimit(p)));
        }
    }

    public static class Delhome implements Commands {
        public YamlConfiguration cfg;

        public Delhome() {
            this.cfg = LanguageUtils.getConfigFile();
        }

        public String getCmd() {
            return "delhome";
        }

        public String getUsage() {
            String path = LanguageUtils.formPath(cmds, getCmd(), "usage");
            return this.cfg.getString(path)
                    .replace("%command%", Main.cfgu.CmdDelhomeCommand());
        }

        public String getOutput() {
            return null;
        }

        public String getOutput(String home) {
            String path = LanguageUtils.formPath(cmds, getCmd(), "output");
            return this.cfg.getString(path)
                    .replace("%command%", Main.cfgu.CmdDelhomeCommand())
                    .replace("%home%", home);
        }
    }

    public static class Home implements Commands {
        public YamlConfiguration cfg;

        public Home() {
            this.cfg = LanguageUtils.getConfigFile();
        }

        public String getCmd() {
            return "home";
        }

        public String getUsage() {
            String path = LanguageUtils.formPath(cmds, getCmd(), "usage");
            return this.cfg.getString(path)
                    .replace("%command%", Main.cfgu.CmdHomeCommand());
        }

        public String getOutput() {
            return null;
        }

        public String getOutput(String home) {
            String path = LanguageUtils.formPath(cmds, getCmd(), "output");
            return this.cfg.getString(path)
                    .replace("%command%", Main.cfgu.CmdHomeCommand())
                    .replace("%home%", home);
        }

        public String getCantDimensionalTeleport() {
            String path = LanguageUtils.formPath(cmds, getCmd(), "cant-dimensional-teleport");
            return this.cfg.getString(path);
        }
    }

    public static class Homes implements Commands {
        public YamlConfiguration cfg;

        public Homes() {
            this.cfg = LanguageUtils.getConfigFile();
        }

        public String getCmd() {
            return "homes";
        }

        public String getUsage() {
            return null;
        }

        public String getOutput() {
            String path = LanguageUtils.formPath(cmds, getCmd(), "output");
            return this.cfg.getString(path)
                    .replace("%command%", Main.cfgu.CmdHomesCommand());
        }

        public String getOutput(OfflinePlayer p) {
            String path = LanguageUtils.formPath(cmds, getCmd(), "others.output");
            return this.cfg.getString(path)
                    .replace("%command%", Main.cfgu.CmdHomesCommand())
                    .replace("%homes%", Main.hu.homes(p))
                    .replace("%player%", Objects.requireNonNull(p.getName()));
        }
    }

    public static class MainCMD implements Commands {
        public YamlConfiguration cfg;

        public MainCMD() {
            this.cfg = LanguageUtils.getConfigFile();
        }

        public String getCmd() {
            return "main";
        }

        public String getUsage() {
            return null;
        }

        public String getOutput() {
            return null;
        }

        public static class MainHelp implements LanguageUtils.Commands {
            public YamlConfiguration cfg;

            public MainHelp() {
                this.cfg = LanguageUtils.getConfigFile();
            }

            public String getCmd() {
                return "main.help";
            }

            public String getUsage() {
                String path = LanguageUtils.formPath(cmds, getCmd(), "help-noperm");
                return this.cfg.getString(path)
                        .replace("%command%", Main.cfgu.CmdMainCommand());
            }

            public String getUsageWithPerm() {
                String path = LanguageUtils.formPath(cmds, getCmd(), "help-perm");
                return this.cfg.getString(path)
                        .replace("%command%", Main.cfgu.CmdMainCommand());
            }

            public String getOutput() {
                String path = LanguageUtils.formPath(cmds, getCmd(), "output");
                return this.cfg.getString(path)
                        .replace("%version%", LanguageUtils.main.getDescription().getVersion());
            }
        }

        public static class MainVersion implements LanguageUtils.Commands {
            public YamlConfiguration cfg;

            public MainVersion() {
                this.cfg = LanguageUtils.getConfigFile();
            }

            public String getCmd() {
                return "main.version";
            }

            public String getUsage() {
                return null;
            }

            public String getOutput() {
                String path = LanguageUtils.formPath(cmds, getCmd(), "output");
                return this.cfg.getString(path)
                        .replace("%version%", LanguageUtils.main.getDescription().getVersion());
            }
        }

        public static class MainReload implements LanguageUtils.Commands {
            public YamlConfiguration cfg;

            public MainReload() {
                this.cfg = LanguageUtils.getConfigFile();
            }

            public String getCmd() {
                return "main.reload";
            }

            public String getUsage() {
                String path = LanguageUtils.formPath(cmds, getCmd(), "usage");
                return this.cfg.getString(path)
                        .replace("%command%", Main.cfgu.CmdMainCommand());
            }

            public String getOutput() {
                return null;
            }

            public String getOutput(long time) {
                String path = LanguageUtils.formPath(cmds, getCmd(), "output");
                return this.cfg.getString(path)
                        .replace("%command%", Main.cfgu.CmdMainCommand())
                        .replace("%time%", String.valueOf(time));
            }

            public String getOutputCommands(long time) {
                String path = LanguageUtils.formPath(cmds, getCmd(), "commands.output");
                return this.cfg.getString(path)
                        .replace("%command%", Main.cfgu.CmdMainCommand())
                        .replace("%time%", String.valueOf(time));
            }

            public String getOutputConfig(long time) {
                String path = LanguageUtils.formPath(cmds, getCmd(), "config.output");
                return this.cfg.getString(path)
                        .replace("%command%", Main.cfgu.CmdMainCommand())
                        .replace("%time%", String.valueOf(time));
            }

            public String getOutputLanguages(long time) {
                String path = LanguageUtils.formPath(cmds, getCmd(), "languages.output");
                return this.cfg.getString(path)
                        .replace("%command%", Main.cfgu.CmdMainCommand())
                        .replace("%time%", String.valueOf(time));
            }
        }

        public static class MainConverter implements LanguageUtils.Commands {
            public YamlConfiguration cfg;

            public MainConverter() {
                this.cfg = LanguageUtils.getConfigFile();
            }

            public String getCmd() {
                return "main.converter";
            }

            public String getUsage() {
                String path = LanguageUtils.formPath(cmds, getCmd(), "usage");
                return this.cfg.getString(path)
                        .replace("%command%", Main.cfgu.CmdMainCommand());
            }

            public String getOutput() {
                String path = LanguageUtils.formPath(cmds, getCmd(), "output");
                return this.cfg.getString(path)
                        .replace("%command%", Main.cfgu.CmdMainCommand());
            }

            public String getError() {
                String path = LanguageUtils.formPath(cmds, getCmd(), "error");
                return this.cfg.getString(path)
                        .replace("%command%", Main.cfgu.CmdMainCommand());
            }
        }
    }

    public static class CommandsMSG implements Helper {
        public YamlConfiguration cfg;

        public CommandsMSG() {
            this.cfg = LanguageUtils.getConfigFile();
        }

        public String getHomeAlreadyExist() {
            String path = LanguageUtils.formPath(cmds, "home-already-exist");
            return this.cfg.getString(path);
        }

        public String getHomeDoesntExist() {
            String path = LanguageUtils.formPath(cmds, "home-doesnt-exist");
            return this.cfg.getString(path);
        }

        public String getHomeDoesntExistOthers(OfflinePlayer p) {
            String path = LanguageUtils.formPath(cmds, "home-doesnt-exist-others");
            return this.cfg.getString(path)
                    .replace("%player%", Objects.requireNonNull(p.getName()));
        }

        public String getCantAfford(Float cost) {
            String path = LanguageUtils.formPath(cmds, "cant-afford");
            return this.cfg.getString(path)
                    .replace("%cost%", Float.toString(cost));
        }

        public String getNoPermission() {
            String path = LanguageUtils.formPath(cmds, "no-permission");
            return this.cfg.getString(path);
        }

        public String getCantUse2Dot() {
            String path = LanguageUtils.formPath(cmds, "cant-use-2dot");
            return this.cfg.getString(path);
        }

        public String getCantFindPlayer() {
            String path = LanguageUtils.formPath(cmds, "cant-find-player");
            return this.cfg.getString(path);
        }
    }

    public interface Commands extends Helper {
        String getCmd();

        String getUsage();

        String getOutput();
    }

    public interface Helper {
        default void sendMsg(Player p, String text) {
            if(text.isEmpty()) return;
            text = hex(text);
            text = ChatColor.translateAlternateColorCodes('&', text
                    .replace("%prefix%", hex(Main.cfgu.prefix()))
                    .replace("%limit%", String.valueOf(Main.cfgu.getMaxLimit(p)))
                    .replace("%numberofhomes%", String.valueOf(Main.hu.numberOfHomes(p)))
                    .replace("%homes%", Main.hu.homes(p)));
            if (LanguageUtils.main.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI"))
                text = PlaceholderAPI.setPlaceholders(p, text);
            p.sendMessage(text);
        }

        default void sendMsg(CommandSender s, String text) {
            if(text.isEmpty()) return;
            text = hex(text);
            text = ChatColor.translateAlternateColorCodes('&', text
                    .replace("%prefix%", hex(Main.cfgu.prefix())));
            if (s instanceof Player) {
                Player p = (Player)s;
                text = ChatColor.translateAlternateColorCodes('&', text
                        .replace("%limit%", String.valueOf(Main.cfgu.getMaxLimit(p)))
                        .replace("%numberofhomes%", String.valueOf(Main.hu.numberOfHomes(p)))
                        .replace("%homes%", Main.hu.homes(p)));
                if (LanguageUtils.main.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI"))
                    text = PlaceholderAPI.setPlaceholders(p, text);
            }
            s.sendMessage(text);
        }

        default void broadcast(String text) {
            if(text.isEmpty()) return;
            text = hex(text);
            text = ChatColor.translateAlternateColorCodes('&', text
                    .replace("%prefix%", hex(Main.cfgu.prefix())));
            text = PlaceholderAPI.setPlaceholders(null, text);
            Bukkit.getServer().broadcastMessage(text);
        }

        default String hex(String text) {
            Matcher matcher = Pattern.compile("&#([A-Za-z0-9]{6})").matcher(text);
            StringBuilder result = new StringBuilder();
            while (matcher.find()) {
                String replacement = "&x" + matcher.group(1).replaceAll("(.)", "&$1");
                matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
            }
            return matcher.appendTail(result).toString();
        }
    }

}
