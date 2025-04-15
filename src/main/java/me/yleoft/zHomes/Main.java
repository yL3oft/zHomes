package me.yleoft.zHomes;

import com.zaxxer.hikari.HikariDataSource;
import me.yleoft.zAPI.managers.*;
import me.yleoft.zAPI.utils.FileUtils;
import me.yleoft.zAPI.zAPI;
import me.yleoft.zHomes.commands.*;
import me.yleoft.zHomes.hooks.placeholderapi.*;
import me.yleoft.zHomes.storage.*;
import me.yleoft.zHomes.tabcompleters.*;
import me.yleoft.zHomes.utils.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Driver;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

public final class Main extends JavaPlugin {

    public static zAPI zAPI;

    public static FileUtils configFileUtils;
    public static String homesMenuPath = "menus/menu-homes.yml";

    private static Main main;
    public static PluginYAMLManager pym;
    public static FileManager fm;
    public static ConfigUtils cfgu;
    public static HomesUtils hu;
    public static DatabaseConnection db;
    public static DatabaseEditor dbe;
    public static Main getInstance() {
        return main;
    }

    public static HikariDataSource dataSource = null;
    public static database_type type = database_type.SQLITE;
    public static Driver sqliteDriver = null;
    public static Driver mysqlDriver = null;
    public static Driver mariadbDriver = null;
    public static Driver h2Driver = null;

    public static Object papi;
    public static Object economy;

    public String pluginName = getDescription().getName();
    public String coloredPluginName = this.pluginName;
    public String pluginVer = getDescription().getVersion();
    public String site = "https://modrinth.com/plugin/zhomes/version/latest";
    public static int bStatsId = 25021;

