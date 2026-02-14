package me.yleoft.zHomes;

import de.tr7zw.changeme.nbtapi.NBT;
import me.yleoft.zAPI.configuration.Path;
import me.yleoft.zAPI.hooks.HookMiniPlaceholders;
import me.yleoft.zAPI.hooks.HookPlaceholderAPI;
import me.yleoft.zAPI.libs.bstats.bukkit.Metrics;
import me.yleoft.zAPI.libs.bstats.charts.SimplePie;
import me.yleoft.zAPI.logging.Logger;
import me.yleoft.zAPI.player.PlayerHandler;
import me.yleoft.zAPI.utility.ExternalDependencyManager;
import me.yleoft.zAPI.utility.PluginYAML;
import me.yleoft.zAPI.zAPI;
import me.yleoft.zHomes.commands.*;
import me.yleoft.zHomes.configuration.*;
import me.yleoft.zHomes.configuration.languages.LanguageBuilder;
import me.yleoft.zHomes.configuration.menus.menuhomesYAML;
import me.yleoft.zHomes.hooks.HookRegistry;
import me.yleoft.zHomes.listeners.GriefPreventionListeners;
import me.yleoft.zHomes.listeners.PlayerListeners;
import me.yleoft.zHomes.listeners.WorldGuardListeners;
import me.yleoft.zHomes.storage.DatabaseConnection;
import me.yleoft.zHomes.storage.DatabaseEditor;
import me.yleoft.zHomes.utility.LanguageUtils;
import me.yleoft.zHomes.utility.UpdateUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URI;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import static me.yleoft.zAPI.utility.TextFormatter.transform;

public final class zHomes extends JavaPlugin {

    public static ConfigYAML config;
    public static menuhomesYAML menuHomes;

    ExternalDependencyManager mgr = new ExternalDependencyManager();

    private static zHomes instance;
    public static Logger logger;
    public static UpdateUtils updateUtils;
    public static zHomes getInstance() {
        return instance;
    }

    public Component pluginPrefix;
    public static String bc = "<green>";
    public static String wc = "<gold>";

    @Override
    public void onLoad() {
        zAPI.setPlugin(this);
        instance = this;
        logger = new Logger(pluginPrefix);
        zAPI.setPluginLogger(logger);
        HookRegistry.registerHooks();
        zAPI.preload(this);
    }

