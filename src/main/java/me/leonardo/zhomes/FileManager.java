package me.leonardo.zhomes;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FileManager {

    public File df = Main.main.getDataFolder();
    public File f = new File(df, "homes.yml");
    public File f2 = new File(df, "limits.yml");
    public YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
    public YamlConfiguration cfg2 = YamlConfiguration.loadConfiguration(f2);




    public void saveCfg() {
        try {
            cfg.save(f);
        }catch (Exception e) {
        }
    }

    public void saveCfg2() {
        try {
            cfg2.save(f2);
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
