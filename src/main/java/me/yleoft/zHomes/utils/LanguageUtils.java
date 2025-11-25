package me.yleoft.zHomes.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import me.yleoft.zAPI.managers.FileManager;
import me.yleoft.zAPI.mutable.Messages;
import me.yleoft.zAPI.utils.FileUtils;
import me.yleoft.zHomes.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import static me.yleoft.zAPI.utils.ConfigUtils.formPath;
import static me.yleoft.zAPI.utils.StringUtils.transform;
import static me.yleoft.zHomes.Main.needsUpdate;

public class LanguageUtils extends ConfigUtils {

    private static final Main main = Main.getInstance();
    private static FileUtils fuBACKUP = null;

    public static final String hooks = "hooks";
    public static final String cmds = "commands";
    public static final String tpw = "teleport-warmup";
    public static final String griefprevention = "griefprevention";
    public static final String worldguard = "worldguard";
    public static final String vault = "vault";

    public static final File f = new File(main.getDataFolder(), "languages/en.yml");

    public static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);

    public LanguageUtils() {
        cfg = getConfigFile();
    }

    public static YamlConfiguration getConfigFile() {
        List<FileUtils> list = new ArrayList<>();
        list.add(FileManager.getFileUtil("languages/de.yml"));
        list.add(FileManager.getFileUtil("languages/en.yml"));
        list.add(FileManager.getFileUtil("languages/es.yml"));
        list.add(FileManager.getFileUtil("languages/fr.yml"));
        list.add(FileManager.getFileUtil("languages/it.yml"));
        list.add(FileManager.getFileUtil("languages/nl.yml"));
        list.add(FileManager.getFileUtil("languages/pl.yml"));
        list.add(FileManager.getFileUtil("languages/pt-br.yml"));
        list.add(FileManager.getFileUtil("languages/ru.yml"));
        list.add(FileManager.getFileUtil("languages/zhcn.yml"));
        list.add(fuBACKUP);
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
                fuBACKUP = new FileUtils(f, resource);
                returned = (YamlConfiguration)fuBACKUP.getConfig();
            }
        }
        return returned;
    }

    public static class Sethome extends HomesUtils implements Commands {
        public final YamlConfiguration cfg;

        public Sethome() {
            this.cfg = LanguageUtils.getConfigFile();
        }

        public String getCmd() {
            return "sethome";
        }

        public String getUsage() {
            String path = formPath(cmds, getCmd(), "usage");
            return this.cfg.getString(path)
                    .replace("%command%", Main.cfgu.CmdSethomeCommand());
        }

        public String getOutput() {
            return null;
        }

        public String getOutput(String home) {
            String path = formPath(cmds, getCmd(), "output");
            return this.cfg.getString(path)
                    .replace("%command%", Main.cfgu.CmdSethomeCommand())
                    .replace("%home%", home);
        }

        public String getLimitReached(Player p) {
            String path = formPath(cmds, getCmd(), "limit-reached");
            return this.cfg.getString(path)
                    .replace("%command%", Main.cfgu.CmdSethomeCommand())
                    .replace("%limit%", String.valueOf(Main.hu.getMaxLimit(p)));
        }
    }

    public static class Delhome implements Commands {
        public final YamlConfiguration cfg;

        public Delhome() {
            this.cfg = LanguageUtils.getConfigFile();
        }

        public String getCmd() {
            return "delhome";
        }

        public String getUsage() {
            String path = formPath(cmds, getCmd(), "usage");
            return this.cfg.getString(path)
                    .replace("%command%", Main.cfgu.CmdDelhomeCommand());
        }

        public String getOutput() {
            return null;
        }

        public String getOutput(String home) {
            String path = formPath(cmds, getCmd(), "output");
            return this.cfg.getString(path)
                    .replace("%command%", Main.cfgu.CmdDelhomeCommand())
                    .replace("%home%", home);
        }
    }

    public static class Home implements Commands {
        public final YamlConfiguration cfg;

        public Home() {
            this.cfg = LanguageUtils.getConfigFile();
        }

        public String getCmd() {
            return "home";
        }

        public String getUsage() {
            String path = formPath(cmds, getCmd(), "usage");
            return this.cfg.getString(path)
                    .replace("%command%", Main.cfgu.CmdHomeCommand());
        }

        public String getOutput() {
            return null;
        }

        public String getOutput(String home) {
            String path = formPath(cmds, getCmd(), "output");
            return this.cfg.getString(path)
                    .replace("%command%", Main.cfgu.CmdHomeCommand())
                    .replace("%home%", home);
        }

        public String getCantDimensionalTeleport() {
            String path = formPath(cmds, getCmd(), "cant-dimensional-teleport");
            return this.cfg.getString(path);
        }

        public static class HomeRename implements Commands {
            public final YamlConfiguration cfg;

            public HomeRename() {
                this.cfg = LanguageUtils.getConfigFile();
            }

            public String getCmd() {
                return "home.rename";
            }

            public String getUsage() {
                String path = formPath(cmds, getCmd(), "usage");
                return this.cfg.getString(path)
                        .replace("%command%", Main.cfgu.CmdHomeCommand());
            }

            public String getOutput() {
                return null;
            }

            public String getOutput(String home, String newname) {
                String path = formPath(cmds, getCmd(), "output");
                return this.cfg.getString(path)
                        .replace("%home%", home)
                        .replace("%newname%", newname);
            }

            public String getSameName() {
                String path = formPath(cmds, getCmd(), "same-name");
                return this.cfg.getString(path);
            }
        }
    }

    public static class Homes implements Commands {
        public final YamlConfiguration cfg;

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

        public String getOutputSelf(Player p) {
            String path = formPath(cmds, getCmd(), "output");
            return this.cfg.getString(path)
                    .replace("%command%", Main.cfgu.CmdHomesCommand())
                    .replace("%homes%", Main.hu.homes(p))
                    .replace("%amount%", String.valueOf(Main.hu.numberOfHomes(p)));
        }

        public String getInvalidPage() {
            String path = formPath(cmds, getCmd(), "invalid-page");
            return this.cfg.getString(path)
                    .replace("%command%", Main.cfgu.CmdHomesCommand());
        }

        public String getOutput(OfflinePlayer p) {
            String path = formPath(cmds, getCmd(), "others.output");
            return this.cfg.getString(path)
                    .replace("%command%", Main.cfgu.CmdHomesCommand())
                    .replace("%homes%", Main.hu.homes(p))
                    .replace("%amount%", String.valueOf(Main.hu.numberOfHomes(p)))
                    .replace("%player%", Objects.requireNonNull(p.getName()));
        }
    }

    public static class MainCMD implements Commands {
        public final YamlConfiguration cfg;

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
            public final YamlConfiguration cfg;

            public MainHelp() {
                this.cfg = LanguageUtils.getConfigFile();
            }

            public String getCmd() {
                return "main.help";
            }

            public String getUsage() {
                String path = formPath(cmds, getCmd(), "help-noperm");
                return this.cfg.getString(path)
                        .replace("%command%", Main.cfgu.CmdMainCommand());
            }

            public String getUsageWithPerm() {
                String path = formPath(cmds, getCmd(), "help-perm");
                return this.cfg.getString(path)
                        .replace("%command%", Main.cfgu.CmdMainCommand());
            }

            public String getOutput() {
                String path = formPath(cmds, getCmd(), "output");
                return this.cfg.getString(path)
                        .replace("%version%", LanguageUtils.main.getDescription().getVersion());
            }
        }

        public static class MainVersion implements LanguageUtils.Commands {
            public final YamlConfiguration cfg;

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
                String path = formPath(cmds, getCmd(), "output");
                return this.cfg.getString(path)
                        .replace("%version%", LanguageUtils.main.getDescription().getVersion());
            }

            public static class MainVersionUpdate implements LanguageUtils.Commands {
                public final YamlConfiguration cfg;

                public MainVersionUpdate() {
                    this.cfg = LanguageUtils.getConfigFile();
                }

                public String getCmd() {
                    return "main.version.update";
                }

                public String getUsage() {
                    return null;
                }

                public String getOutput() {
                    return null;
                }

                public String getOutput(String newVersion) {
                    String path = formPath(cmds, getCmd(), "output");
                    return this.cfg.getString(path)
                            .replace("%update%", newVersion);
                }

                public String getNoUpdate() {
                    String path = formPath(cmds, getCmd(), "no-update");
                    return this.cfg.getString(path);
                }
            }
        }

        public static class MainInfo implements LanguageUtils.Commands {
            public final YamlConfiguration cfg;

            public MainInfo() {
                this.cfg = LanguageUtils.getConfigFile();
            }

            public String getCmd() {
                return "main.info";
            }

            public String getUsage() {
                return null;
            }

            public String getOutput() {
                String path = formPath(cmds, getCmd(), "output");
                return this.cfg.getString(path)
                        .replace("%name%", LanguageUtils.main.getDescription().getName())
                        .replace("%version%", LanguageUtils.main.getDescription().getVersion())
                        .replace("%author%", String.join(", ", LanguageUtils.main.getDescription().getAuthors()))
                        .replace("%server.software%", Bukkit.getServer().getName())
                        .replace("%server.version%", Bukkit.getServer().getVersion() + " - " + Bukkit.getBukkitVersion())
                        .replace("%requpdate%", (needsUpdate ? this.cfg.getString(formPath(cmds, getCmd(), "requpdate-yes")) : this.cfg.getString(formPath(cmds, getCmd(), "requpdate-no"))))
                        .replace("%command%", Main.cfgu.CmdMainCommand())
                        .replace("%storage.type%", Main.db.databaseType())
                        .replace("%storage.users%", String.valueOf(Main.dbe.getTotalUsers()))
                        .replace("%storage.homes%", String.valueOf(Main.dbe.getTotalHomes()))
                        .replace("%language%", langType())
                        .replace("%use.placeholderapi%", (Main.usePlaceholderAPI ? "&aYes" : "&cNo"))
                        .replace("%use.griefprevention%", (Main.useGriefPrevention ? "&aYes" : "&cNo"))
                        .replace("%use.worldguard%", (Main.useWorldGuard ? "&aYes" : "&cNo"))
                        .replace("%use.vault%", (Main.useVault ? "&aYes" : "&cNo"));
            }
        }

        public static class MainReload implements LanguageUtils.Commands {
            public final YamlConfiguration cfg;

            public MainReload() {
                this.cfg = LanguageUtils.getConfigFile();
            }

            public String getCmd() {
                return "main.reload";
            }

            public String getUsage() {
                String path = formPath(cmds, getCmd(), "usage");
                return this.cfg.getString(path)
                        .replace("%command%", Main.cfgu.CmdMainCommand());
            }

            public String getOutput() {
                return null;
            }

            public String getOutput(long time) {
                String path = formPath(cmds, getCmd(), "output");
                return this.cfg.getString(path)
                        .replace("%command%", Main.cfgu.CmdMainCommand())
                        .replace("%time%", String.valueOf(time));
            }

            public String getOutputCommands(long time) {
                String path = formPath(cmds, getCmd(), "commands.output");
                return this.cfg.getString(path)
                        .replace("%command%", Main.cfgu.CmdMainCommand())
                        .replace("%time%", String.valueOf(time));
            }

            public String getOutputConfig(long time) {
                String path = formPath(cmds, getCmd(), "config.output");
                return this.cfg.getString(path)
                        .replace("%command%", Main.cfgu.CmdMainCommand())
                        .replace("%time%", String.valueOf(time));
            }

            public String getOutputLanguages(long time) {
                String path = formPath(cmds, getCmd(), "languages.output");
                return this.cfg.getString(path)
                        .replace("%command%", Main.cfgu.CmdMainCommand())
                        .replace("%time%", String.valueOf(time));
            }
        }

        public static class MainNearhomes implements LanguageUtils.Commands {
            public final YamlConfiguration cfg;

            public MainNearhomes() {
                this.cfg = LanguageUtils.getConfigFile();
            }

            public String getCmd() {
                return "main.nearhomes";
            }

            public String getUsage() {
                String path = formPath(cmds, getCmd(), "usage");
                return this.cfg.getString(path)
                        .replace("%command%", Main.cfgu.CmdMainCommand());
            }

            public String getOutput() {
                return null;
            }

            public String getOutput(HashMap<OfflinePlayer, List<String>> homes, double radius) {
                String path = formPath(cmds, getCmd(), "output");
                StringBuilder homesList = new StringBuilder();
                for (OfflinePlayer p : homes.keySet()) {
                    for(String home : homes.get(p)) {
                        if(!homesList.toString().isEmpty()) homesList.append(", ");
                        homesList.append(getHomeString(p, home));
                    }
                }
                if(homesList.toString().isEmpty()) {
                    return this.cfg.getString(path + "-not-found")
                            .replace("%command%", Main.cfgu.CmdMainCommand())
                            .replace("%radius%", String.valueOf(radius));
                }
                return this.cfg.getString(path)
                        .replace("%command%", Main.cfgu.CmdMainCommand())
                        .replace("%radius%", String.valueOf(radius))
                        .replace("%homes%", homesList.toString());
            }

            private String getHomeString(OfflinePlayer p, String home) {
                String path = formPath(cmds, getCmd(), "home-string");
                return this.cfg.getString(path)
                        .replace("%owner%", (p == null || p.getName() == null) ? "----" : p.getName())
                        .replace("%home%", home);
            }
        }

        public static class MainConverter implements LanguageUtils.Commands {
            public final YamlConfiguration cfg;

            public MainConverter() {
                this.cfg = LanguageUtils.getConfigFile();
            }

            public String getCmd() {
                return "main.converter";
            }

            public String getUsage() {
                String path = formPath(cmds, getCmd(), "usage");
                return this.cfg.getString(path)
                        .replace("%command%", Main.cfgu.CmdMainCommand());
            }

            public String getOutput() {
                String path = formPath(cmds, getCmd(), "output");
                return this.cfg.getString(path)
                        .replace("%command%", Main.cfgu.CmdMainCommand());
            }

            public String getError() {
                String path = formPath(cmds, getCmd(), "error");
                return this.cfg.getString(path)
                        .replace("%command%", Main.cfgu.CmdMainCommand());
            }
        }
    }

    public static class TeleportWarmupMSG implements Helper {
        public final YamlConfiguration cfg;

        public TeleportWarmupMSG() {
            this.cfg = LanguageUtils.getConfigFile();
        }

        public String getWarmup(int time) {
            String path = formPath(tpw, "warmup");
            return this.cfg.getString(path)
                    .replace("%time%", String.valueOf(time));
        }

        public String getWarmupActionbar(int time) {
            String path = formPath(tpw, "warmup-actionbar");
            return this.cfg.getString(path)
                    .replace("%time%", String.valueOf(time));
        }

        public String getCancelled() {
            String path = formPath(tpw, "cancelled");
            return this.cfg.getString(path);
        }

        public String getCancelledActionbar() {
            String path = formPath(tpw, "cancelled-actionbar");
            return this.cfg.getString(path);
        }
    }

    public static class HooksMSG implements Helper {
        public final YamlConfiguration cfg;

        public HooksMSG() {
            this.cfg = LanguageUtils.getConfigFile();
        }

        public String getGriefPreventionCantSetHomes() {
            String path = formPath(hooks, griefprevention, "cant-set-homes");
            return this.cfg.getString(path);
        }

        public String getWorldGuardCantUseHomes() {
            String path = formPath(hooks, worldguard, "cant-use-homes");
            return this.cfg.getString(path);
        }

        public String getWorldGuardCantSetHomes() {
            String path = formPath(hooks, worldguard, "cant-set-homes");
            return this.cfg.getString(path);
        }

        public String getVaultCantAfford(Float cost) {
            String path = formPath(hooks, vault, "cant-afford-command");
            return this.cfg.getString(path)
                    .replace("%cost%", Float.toString(cost));
        }
    }

    public static class CommandsMSG implements Helper {
        public final YamlConfiguration cfg;

        public CommandsMSG() {
            this.cfg = LanguageUtils.getConfigFile();
        }

        public String getHomeAlreadyExist() {
            String path = formPath(cmds, "home-already-exist");
            return this.cfg.getString(path);
        }

        public String getHomeDoesntExist() {
            String path = formPath(cmds, "home-doesnt-exist");
            return this.cfg.getString(path);
        }

        public String getHomeDoesntExistOthers(OfflinePlayer p) {
            String path = formPath(cmds, "home-doesnt-exist-others");
            return this.cfg.getString(path)
                    .replace("%player%", Objects.requireNonNull(p.getName()));
        }

        public String getNoPermission() {
            String path = formPath(cmds, "no-permission");
            return this.cfg.getString(path);
        }

        public String getOnlyPlayers() {
            String path = formPath(cmds, "only-players");
            return this.cfg.getString(path);
        }

        public String getCantUse2Dot() {
            String path = formPath(cmds, "cant-use-2dot");
            return this.cfg.getString(path);
        }

        public String getCantFindPlayer() {
            String path = formPath(cmds, "cant-find-player");
            return this.cfg.getString(path);
        }

        public String getUnableToFindSafeLocation() {
            String path = formPath(cmds, "unable-to-find-safe-location");
            return this.cfg.getString(path);
        }

        public String getWorldRestricted() {
            String path = formPath(cmds, "world-restricted");
            return this.cfg.getString(path);
        }
    }

    public static void loadzAPIMessages() {
        YamlConfiguration config = LanguageUtils.getConfigFile();
        Messages.setCooldownExpired(Objects.requireNonNull(Helper.getText(config.getString(formPath(cmds, "in-cooldown")))));
    }

    public interface Commands extends Helper {
        String getCmd();

        String getUsage();

        String getOutput();
    }

    public interface Helper {
        default void sendMsg(Player p, String text) {
            if(text.isEmpty()) return;
            text = getText(p, text
                    .replace("%limit%", String.valueOf(Main.cfgu.getMaxLimit(p)))
                    .replace("%numberofhomes%", String.valueOf(Main.hu.numberOfHomes(p)))
                    .replace("%homes%", Main.hu.homes(p)));
            p.sendMessage(text);
        }

        default void sendMsg(CommandSender s, String text) {
            if(text.isEmpty()) return;
            text = getText(s, text);
            if (s instanceof Player) {
                Player p = (Player)s;
                text = text.replace("%limit%", String.valueOf(Main.cfgu.getMaxLimit(p)))
                        .replace("%numberofhomes%", String.valueOf(Main.hu.numberOfHomes(p)))
                        .replace("%homes%", Main.hu.homes(p));
            }
            s.sendMessage(text);
        }

        default void broadcast(String text) {
            if(text.isEmpty()) return;
            text = getText(null, text);
            Bukkit.getServer().broadcastMessage(text);
        }

        static String getText(CommandSender s, String text) {
            text = transform(text
                    .replace("%prefix%", Main.cfgu.prefix()));
            if(s instanceof Player) {
                Player p = (Player)s;
                text = transform(p, text);
            }
            return text;
        }
        static String getText(String text) {
            return getText(null, text);
        }

    }

}
