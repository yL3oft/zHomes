package me.leonardo.zhomes;

import me.leonardo.zhomes.utils.FileUtils;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FileManager {

    public File df = Main.main.getDataFolder();
    public File f = new File(df, "homes.yml");
    public File f2 = new File(df, "limits.yml");
    public File f3 = new File(df, "languages/en.yml");
    public File f4 = new File(df, "languages/pt-br.yml");
    public File fBACKUP = null;
    public FileUtils fu = new FileUtils(f, "homes.yml");
    public FileUtils fu2 = new FileUtils(f2, "limits.yml");
    public FileUtils fu3 = new FileUtils(f3, "languages/en.yml");
    public FileUtils fu4 = new FileUtils(f4, "languages/pt-br.yml");
    public FileUtils fuBACKUP = null;

}
