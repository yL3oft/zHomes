package me.yleoft.zHomes.configuration;

import me.yleoft.zAPI.configuration.YAMLBuilder;
import me.yleoft.zHomes.zHomes;

import java.io.File;
import java.util.List;

public class ConfigYAML extends YAMLBuilder {

    public ConfigYAML() {
        super(new File(zHomes.getInstance().getDataFolder(), "config.yml"));
        migrateLegacyColors(true);
        buildConfig();
    }

    private void buildConfig() {
        header("""
                ####################################################################################################
                # +----------------------------------------------------------------------------------------------+ #
                # |                                                                                              | #
                # |                      ░██     ░██                                                             | #
                # |                      ░██     ░██                                                             | #
                # |           ░█████████ ░██     ░██  ░███████  ░█████████████   ░███████   ░███████             | #
                # |                ░███  ░██████████ ░██    ░██ ░██   ░██   ░██ ░██    ░██ ░██                   | #
                # |              ░███    ░██     ░██ ░██    ░██ ░██   ░██   ░██ ░█████████  ░███████             | #
                # |            ░███      ░██     ░██ ░██    ░██ ░██   ░██   ░██ ░██               ░██            | #
                # |           ░█████████ ░██     ░██  ░███████  ░██   ░██   ░██  ░███████   ░███████             | #
                # |                                                                                              | #
                # |                                   Plugin links & support                                     | #
                # |                                                                                              | #
                # |   • Wiki:        https://docs.yleoft.me/zhomes                                               | #
                # |   • Discord:     https://discord.gg/yCdhVDgn4K                                               | #
                # |   • Bug reports: https://github.com/yL3oft/zHomes/issues                                     | #
                # |                                                                                              | #
                # |   zHomes (c) yL3oft — released under the MIT License.                                        | #
                # |                                                                                              | #
                # +----------------------------------------------------------------------------------------------+ #
                ####################################################################################################
                """);

        addDefault("prefix", "<dark_gray>[<red>zHomes<dark_gray>]");

        // -------------------------
        // Database
        // -------------------------
        commentSection("database", "Edit your database settings below");
        comment(false, """
                Here you can define how to store the plugin data.
                OPTIONS:
                - H2 (Preferred over SQLite)
                - SQLite
                - MariaDB (Preferred over MySQL)
                - MySQL
                DEFAULT: H2
                """);
        addDefault(formPath("database", "type"), "H2");
        addDefault(formPath("database", "host"), "localhost");
        addDefault(formPath("database", "port"), 3306);
        addDefault(formPath("database", "database"), "db");
        addDefault(formPath("database", "username"), "root");
        addDefault(formPath("database", "password"), "pass");
        addDefault(formPath("database", "options", "useSSL"), false);
        addDefault(formPath("database", "options", "allowPublicKeyRetrieval"), false);
        comment(false, "# -------------------- #",
                "# WARNING: DO NOT CHANGE ANYTHING BELOW IF YOU DON'T KNOW WHAT YOU'RE DOING");
        addDefault(formPath("database", "pool-size"), 10);
        addDefault(formPath("database", "table-prefix"), "zhomes");

        // -------------------------
        // General
        // -------------------------
        comment(false, "Here you can define the language of the plugin, all languages can be found, edited and created on languages' directory.",
                "CURRENTLY AVAILABLE LANGUAGES: [de, en, es, fr, it, nl, pl, pt-br, ru, zhcn, <custom>]");
        addDefault(formPath("general", "language"), "en");
        comment(false, "Enable or disable automatic updates for the plugin.");
        addDefault(formPath("general", "auto-update"), false);
        comment(false, "Toggle whether the plugin should announce available updates in the console and to players with the appropriate permission.");
        addDefault(formPath("general", "announce-update"), true);
        comment(false, "Enable or disable metrics collection to help improve the plugin.",
                "All data collected is anonymous and used solely for statistical purposes.",
                "!WARNING: Requires server restart to take effect!");
        addDefault(formPath("general", "metrics"), true);
        comment(false, "Enable or disable debug mode for more detailed logging output.");
        addDefault(formPath("general", "debug-mode"), false);

        // -------------------------
        // Teleport options
        // -------------------------
        commentSection("teleport-options", "Settings related to teleportation behavior");
        comment(false, "Enable or disable safe teleportation to prevent players from being teleported into dangerous locations.");
        addDefault(formPath("teleport-options", "enable-safe-teleport"), true);
        comment(false, "Enable or disable dimensional teleportation, allowing players to teleport between different worlds or dimensions.");
        addDefault(formPath("teleport-options", "dimensional-teleportation"), true);
        comment(false, "Play a sound effect when a player is teleported.");
        addDefault(formPath("teleport-options", "play-sound"), true);

        comment(false, "Enable or disable the restriction of teleportation to certain worlds.");
        addDefault(formPath("teleport-options", "restricted-worlds", "enable"), false);
        comment(false, """
                Define the mode for restricted worlds.
                OPTIONS:
                - blacklist: Players cannot teleport to worlds listed below.
                - whitelist: Players can only teleport to worlds listed below.
                """);
        addDefault(formPath("teleport-options", "restricted-worlds", "mode"), "blacklist");
        comment(false, "List of worlds affected by the restricted worlds setting.");
        addDefault(formPath("teleport-options", "restricted-worlds", "worlds"), List.of("world_nether", "world_the_end"));

        comment(false, "Enable or disable teleportation warmup period.");
        addDefault(formPath("teleport-options", "warmup", "enable"), true);
        comment(false, "Define the warmup time in seconds before teleportation occurs.");
        addDefault(formPath("teleport-options", "warmup", "time"), 5);
        comment(false, "Cancel the teleportation if the player moves during the warmup period.");
        addDefault(formPath("teleport-options", "warmup", "cancel-on-move"), true);
        comment(false, "Show warmup countdown on the action bar.");
        addDefault(formPath("teleport-options", "warmup", "show-on-actionbar"), true);

        // -------------------------
        // Limits
        // -------------------------
        comment(false, "Enable or disable home limits for players.");
        addDefault(formPath("limits", "enabled"), false);
        comment(false, "Default number of homes a player can set.");
        addDefault(formPath("limits", "default"), 10);
        comment("Limit examples based on player groups.");
        setValue(formPath("limits", "15"), List.of("group.vip", "group.mvp"));
        setValue(formPath("limits", "999"), List.of("group.admin"));

        // -------------------------
        // Commands
        // -------------------------
        commentSection("commands", "!WARNING: Mostly everything below needs a restart to apply.");

        addDefault(formPath("commands", "main", "command"), "zhomes");
        addDefault(formPath("commands", "main", "description"), "The main command for the plugin");
        addDefault(formPath("commands", "main", "permission"), "zhomes.command.main");
        addDefault(formPath("commands", "main", "aliases"), List.of("zh"));
        addDefault(formPath("commands", "main", "help", "permission"), "zhomes.command.main.help");
        addDefault(formPath("commands", "main", "info", "permission"), "zhomes.command.main.info");
        addDefault(formPath("commands", "main", "version", "permission"), "zhomes.command.main.version");
        addDefault(formPath("commands", "main", "version", "update", "permission"), "zhomes.command.main.version.update");
        addDefault(formPath("commands", "main", "reload", "permission"), "zhomes.command.main.reload");
        addDefault(formPath("commands", "main", "nearhomes", "permission"), "zhomes.command.main.nearhomes");
        addDefault(formPath("commands", "main", "nearhomes", "limit"), 500.0);
        addDefault(formPath("commands", "main", "parse", "permission"), "zhomes.command.main.parse");
        addDefault(formPath("commands", "main", "converter", "permission"), "zhomes.command.main.converter");
        addDefault(formPath("commands", "main", "importexport", "permission"), "zhomes.command.main.importexport");

        addDefault(formPath("commands", "sethome", "command"), "sethome");
        addDefault(formPath("commands", "sethome", "description"), "Use to set a home");
        addDefault(formPath("commands", "sethome", "permission"), "zhomes.command.sethome");
        addDefault(formPath("commands", "sethome", "aliases"), List.of("seth"));
        addDefault(formPath("commands", "sethome", "cooldown"), 0D);
        comment(false, "command-cost requires Vault to work.");
        addDefault(formPath("commands", "sethome", "command-cost"), 0);
        addDefault(formPath("commands", "sethome", "others", "permission"), "zhomes.command.sethome.others");

        addDefault(formPath("commands", "delhome", "command"), "delhome");
        addDefault(formPath("commands", "delhome", "description"), "Use to delete a home");
        addDefault(formPath("commands", "delhome", "permission"), "zhomes.command.delhome");
        addDefault(formPath("commands", "delhome", "aliases"), List.of("deletehome", "delh"));
        addDefault(formPath("commands", "delhome", "cooldown"), 0D);
        comment(false, "command-cost requires Vault to work.");
        addDefault(formPath("commands", "delhome", "command-cost"), 0);
        addDefault(formPath("commands", "delhome", "others", "permission"), "zhomes.command.delhome.others");

        addDefault(formPath("commands", "homes", "command"), "homes");
        addDefault(formPath("commands", "homes", "description"), "List your homes");
        addDefault(formPath("commands", "homes", "permission"), "zhomes.command.homes");
        addDefault(formPath("commands", "homes", "aliases"), List.of("myhomes"));
        addDefault(formPath("commands", "homes", "cooldown"), 0D);
        comment(false, "command-cost requires Vault to work.");
        addDefault(formPath("commands", "homes", "command-cost"), 0);
        comment(false, "Define how the homes will be displayed to the player.",
                "OPTIONS:",
                "- list: Displays homes in a simple list format.",
                "- menu: Opens a graphical menu to select homes."
        );
        addDefault(formPath("commands", "homes", "type"), "menu");
        addDefault(formPath("commands", "homes", "others", "permission"), "zhomes.command.homes.others");

        addDefault(formPath("commands", "home", "command"), "home");
        addDefault(formPath("commands", "home", "description"), "Teleport to a home");
        addDefault(formPath("commands", "home", "permission"), "zhomes.command.home");
        addDefault(formPath("commands", "home", "aliases"), List.of());
        addDefault(formPath("commands", "home", "cooldown"), 3D);
        comment(false, "command-cost requires Vault to work.");
        addDefault(formPath("commands", "home", "command-cost"), 0);
        addDefault(formPath("commands", "home", "others", "permission"), "zhomes.command.home.others");
        addDefault(formPath("commands", "home", "rename", "permission"), "zhomes.command.home.rename");
        comment(false, "command-cost requires Vault to work.");
        addDefault(formPath("commands", "home", "rename", "command-cost"), 0);

        // -------------------------
        // Permissions
        // -------------------------
        commentSection("permissions", "Permission nodes used by the plugin");

        comment(false, "Permission to bypass home limits");
        addDefault(formPath("permissions", "bypass", "limit"), "zhomes.bypass.limit");
        comment(false, "Permission to bypass dimensional teleportation restrictions");
        addDefault(formPath("permissions", "bypass", "dimensional-teleportation"), "zhomes.bypass.dimensionalteleportation");
        comment(false, "Permission to bypass safe teleportation checks");
        addDefault(formPath("permissions", "bypass", "safe-teleportation"), "zhomes.bypass.safeteleport");
        comment(false, "Permission to bypass restricted worlds checks");
        addDefault(formPath("permissions", "bypass", "restricted-worlds"), "zhomes.bypass.restrictedworlds");
        comment(false, "Permission to bypass teleportation warmup");
        addDefault(formPath("permissions", "bypass", "warmup"), "zhomes.bypass.warmup");
        comment(false, "Permission to bypass command costs");
        addDefault(formPath("permissions", "bypass", "command-cost"), "%command_permission%.bypass.command-cost");
        addDefault(formPath("permissions", "bypass", "command-cooldown"), "%command_permission%.bypass.command-cooldown");

        build();
    }