    public File libsFolder = new File(getDataFolder(), "libs");

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
    public void onEnable() {
        zAPI = new zAPI(this, pluginName, coloredPluginName, true);
        //<editor-fold desc="Variables">
        main = Main.this;
        pluginName = getDescription().getName();
        coloredPluginName = this.pluginName;
        pluginVer = getDescription().getVersion();
        pym = zAPI.getPluginYAMLManager();
        fm = zAPI.getFileManager();
        cfgu = new ConfigUtils();
        hu = new HomesUtils();
        db = new DatabaseConnection();
        dbe = new DatabaseEditor();
        //</editor-fold>
        LanguageUtils.CommandsMSG cmdm = new LanguageUtils.CommandsMSG();
        coloredPluginName = cmdm.hex(coloredPluginName);
        coloredPluginName = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("prefix")));
        zAPI.setColoredPluginName(coloredPluginName);
        cmdm.sendMsg(getServer().getConsoleSender(), coloredPluginName+"§f------------------------------------------------------");
        cmdm.sendMsg(getServer().getConsoleSender(), coloredPluginName+"§fPlugin started loading...");
        updatePlugin();
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
                cmdm.sendMsg(getServer().getConsoleSender(), coloredPluginName+"§aLibrary §9H2Database §asaved to "+outputFile.getAbsolutePath());
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
                cmdm.sendMsg(getServer().getConsoleSender(), coloredPluginName+"§aLibrary §9MySQL Connector Java §asaved to "+outputFile.getAbsolutePath());
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
                cmdm.sendMsg(getServer().getConsoleSender(), coloredPluginName+"§aLibrary §9MariaDB Java Client §asaved to "+outputFile.getAbsolutePath());
            }
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "Failed to download MariaDB library", e);
        }
        //</editor-fold>
        //</editor-fold>
        //<editor-fold desc="Database">
        db.connect();
        dbe.renameTable("ZHOMES", db.databaseTable());
        dbe.createTable(db.databaseTable(), "(UUID VARCHAR(36),HOME VARCHAR(100),LOCATION VARCHAR(255),PRIMARY KEY (UUID, HOME))");
        //</editor-fold>
        //<editor-fold desc="Metrics">
        if(cfgu.hasMetrics()) {
            zAPI.startMetrics(bStatsId);
            cmdm.sendMsg(getServer().getConsoleSender(), ChatColor.translateAlternateColorCodes('&',
                    coloredPluginName+"&aMetrics enabled."
            ));
        }
        //</editor-fold>
        //<editor-fold desc="Files">
        cmdm.sendMsg(getServer().getConsoleSender(), ChatColor.translateAlternateColorCodes('&', coloredPluginName + "&fChecking if files exist..."));
        if(configFileUtils == null) {
            getConfig();
        }
        fm.fuLang.saveDefaultConfig();
        fm.fuLang.reloadConfig();
        fm.fuLang2.saveDefaultConfig();
        fm.fuLang2.reloadConfig();
        FileUtils fu = fm.createFile(homesMenuPath);
        if(!fu.getFile().exists()) {
            fu.saveDefaultConfig();
            fu.reloadConfig(true);
        }else {
            fu.saveDefaultConfig();
            fu.reloadConfig(false);
        }
        cmdm.sendMsg(getServer().getConsoleSender(), ChatColor.translateAlternateColorCodes('&', coloredPluginName + "&fAll files have been created!"));
        //</editor-fold>
        loadCommands();
        //<editor-fold desc="Hooks">
        cmdm.sendMsg(getServer().getConsoleSender(), coloredPluginName + "§fTrying to connect to hooks...");
        try {
            zAPI.setPlaceholderAPIHandler(new PlaceholderAPIHandler(zAPI));
            if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
                zAPI.registerPlaceholderExpansion(getDescription().getAuthors().toString(), pluginVer, true, true);
                papi = zAPI.getPlaceholderExpansion();
                cmdm.sendMsg(getServer().getConsoleSender(), coloredPluginName + "§aPlaceholderAPI hooked successfully!");
            } else {
                cmdm.sendMsg(getServer().getConsoleSender(), coloredPluginName + "§cPlaceholderAPI plugin not found! Disabling hook...");
            }
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "Error hooking into PlaceholderAPI", e);
        }
        try {
            if (getServer().getPluginManager().getPlugin("Vault") != null) {
                setupEconomy();
                cmdm.sendMsg(getServer().getConsoleSender(), coloredPluginName + "§aConnected to Vault successfully!");
            }
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "Error hooking into VaultAPI", e);
        }
        //</editor-fold>
        cmdm.sendMsg(getServer().getConsoleSender(), coloredPluginName+"§fPlugin started (Any errors will be above this message)");
        cmdm.sendMsg(getServer().getConsoleSender(), coloredPluginName+"§f------------------------------------------------------");
    }

    @Override
    public void onDisable() {
        if (!getServer().getName().contains("Folia")) {
            Bukkit.getScheduler().cancelTasks(this);
        }
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
        LanguageUtils.CommandsMSG cmdm = new LanguageUtils.CommandsMSG();
        UpdateChecker checker = new UpdateChecker();
        String version = checker.getVersion();
        String pf = "&8&l|> &r";

        if(!getDescription().getVersion().equals(version)) {
            cmdm.sendMsg(getServer().getConsoleSender(), ChatColor.translateAlternateColorCodes('&',
                    coloredPluginName+"&cPlugin is out-dated!"
            ));
            cmdm.sendMsg(getServer().getConsoleSender(), ChatColor.translateAlternateColorCodes('&',
                    pf+"&fNew version: &e"+version
            ));
            cmdm.sendMsg(getServer().getConsoleSender(), ChatColor.translateAlternateColorCodes('&',
                    pf+"&fYour version: &e"+getDescription().getVersion()
            ));
            if(cfgu.isAutoUpdate()) {
                cmdm.sendMsg(getServer().getConsoleSender(), ChatColor.translateAlternateColorCodes('&',
                        pf+"&fAttempting to auto-update it..."
                ));
                try {
                    String path = checker.update(pluginName, version);
                    cmdm.sendMsg(getServer().getConsoleSender(), ChatColor.translateAlternateColorCodes('&',
                            pf+"&aPlugin updated! &7Saved in: "+path
                    ));
                    cmdm.sendMsg(getServer().getConsoleSender(), ChatColor.translateAlternateColorCodes('&',
                            pf+"&aRestart the server to apply changes."
                    ));
                }catch (Exception e) {
                    cmdm.sendMsg(getServer().getConsoleSender(), ChatColor.translateAlternateColorCodes('&',
                            pf+"&cCould not auto-update it."
                    ));
                    cmdm.sendMsg(getServer().getConsoleSender(), ChatColor.translateAlternateColorCodes('&',
                            pf+"&fYou can update your plugin here: &e"+site
                    ));
                }
            }else {
                cmdm.sendMsg(getServer().getConsoleSender(), ChatColor.translateAlternateColorCodes('&',
                        pf+"&fYou can update your plugin here: &e"+site
                ));
            }
        }else {
            cmdm.sendMsg(getServer().getConsoleSender(), ChatColor.translateAlternateColorCodes('&',
                    coloredPluginName+"&aPlugin is up-to-date!"
            ));
        }
    }
    public void loadCommands() {
        LanguageUtils.CommandsMSG cmdm = new LanguageUtils.CommandsMSG();
        cmdm.sendMsg(getServer().getConsoleSender(), ChatColor.translateAlternateColorCodes('&', coloredPluginName + "§fTrying to load commands, permissions & events..."));
        //<editor-fold desc="Commands">
        try {
            pym.unregisterCommands();
            pym.registerCommand(cfgu.CmdMainCommand(), new MainCommand(), new MainCompleter(), cfgu.CmdMainDescription(), cfgu.CmdMainAliases().toArray(new String[0]));
            pym.registerCommand(cfgu.CmdSethomeCommand(), new SethomeCommand(), cfgu.CmdSethomeDescription(), cfgu.CmdSethomeAliases().toArray(new String[0]));
            pym.registerCommand(cfgu.CmdDelhomeCommand(), new DelhomeCommand(), new DelhomeCompleter(), cfgu.CmdDelhomeDescription(), cfgu.CmdDelhomeAliases().toArray(new String[0]));
            pym.registerCommand(cfgu.CmdHomesCommand(), new HomesCommand(), new HomesCompleter(), cfgu.CmdHomesDescription(), cfgu.CmdHomesAliases().toArray(new String[0]));
            pym.registerCommand(cfgu.CmdHomeCommand(), new HomeCommand(), new HomeCompleter(), cfgu.CmdHomeDescription(), cfgu.CmdHomeAliases().toArray(new String[0]));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load commands", e);
        }
        //</editor-fold>
        //<editor-fold desc="Permissions">
        try {
            pym.unregisterPermissions();
            Map<String, Boolean> helpANDmainChildren = new HashMap<>();
            helpANDmainChildren.put(cfgu.CmdMainPermission(), true);
            helpANDmainChildren.put(cfgu.CmdMainHelpPermission(), true);
            pym.registerPermission(cfgu.CmdMainPermission(), "Permission to use the '/" + cfgu.CmdMainCommand() + "' command", PermissionDefault.OP);
            pym.registerPermission(cfgu.CmdMainHelpPermission(), "Permission to use the '/" + cfgu.CmdMainCommand() + " (help|?)' command (With perm)", PermissionDefault.OP);
            pym.registerPermission(cfgu.CmdMainVersionPermission(), "Permission to use the '/" + cfgu.CmdMainCommand() + " (version|ver)' command", PermissionDefault.TRUE);
            pym.registerPermission(cfgu.CmdMainReloadPermission(), "Permission to use the '/" + cfgu.CmdMainCommand() + " (reload|rl)' command", PermissionDefault.OP, helpANDmainChildren);
            pym.registerPermission(cfgu.CmdMainConverterPermission(), "Permission to use the '/" + cfgu.CmdMainCommand() + " converter' command", PermissionDefault.OP);
            pym.registerPermission(cfgu.CmdSethomePermission(), "Permission to use the '/" + cfgu.CmdSethomeCommand() + "' command", PermissionDefault.TRUE);
            pym.registerPermission(cfgu.CmdDelhomePermission(), "Permission to use the '/" + cfgu.CmdDelhomeCommand() + "' command", PermissionDefault.TRUE);
            pym.registerPermission(cfgu.CmdDelhomeOthersPermission(), "Permission to use the '/" + cfgu.CmdDelhomeCommand() + " (Player:Home)' command", PermissionDefault.OP);
            pym.registerPermission(cfgu.CmdHomesPermission(), "Permission to use the '/" + cfgu.CmdHomesCommand() + "' command", PermissionDefault.TRUE);
            pym.registerPermission(cfgu.CmdHomesOthersPermission(), "Permission to use the '/" + cfgu.CmdHomesCommand() + " (Player)' command", PermissionDefault.OP);
            pym.registerPermission(cfgu.CmdHomePermission(), "Permission to use the '/" + cfgu.CmdHomeCommand() + "' command", PermissionDefault.TRUE);
            pym.registerPermission(cfgu.CmdHomeOthersPermission(), "Permission to use the '/" + cfgu.CmdHomeCommand() + " (Player:Home)' command", PermissionDefault.OP);
            pym.registerPermission(cfgu.PermissionBypassLimit(), "Bypass homes limit", PermissionDefault.OP);
            pym.registerPermission(cfgu.PermissionBypassDT(), "Bypass dimensional teleportation config", PermissionDefault.OP);
        } catch (Exception e) {
            cmdm.sendMsg(getServer().getConsoleSender(), this.coloredPluginName + "§cError registering permissions (This doesn't affect anything in general)!");
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

    //<editor-fold desc="Location Serialization">
    public String serialize(Location loc) {
        String w = Objects.requireNonNull(loc.getWorld()).getName();
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        float yaw = loc.getYaw();
        float pitch = loc.getPitch();

        return w+";"+x+";"+y+";"+z+";"+yaw+";"+pitch+";";
    }
    public String serialize(String w, double x, double y, double z, float yaw, float pitch) {
        return w+";"+x+";"+y+";"+z+";"+yaw+";"+pitch+";";
    }

    public Location deserialize(String serialized) {

        String[] serializedSplit = serialized.split(";");

        World w = Bukkit.getWorld(serializedSplit[0]);
        double x = Double.parseDouble(serializedSplit[1]);
        double y = Double.parseDouble(serializedSplit[2]);
        double z = Double.parseDouble(serializedSplit[3]);
        float yaw = Float.parseFloat(serializedSplit[4]);
        float pitch = Float.parseFloat(serializedSplit[5]);

        return new Location(w, x, y, z, yaw, pitch);
    }
    //</editor-fold>
    //<editor-fold desc="Java Overrides">
    @Override
    public @NotNull FileConfiguration getConfig() {
        if (configFileUtils == null) {
            // Lazy initialization if not already set
            File configFile = new File(getDataFolder(), "config.yml");
            configFileUtils = new FileUtils(zAPI, configFile, "config.yml");
            configFileUtils.saveDefaultConfig();
            configFileUtils.reloadConfig(false);
        }
        return configFileUtils.getConfig();
    }

    @Override
    public void saveDefaultConfig() {
        if (configFileUtils == null) {
            File configFile = new File(getDataFolder(), "config.yml");
            configFileUtils = new FileUtils(zAPI, configFile, "config.yml");
        }
        configFileUtils.saveDefaultConfig();
    }

    @Override
    public void reloadConfig() {
        if (configFileUtils == null) {
            File configFile = new File(getDataFolder(), "config.yml");
            configFileUtils = new FileUtils(zAPI, configFile, "config.yml");
            configFileUtils.saveDefaultConfig();
        }
        configFileUtils.reloadConfig(false);
    }

    public File getFileJava() {
        return getFile();
    }
    //</editor-fold>

}
