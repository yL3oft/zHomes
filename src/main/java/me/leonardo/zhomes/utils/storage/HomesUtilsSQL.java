package me.leonardo.zhomes.utils.storage;

import me.leonardo.zhomes.FileManager;
import me.leonardo.zhomes.Main;
import me.leonardo.zhomes.api.events.TeleportToHomeEvent;
import me.leonardo.zhomes.mysql.DatabaseEditor;
import me.leonardo.zhomes.utils.ConfigUtils;
import me.leonardo.zhomes.utils.LanguageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class HomesUtilsSQL extends DatabaseEditor {

    Main main = Main.main;
    FileManager fm = Main.fm;
    String homes = "homes";
    String limits = "limits";

    public boolean hasHome(OfflinePlayer p, String home) {
        return hasHomeSQL(p, home);
    }

    public boolean inSameWorld(String w, Player p) {
        String w2 = p.getWorld().getName();
        return w.equals(w2);
    }

    public boolean inMaxLimit(Player p) {
        if(needsLimit()) {
            return getLimit(p) >= getMaxLimit(p);
        }

        return false;
    }

    public int getLimit(OfflinePlayer p) {
        return getHomes(p).size();
    }

    public void addHome(OfflinePlayer p, String home, Location loc) {
        setHome(p, home, main.serializeDelDot(loc));
    }

    public void delHome(OfflinePlayer p, String home) {
        delHome(p, home);
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
            LanguageUtils.Home lang = new LanguageUtils.Home();
            lang.sendMsg(p, lang.getCantDimensionalTeleport());
            return;
        }

        p.teleport(loc);
    }

    public void teleportPlayer(Player p, OfflinePlayer t, String home) {
        Location loc = getHomeLoc(t, home);
        boolean isDimensionalTeleport = false;
        if(!inSameWorld(getHomeWorld(t, home), p)) {
            isDimensionalTeleport = true;
        }
        TeleportToHomeEvent event = new TeleportToHomeEvent(p, home, p.getLocation(), loc, isDimensionalTeleport, false, t);
        Bukkit.getPluginManager().callEvent(event);
        if(event.isCancelled()) return;

        if(isDimensionalTeleport && !canDimensionalTeleport()) {
            LanguageUtils.Home lang = new LanguageUtils.Home();
            lang.sendMsg(p, lang.getCantDimensionalTeleport());
            return;
        }

        p.teleport(loc);
    }

    public String homes(OfflinePlayer p) {
        String returned = "";
        try {
            List<String> homes = getHomes(p);
            if(!homes.isEmpty()) {
                for (String home : homes) {
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
        return getHomes(p);
    }

    public int numberOfHomes(OfflinePlayer p) {
        return getHomes(p).size();
    }

    public Location getHomeLoc(OfflinePlayer p, String home) {
        String locS = getHomeLocation(p, home);

        try {
            return main.deserializeAddDot(locS);
        }catch (Exception e) {
        }
        return null;
    }

    public String getHomeWorld(OfflinePlayer p, String home) {
        String locS = getHomeLocation(p, home);
        String[] locP = locS.split(";");

        try {
            return locP[0];
        }catch (Exception e) {
        }
        return null;
    }

}