    public String getPrefix() {
        return getString("prefix");
    }

    //<editor-fold desc="Database Settings">
    public String getDatabaseType() {
        return getString(formPath("database", "type"));
    }
    public String getDatabaseHost() {
        return getString(formPath("database", "host"));
    }
    public int getDatabasePort() {
        return getInt(formPath("database", "port"));
    }
    public String getDatabaseName() {
        return getString(formPath("database", "database"));
    }
    public String getDatabaseUsername() {
        return getString(formPath("database", "username"));
    }
    public String getDatabasePassword() {
        return getString(formPath("database", "password"));
    }
    public boolean isDatabaseUseSSL() {
        return getBoolean(formPath("database", "options", "useSSL"));
    }
    public boolean isDatabaseAllowPublicKeyRetrieval() {
        return getBoolean(formPath("database", "options", "allowPublicKeyRetrieval"));
    }
    public int getDatabasePoolSize() {
        return getInt(formPath("database", "pool-size"));
    }
    public String getDatabaseTablePrefix() {
        return getString(formPath("database", "table-prefix"));
    }
    public String getDatabaseTable() {
        return getDatabaseTablePrefix()+"_homes";
    }
    //</editor-fold>

    //<editor-fold desc="General Settings">
    public String getLanguageCode() {
        return getString(formPath("general", "language"));
    }
    public boolean isAutoUpdateEnabled() {
        return getBoolean(formPath("general", "auto-update"));
    }
    public boolean isAnnounceUpdateEnabled() {
        return getBoolean(formPath("general", "announce-update"));
    }
    public boolean isMetricsEnabled() {
        return getBoolean(formPath("general", "metrics"));
    }
    public boolean isDebugModeEnabled() {
        return getBoolean(formPath("general", "debug-mode"));
    }
    //</editor-fold>

