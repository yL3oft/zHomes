package me.leonardo.zhomes;

import me.leonardo.zhomes.commands.*;
import me.leonardo.zhomes.utils.*;
import me.leonardo.zhomes.expansions.*;
import me.leonardo.zhomes.utils.storage.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {

    public static Main main;
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
        pym = new PluginYAMLManager();
        fm = new FileManager();
        cfgu = new ConfigUtils();
        hu = new HomesUtilsYAML();


        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&7[&9z&cHomes&7] &fChecking if files exist..."
        ));
        File f = new File(getDataFolder(), "config.yml");
        File f2 = new File(getDataFolder(), "homes.example.yml");
        File f3 = new File(getDataFolder(), "languages/en.yml");
        if(!f.exists()) {
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&7[&9z&cHomes&7] &econfig.yml &fdoesn't exist, attempting to create it..."
            ));
            saveDefaultConfig();
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
        if(!f3.exists()) {
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&7[&9z&cHomes&7] &eLanguage 'en' &fdoesn't exist, attempting to create it..."
            ));
            saveResource("languages/en.yml", false);
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&7[&9z&cHomes&7] &eLanguage 'en' &fhave been created!"
            ));
        }
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&7[&9z&cHomes&7] &fAll files have been created!"
        ));


        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&7[&9z&cHomes&7] &fAttempting to load commands..."
        ));
        try {
            pym.registerCommand("sethome", new SethomeCommand());
            pym.registerCommand("delhome", new DelhomeCommand());
            pym.registerCommand("home", new HomeCommand());
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&7[&9z&cHomes&7] &fAll commands have been loaded. (If not it will be a message up)"
            ));
        }catch (Exception e) {
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&7[&9z&cHomes&7] &fAttempting to load commands..."
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
                "&7[&9z&cHomes&7] &fPlugin loaded successfully."
        ));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
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

}