    @Override
    public void onEnable() {
        zAPI.init(this, NBT.preloadApi());
        LanguageUtils.loadInitiial();
        config = new ConfigYAML();
        pluginPrefix = transform(config.getString("prefix"));
        //<editor-fold desc="Logger and Update Checker">
        logger.setPrefix(pluginPrefix);
        boolean debugMode = config.isDebugModeEnabled();
        logger.setDebugMode(debugMode);
        zAPI.getLogger().setDebugMode(debugMode);
        logger.info("""
                %bc| ------------------------------------------------------- |
                %bc|%tc               _   _                                     %bc|
                %bc|%tc           ___| | | | ___  _ __ ___   ___  ___           %bc|
                %bc|%tc          |_  / |_| |/ _ \\| '_ ` _ \\ / _ \\/ __|          %bc|
                %bc|%tc           / /|  _  | (_) | | | | | |  __/\\__ \\          %bc|
                %bc|%tc          /___|_| |_|\\___/|_| |_| |_|\\___||___/          %bc|
                %bc|                                                         |
                %bc|                     %wcMade by yLeoft                      %bc|
                %bc|                     %wcVersion: %v             %bc|
                """
                .replace("%v", getPluginMeta().getVersion().contains("-SNAPSHOT") ? getPluginMeta().getVersion() : getPluginMeta().getVersion()+"         ")
                .replace("%bc", bc)
                .replace("%tc", "<red>")
                .replace("%wc", wc)
        );
        updateUtils = new UpdateUtils(this);
        logger.info("%bc| ------------------------------------------------------- |".replace("%bc", bc));
        //</editor-fold>
        //<editor-fold desc="Files">
        logger.info("<white>Loading plugin files...");
        logger.info("<yellow>> Checking language files...");
        LanguageUtils.loadLanguages();
        config = new ConfigYAML();
        logger.info("<yellow>> Checking menu files...");
        menuHomes = new menuhomesYAML();
        logger.info("<green>All files ready.");
        //</editor-fold>
        //<editor-fold desc="Libs">
        logger.info("<white>Downloading external libraries...");
        ExternalDependencyManager.STATIC_DIRECTORY = new File(getDataFolder(), "libs");

        mgr = new ExternalDependencyManager();
        mgr.registerDependency(
                "mysql",
                URI.create("https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.23/mysql-connector-java-8.0.23.jar")
        );
        mgr.registerDependency(
                "mariadb",
                URI.create("https://repo1.maven.org/maven2/org/mariadb/jdbc/mariadb-java-client/3.5.3/mariadb-java-client-3.5.3.jar")
        );
        mgr.registerDependency(
                "h2",
                URI.create("https://repo1.maven.org/maven2/com/h2database/h2/2.3.232/h2-2.3.232.jar")
        );
        mgr.registerDependency(
                "sqlite",
                URI.create("https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.51.1.0/sqlite-jdbc-3.51.1.0.jar")
        );

        try {
            DatabaseConnection.mysqlDriverLoader = mgr.loadIsolated("mysql", ExternalDependencyManager.class.getClassLoader());
            Class<?> driver = Class.forName("com.mysql.cj.jdbc.Driver", true, DatabaseConnection.mysqlDriverLoader);
            DatabaseConnection.mysqldriverClass = driver;
            logger.info("<yellow>> Loaded MySQL Driver.");
            logger.debug("<yellow>" + driver.getName() + " loaded by " + driver.getClassLoader());
        } catch (Exception ignored) {}

        try {
            DatabaseConnection.mariadbDriverLoader = mgr.loadIsolated("mariadb", ExternalDependencyManager.class.getClassLoader());
            Class<?> driver = Class.forName("org.mariadb.jdbc.Driver", true, DatabaseConnection.mariadbDriverLoader);
            DatabaseConnection.mariadbdriverClass = driver;
            logger.info("<yellow>> Loaded MariaDB Driver.");
            logger.debug("<yellow>" + driver.getName() + " loaded by " + driver.getClassLoader());
        } catch (Exception ignored) {}

        try {
            DatabaseConnection.h2DriverLoader = mgr.loadIsolated("h2", ExternalDependencyManager.class.getClassLoader());
            Class<?> driver = Class.forName("org.h2.Driver", true, DatabaseConnection.h2DriverLoader);
            DatabaseConnection.h2driverClass = driver;
            logger.info("<yellow>> Loaded H2 Driver.");
            logger.debug("<yellow>" + driver.getName() + " loaded by " + driver.getClassLoader());
        } catch (Exception ignored) {}

        try {
            DatabaseConnection.sqliteDriverLoader = mgr.loadIsolated("sqlite", ExternalDependencyManager.class.getClassLoader());
            Class<?> driver = Class.forName("org.sqlite.JDBC", true, DatabaseConnection.sqliteDriverLoader);
            DatabaseConnection.sqlitedriverClass = driver;
            logger.info("<yellow>> Loaded SQLite Driver.");
            logger.debug("<yellow>" + driver.getName() + " loaded by " + driver.getClassLoader());
        } catch (Exception ignored) {}
        logger.info("<green>All external libraries have been loaded!");
        //</editor-fold>
        //<editor-fold desc="Database">
        logger.info("<white>Initializing database connection...");
        DatabaseConnection.connect();
        DatabaseEditor.createTable(DatabaseEditor.databaseTable(), "(UUID VARCHAR(36),NAME VARCHAR(45),HOME VARCHAR(100),LOCATION VARCHAR(255),PRIMARY KEY (UUID, HOME))");
        DatabaseEditor.addNameColumn();
        PlayerHandler.setOfflineUUIDResolver(DatabaseEditor::getUUIDByName);
        logger.info("<green>Database connection has been established!");
        //</editor-fold>
        //<editor-fold desc="Hooks">
        logger.info("<white>Enabling hooks...");
        HookPlaceholderAPI.message = "<yellow>PlaceholderAPI hooked successfully!";
        HookMiniPlaceholders.message = "<yellow>MiniPlaceholders hooked successfully!";
        me.yleoft.zAPI.hooks.HookRegistry.load();
        zAPI.setPlaceholdersHandler(HookRegistry.PLUGIN_PAPI);
        logger.info("<green>All hooks have been enabled!");
        //</editor-fold>
        //<editor-fold desc="Commands">
        logger.info("<yellow>Loading commands & events...");
        loadCommands();
        registerEvents();
        logger.info("<green>All commands have been loaded!");
        //</editor-fold>
        //<editor-fold desc="Metrics">
        if(getConfigYAML().isMetricsEnabled()) {
            logger.info("<yellow>Enabling bStats metrics...");
            try {
                int bStatsID = 25021;
                new zHomesMetrics(this, bStatsID);
            } catch (Exception exception) {
                logger.warn("Failed to create a bStats instance.", exception);
            }
            logger.info("<green>bStats metrics enabled! Thank you for helping us improve zHomes.");
        }
        //</editor-fold>
    }