    //<editor-fold desc="Teleport Options">
    public boolean isSafeTeleportEnabled() {
        return getBoolean(formPath("teleport-options", "enable-safe-teleport"));
    }
    public boolean isDimensionalTeleportationEnabled() {
        return getBoolean(formPath("teleport-options", "dimensional-teleportation"));
    }
    public boolean isTeleportSoundEnabled() {
        return getBoolean(formPath("teleport-options", "play-sound"));
    }
    public boolean isRestrictedWorldsEnabled() {
        return getBoolean(formPath("teleport-options", "restricted-worlds", "enable"));
    }
    public String getRestrictedWorldsMode() {
        return getString(formPath("teleport-options", "restricted-worlds", "mode"));
    }
    public List<String> getRestrictedWorldsList() {
        return getStringList(formPath("teleport-options", "restricted-worlds", "worlds"));
    }
    public boolean isWarmupEnabled() {
        return getBoolean(formPath("teleport-options", "warmup", "enable"));
    }
    public int getWarmupTime() {
        return getInt(formPath("teleport-options", "warmup", "time"));
    }
    public boolean isWarmupCancelOnMove() {
        return getBoolean(formPath("teleport-options", "warmup", "cancel-on-move"));
    }
    public boolean isWarmupShowOnActionBar() {
        return getBoolean(formPath("teleport-options", "warmup", "show-on-actionbar"));
    }
    //</editor-fold>

