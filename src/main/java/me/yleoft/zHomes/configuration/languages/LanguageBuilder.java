package me.yleoft.zHomes.configuration.languages;

import me.yleoft.zAPI.configuration.YAMLBuilder;
import me.yleoft.zAPI.hooks.HookRegistry;
import me.yleoft.zAPI.utility.TextFormatter;
import me.yleoft.zHomes.storage.DatabaseEditor;
import me.yleoft.zHomes.utility.HomesUtils;
import me.yleoft.zHomes.zHomes;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanguageBuilder extends YAMLBuilder {

    private static final String currentVersion = "1.0.1";

    private final static File languageFolder = new File(zHomes.getInstance().getDataFolder(), "languages");

    protected Map<String, String> translations() {
        HashMap<String, String> t = new HashMap<>();
        t.put(formPath("config", "comment", "header1"), "# |                                   Plugin links & support                                     | #");
        t.put(formPath("config", "comment", "header2"), "# |   zHomes (c) yL3oft — released under the MIT License.                                        | #");
        t.put(formPath("config", "comment", "database"), "Edit your database settings below");
        t.put(formPath("config", "comment", "database", "type"), """
                Here you can define how to store the plugin data.
                OPTIONS:
                - H2 (Preferred over SQLite)
                - SQLite
                - MariaDB (Preferred over MySQL)
                - MySQL
                DEFAULT: H2
                """);
        t.put(formPath("config", "comment", "pool-size"), "# WARNING: DO NOT CHANGE ANYTHING BELOW IF YOU DON'T KNOW WHAT YOU'RE DOING");
        t.put(formPath("config", "comment", "general", "language"), """
                Here you can define the language of the plugin, all languages can be found, edited and created on languages' directory.
                CURRENTLY AVAILABLE LANGUAGES: [de, en, es, fr, it, nl, pl, pt-br, ru, zhcn, <custom>]
                """);
        t.put(formPath("config", "comment", "general", "announce-update"), "Toggle whether the plugin should announce available updates in the console and to players with the appropriate permission.");
        t.put(formPath("config", "comment", "general", "metrics"), """
                Enable or disable metrics collection to help improve the plugin.
                All data collected is anonymous and used solely for statistical purposes.
                !WARNING: Requires server restart to take effect!
                """);
        t.put(formPath("config", "comment", "general", "debug-mode"), "Enable or disable debug mode for more detailed logging output.");
        t.put(formPath("config", "comment", "teleport-options"), "Settings related to teleportation behavior");
        t.put(formPath("config", "comment", "teleport-options", "enable-safe-teleport"), "Enable or disable safe teleportation to prevent players from being teleported into dangerous locations.");
        t.put(formPath("config", "comment", "teleport-options", "dimensional-teleportation"), "Enable or disable dimensional teleportation, allowing players to teleport between different worlds or dimensions.");
        t.put(formPath("config", "comment", "teleport-options", "play-sound"), "Play a sound effect when a player is teleported.");
        t.put(formPath("config", "comment", "teleport-options", "restricted-worlds", "enable"), "Enable or disable the restriction of teleportation to certain worlds.");
        t.put(formPath("config", "comment", "teleport-options", "restricted-worlds", "mode"), """
                Define the mode for restricted worlds.
                OPTIONS:
                - blacklist: Players cannot teleport to worlds listed below.
                - whitelist: Players can only teleport to worlds listed below.
                """);
        t.put(formPath("config", "comment", "teleport-options", "restricted-worlds", "worlds"), "List of worlds affected by the restricted worlds setting.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "enable"), "Enable or disable teleportation warmup period.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "time"), "Define the warmup time in seconds before teleportation occurs.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "cancel-on-move"), "Cancel the teleportation if the player moves during the warmup period.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "show-on-actionbar"), "Show warmup countdown on the action bar.");
        t.put(formPath("config", "comment", "limits", "enabled"), "Enable or disable home limits for players.");
        t.put(formPath("config", "comment", "limits", "default"), "Default number of homes a player can set.");
        t.put(formPath("config", "comment", "limits", "examples"), "Limit examples based on player groups.");
        t.put(formPath("config", "comment", "commands"), "!WARNING: Mostly everything below needs a restart to apply.");
        t.put(formPath("config", "comment", "commands", "command-cost"), "command-cost requires Vault to work.");
        t.put(formPath("config", "comment", "commands", "homes", "types"), """
                Define how the homes will be displayed to the player.
                OPTIONS:
                - text: Displays homes in a simple list format.
                - menu: Opens a graphical menu to select homes.
                """);
        t.put(formPath("config", "comment", "permissions"), "Permission nodes used by the plugin");
        t.put(formPath("config", "comment", "permissions", "bypass", "limit"), "Permission to bypass home limits");
        t.put(formPath("config", "comment", "permissions", "bypass", "dimensional-teleportation"), "Permission to bypass dimensional teleportation restrictions");
        t.put(formPath("config", "comment", "permissions", "bypass", "safe-teleportation"), "Permission to bypass safe teleportation checks");
        t.put(formPath("config", "comment", "permissions", "bypass", "restricted-worlds"), "Permission to bypass restricted worlds checks");
        t.put(formPath("config", "comment", "permissions", "bypass", "warmup"), "Permission to bypass teleportation warmup");
        t.put(formPath("config", "comment", "permissions", "bypass", "command-costs"), "Permission to bypass command costs");
        t.put(formPath("config", "comment", "permissions", "bypass", "command-cooldowns"), "Permission to bypass command cooldowns");
        return t;
    }

    public LanguageBuilder(String langFileName) {
        super(new File(languageFolder, langFileName+".yml"));
        migrateLegacyColors(true);
        buildLang();
    }

    public void buildLang() {
        header(t("header", """
                ####################################################################################################
                # +----------------------------------------------------------------------------------------------+ #
                # |                                                                                              | #
                # |                                   zHomes — Language File                                     | #
                # |                                                                                              | #
                # |   • Wiki:        https://docs.yleoft.me/zhomes                                               | #
                # |   • Discord:     https://discord.gg/yCdhVDgn4K                                               | #
                # |   • GitHub:      https://github.com/yL3oft/zHomes                                            | #
                # |                                                                                              | #
                # +----------------------------------------------------------------------------------------------+ #
                ####################################################################################################
                """));

        if(versionOlder(currentVersion)) {
            voidPath(formPath("commands", "main", "version", "update"));
            updateVersion(currentVersion);
        }

        commentSection("hooks", t(formPath("comments", "hooks"), "Here you can manage hook messages."));
        addDefault(formPath("hooks", "griefprevention", "cant-set-homes"), t(formPath("hooks", "griefprevention", "cant-set-homes"),
                "%prefix% <red>You can't set homes in this area."));

        addDefault(formPath("hooks", "worldguard", "cant-use-homes"), t(formPath("hooks", "worldguard", "cant-use-homes"),
                "%prefix% <red>You can't use homes here."));
        addDefault(formPath("hooks", "worldguard", "cant-set-homes"), t(formPath("hooks", "worldguard", "cant-set-homes"),
                "%prefix% <red>You can't set a home here."));

        addDefault(formPath("hooks", "vault", "cant-afford-command"), t(formPath("hooks", "vault", "cant-afford-command"),
                "%prefix% <red>You need <gold>$%cost% <red>in order to execute this command."));

        commentSection("teleport-warmup", t(formPath("comments", "teleport-warmup"), "Messages related to teleport warmup."));
        addDefault(formPath("teleport-warmup", "warmup"), t(formPath("teleport-warmup", "warmup"),
                "%prefix% <green>Teleporting in %time% seconds... Don't move!"));
        addDefault(formPath("teleport-warmup", "warmup-actionbar"), t(formPath("teleport-warmup", "warmup-actionbar"),
                "<green>Teleporting in %time% seconds..."));
        addDefault(formPath("teleport-warmup", "cancelled"), t(formPath("teleport-warmup", "cancelled"),
                "%prefix% <red>You moved! Teleportation cancelled."));
        addDefault(formPath("teleport-warmup", "cancelled-actionbar"), t(formPath("teleport-warmup", "cancelled-actionbar"),
                "<red>You moved! Teleportation cancelled."));

        commentSection("commands", t(formPath("comments", "commands"), "Messages related to commands."));
        comment(false, t(formPath("comments", "commands", "no-permission"), "Up here you will find messages that can be used in multiple commands."));
        addDefault(formPath("commands", "no-permission"), t(formPath("commands", "no-permission"),
                "%prefix% <red>You don't have permission to execute this command."));
        addDefault(formPath("commands", "only-players"), t(formPath("commands", "only-players"),
                "%prefix% <red>Only players can execute this command."));
        addDefault(formPath("commands", "in-cooldown"), t(formPath("commands", "in-cooldown"),
                "%prefix% <red>You must wait %time% seconds before using this command again."));
        addDefault(formPath("commands", "home-already-exist"), t(formPath("commands", "home-already-exist"),
                "%prefix% <red>You already have a home with this name."));
        addDefault(formPath("commands", "home-doesnt-exist"), t(formPath("commands", "home-doesnt-exist"),
                "%prefix% <red>You don't have any home with this name."));
        addDefault(formPath("commands", "home-doesnt-exist-others"), t(formPath("commands", "home-doesnt-exist-others"),
                "%prefix% <yellow>%player% <red>don't have any home with this name."));
        addDefault(formPath("commands", "cant-use-2dot"), t(formPath("commands", "cant-use-2dot"),
                "%prefix% <red>You can't use <yellow>':' <red>in this command."));
        addDefault(formPath("commands", "cant-find-player"), t(formPath("commands", "cant-find-player"),
                "%prefix% <red>This player was not found."));
        addDefault(formPath("commands", "unable-to-find-safe-location"), t(formPath("commands", "unable-to-find-safe-location"),
                "%prefix% <red>Unable to find a safe location to teleport you."));
        addDefault(formPath("commands", "world-restricted-sethome"), t(formPath("commands", "world-restricted-sethome"),
                "%prefix% <red>You can't set homes in this world."));
        addDefault(formPath("commands", "world-restricted-home"), t(formPath("commands", "world-restricted-home"),
                "%prefix% <red>You can't teleport to homes in that world."));

        commentSection(formPath("commands", "main"), t(formPath("comments", "commands", "main"), "Below you will find messages specific for the commands."));

        // commands.main.help
        addDefault(formPath("commands", "main", "help", "help-perm"), t(formPath("commands", "main", "help", "help-perm"), """
                %prefix% <aqua>Usages of <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>help <gray>Shows this exact help message
                <red>-> <yellow>/%command% <green>info <gray>Shows plugin information
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                <red>-> <yellow>/%command% <green>(version|ver)
                <red>-> <yellow>/%command% <green>nearhomes <gold>(<radius>) <gray>List homes near you within a certain radius
                <red>-> <yellow>/%command% <green>parse <gold>(Player) (String) <gray>Parses a string with placeholders for a specific player
                <red>-> <yellow>/%command% <green>converter (<converter-type>) <gray>Convert data from one place to another
                <red>-> <yellow>/%command% <green>export <gray>Exports all homes to a single file
                <red>-> <yellow>/%command% <green>import (<file>) <gray>Imports homes from a single file
                """));
        addDefault(formPath("commands", "main", "help", "help-noperm"), t(formPath("commands", "main", "help", "help-noperm"), """
                %prefix% <aqua>Usages of <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>(help|?) <gray>Shows this exact help message
                <red>-> <yellow>/%command% <green>(version|ver) <gray>Shows plugin version
                """));

        // commands.main.info
        addDefault(formPath("commands", "main", "info", "output"), t(formPath("commands", "main", "info", "output"), """
                %prefix% <aqua>Running <dark_aqua>%name% v%version% <aqua>by <dark_aqua>%author%<aqua>:
                %prefix% <aqua>- Server Info:
                %prefix% <dark_aqua>   Software: <white>%server.software%
                %prefix% <dark_aqua>   Version: <white>%server.version%
                %prefix% <dark_aqua>   Require update: <white>%requpdate%
                %prefix% <dark_aqua>   Language: <white>%language%
                %prefix% <aqua>- Storage:
                %prefix% <dark_aqua>   Type: <white>%storage.type%
                %prefix% <dark_aqua>   Users: <white>%storage.users%
                %prefix% <dark_aqua>   Homes: <white>%storage.homes%
                %prefix% <aqua>- Hooks:
                %prefix% <dark_aqua>   PlaceholderAPI: <white>%use.placeholderapi%
                %prefix% <dark_aqua>   MiniPlaceholders: <white>%use.miniplaceholders%
                %prefix% <dark_aqua>   GriefPrevention: <white>%use.griefprevention%
                %prefix% <dark_aqua>   WorldGuard: <white>%use.worldguard%
                %prefix% <dark_aqua>   Vault: <white>%use.vault%
                """));
        addDefault(formPath("commands", "main", "info", "requpdate-yes"), t(formPath("commands", "main", "info", "requpdate-yes"),
                "<red>Yes <gray>(Use <yellow>/%command% version <gray>for more info)"));
        addDefault(formPath("commands", "main", "info", "requpdate-no"), t(formPath("commands", "main", "info", "requpdate-no"),
                "<green>No"));

        // commands.main.version
        addDefault(formPath("commands", "main", "version", "output"), t(formPath("commands", "main", "version", "output"), """
                %prefix% <aqua>Current version: <green>%version%
                %prefix% <green>You are already using the latest version of zHomes.
                """));

        // commands.main.reload
        addDefault(formPath("commands", "main", "reload", "usage"), t(formPath("commands", "main", "reload", "usage"), """
                %prefix% <aqua>Usages of <yellow>/%command% <green>(reload|rl)<aqua>:
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                """));
        addDefault(formPath("commands", "main", "reload", "output"), t(formPath("commands", "main", "reload", "output"),
                "%prefix% <green>Reloaded plugin in <aqua>%time%ms<green>."));
        addDefault(formPath("commands", "main", "reload", "commands", "output"), t(formPath("commands", "main", "reload", "commands", "output"),
                "%prefix% <green>Reloaded all plugin commands in <aqua>%time%ms<green>."));
        addDefault(formPath("commands", "main", "reload", "config", "output"), t(formPath("commands", "main", "reload", "config", "output"),
                "%prefix% <green>Reloaded plugin config file in <aqua>%time%ms<green>."));
        addDefault(formPath("commands", "main", "reload", "languages", "output"), t(formPath("commands", "main", "reload", "languages", "output"), "%prefix% <green>Reloaded plugin languages in <aqua>%time%ms<green>."));

        // commands.main.nearhomes
        addDefault(formPath("commands", "main", "nearhomes", "usage"), t(formPath("commands", "main", "nearhomes", "usage"),
                "<red>-> <yellow>/%command% nearhomes <green>[<Radius>]"));
        addDefault(formPath("commands", "main", "nearhomes", "output"), t(formPath("commands", "main", "nearhomes", "output"),
                "%prefix% <gray>Homes near you within <yellow>%radius% <gray>blocks: <white>%homes%"));
        addDefault(formPath("commands", "main", "nearhomes", "output-not-found"), t(formPath("commands", "main", "nearhomes", "output-not-found"),
                "%prefix% <red>No homes found within <yellow>%radius% <red>blocks."));
        addDefault(formPath("commands", "main", "nearhomes", "home-string"), t(formPath("commands", "main", "nearhomes", "home-string"),
                "<yellow><hover:show_text:'<green>Click to teleport.'><click:run_command:'/%homecommand% %owner%:%home%'>%home%</click></hover> <gray><hover:show_text:'<green>Click to filter search for player.'><click:run_command:'/%maincommand% nearhomes %radius% -user %owner%'>(%owner%)</click></hover>"));
        addDefault(formPath("commands", "main", "nearhomes", "filtered-player", "output"), t(formPath("commands", "main", "nearhomes", "filtered-player", "output"),
                "%prefix% <gray>Homes of <yellow>%player% <gray>near you within <yellow>%radius% <gray>blocks: <white>%homes%"));
        addDefault(formPath("commands", "main", "nearhomes", "filtered-player", "home-string"), t(formPath("commands", "main", "nearhomes", "filtered-player", "home-string"),
                "<yellow><hover:show_text:'<green>Click to teleport.'><click:run_command:'/%homecommand% %player%:%home%'>%home%</click></hover>"));

        // commands.main.parse
        addDefault(formPath("commands", "main", "parse", "usage"), t(formPath("commands", "main", "parse", "usage"),
                "<red>-> <yellow>/%command% parse <gold>(Player) (String)"));
        addDefault(formPath("commands", "main", "parse", "output"), t(formPath("commands", "main", "parse", "output"),
                "%prefix% <gray>Parsed text: <white>%parsed%"));

        // commands.main.converter
        addDefault(formPath("commands", "main", "converter", "usage"), t(formPath("commands", "main", "converter", "usage"), """
                %prefix% <aqua>Usages of <yellow>/%command% <green>converter<aqua>:
                <red>-> <yellow>/%command% <green>converter sqlitetoh2
                <red>-> <yellow>/%command% <green>converter sqlitetomysql
                <red>-> <yellow>/%command% <green>converter sqlitetomariadb
                <red>-> <yellow>/%command% <green>converter mysqltosqlite
                <red>-> <yellow>/%command% <green>converter mysqltoh2
                <red>-> <yellow>/%command% <green>converter mariadbtosqlite
                <red>-> <yellow>/%command% <green>converter mariadbtoh2
                <red>-> <yellow>/%command% <green>converter h2tosqlite
                <red>-> <yellow>/%command% <green>converter h2tomysql
                <red>-> <yellow>/%command% <green>converter h2tomariadb
                <red>-> <yellow>/%command% <green>converter essentials
                <red>-> <yellow>/%command% <green>converter sethome
                <red>-> <yellow>/%command% <green>converter ultimatehomes
                <red>-> <yellow>/%command% <green>converter xhomes
                <red>-> <yellow>/%command% <green>converter zhome
                """));
        addDefault(formPath("commands", "main", "converter", "output"), t(formPath("commands", "main", "converter", "output"),
                "%prefix% <green>All data converted!"));
        addDefault(formPath("commands", "main", "converter", "error"), t(formPath("commands", "main", "converter", "error"),
                "%prefix% <red>Something went wrong converting the data, please check your server console."));

        // commands.main.export
        addDefault(formPath("commands", "main", "export", "output"), t(formPath("commands", "main", "export", "output"),
                "%prefix% <green>All homes exported to <yellow>%file%<green>!"));
        addDefault(formPath("commands", "main", "export", "error"), t(formPath("commands", "main", "export", "error"),
                "%prefix% <red>Something went wrong exporting the data, please check your server console."));

        // commands.main.import
        addDefault(formPath("commands", "main", "import", "usage"), t(formPath("commands", "main", "import", "usage"),
                "<red>-> <yellow>/%command% import <green>(<file>)"));
        addDefault(formPath("commands", "main", "import", "output"), t(formPath("commands", "main", "import", "output"),
                "%prefix% <green>All homes imported from <yellow>%file%<green>!"));
        addDefault(formPath("commands", "main", "import", "file-not-found"), t(formPath("commands", "main", "import", "file-not-found"),
                "%prefix% <red>The file <yellow>%file% <red>was not found."));
        addDefault(formPath("commands", "main", "import", "error"), t(formPath("commands", "main", "import", "error"),
                "%prefix% <red>Something went wrong importing the data, please check your server console."));

        // commands.sethome
        addDefault(formPath("commands", "sethome", "usage"), t(formPath("commands", "sethome", "usage"),
                "<red>-> <yellow>/%command% <green>(Home)"));
        addDefault(formPath("commands", "sethome", "output"), t(formPath("commands", "sethome", "output"),
                "%prefix% <green>Home <yellow>%home% <green>set to your position."));
        addDefault(formPath("commands", "sethome", "limit-reached"), t(formPath("commands", "sethome", "limit-reached"),
                "<red>You can't set more homes because you reached your limit <yellow>(%limit% homes)<red>!"));

        // commands.delhome
        addDefault(formPath("commands", "delhome", "usage"), t(formPath("commands", "delhome", "usage"),
                "<red>-> <yellow>/%command% <green>(Home)"));
        addDefault(formPath("commands", "delhome", "output"), t(formPath("commands", "delhome", "output"),
                "%prefix% <red>Home <yellow>%home% <red>deleted."));

        // commands.home
        addDefault(formPath("commands", "home", "usage"), t(formPath("commands", "home", "usage"),
                "<red>-> <yellow>/%command% <green>(Home)"));
        addDefault(formPath("commands", "home", "output"), t(formPath("commands", "home", "output"),
                "%prefix% <green>Teleported to <yellow>%home%<green>..."));
        addDefault(formPath("commands", "home", "cant-dimensional-teleport"), t(formPath("commands", "home", "cant-dimensional-teleport"),
                "<red>Your teleport was cancelled! Dimensional teleportation is disabled."));

        // commands.home.rename
        addDefault(formPath("commands", "home", "rename", "usage"), t(formPath("commands", "home", "rename", "usage"),
                "<red>-> <yellow>/%command% <green>rename (Home) (NewName)"));
        addDefault(formPath("commands", "home", "rename", "output"), t(formPath("commands", "home", "rename", "output"),
                "%prefix% <green>Home <yellow>%home% <green>renamed to <yellow>%newname%<green>."));
        addDefault(formPath("commands", "home", "rename", "same-name"), t(formPath("commands", "home", "rename", "same-name"),
                "%prefix% <red>You can't rename a home to the same name."));

        // commands.homes
        addDefault(formPath("commands", "homes", "output"), t(formPath("commands", "homes", "output"),
                "%prefix% <gray>Your homes (%amount%): <white>%homes%"));
        addDefault(formPath("commands", "homes", "home-string"), t(formPath("commands", "homes", "home-string"), "<reset><hover:show_text:'<green>Click to teleport.'><click:run_command:'/%homecommand% %home%'>%home%</click></hover>"));
        addDefault(formPath("commands", "homes", "invalid-page"), t(formPath("commands", "homes", "invalid-page"),
                "%prefix% <red>Invalid page number! Please use a number higher than 0."));

        // commands.homes.others
        addDefault(formPath("commands", "homes", "others", "output"), t(formPath("commands", "homes", "others", "output"),
                "%prefix% <yellow>%player%'s <gray>homes (%amount%): <white>%homes%"));
        addDefault(formPath("commands", "homes", "others", "home-string"), t(formPath("commands", "homes", "others", "home-string"), "<reset><hover:show_text:'<green>Click to teleport.'><click:run_command:'/%homecommand% %player%:%home%'>%home%</click></hover>"));

        footer("!!! DON'T TOUCH config-version UNLESS YOU KNOW EXACTLY WHAT YOU'RE DOING !!!");

        build();
    }

    //<editor-fold desc="Config">
    public String getConfigCommentHeader1() {
        return translations().get(formPath("config", "comment", "header1"));
    }
    public String getConfigCommentHeader2() {
        return translations().get(formPath("config", "comment", "header2"));
    }
    public String getConfigCommentDatabase() {
        return translations().get(formPath("config", "comment", "database"));
    }
    public String getConfigCommentDatabaseType() {
        return translations().get(formPath("config", "comment", "database", "type"));
    }
    public String getConfigCommentPoolSize() {
        return translations().get(formPath("config", "comment", "pool-size"));
    }
    public String getConfigCommentGeneralLanguage() {
        return translations().get(formPath("config", "comment", "general", "language"));
    }
    public String getConfigCommentGeneralAnnounceUpdate() {
        return translations().get(formPath("config", "comment", "general", "announce-update"));
    }
    public String getConfigCommentGeneralMetrics() {
        return translations().get(formPath("config", "comment", "general", "metrics"));
    }
    public String getConfigCommentGeneralDebugMode() {
        return translations().get(formPath("config", "comment", "general", "debug-mode"));
    }
    public String getConfigCommentTeleportOptions() {
        return translations().get(formPath("config", "comment", "teleport-options"));
    }
    public String getConfigCommentTeleportOptionsEnableSafeTeleport() {
        return translations().get(formPath("config", "comment", "teleport-options", "enable-safe-teleport"));
    }
    public String getConfigCommentTeleportOptionsDimensionalTeleportation() {
        return translations().get(formPath("config", "comment", "teleport-options", "dimensional-teleportation"));
    }
    public String getConfigCommentTeleportOptionsPlaySound() {
        return translations().get(formPath("config", "comment", "teleport-options", "play-sound"));
    }
    public String getConfigCommentTeleportOptionsRestrictedWorldsEnable() {
        return translations().get(formPath("config", "comment", "teleport-options", "restricted-worlds", "enable"));
    }
    public String getConfigCommentTeleportOptionsRestrictedWorldsMode() {
        return translations().get(formPath("config", "comment", "teleport-options", "restricted-worlds", "mode"));
    }
    public String getConfigCommentTeleportOptionsRestrictedWorldsWorlds() {
        return translations().get(formPath("config", "comment", "teleport-options", "restricted-worlds", "worlds"));
    }
    public String getConfigCommentTeleportOptionsWarmupEnable() {
        return translations().get(formPath("config", "comment", "teleport-options", "warmup", "enable"));
    }
    public String getConfigCommentTeleportOptionsWarmupTime() {
        return translations().get(formPath("config", "comment", "teleport-options", "warmup", "time"));
    }
    public String getConfigCommentTeleportOptionsWarmupCancelOnMove() {
        return translations().get(formPath("config", "comment", "teleport-options", "warmup", "cancel-on-move"));
    }
    public String getConfigCommentTeleportOptionsWarmupShowOnActionbar() {
        return translations().get(formPath("config", "comment", "teleport-options", "warmup", "show-on-actionbar"));
    }
    public String getConfigCommentLimitsEnabled() {
        return translations().get(formPath("config", "comment", "limits", "enabled"));
    }
    public String getConfigCommentLimitsDefault() {
        return translations().get(formPath("config", "comment", "limits", "default"));
    }
    public String getConfigCommentLimitsExamples() {
        return translations().get(formPath("config", "comment", "limits", "examples"));
    }
    public String getConfigCommentCommands() {
        return translations().get(formPath("config", "comment", "commands"));
    }
    public String getConfigCommentCommandsCommandCost() {
        return translations().get(formPath("config", "comment", "commands", "command-cost"));
    }
    public String getConfigCommentCommandsHomesTypes() {
        return translations().get(formPath("config", "comment", "commands", "homes", "types"));
    }
    public String getConfigCommentPermissions() {
        return translations().get(formPath("config", "comment", "permissions"));
    }
    public String getConfigCommentPermissionsBypassLimit() {
        return translations().get(formPath("config", "comment", "permissions", "bypass", "limit"));
    }
    public String getConfigCommentPermissionsBypassDimensionalTeleportation() {
        return translations().get(formPath("config", "comment", "permissions", "bypass", "dimensional-teleportation"));
    }
    public String getConfigCommentPermissionsBypassSafeTeleportation() {
        return translations().get(formPath("config", "comment", "permissions", "bypass", "safe-teleportation"));
    }
    public String getConfigCommentPermissionsBypassRestrictedWorlds() {
        return translations().get(formPath("config", "comment", "permissions", "bypass", "restricted-worlds"));
    }
    public String getConfigCommentPermissionsBypassWarmup() {
        return translations().get(formPath("config", "comment", "permissions", "bypass", "warmup"));
    }
    public String getConfigCommentPermissionsBypassCommandCosts() {
        return translations().get(formPath("config", "comment", "permissions", "bypass", "command-costs"));
    }
    public String getConfigCommentPermissionsBypassCommandCooldowns() {
        return translations().get(formPath("config", "comment", "permissions", "bypass", "command-cooldowns"));
    }
    //</editor-fold>

    //<editor-fold desc="Hooks">
    public String getGFCantSetHomes() {
        return getString(formPath("hooks", "griefprevention", "cant-set-homes"));
    }
    public String getWGCantUseHomes() {
        return getString(formPath("hooks", "worldguard", "cant-use-homes"));
    }
    public String getWGCantSetHomes() {
        return getString(formPath("hooks", "worldguard", "cant-set-homes"));
    }
    public String getVaultCantAffordCommand() {
        return getString(formPath("hooks", "vault", "cant-afford-command"));
    }
    //</editor-fold>

    //<editor-fold desc="Teleport Warmup">
    public String getWarmup(int time) {
        return getString(formPath("teleport-warmup", "warmup"))
                .replace("%time%", String.valueOf(time));
    }
    public String getWarmupActionbar(int time) {
        return getString(formPath("teleport-warmup", "warmup-actionbar"))
                .replace("%time%", String.valueOf(time));
    }
    public String getWarmupCancelled() {
        return getString(formPath("teleport-warmup", "cancelled"));
    }
    public String getWarmupCancelledActionbar() {
        return getString(formPath("teleport-warmup", "cancelled-actionbar"));
    }
    //</editor-fold>

    //<editor-fold desc="Commands">
    //<editor-fold desc="General">
    public String getNoPermission() {
        return getString(formPath("commands", "no-permission"));
    }
    public String getOnlyPlayers() {
        return getString(formPath("commands", "only-players"));
    }
    public String getInCooldown() {
        return getString(formPath("commands", "in-cooldown"));
    }
    public String getHomeAlreadyExist() {
        return getString(formPath("commands", "home-already-exist"));
    }
    public String getHomeDoesntExist() {
        return getString(formPath("commands", "home-doesnt-exist"));
    }
    public String getHomeDoesntExistOthers(String player) {
        return getString(formPath("commands", "home-doesnt-exist-others"))
                .replace("%player%", player);
    }
    public String getCantUse2Dot() {
        return getString(formPath("commands", "cant-use-2dot"));
    }
    public String getCantFindPlayer() {
        return getString(formPath("commands", "cant-find-player"));
    }
    public String getUnableToFindSafeLocation() {
        return getString(formPath("commands", "unable-to-find-safe-location"));
    }
    public String getWorldRestrictedSethome() {
        return getString(formPath("commands", "world-restricted-sethome"));
    }
    public String getWorldRestrictedHome() {
        return getString(formPath("commands", "world-restricted-home"));
    }
    //</editor-fold>
    //<editor-fold desc="Main">
    public String getMainHelpPerm() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "help", "help-perm"));
    }
    public String getMainHelpNoPerm() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "help", "help-noperm"));
    }
    public String getMainInfoOutput() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "info", "output"))
                .replace("%name%", zHomes.getInstance().getPluginMeta().getName())
                .replace("%version%", zHomes.getInstance().getPluginMeta().getVersion())
                .replace("%author%", String.join(", ", zHomes.getInstance().getPluginMeta().getAuthors()))
                .replace("%server.software%", Bukkit.getServer().getName())
                .replace("%server.version%", Bukkit.getServer().getVersion() + " - " + Bukkit.getServer().getBukkitVersion())
                .replace("%requpdate%", zHomes.updateUtils.needsUpdate ? getRequireUpdateYes() : getRequireUpdateNo())
                .replace("%language%", zHomes.getConfigYAML().getLanguageCode())
                .replace("%storage.type%", zHomes.getConfigYAML().getDatabaseType())
                .replace("%storage.users%", String.valueOf(DatabaseEditor.getTotalUsers()))
                .replace("%storage.homes%", String.valueOf(DatabaseEditor.getTotalHomes()))
                .replace("%use.placeholderapi%", HookRegistry.PAPI.exists() ? "<green>Yes" : "<red>No")
                .replace("%use.miniplaceholders%", HookRegistry.MINI_PLACEHOLDERS.exists() ? "<green>Yes" : "<red>No")
                .replace("%use.griefprevention%", me.yleoft.zHomes.hooks.HookRegistry.GRIEF_PREVENTION.exists() ? "<green>Yes" : "<red>No")
                .replace("%use.worldguard%", me.yleoft.zHomes.hooks.HookRegistry.WORLDGUARD.exists() ? "<green>Yes" : "<red>No")
                .replace("%use.vault%", me.yleoft.zHomes.hooks.HookRegistry.VAULT.exists() ? "<green>Yes" : "<red>No");
    }
    public String getRequireUpdateYes() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "info", "requpdate-yes"));
    }
    public String getRequireUpdateNo() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "info", "requpdate-no"));
    }
    public String getMainVersionOutput() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "version", "output"))
                .replace("%version%", zHomes.getInstance().getPluginMeta().getVersion());
    }
    public String getMainReloadUsage() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "reload", "usage"));
    }
    public String getMainReloadOutput(long time) {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "reload", "output"))
                .replace("%time%", String.valueOf(time));
    }
    public String getMainReloadCommandsOutput(long time) {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "reload", "commands", "output"))
                .replace("%time%", String.valueOf(time));
    }
    public String getMainReloadConfigOutput(long time) {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "reload", "config", "output"))
                .replace("%time%", String.valueOf(time));
    }
    public String getMainReloadLanguagesOutput(long time) {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "reload", "languages", "output"))
                .replace("%time%", String.valueOf(time));
    }
    public String getMainNearhomesUsage() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "nearhomes", "usage"));
    }
    public String getMainNearhomesOutput(double radius, HashMap<OfflinePlayer, List<String>> homes, @Nullable String playerName, @Nullable String homeName) {
        StringBuilder homesList = new StringBuilder();
        for (OfflinePlayer p : homes.keySet()) {
            if(playerName != null && p.getName() != null && !p.getName().equalsIgnoreCase(playerName)) continue;
            for(String home : homes.get(p)) {
                if(homeName != null && !home.startsWith(homeName)) continue;
                if(!homesList.toString().isEmpty()) homesList.append(", ");
                homesList.append(playerName == null ? getNearhomesHomeString(p, home, radius) : getNearhomesFilteredPlayerHomeString(p, home, radius));
            }
        }
        if(homesList.toString().isEmpty()) {
            return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "nearhomes", "output-not-found"))
                    .replace("%radius%", String.valueOf(radius));
        }
        return playerName == null ? getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "nearhomes", "output"))
                .replace("%radius%", String.valueOf(radius))
                .replace("%homes%", homesList.toString())
                : getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "nearhomes", "filtered-player", "output"))
                .replace("%radius%", String.valueOf(radius))
                .replace("%homes%", homesList.toString())
                .replace("%player%", playerName);
    }
    public String getMainNearhomesOutput(double radius, HashMap<OfflinePlayer, List<String>> homes) {
        return getMainNearhomesOutput(radius, homes, null, null);
    }
    public String getNearhomesHomeString(OfflinePlayer player, String home, double radius) {
        return getString(formPath("commands", "main", "nearhomes", "home-string"))
                .replace("%homecommand%", zHomes.getConfigYAML().getHomeCommand())
                .replace("%maincommand%", zHomes.getConfigYAML().getMainCommand())
                .replace("%radius%", String.valueOf(radius))
                .replace("%home%", home)
                .replace("%owner%", (player == null || player.getName() == null) ? "----" : player.getName());
    }
    public String getNearhomesFilteredPlayerHomeString(OfflinePlayer player, String home, double radius) {
        return getString(formPath("commands", "main", "nearhomes", "filtered-player", "home-string"))
                .replace("%homecommand%", zHomes.getConfigYAML().getHomeCommand())
                .replace("%maincommand%", zHomes.getConfigYAML().getMainCommand())
                .replace("%radius%", String.valueOf(radius))
                .replace("%home%", home)
                .replace("%player%", (player == null || player.getName() == null) ? "----" : player.getName());
    }
    public String getMainParseUsage() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "parse", "usage"));
    }
    public Component getMainParseOutput(@Nullable OfflinePlayer player, String parsed) {
        return TextFormatter.transform(player, getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "parse", "output"))
                .replace("%parsed%", parsed));
    }
    public Component getMainParseOutput(String parsed) {
        return getMainParseOutput(null, parsed);
    }
    public String getMainConverterUsage() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "converter", "usage"));
    }
    public String getMainConverterOutput() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "converter", "output"));
    }
    public String getMainConverterError() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "converter", "error"));
    }
    public String getMainExportOutput(String file) {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "export", "output"))
                .replace("%file%", file);
    }
    public String getMainExportError() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "export", "error"));
    }
    public String getMainImportUsage() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "import", "usage"));
    }
    public String getMainImportOutput(String file) {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "import", "output"))
                .replace("%file%", file);
    }
    public String getMainImportFileNotFound(String file) {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "import", "file-not-found"))
                .replace("%file%", file);
    }
    public String getMainImportError() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "import", "error"));
    }
    //</editor-fold>
    //<editor-fold desc="Sethome">
    public String getSethomeUsage() {
        return getString(zHomes.getConfigYAML().getSetHomeCommand(), formPath("commands", "sethome", "usage"));
    }
    public String getSethomeOutput(String home) {
        return getString(zHomes.getConfigYAML().getSetHomeCommand(), formPath("commands", "sethome", "output"))
                .replace("%home%", home);
    }
    public String getSethomeLimitReached(OfflinePlayer player) {
        return getString(zHomes.getConfigYAML().getSetHomeCommand(), formPath("commands", "sethome", "limit-reached"))
                .replace("%limit%", String.valueOf(HomesUtils.getLimit(player)));
    }
    //</editor-fold>
    //<editor-fold desc="Delhome">
    public String getDelhomeUsage() {
        return getString(zHomes.getConfigYAML().getDelHomeCommand(), formPath("commands", "delhome", "usage"));
    }
    public String getDelhomeOutput(String home) {
        return getString(zHomes.getConfigYAML().getDelHomeCommand(), formPath("commands", "delhome", "output"))
                .replace("%home%", home);
    }
    //</editor-fold>
    //<editor-fold desc="Home">
    public String getHomeUsage() {
        return getString(zHomes.getConfigYAML().getHomeCommand(), formPath("commands", "home", "usage"));
    }
    public String getHomeOutput(String home) {
        return getString(zHomes.getConfigYAML().getHomeCommand(), formPath("commands", "home", "output"))
                .replace("%home%", home);
    }
    public String getHomeCantDimensionalTeleport() {
        return getString(zHomes.getConfigYAML().getHomeCommand(), formPath("commands", "home", "cant-dimensional-teleport"));
    }
    public String getHomeRenameUsage() {
        return getString(zHomes.getConfigYAML().getHomeCommand(), formPath("commands", "home", "rename", "usage"));
    }
    public String getHomeRenameOutput(String home, String newName) {
        return getString(zHomes.getConfigYAML().getHomeCommand(), formPath("commands", "home", "rename", "output"))
                .replace("%home%", home)
                .replace("%newname%", newName);
    }
    public String getHomeRenameSameName() {
        return getString(zHomes.getConfigYAML().getHomeCommand(), formPath("commands", "home", "rename", "same-name"));
    }
    //</editor-fold>
    //<editor-fold desc="Homes">
    public String getHomesOutputSelf(Player player) {
        return getString(zHomes.getConfigYAML().getHomesCommand(), formPath("commands", "homes", "output"))
                .replace("%homes%", HomesUtils.homes(player))
                .replace("%amount%", String.valueOf(HomesUtils.numberOfHomes(player)));
    }
    public String getHomesOutputOthers(OfflinePlayer player) {
        return getString(zHomes.getConfigYAML().getHomesCommand(), formPath("commands", "homes", "others", "output"))
                .replace("%homes%", HomesUtils.homes(player, true))
                .replace("%amount%", String.valueOf(HomesUtils.numberOfHomes(player)))
                .replace("%player%", (player == null || player.getName() == null) ? "----" : player.getName());
    }
    public String getHomesHomeString(String home) {
        return getString(formPath("commands", "homes", "home-string"))
                .replace("%home%", home)
                .replace("%homecommand%", zHomes.getConfigYAML().getHomeCommand());
    }
    public String getHomesHomeStringOthers(String home, String owner) {
        return getString(formPath("commands", "homes", "others", "home-string"))
                .replace("%home%", home)
                .replace("%homecommand%", zHomes.getConfigYAML().getHomeCommand())
                .replace("%player%", owner);
    }
    public String getHomesInvalidPage() {
        return getString(zHomes.getConfigYAML().getHomesCommand(), formPath("commands", "homes", "invalid-page"));
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold desc="LanguageBuilder Methods">
    public static File getLanguageFolder() {
        return languageFolder;
    }
    public String getLanguageFileName() {
        return getFile().getName().replace(".yml", "");
    }
    //</editor-fold>

    public static void sendMessage(@Nullable CommandSender sender, String message) {
        if(sender != null) sender.sendMessage(TextFormatter.transform(sender instanceof Player ? (Player)sender : null, message));
    }

    public static void sendActionBar(CommandSender sender, String message) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(TextFormatter.transform(message));
            return;
        }
        player.sendActionBar(TextFormatter.transform(player, message));
    }

    //<editor-fold desc="Overrides">
    public String getString(String command, String path) {
        return getString(path)
                .replace("%command%", command);
    }

    // Helper to reduce repetition
    private String t(String key, String englishDefault) {
        return translations().getOrDefault(key, englishDefault);
    }
    //</editor-fold>

}