    @Override
    public void onDisable() {
        try {
            DatabaseConnection.disconnect();
        } catch (Exception ignored) {}

        closeLoader(DatabaseConnection.mysqlDriverLoader);
        closeLoader(DatabaseConnection.mariadbDriverLoader);
        closeLoader(DatabaseConnection.h2DriverLoader);
        closeLoader(DatabaseConnection.sqliteDriverLoader);

        zAPI.disable();
        instance = null;
    }

    private void closeLoader(ClassLoader cl) {
        if (cl instanceof URLClassLoader ucl) {
            try {
                ucl.close();
            } catch (Exception ignored) {}
        }
    }

    public void loadCommands() {
        PluginYAML.unregisterCommands();
        PluginYAML.registerCommand(new MainCommand());
        PluginYAML.registerCommand(new SethomeCommand());
        PluginYAML.registerCommand(new DelhomeCommand());
        PluginYAML.registerCommand(new HomesCommand());
        PluginYAML.registerCommand(new HomeCommand());
        //<editor-fold desc="Permissions">
        try {
            PluginYAML.unregisterPermissions();
            Map<String, Boolean> helpANDmainChildren = new HashMap<>();
            helpANDmainChildren.put(getConfigYAML().getMainCommandPermission(), true);
            helpANDmainChildren.put(getConfigYAML().getMainCommandHelpPermission(), true);
            PluginYAML.registerPermission(getConfigYAML().getMainCommandPermission(), "Permission to use the '/" + getConfigYAML().getMainCommand() + "' command", PermissionDefault.TRUE);
            PluginYAML.registerPermission(getConfigYAML().getMainCommandHelpPermission(), "Permission to use the '/" + getConfigYAML().getMainCommand() + " (help|?)' command (With perm)", PermissionDefault.OP);
            PluginYAML.registerPermission(getConfigYAML().getMainCommandVersionPermission(), "Permission to use the '/" + getConfigYAML().getMainCommand() + " (version|ver)' command", PermissionDefault.TRUE);
            PluginYAML.registerPermission(getConfigYAML().getMainCommandVersionAnnouncePermission(), "Should the plugin announce a version update for the player?", PermissionDefault.OP);
            PluginYAML.registerPermission(getConfigYAML().getMainCommandInfoPermission(), "Permission to use the '/" + getConfigYAML().getMainCommand() + " info' command", PermissionDefault.OP);
            PluginYAML.registerPermission(getConfigYAML().getMainCommandReloadPermission(), "Permission to use the '/" + getConfigYAML().getMainCommand() + " (reload|rl)' command", PermissionDefault.OP, helpANDmainChildren);
            PluginYAML.registerPermission(getConfigYAML().getMainCommandNearHomesPermission(), "Permission to use the '/" + getConfigYAML().getMainCommand() + " nearhomes' command", PermissionDefault.OP);
            PluginYAML.registerPermission(getConfigYAML().getMainCommandParsePermission(), "Permission to use the '/" + getConfigYAML().getMainCommand() + " parse' command", PermissionDefault.OP);
            PluginYAML.registerPermission(getConfigYAML().getMainCommandPurgePermission(), "Permission to use the '/" + getConfigYAML().getMainCommand() + " purge' command", PermissionDefault.OP);
            PluginYAML.registerPermission(getConfigYAML().getMainCommandConverterPermission(), "Permission to use the '/" + getConfigYAML().getMainCommand() + " converter' command", PermissionDefault.OP);
            PluginYAML.registerPermission(getConfigYAML().getMainCommandImportExportPermission(), "Permission to use the '/" + getConfigYAML().getMainCommand() + " (export|import)' command", PermissionDefault.OP);
            PluginYAML.registerPermission(getConfigYAML().getSetHomeCommandPermission(), "Permission to use the '/" + getConfigYAML().getSetHomeCommand() + "' command", PermissionDefault.TRUE);
            PluginYAML.registerPermission(getConfigYAML().getDelHomeCommandPermission(), "Permission to use the '/" + getConfigYAML().getDelHomeCommand() + "' command", PermissionDefault.TRUE);
            PluginYAML.registerPermission(getConfigYAML().getDelHomeOthersPermission(), "Permission to use the '/" + getConfigYAML().getDelHomeCommand() + " (Player:Home)' command", PermissionDefault.OP);
            PluginYAML.registerPermission(getConfigYAML().getHomesCommandPermission(), "Permission to use the '/" + getConfigYAML().getHomesCommand() + "' command", PermissionDefault.TRUE);
            PluginYAML.registerPermission(getConfigYAML().getHomesOthersPermission(), "Permission to use the '/" + getConfigYAML().getHomesCommand() + " (Player)' command", PermissionDefault.OP);
            PluginYAML.registerPermission(getConfigYAML().getHomeCommandPermission(), "Permission to use the '/" + getConfigYAML().getHomeCommand() + "' command", PermissionDefault.TRUE);
            PluginYAML.registerPermission(getConfigYAML().getHomeRenamePermission(), "Permission to use the '/" + getConfigYAML().getHomeCommand() + " rename (Home) (NewName)' command", PermissionDefault.TRUE);
            PluginYAML.registerPermission(getConfigYAML().getHomeOthersPermission(), "Permission to use the '/" + getConfigYAML().getHomeCommand() + " (Player:Home)' command", PermissionDefault.OP);
            PluginYAML.registerPermission(getConfigYAML().getBypassLimitPermission(), "Bypass homes limit", PermissionDefault.OP);
            PluginYAML.registerPermission(getConfigYAML().getBypassSafeTeleportationPermission(), "Bypass safe teleport", PermissionDefault.FALSE);
            PluginYAML.registerPermission(getConfigYAML().getBypassDimensionalTeleportationPermission(), "Bypass dimensional teleportation config", PermissionDefault.OP);
            PluginYAML.registerPermission(getConfigYAML().getBypassRestrictedWorldsPermission(), "Bypass restricted worlds", PermissionDefault.OP);
            PluginYAML.registerPermission(getConfigYAML().getBypassWarmupPermission(), "Bypass teleportation warmup time", PermissionDefault.OP);
        } catch (Exception e) {
            getLoggerInstance().warn("Failed to register permissions. Don't worry, this affects nothing in general.", e);
        }
        //</editor-fold>
    }

