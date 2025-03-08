package me.yleoft.zHomes.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import me.clip.placeholderapi.PlaceholderAPI;
import me.yleoft.zHomes.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class LanguageUtils extends ConfigUtils {

    private static Main main = Main.getInstance();

    public static String cmds = "commands";

    public static String cmdscfg = "commands-config";

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
        public File f = new File(this.main.getDataFolder(), "languages/en.yml");

        public YamlConfiguration cfg = YamlConfiguration.loadConfiguration(this.f);

        public Sethome() {
            this.cfg = LanguageUtils.getConfigFile();
        }

        public String getCmd() {
            return "sethome";
        }

        public String getUsage() {
            String path = LanguageUtils.formPath(cmds, getCmd(), "usage");
            return this.cfg.getString(path);
        }

        public String getOutput() {
            return null;
        }

        public String getOutput(String home) {
            String path = LanguageUtils.formPath(cmds, getCmd(), "output");
            return this.cfg.getString(path)
                    .replace("%home%", home);
        }

        public double getCooldown() {
            String path = LanguageUtils.formPath(cmdscfg, getCmd(), "cooldown");
            return Main.getInstance().getConfig().getDouble(path);
        }

        public String getLimitReached(Player p) {
            String path = LanguageUtils.formPath(cmds, getCmd(), "limit-reached");
            return this.cfg.getString(path);
        }
    }

    public static class Delhome implements Commands {
        public File f = new File(LanguageUtils.main.getDataFolder(), "languages/en.yml");

        public YamlConfiguration cfg = YamlConfiguration.loadConfiguration(this.f);

        public Delhome() {
            this.cfg = LanguageUtils.getConfigFile();
        }

        public String getCmd() {
            return "delhome";
        }

        public String getUsage() {
            String path = LanguageUtils.formPath(cmds, getCmd(), "usage");
            return this.cfg.getString(path);
        }

        public String getOutput() {
            return null;
        }

        public String getOutput(String home) {
            String path = LanguageUtils.formPath(cmds, getCmd(), "output");
            return this.cfg.getString(path)
                    .replace("%home%", home);
        }

        public double getCooldown() {
            String path = LanguageUtils.formPath(cmdscfg, getCmd(), "cooldown");
            return Main.getInstance().getConfig().getInt(path);
        }
    }

    public static class Home implements Commands {
        public File f = new File(LanguageUtils.main.getDataFolder(), "languages/en.yml");

        public YamlConfiguration cfg = YamlConfiguration.loadConfiguration(this.f);

        public Home() {
            this.cfg = LanguageUtils.getConfigFile();
        }

        public String getCmd() {
            return "home";
        }

        public String getUsage() {
            String path = LanguageUtils.formPath(cmds, getCmd(), "usage");
            return this.cfg.getString(path);
        }

        public String getOutput() {
            return null;
        }

        public String getOutput(String home) {
            String path = LanguageUtils.formPath(cmds, getCmd(), "output");
            return this.cfg.getString(path)
                    .replace("%home%", home);
        }

        public double getCooldown() {
            String path = LanguageUtils.formPath(cmdscfg, getCmd(), "cooldown");
            return Main.getInstance().getConfig().getInt(path);
        }

        public String getCantDimensionalTeleport() {
            String path = LanguageUtils.formPath(cmds, getCmd(), "cant-dimensional-teleport");
            return this.cfg.getString(path);
        }
    }

    public static class Homes implements Commands {
        public File f = new File(LanguageUtils.main.getDataFolder(), "languages/en.yml");

        public YamlConfiguration cfg = YamlConfiguration.loadConfiguration(this.f);

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
            return null;
        }

        public String getOutput(OfflinePlayer p) {
            String path = LanguageUtils.formPath(cmds, getCmd(), "output");
            return this.cfg.getString(path);
        }

        public double getCooldown() {
            String path = LanguageUtils.formPath(cmdscfg, getCmd(), "cooldown");
            return Main.getInstance().getConfig().getInt(path);
        }
    }

    public static class MainCMD implements Commands {
        public File f = new File(LanguageUtils.main.getDataFolder(), "languages/en.yml");

        public YamlConfiguration cfg = YamlConfiguration.loadConfiguration(this.f);

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

        public double getCooldown() {
            String path = LanguageUtils.formPath(cmdscfg, getCmd(), "cooldown");
            return LanguageUtils.main.getConfig().getDouble(path);
        }

        public static class MainHelp implements LanguageUtils.Commands {
            public File f = new File(LanguageUtils.main.getDataFolder(), "languages/en.yml");

            public YamlConfiguration cfg = YamlConfiguration.loadConfiguration(this.f);

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
                return ((String) Objects.<String>requireNonNull(this.cfg.getString(path)))
                        .replace("%version%", LanguageUtils.main.getDescription().getVersion());
            }

            public double getCooldown() {
                return 0.0D;
            }
        }

        public static class MainVersion implements LanguageUtils.Commands {
            public File f = new File(LanguageUtils.main.getDataFolder(), "languages/en.yml");

            public YamlConfiguration cfg = YamlConfiguration.loadConfiguration(this.f);

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
                return ((String) Objects.<String>requireNonNull(this.cfg.getString(path)))
                        .replace("%version%", LanguageUtils.main.getDescription().getVersion());
            }

            public double getCooldown() {
                return 0.0D;
            }
        }

        public static class MainReload implements LanguageUtils.Commands {
            public File f = new File(LanguageUtils.main.getDataFolder(), "languages/en.yml");

            public YamlConfiguration cfg = YamlConfiguration.loadConfiguration(this.f);

            public MainReload() {
                this.cfg = LanguageUtils.getConfigFile();
            }

            public String getCmd() {
                return "main.reload";
            }

            public String getUsage() {
                String path = LanguageUtils.formPath(cmds, getCmd(), "usage");
                return ((String) Objects.<String>requireNonNull(this.cfg.getString(path)))
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

            public double getCooldown() {
                return 0.0D;
            }
        }
    }

    public static class CommandsMSG implements Helper {
        public File f = new File(LanguageUtils.main.getDataFolder(), "languages/en.yml");

        public YamlConfiguration cfg = YamlConfiguration.loadConfiguration(this.f);

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
                    .replace("%player%", p.getName());
        }

        public String getNoPermission() {
            String path = LanguageUtils.formPath(cmds, "no-permission");
            return this.cfg.getString(path);
        }

        public String getCooldown(String time) {
            String path = LanguageUtils.formPath(cmds, "cooldown");
            return this.cfg.getString(path)
                    .replace("%time%", time);
        }

        public String getCantUse2Dot() {
            String path = LanguageUtils.formPath(cmds, "cant-use-2dot");
            return this.cfg.getString(path);
        }

        public String getCantFindPlayer() {
            String path = LanguageUtils.formPath(cmds, "cant-find-player");
            return this.cfg.getString(path);
        }

        public int getDecimalPoints() {
            String path = LanguageUtils.formPath(cmdscfg, "decimal-points");
            return Main.getInstance().getConfig().getInt(path);
        }
    }

    public interface Commands extends Helper {
        File f = new File(LanguageUtils.main.getDataFolder(), "languages/en.yml");

        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);

        String getCmd();

        String getUsage();

        String getOutput();

        double getCooldown();
    }

    public interface Helper {
        default void sendMsg(Player p, String text) {
            text = ChatColor.translateAlternateColorCodes('&', text
                    .replace("%prefix%", Main.cfgu.prefix())
                    .replace("%limit%", String.valueOf(Main.cfgu.getMaxLimit(p)))
                    .replace("%numberofhomes%", String.valueOf(Main.hu.numberOfHomes(p)))
                    .replace("%homes%", Main.hu.homes(p)));
            if (LanguageUtils.main.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI"))
                text = PlaceholderAPI.setPlaceholders(p, text);
            p.sendMessage(text);
        }

        default void sendMsg(CommandSender s, String text) {
            text = ChatColor.translateAlternateColorCodes('&', text
                    .replace("%prefix%", Main.cfgu.prefix()));
            if (s instanceof Player p) {
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
            text = ChatColor.translateAlternateColorCodes('&', text
                    .replace("%prefix%", Main.cfgu.prefix()));
            text = PlaceholderAPI.setPlaceholders(null, text);
            Bukkit.getServer().broadcastMessage(text);
        }
    }

}
