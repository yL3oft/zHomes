package me.yleoft.zHomes.managers;

import java.io.File;
import me.yleoft.zHomes.Main;
import me.yleoft.zHomes.utils.FileUtils;

public class FileManager {

    public File df = Main.getInstance().getDataFolder();
    public File lang = new File(this.df, "languages/en.yml");
    public File lang2 = new File(this.df, "languages/pt-br.yml");
    public File fBACKUP = null;
    public FileUtils fuLang = new FileUtils(this.lang, "languages/en.yml");
    public FileUtils fuLang2 = new FileUtils(this.lang2, "languages/pt-br.yml");
    public FileUtils fuBACKUP = null;

}