    public void registerEvents() {
        logger.debug("<yellow>Registering event listeners...");
        logger.debug("<yellow>> PlayerListeners");
        PluginYAML.registerEvent(new PlayerListeners());
        if(HookRegistry.WORLDGUARD.exists()) {
            logger.debug("<yellow>> WorldGuardListeners");
            PluginYAML.registerEvent(new WorldGuardListeners());
        }
        if(HookRegistry.GRIEF_PREVENTION.exists()) {
            logger.debug("<yellow>> GriefPreventionListeners");
            PluginYAML.registerEvent(new GriefPreventionListeners());
        }
    }

    private static class zHomesMetrics extends Metrics {
        public zHomesMetrics(@NotNull JavaPlugin plugin, int id) {
            super(plugin, id);
            addCustomChart(new SimplePie("database_type", () -> {
                String dbType = config.getString(Path.formPath("database.type"), "H2");
                return switch (dbType.toLowerCase()) {
                    case "sqlite" -> "SQLite";
                    case "mysql" -> "MySQL";
                    case "mariadb" -> "MariaDB";
                    default -> "H2";
                };
            }));
            addCustomChart(new SimplePie("language", () -> {
                String language = getConfigYAML().getLanguageCode();
                return switch (language.toLowerCase()) {
                    case "de" -> "German";
                    case "en" -> "English";
                    case "es" -> "Spanish";
                    case "fr" -> "French";
                    case "it" -> "Italian";
                    case "nl" -> "Dutch";
                    case "pl" -> "Polish";
                    case "pt-br" -> "Portuguese (Brazil)";
                    case "ru" -> "Russian";
                    case "zhcn" -> "Chinese (Simplified)";
                    default -> "Custom";
                };
            }));
        }
    }

    //<editor-fold desc="Java Overrides & Getters">
    @Override
    public @NotNull FileConfiguration getConfig() {
        return config.getConfig();
    }

    public static ConfigYAML getConfigYAML() {
        return config;
    }

    public static LanguageBuilder getLanguageYAML() {
        return LanguageUtils.getCurrentLanguageYAML();
    }

    @Override
    public void saveDefaultConfig() {
        reloadConfig();
    }

    @Override
    public void reloadConfig() {
        config = new ConfigYAML();
    }

    public Logger getLoggerInstance() {
        return logger;
    }
    //</editor-fold>
}
