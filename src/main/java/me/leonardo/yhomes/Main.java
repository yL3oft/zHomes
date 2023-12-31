package me.leonardo.yhomes;

import me.leonardo.yhomes.utils.ConfigUtils;
import me.leonardo.yhomes.utils.PluginYAMLManager;
import me.leonardo.yhomes.expansions.*;
import me.leonardo.yhomes.utils.storage.HomesUtilsYAML;
import org.bukkit.Bukkit;
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
        main = Main.this;
        pym = new PluginYAMLManager();
        fm = new FileManager();
        cfgu = new ConfigUtils();
        hu = new HomesUtilsYAML();


        File f = new File(getDataFolder(), "config.yml");
        File f2 = new File(getDataFolder(), "homes.example.yml");
        if(!f.exists()) {
            saveDefaultConfig();
        }
        if(!f2.exists()) {
            YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f2);
            try {
                cfg = YamlConfiguration.loadConfiguration(f2);
            }catch (Exception e) {
            }
        }


        if(getServer().getPluginManager().isPluginEnabled("PlaceHolderAPI")) {
            new PlaceHolderAPIExpansion().register();
        }
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
