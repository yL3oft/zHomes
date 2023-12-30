package me.leonardo.yhomes.utils.storage;

import me.leonardo.yhomes.FileManager;
import me.leonardo.yhomes.Main;
import me.leonardo.yhomes.utils.ConfigUtils;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

public class HomesUtilsYAML extends ConfigUtils {

    Main main = Main.main;
    FileManager fm = Main.fm;
    String homes = "homes";

    public boolean hasHome(OfflinePlayer p, String home) {
        String saveas = saveAsType();
        String saveasp = p.getName();
        if(isSaveAsTypeUuid()) saveasp = p.getUniqueId().toString();

        return fm.cfg.contains(homes+"."+saveas+"."+saveasp+"."+home);
    }

    public void addHome(OfflinePlayer p, String home, Location loc) {
        String saveas = saveAsType();
        String saveasp = p.getName();
        if(isSaveAsTypeUuid()) saveasp = p.getUniqueId().toString();

        fm.cfg.set(homes+"."+saveas+"."+saveasp+"."+home, main.serializeDelDot(loc));
        fm.saveCfg();
    }

    public void delHome(OfflinePlayer p, String home) {
        String saveas = saveAsType();
        String saveasp = p.getName();
        if(isSaveAsTypeUuid()) saveasp = p.getUniqueId().toString();

        fm.cfg.set(homes+"."+saveas+"."+saveasp+"."+home, null);
        fm.saveCfg();
    }

}
