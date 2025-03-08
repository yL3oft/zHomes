package me.yleoft.zHomes;

import me.yleoft.zHomes.commands.*;
import me.yleoft.zHomes.hooks.*;
import me.yleoft.zHomes.managers.*;
import me.yleoft.zHomes.storage.*;
import me.yleoft.zHomes.tabcompleters.*;
import me.yleoft.zHomes.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class Main extends JavaPlugin {

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

    public String pluginName = getDescription().getName();
    public String coloredPluginName = this.pluginName;
    public String pluginVer = getDescription().getVersion();

    public void onEnable() {
        coloredPluginName = ChatColor.translateAlternateColorCodes('&', Objects.<String>requireNonNull(getConfig().getString("prefix")));
        getServer().getConsoleSender().sendMessage(coloredPluginName+"§f------------------------------------------------------");
        getServer().getConsoleSender().sendMessage(coloredPluginName+"§fPlugin started loading...");
        //<editor-fold desc="Variables">
        main = this;
        pym = new PluginYAMLManager();
        fm = new FileManager();
        cfgu = new ConfigUtils();
        hu = new HomesUtils();
        db = new DatabaseConnection();
        dbe = new DatabaseEditor();
        //</editor-fold>
        //<editor-fold desc="Database">
        db.connect();
        dbe.createTable(db.databaseTable(), "(UUID VARCHAR(36),HOME VARCHAR(100),LOCATION VARCHAR(255))");
        db.disconnect();
        //</editor-fold>
        //<editor-fold desc="Files">
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', coloredPluginName + "&fChecking if files exist..."));
        File f = new File(getDataFolder(), "config.yml");
        if (!f.exists()) {
            saveDefaultConfig();
            reloadConfig();
        }
        fm.fuLang.saveDefaultConfig();
        fm.fuLang.reloadConfig();
        fm.fuLang2.saveDefaultConfig();
        fm.fuLang2.reloadConfig();
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', coloredPluginName + "&fAll files have been created!"));
        //</editor-fold>
        loadCommands();
        //<editor-fold desc="Hooks">
        getServer().getConsoleSender().sendMessage(coloredPluginName + "§fTrying to connect to hooks...");
        if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            (new PlaceholderAPIExpansion()).register();
            getServer().getConsoleSender().sendMessage(coloredPluginName + "§ahook connected successfully!");
        } else {
            getServer().getConsoleSender().sendMessage(coloredPluginName + "§cPlaceholderAPI plugin not found! Disabling hook...");
        }
        //</editor-fold>
        getServer().getConsoleSender().sendMessage(coloredPluginName+"§fPlugin started (Any errors will be above this message)");
        getServer().getConsoleSender().sendMessage(coloredPluginName+"§f------------------------------------------------------");
    }

    public void onDisable() {
        db.disconnect();
    }

    public void loadCommands() {
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', coloredPluginName + "§fTrying to load commands, permissions & events..."));
        //<editor-fold desc="Commands">
        try {
            pym.unregisterCommands();
            pym.registerCommand(cfgu.CmdMainCommand(), new MainCommand(), new MainCompleter(), cfgu.CmdMainDescription(), cfgu.CmdMainAliases().toArray(new String[0]));
            pym.registerCommand(cfgu.CmdSethomeCommand(), new SethomeCommand(), cfgu.CmdSethomeDescription(), cfgu.CmdSethomeAliases().toArray(new String[0]));
            pym.registerCommand(cfgu.CmdDelhomeCommand(), new DelhomeCommand(), new DelhomeCompleter(), cfgu.CmdDelhomeDescription(), cfgu.CmdDelhomeAliases().toArray(new String[0]));
            pym.registerCommand(cfgu.CmdHomesCommand(), new HomesCommand(), cfgu.CmdHomesDescription(), cfgu.CmdHomesAliases().toArray(new String[0]));
            pym.registerCommand(cfgu.CmdHomeCommand(), new HomeCommand(), new HomeCompleter(), cfgu.CmdHomeDescription(), cfgu.CmdHomeAliases().toArray(new String[0]));
        } catch (Exception e) {
            e.printStackTrace();
            getServer().getConsoleSender().sendMessage(coloredPluginName + "§cError loading commands!");
        }
        //</editor-fold>
        //<editor-fold desc="Permissions">
        try {
            pym.unregisterPermissions();
            Map<String, Boolean> helpANDmainChildren = new HashMap<>();
            helpANDmainChildren.put(cfgu.CmdMainPermission(), Boolean.valueOf(true));
            helpANDmainChildren.put(cfgu.CmdMainHelpPermission(), Boolean.valueOf(true));
            pym.registerPermission(cfgu.CmdMainPermission(), "Permission to use the '/" + cfgu.CmdMainCommand() + "' command", PermissionDefault.OP);
            pym.registerPermission(cfgu.CmdMainHelpPermission(), "Permission to use the '/" + cfgu.CmdMainCommand() + " (help|?)' command (With perm)", PermissionDefault.OP);
            pym.registerPermission(cfgu.CmdMainVersionPermission(), "Permission to use the '/" + cfgu.CmdMainCommand() + " (version|ver)' command", PermissionDefault.TRUE);
            pym.registerPermission(cfgu.CmdMainReloadPermission(), "Permission to use the '/" + cfgu.CmdMainCommand() + " (reload|rl)' command", PermissionDefault.OP, helpANDmainChildren);
            pym.registerPermission(cfgu.CmdMainMigratePermission(), "Permission to use the '/" + cfgu.CmdMainCommand() + " migrate' command", PermissionDefault.OP);
            pym.registerPermission(cfgu.CmdSethomePermission(), "Permission to use the '/" + cfgu.CmdSethomeCommand() + "' command", PermissionDefault.TRUE);
            pym.registerPermission(cfgu.CmdDelhomePermission(), "Permission to use the '/" + cfgu.CmdDelhomeCommand() + "' command", PermissionDefault.TRUE);
            pym.registerPermission(cfgu.CmdDelhomeOthersPermission(), "Permission to use the '/" + cfgu.CmdDelhomeCommand() + " (Player:Home)' command", PermissionDefault.OP);
            pym.registerPermission(cfgu.CmdHomesPermission(), "Permission to use the '/" + cfgu.CmdHomesCommand() + "' command", PermissionDefault.TRUE);
            pym.registerPermission(cfgu.CmdHomePermission(), "Permission to use the '/" + cfgu.CmdHomeCommand() + "' command", PermissionDefault.TRUE);
            pym.registerPermission(cfgu.CmdHomeOthersPermission(), "Permission to use the '/" + cfgu.CmdHomeCommand() + " (Player:Home)' command", PermissionDefault.OP);
            pym.registerPermission(cfgu.PermissionBypassDT(), "Bypass dimensional teleportation config", PermissionDefault.OP);
        } catch (Exception e) {
            getServer().getConsoleSender().sendMessage(this.coloredPluginName + "§cError registering permissions (This doesn't affect anything in general)!");
        }
        //</editor-fold>
    }

    //<editor-fold desc="Location Serialization">
    public String serialize(Location loc) {
        String w = loc.getWorld().getName();
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        float yaw = loc.getYaw();
        float pitch = loc.getPitch();

        String serialized = w+";"+x+";"+y+";"+z+";"+yaw+";"+pitch+";";

        return serialized;
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
    public void reloadConfig() {
        super.reloadConfig();
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public File getFileJava() {
        return getFile();
    }
    //</editor-fold>

}