    //<editor-fold desc="Limits Settings">
    public boolean isLimitsEnabled() {
        return getBoolean(formPath("limits", "enabled"));
    }
    public int getDefaultHomeLimit() {
        return getInt(formPath("limits", "default"));
    }
    //</editor-fold>

    //<editor-fold desc="Commands Settings">
    //<editor-fold desc="Main Command">
    public String getMainCommand() {
        return getString(formPath("commands", "main", "command"));
    }
    public String getMainCommandDescription() {
        return getString(formPath("commands", "main", "description"));
    }
    public String getMainCommandPermission() {
        return getString(formPath("commands", "main", "permission"));
    }
    public List<String> getMainCommandAliases() {
        return getStringList(formPath("commands", "main", "aliases"));
    }
    public String getMainCommandHelpPermission() {
        return getString(formPath("commands", "main", "help", "permission"));
    }
    public String getMainCommandInfoPermission() {
        return getString(formPath("commands", "main", "info", "permission"));
    }
    public String getMainCommandVersionPermission() {
        return getString(formPath("commands", "main", "version", "permission"));
    }
    public String getMainCommandVersionUpdatePermission() {
        return getString(formPath("commands", "main", "version", "update", "permission"));
    }
    public String getMainCommandReloadPermission() {
        return getString(formPath("commands", "main", "reload", "permission"));
    }
    public String getMainCommandNearHomesPermission() {
        return getString(formPath("commands", "main", "nearhomes", "permission"));
    }
    public double getMainCommandNearHomesLimit() {
        return getDouble(formPath("commands", "main", "nearhomes", "limit"));
    }
    public String getMainCommandParsePermission() {
        return getString(formPath("commands", "main", "parse", "permission"));
    }
    public String getMainCommandConverterPermission() {
        return getString(formPath("commands", "main", "converter", "permission"));
    }
    public String getMainCommandImportExportPermission() {
        return getString(formPath("commands", "main", "importexport", "permission"));
    }
    //</editor-fold>
    //<editor-fold desc="SetHome Command">
    public String getSetHomeCommand() {
        return getString(formPath("commands", "sethome", "command"));
    }
    public String getSetHomeCommandDescription() {
        return getString(formPath("commands", "sethome", "description"));
    }
    public String getSetHomeCommandPermission() {
        return getString(formPath("commands", "sethome", "permission"));
    }
    public List<String> getSetHomeCommandAliases() {
        return getStringList(formPath("commands", "sethome", "aliases"));
    }
    public double getSetHomeCommandCooldown() {
        return getDouble(formPath("commands", "sethome", "cooldown"));
    }
    public double getSetHomeCommandCost() {
        return getDouble(formPath("commands", "sethome", "command-cost"));
    }
    public String getSetHomeOthersPermission() {
        return getString(formPath("commands", "sethome", "others", "permission"));
    }
    //</editor-fold>
    //<editor-fold desc="DelHome Command">
    public String getDelHomeCommand() {
        return getString(formPath("commands", "delhome", "command"));
    }
    public String getDelHomeCommandDescription() {
        return getString(formPath("commands", "delhome", "description"));
    }
    public String getDelHomeCommandPermission() {
        return getString(formPath("commands", "delhome", "permission"));
    }
    public List<String> getDelHomeCommandAliases() {
        return getStringList(formPath("commands", "delhome", "aliases"));
    }
    public double getDelHomeCommandCooldown() {
        return getDouble(formPath("commands", "delhome", "cooldown"));
    }
    public double getDelHomeCommandCost() {
        return getDouble(formPath("commands", "delhome", "command-cost"));
    }
    public String getDelHomeOthersPermission() {
        return getString(formPath("commands", "delhome", "others", "permission"));
    }
    //</editor-fold>
    //<editor-fold desc="Homes Command">
    public String getHomesCommand() {
        return getString(formPath("commands", "homes", "command"));
    }
    public String getHomesCommandDescription() {
        return getString(formPath("commands", "homes", "description"));
    }
    public String getHomesCommandPermission() {
        return getString(formPath("commands", "homes", "permission"));
    }
    public List<String> getHomesCommandAliases() {
        return getStringList(formPath("commands", "homes", "aliases"));
    }
    public double getHomesCommandCooldown() {
        return getDouble(formPath("commands", "homes", "cooldown"));
    }
    public double getHomesCommandCost() {
        return getDouble(formPath("commands", "homes", "command-cost"));
    }
    public String getHomesDisplayType() {
        return getString(formPath("commands", "homes", "type"));
    }
    public String getHomesOthersPermission() {
        return getString(formPath("commands", "homes", "others", "permission"));
    }
    //</editor-fold>
    //<editor-fold desc="Home Command">
    public String getHomeCommand() {
        return getString(formPath("commands", "home", "command"));
    }
    public String getHomeCommandDescription() {
        return getString(formPath("commands", "home", "description"));
    }
    public String getHomeCommandPermission() {
        return getString(formPath("commands", "home", "permission"));
    }
    public List<String> getHomeCommandAliases() {
        return getStringList(formPath("commands", "home", "aliases"));
    }
    public double getHomeCommandCooldown() {
        return getDouble(formPath("commands", "home", "cooldown"));
    }
    public double getHomeCommandCost() {
        return getDouble(formPath("commands", "home", "command-cost"));
    }
    public String getHomeOthersPermission() {
        return getString(formPath("commands", "home", "others", "permission"));
    }
    public String getHomeRenamePermission() {
        return getString(formPath("commands", "home", "rename", "permission"));
    }
    public double getHomeRenameCommandCost() {
        return getDouble(formPath("commands", "home", "rename", "command-cost"));
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold desc="Permissions Settings">
    public String getBypassLimitPermission() {
        return getString(formPath("permissions", "bypass", "limit"));
    }
    public String getBypassDimensionalTeleportationPermission() {
        return getString(formPath("permissions", "bypass", "dimensional-teleportation"));
    }
    public String getBypassSafeTeleportationPermission() {
        return getString(formPath("permissions", "bypass", "safe-teleportation"));
    }
    public String getBypassRestrictedWorldsPermission() {
        return getString(formPath("permissions", "bypass", "restricted-worlds"));
    }
    public String getBypassWarmupPermission() {
        return getString(formPath("permissions", "bypass", "warmup"));
    }
    public String getBypassCommandCostPermission(String commandPermission) {
        return getString(formPath("permissions", "bypass", "command-cost"))
                .replace("%command_permission%", commandPermission);
    }
    public String getBypassCommandCooldownPermission(String commandPermission) {
        return getString(formPath("permissions", "bypass", "command-cooldown"))
                .replace("%command_permission%", commandPermission);
    }
    //</editor-fold>

}