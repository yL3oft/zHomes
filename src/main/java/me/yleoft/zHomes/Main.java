package me.yleoft.zHomes;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.zaxxer.hikari.HikariDataSource;
import de.tr7zw.changeme.nbtapi.NBT;
import me.yleoft.zAPI.Metrics;
import me.yleoft.zAPI.managers.*;
import me.yleoft.zAPI.utils.FileUtils;
import me.yleoft.zAPI.zAPI;
import me.yleoft.zHomes.commands.*;
import me.yleoft.zHomes.hooks.*;
import me.yleoft.zHomes.listeners.*;
import me.yleoft.zHomes.storage.*;
import me.yleoft.zHomes.tabcompleters.*;
import me.yleoft.zHomes.utils.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import static java.util.Objects.requireNonNull;
import static me.yleoft.zAPI.managers.PluginYAMLManager.*;
import static me.yleoft.zAPI.utils.StringUtils.transform;
import static me.yleoft.zHomes.utils.LanguageUtils.loadzAPIMessages;

public final class Main extends JavaPlugin {

    public static FileUtils configFileUtils;
    public static final String homesMenuPath = "menus/menu-homes.yml";
    public static final String smallerHomesMenuExamplePath = "menus/small-homes-menu-example.yml";
    public static StateFlag useHomesFlag;
    public static StateFlag setHomesFlag;

    public static boolean useGriefPrevention = false;
    public static boolean usePlaceholderAPI = false;
    public static boolean useWorldGuard = false;
    public static boolean isWG7 = false;
    public static boolean useVault = false;

    private static Main main;
    public static ConfigUtils cfgu;
    public static HomesUtils hu;
    public static DatabaseConnection db;
    public static DatabaseEditor dbe;
    public static Main getInstance() {
        return main;
    }

    public static HikariDataSource dataSource = null;
    public static database_type type = database_type.SQLITE;
    public static Driver mysqlDriver = null;
    public static Driver mariadbDriver = null;
    public static Driver h2Driver = null;

    public static Object papi;
    public static Object economy;

    public static UpdateManager checker;
    public static boolean needsUpdate = false;
    public String updateVersion = getDescription().getVersion();

    public final String pluginName = getDescription().getName();
    public String coloredPluginName = this.pluginName;
    public final String pluginVer = getDescription().getVersion();
    public final String site = "https://modrinth.com/plugin/zhomes/version/latest";
    public final static int bStatsId = 25021;

    public final File libsFolder = new File(getDataFolder(), "libs");

    public final String mysqlVersion = "8.0.23";
    public final String mysqlJar = "mysql-connector-java-" + mysqlVersion + ".jar";
    public final String mysqlRepo = "https://repo1.maven.org/maven2/mysql/mysql-connector-java/" + mysqlVersion + "/" + mysqlJar;

    public final String mariadbVersion = "3.5.3";
    public final String mariadbJar = "mariadb-java-client-" + mariadbVersion + ".jar";
    public final String mariadbRepo = "https://repo1.maven.org/maven2/org/mariadb/jdbc/mariadb-java-client/" + mariadbVersion + "/" + mariadbJar;

    public final String h2Version = "2.3.232";
    public final String h2Jar = "h2-" + h2Version + ".jar";
    public final String h2Repo = "https://repo1.maven.org/maven2/com/h2database/h2/" + h2Version + "/" + h2Jar;

