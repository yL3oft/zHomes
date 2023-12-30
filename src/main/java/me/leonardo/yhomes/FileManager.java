package me.leonardo.yhomes;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FileManager {

    public File df = Main.main.getDataFolder();
    public File f = new File(df, "homes.yml");
    public YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);




    public void saveCfg() {
        try {
            cfg.save(f);
        }catch (Exception e) {
        }
    }

    public void reloadCfg() {
        try {
            cfg = YamlConfiguration.loadConfiguration(f);
        }catch (Exception e) {
        }
    }

}
