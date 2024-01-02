package me.leonardo.zhomes.utils.storage;

import me.leonardo.zhomes.FileManager;
import me.leonardo.zhomes.Main;
import me.leonardo.zhomes.api.events.TeleportToHomeEvent;
import me.leonardo.zhomes.utils.ConfigUtils;
import me.leonardo.zhomes.utils.LanguageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

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

    public boolean inSameWorld(String w, Player p) {
        String w2 = p.getWorld().getName();
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
        boolean isDimensionalTeleport = false;
        if(!inSameWorld(getHomeWorld(p, home), p)) {
            isDimensionalTeleport = true;
        }
        TeleportToHomeEvent event = new TeleportToHomeEvent(p, home, p.getLocation(), loc, isDimensionalTeleport);
        Bukkit.getPluginManager().callEvent(event);
        if(event.isCancelled()) return;

        if(isDimensionalTeleport && !canDimensionalTeleport()) {
            LanguageUtils.Homes lang = new LanguageUtils.Homes();
            lang.sendMsg(p, lang.getCantDimensionalTeleport());
            return;
        }

        p.teleport(loc);
    }

    public String homes(OfflinePlayer p) {
        String saveas = saveAsType();
        String saveasp = p.getName();
        if(isSaveAsTypeUuid()) saveasp = p.getUniqueId().toString();

        String returned = "";
        try {
            if(fm.cfg.contains(homes+"."+saveas+"."+saveasp)) {
                for (String home : fm.cfg.getConfigurationSection(homes + "." + saveas + "." + saveasp).getKeys(false)) {
                    if (returned == "") {
                        returned = home;
                        continue;
                    }
                    returned += ", " + home;
                }
            }else {
                returned = "None";
            }
        }catch (Exception e) {
            returned = "None";
        }

        return returned;
    }

    public List<String> homesW(OfflinePlayer p) {
        String saveas = saveAsType();
        String saveasp = p.getName();
        if(isSaveAsTypeUuid()) saveasp = p.getUniqueId().toString();

        List<String> returned = new ArrayList<>();
        try {
            if(fm.cfg.contains(homes+"."+saveas+"."+saveasp)) {
                returned.addAll(fm.cfg.getConfigurationSection(homes + "." + saveas + "." + saveasp).getKeys(false));
            }
        }catch (Exception e) {
        }

        return returned;
    }

    public int numberOfHomes(OfflinePlayer p) {
        String saveas = saveAsType();
        String saveasp = p.getName();
        if(isSaveAsTypeUuid()) saveasp = p.getUniqueId().toString();

        return fm.cfg.getConfigurationSection(homes+"."+saveas+"."+saveasp).getKeys(false).size();
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

    public String getHomeWorld(OfflinePlayer p, String home) {
        String saveas = saveAsType();
        String saveasp = p.getName();
        if(isSaveAsTypeUuid()) saveasp = p.getUniqueId().toString();

        String locS = fm.cfg.getString(homes+"."+saveas+"."+saveasp+"."+home);
        String[] locP = locS.split(";");

        try {
            return locP[0];
        }catch (Exception e) {
        }
        return null;
    }

}
