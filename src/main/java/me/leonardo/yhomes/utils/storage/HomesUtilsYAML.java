package me.leonardo.yhomes.utils.storage;

import me.leonardo.yhomes.FileManager;
import me.leonardo.yhomes.Main;
import me.leonardo.yhomes.utils.ConfigUtils;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

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

    public boolean inSameWorld(Location loc, Player p) {
        World w = loc.getWorld();
        World w2 = p.getWorld();
        return w.equals(w2);
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

    public void teleportPlayer(Player p, String home) {
        Location loc = getHomeLoc(p, home);
        if(!inSameWorld(loc, p)) {
            // Message
            return;
        }

        p.teleport(loc);
    }

    public Location getHomeLoc(OfflinePlayer p, String home) {
        String saveas = saveAsType();
        String saveasp = p.getName();
        if(isSaveAsTypeUuid()) saveasp = p.getUniqueId().toString();

        String locS = fm.cfg.getString(homes+"."+saveas+"."+saveasp+"."+home);

        try {
            return main.deserializeAddDot(locS);
        }catch (Exception e) {
        }
        return null;
    }

}