    @Override
    public void onLoad() {
        //<editor-fold desc="WorldGuard Hook">
        try {
            Plugin wg = getServer().getPluginManager().getPlugin("WorldGuard");
            if (wg != null) {
                useWorldGuard = true;
                try {
                    Class.forName("com.sk89q.worldguard.WorldGuard");
                    isWG7 = true;
                } catch (ClassNotFoundException ignored) {
                    isWG7 = false;
                    getLogger().warning("Legacy WorldGuard version found, some features may not work as expected.");
                }

                try {
                    WorldGuardHook.setupFlags();
                } catch (Exception e) {
                    getLogger().severe("Failed to setup custom flags with WorldGuard.");
                    e.printStackTrace();
                }
            } else {
                getLogger().warning("WorldGuard not found, continuing without it.");
            }
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "Error hooking into WorldGuard", e);
        }
        //</editor-fold>
    }

    @Override
    public void onEnable() {
        zAPI.init(this, pluginName, coloredPluginName, NBT.preloadApi());
        //<editor-fold desc="Variables">
        main = Main.this;
        coloredPluginName = this.pluginName;
        cfgu = new ConfigUtils();
        hu = new HomesUtils();
        db = new DatabaseConnection();
        dbe = new DatabaseEditor();
        //</editor-fold>
        LanguageUtils.Helper helper = new LanguageUtils.Helper() {};
        coloredPluginName = transform(requireNonNull(getConfig().getString("prefix")));
        zAPI.setColoredPluginName(coloredPluginName);
        helper.sendMsg(getServer().getConsoleSender(), coloredPluginName+"§f------------------------------------------------------");
        helper.sendMsg(getServer().getConsoleSender(), coloredPluginName+"§fPlugin started loading...");
        updatePlugin();
        //<editor-fold desc="Files">
        helper.sendMsg(getServer().getConsoleSender(), ChatColor.translateAlternateColorCodes('&', coloredPluginName + "&fChecking if files exist..."));
        if(configFileUtils == null) {
            getConfig();
        }
        List<FileUtils> fus = new ArrayList<>();
        fus.add(FileManager.createFile("languages/de.yml"));
        fus.add(FileManager.createFile("languages/en.yml"));
        fus.add(FileManager.createFile("languages/es.yml"));
        fus.add(FileManager.createFile("languages/fr.yml"));
        fus.add(FileManager.createFile("languages/it.yml"));
        fus.add(FileManager.createFile("languages/nl.yml"));
        fus.add(FileManager.createFile("languages/pl.yml"));
        fus.add(FileManager.createFile("languages/pt-br.yml"));
        fus.add(FileManager.createFile("languages/ru.yml"));
        fus.add(FileManager.createFile("languages/zhcn.yml"));
        for(FileUtils fu : fus) {
            fu.saveDefaultConfig();
            fu.reloadConfig();
        }
        List<FileUtils> menusFus = new ArrayList<>();
        menusFus.add(FileManager.createFile(homesMenuPath));
        menusFus.add(FileManager.createFile(smallerHomesMenuExamplePath));
        for(FileUtils fu : menusFus) {
            if (!fu.getFile().exists()) {
                fu.saveDefaultConfig();
                fu.reloadConfig(true);
            } else {
                fu.saveDefaultConfig();
                fu.reloadConfig(false);
            }
        }
        helper.sendMsg(getServer().getConsoleSender(), ChatColor.translateAlternateColorCodes('&', coloredPluginName + "&fAll files have been created!"));
        //</editor-fold>
        //<editor-fold desc="Download Libs">
        if(!libsFolder.exists()) {
            try {
                Files.createDirectories(Paths.get(libsFolder.toURI()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //<editor-fold desc="H2">
        try {
            File outputFile = new File(libsFolder, h2Jar);
            if(!outputFile.exists()) {
                downloadFile(h2Repo, outputFile);
                helper.sendMsg(getServer().getConsoleSender(), coloredPluginName+"§aLibrary §9H2Database §asaved to "+outputFile.getAbsolutePath());
            }
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "Failed to download H2Database library", e);
        }
        //</editor-fold>
        //<editor-fold desc="MySQL">
        try {
            File outputFile = new File(libsFolder, mysqlJar);
            if(!outputFile.exists()) {
                downloadFile(mysqlRepo, outputFile);
                helper.sendMsg(getServer().getConsoleSender(), coloredPluginName+"§aLibrary §9MySQL Connector Java §asaved to "+outputFile.getAbsolutePath());
            }
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "Failed to download MySQL Connector Java library", e);
        }
        //</editor-fold>
        //<editor-fold desc="MariaDB">
        try {
            File outputFile = new File(libsFolder, mariadbJar);
            if(!outputFile.exists()) {
                downloadFile(mariadbRepo, outputFile);
                helper.sendMsg(getServer().getConsoleSender(), coloredPluginName+"§aLibrary §9MariaDB Java Client §asaved to "+outputFile.getAbsolutePath());
            }
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "Failed to download MariaDB library", e);
        }
        //</editor-fold>
        //</editor-fold>
        //<editor-fold desc="Database">
        db.connect();
        dbe.createTable(db.databaseTable(), "(UUID VARCHAR(36),HOME VARCHAR(100),LOCATION VARCHAR(255),PRIMARY KEY (UUID, HOME))");
        //</editor-fold>
        //<editor-fold desc="Metrics">
        if(cfgu.hasMetrics()) {
            Metrics metrics = zAPI.startMetrics(bStatsId);
            metrics.addCustomChart(new Metrics.DrilldownPie("player_count", () -> {
                int players = Bukkit.getOnlinePlayers().size();
                return buildDistribution(players);
            }));
            helper.sendMsg(getServer().getConsoleSender(), ChatColor.translateAlternateColorCodes('&',
                    coloredPluginName+"&aMetrics enabled."
            ));
        }
        //</editor-fold>
        loadzAPIMessages();
        //<editor-fold desc="Hooks">
        helper.sendMsg(getServer().getConsoleSender(), coloredPluginName + "§fTrying to connect to hooks...");
        //<editor-fold desc="PlaceholderAPI">
        try {
            zAPI.setPlaceholderAPIHandler(new PlaceholderAPIHandler());
            if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                usePlaceholderAPI = true;
                zAPI.registerPlaceholderExpansion(getDescription().getAuthors().toString(), pluginVer, true, true);
                papi = zAPI.getPlaceholderExpansion();
                helper.sendMsg(getServer().getConsoleSender(), coloredPluginName + "§aPlaceholderAPI hooked successfully!");
            } else {
                helper.sendMsg(getServer().getConsoleSender(), coloredPluginName + "§cPlaceholderAPI plugin not found! Disabling hook...");
            }
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "Error hooking into PlaceholderAPI", e);
        }
        //</editor-fold>
        //<editor-fold desc="Vault">
        try {
            if (getServer().getPluginManager().isPluginEnabled("Vault")) {
                useVault = true;
                setupEconomy();
                helper.sendMsg(getServer().getConsoleSender(), coloredPluginName + "§aConnected to Vault successfully!");
            }
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "Error hooking into VaultAPI", e);
        }
        //</editor-fold>
        //<editor-fold desc="GriefPrevention">
        try {
            if (getServer().getPluginManager().isPluginEnabled("GriefPrevention")) {
                useGriefPrevention = true;
                helper.sendMsg(getServer().getConsoleSender(), coloredPluginName + "§aConnected to GriefPrevention successfully!");
            }
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "Error hooking into GriefPrevention", e);
        }
        //</editor-fold>
        //</editor-fold>
        loadCommands();
        registerEvents();
        helper.sendMsg(getServer().getConsoleSender(), coloredPluginName+"§fPlugin started (Any errors will be above this message)");
        helper.sendMsg(getServer().getConsoleSender(), coloredPluginName+"§f------------------------------------------------------");
    }

    @Override
    public void onDisable() {
        if (!zAPI.isFolia()) {
            Bukkit.getScheduler().cancelTasks(this);
        }
        unregisterPermissions();
        HandlerList.unregisterAll(this);
        if (db != null) {
            db.closePool();
            getLogger().info("Database connection closed.");
        }
        if(papi != null) {
            zAPI.unregisterPlaceholderExpansion();
        }
        main = null;
    }

    public void updatePlugin() {
        LanguageUtils.Helper helper = new LanguageUtils.Helper() {};
        checker = new UpdateManager(this, "https://api.github.com/repos/yL3oft/zHomes/tags", "zhomes");
        String version = checker.getVersion();
        String pf = "&8&l|> &r";

        if(!getDescription().getVersion().equals(version)) {
            needsUpdate = true;
            updateVersion = version;
            helper.sendMsg(getServer().getConsoleSender(), ChatColor.translateAlternateColorCodes('&',
                    coloredPluginName+"&cPlugin is out-dated!"
            ));
            helper.sendMsg(getServer().getConsoleSender(), ChatColor.translateAlternateColorCodes('&',
                    pf+"&fNew version: &e"+version
            ));
            helper.sendMsg(getServer().getConsoleSender(), ChatColor.translateAlternateColorCodes('&',
                    pf+"&fYour version: &e"+getDescription().getVersion()
            ));
            if(cfgu.isAutoUpdate()) {
                helper.sendMsg(getServer().getConsoleSender(), ChatColor.translateAlternateColorCodes('&',
                        pf+"&fAttempting to auto-update it..."
                ));
                try {
                    String path = checker.update();
                    helper.sendMsg(getServer().getConsoleSender(), ChatColor.translateAlternateColorCodes('&',
                            pf+"&aPlugin updated! &7Saved in: "+path
                    ));
                    helper.sendMsg(getServer().getConsoleSender(), ChatColor.translateAlternateColorCodes('&',
                            pf+"&aRestart the server to apply changes."
                    ));
                }catch (Exception e) {
                    helper.sendMsg(getServer().getConsoleSender(), ChatColor.translateAlternateColorCodes('&',
                            pf+"&cCould not auto-update it."
                    ));
                    helper.sendMsg(getServer().getConsoleSender(), ChatColor.translateAlternateColorCodes('&',
                            pf+"&fYou can update your plugin here: &e"+site
                    ));
                }
            }else {
                helper.sendMsg(getServer().getConsoleSender(), ChatColor.translateAlternateColorCodes('&',
                        pf+"&fYou can update your plugin here: &e"+site
                ));
            }
        }else {
            helper.sendMsg(getServer().getConsoleSender(), ChatColor.translateAlternateColorCodes('&',
                    coloredPluginName+"&aPlugin is up-to-date!"
            ));
        }
    }
    public void loadCommands() {
        LanguageUtils.CommandsMSG helper = new LanguageUtils.CommandsMSG();
        helper.sendMsg(getServer().getConsoleSender(), ChatColor.translateAlternateColorCodes('&', coloredPluginName + "§fTrying to load commands, permissions & events..."));
        //<editor-fold desc="Commands">
        try {
            unregisterCommands();
            registerCommand(cfgu.CmdMainCommand(), new MainCommand(), cfgu.CmdMainCooldown(), new MainCompleter(), cfgu.CmdMainDescription(), cfgu.CmdMainAliases().toArray(new String[0]));
            registerCommand(cfgu.CmdSethomeCommand(), new SethomeCommand(), cfgu.CmdSethomeCooldown(), cfgu.CmdSethomeDescription(), cfgu.CmdSethomeAliases().toArray(new String[0]));
            registerCommand(cfgu.CmdDelhomeCommand(), new DelhomeCommand(), cfgu.CmdDelhomeCooldown(), new DelhomeCompleter(), cfgu.CmdDelhomeDescription(), cfgu.CmdDelhomeAliases().toArray(new String[0]));
            registerCommand(cfgu.CmdHomesCommand(), new HomesCommand(), cfgu.CmdHomesCooldown(), new HomesCompleter(), cfgu.CmdHomesDescription(), cfgu.CmdHomesAliases().toArray(new String[0]));
            registerCommand(cfgu.CmdHomeCommand(), new HomeCommand(), cfgu.CmdHomeCooldown(), new HomeCompleter(), cfgu.CmdHomeDescription(), cfgu.CmdHomeAliases().toArray(new String[0]));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load commands", e);
        }
        //</editor-fold>
        //<editor-fold desc="Permissions">
        try {
            unregisterPermissions();
            Map<String, Boolean> helpANDmainChildren = new HashMap<>();
            helpANDmainChildren.put(cfgu.CmdMainPermission(), true);
            helpANDmainChildren.put(cfgu.CmdMainHelpPermission(), true);
            registerPermission(cfgu.CmdMainPermission(), "Permission to use the '/" + cfgu.CmdMainCommand() + "' command", PermissionDefault.TRUE);
            registerPermission(cfgu.CmdMainHelpPermission(), "Permission to use the '/" + cfgu.CmdMainCommand() + " (help|?)' command (With perm)", PermissionDefault.OP);
            registerPermission(cfgu.CmdMainVersionPermission(), "Permission to use the '/" + cfgu.CmdMainCommand() + " (version|ver)' command", PermissionDefault.TRUE);
            registerPermission(cfgu.CmdMainVersionUpdatePermission(), "Permission to use the '/" + cfgu.CmdMainCommand() + " (version|ver) update' command", PermissionDefault.OP);
            registerPermission(cfgu.CmdMainInfoPermission(), "Permission to use the '/" + cfgu.CmdMainCommand() + " info' command", PermissionDefault.OP);
            registerPermission(cfgu.CmdMainReloadPermission(), "Permission to use the '/" + cfgu.CmdMainCommand() + " (reload|rl)' command", PermissionDefault.OP, helpANDmainChildren);
            registerPermission(cfgu.CmdMainNearhomesPermission(), "Permission to use the '/" + cfgu.CmdMainCommand() + " nearhomes' command", PermissionDefault.OP);
            registerPermission(cfgu.CmdMainConverterPermission(), "Permission to use the '/" + cfgu.CmdMainCommand() + " converter' command", PermissionDefault.OP);
            registerPermission(cfgu.CmdMainImportexportPermission(), "Permission to use the '/" + cfgu.CmdMainCommand() + " (export|import)' command", PermissionDefault.OP);
            registerPermission(cfgu.CmdSethomePermission(), "Permission to use the '/" + cfgu.CmdSethomeCommand() + "' command", PermissionDefault.TRUE);
            registerPermission(cfgu.CmdDelhomePermission(), "Permission to use the '/" + cfgu.CmdDelhomeCommand() + "' command", PermissionDefault.TRUE);
            registerPermission(cfgu.CmdDelhomeOthersPermission(), "Permission to use the '/" + cfgu.CmdDelhomeCommand() + " (Player:Home)' command", PermissionDefault.OP);
            registerPermission(cfgu.CmdHomesPermission(), "Permission to use the '/" + cfgu.CmdHomesCommand() + "' command", PermissionDefault.TRUE);
            registerPermission(cfgu.CmdHomesOthersPermission(), "Permission to use the '/" + cfgu.CmdHomesCommand() + " (Player)' command", PermissionDefault.OP);
            registerPermission(cfgu.CmdHomePermission(), "Permission to use the '/" + cfgu.CmdHomeCommand() + "' command", PermissionDefault.TRUE);
            registerPermission(cfgu.CmdHomeRenamePermission(), "Permission to use the '/" + cfgu.CmdHomeCommand() + " rename (Home) (NewName)' command", PermissionDefault.TRUE);
            registerPermission(cfgu.CmdHomeOthersPermission(), "Permission to use the '/" + cfgu.CmdHomeCommand() + " (Player:Home)' command", PermissionDefault.OP);
            registerPermission(cfgu.PermissionBypassLimit(), "Bypass homes limit", PermissionDefault.OP);
            registerPermission(cfgu.PermissionBypassST(), "Bypass safe teleport", PermissionDefault.FALSE);
            registerPermission(cfgu.PermissionBypassDT(), "Bypass dimensional teleportation config", PermissionDefault.OP);
            registerPermission(cfgu.PermissionBypassRestrictedWorlds(), "Bypass restricted worlds", PermissionDefault.OP);
            registerPermission(cfgu.PermissionBypassWarmup(), "Bypass teleportation warmup time", PermissionDefault.OP);
        } catch (Exception e) {
            helper.sendMsg(getServer().getConsoleSender(), this.coloredPluginName + "§cError registering permissions (This doesn't affect anything in general)!");
        }
        //</editor-fold>
    }
    public void registerEvents() {
        //<editor-fold desc="Listeners">
        registerEvent(new PlayerListeners());
        if(useWorldGuard) {
            registerEvent(new WorldGuardListeners());
        }
        if(useGriefPrevention) {
            registerEvent(new GriefPreventionListeners());
        }
        //</editor-fold>
    }
    public static void downloadFile(String fileURL, File saveFilePath) throws IOException {
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = httpConn.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();
        } else {
            System.out.println("Error downloading libs. HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return true;
    }

    //<editor-fold desc="bStats">
    private Map<String, Map<String, Integer>> buildDistribution(int players) {
        Map<String, Map<String, Integer>> outer = new HashMap<>();
        String outerBucket = outerBucket(players);
        String innerBucket = innerBucket(players);
        Map<String, Integer> inner = new HashMap<>();
        inner.put(innerBucket, 1);
        outer.put(outerBucket, inner);
        return outer;
    }

    private String outerBucket(int n) {
        if (n <= 0) return "0";
        if (n <= 10) return "1-10";
        if (n <= 25) return "11-25";
        if (n <= 50) return "26-50";
        if (n <= 100) return "51-100";
        if (n <= 200) return "101-200";
        return "200+";
    }

    private String innerBucket(int n) {
        if (n <= 0) return "0";
        if (n <= 10) {
            return String.valueOf(n);
        }
        if (n <= 25) {
            return rangeOfFive(n, 11, 25);
        }
        if (n <= 50) {
            return rangeOfFive(n, 26, 50);
        }
        if (n <= 100) {
            return rangeOfFive(n, 51, 100);
        }
        if (n <= 200) {
            return rangeOfFive(n, 101, 200);
        }
        return "200+";
    }

    private String rangeOfFive(int n, int start, int end) {
        int bucketStart = ((n - start) / 5) * 5 + start;
        int bucketEnd = Math.min(bucketStart + 4, end);
        return bucketStart + "-" + bucketEnd;
    }
    //</editor-fold>

    //<editor-fold desc="Java Overrides">
    @Override
    public @NotNull FileConfiguration getConfig() {
        if (configFileUtils == null) {
            // Lazy initialization if not already set
            File configFile = new File(getDataFolder(), "config.yml");
            configFileUtils = new FileUtils(configFile, "config.yml");
            configFileUtils.saveDefaultConfig();
            configFileUtils.reloadConfig(false);
        }
        return configFileUtils.getConfig();
    }

    @Override
    public void saveDefaultConfig() {
        if (configFileUtils == null) {
            File configFile = new File(getDataFolder(), "config.yml");
            configFileUtils = new FileUtils(configFile, "config.yml");
        }
        configFileUtils.saveDefaultConfig();
    }

    @Override
    public void reloadConfig() {
        if (configFileUtils == null) {
            File configFile = new File(getDataFolder(), "config.yml");
            configFileUtils = new FileUtils(configFile, "config.yml");
            configFileUtils.saveDefaultConfig();
        }
        configFileUtils.reloadConfig(false);
    }
    //</editor-fold>

}
