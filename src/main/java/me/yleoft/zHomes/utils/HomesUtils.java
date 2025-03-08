package me.yleoft.zHomes.utils;

import java.util.ArrayList;
import java.util.List;
import me.yleoft.zHomes.Main;
import me.yleoft.zHomes.api.events.TeleportToHomeEvent;
import me.yleoft.zHomes.storage.DatabaseEditor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class HomesUtils extends DatabaseEditor {

    Main main = Main.getInstance();

    public boolean hasHome(OfflinePlayer p, String home) {
        return isInTable(p, home);
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
        setHome(p, home, main.serialize(loc));
    }

    public void delHome(OfflinePlayer p, String home) {
        deleteHome(p, home);
    }

    public boolean canDimensionalTeleport(OfflinePlayer p) {
        if (p.isOnline()) {
            Player pl = (Player)p;
            if (pl.hasPermission(PermissionBypassDT()))
                return true;
        }
        return canDimensionalTeleport();
    }

    public void teleportPlayer(Player p, String home) {
        Location loc = getHomeLoc((OfflinePlayer)p, home);
        boolean isDimensionalTeleport = false;
        if (!inSameWorld(getHomeWorld((OfflinePlayer)p, home), p))
            isDimensionalTeleport = true;
        TeleportToHomeEvent event = new TeleportToHomeEvent(p, home, p.getLocation(), loc, isDimensionalTeleport);
        Bukkit.getPluginManager().callEvent((Event)event);
        if (event.isCancelled())
            return;
        if (isDimensionalTeleport && !canDimensionalTeleport(p)) {
            LanguageUtils.Home lang = new LanguageUtils.Home();
            lang.sendMsg(p, lang.getCantDimensionalTeleport());
            return;
        }
        p.teleport(loc);
    }

    public void teleportPlayer(Player p, OfflinePlayer t, String home) {
        Location loc = getHomeLoc(t, home);
        boolean isDimensionalTeleport = false;
        if (!inSameWorld(getHomeWorld(t, home), p))
            isDimensionalTeleport = true;

        TeleportToHomeEvent event = new TeleportToHomeEvent(p, home, p.getLocation(), loc, isDimensionalTeleport, false, t);
        Bukkit.getPluginManager().callEvent((Event)event);
        if (event.isCancelled())
            return;

        if (isDimensionalTeleport && !canDimensionalTeleport(p)) {
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
                    if (returned.isEmpty()) {
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

    public List<String> homesWDD(OfflinePlayer p) {
        List<String> list = new ArrayList<>();
        getHomes(p).forEach(home -> list.add(p.getName()+":"+home));
        return list;
    }

    public int numberOfHomes(OfflinePlayer p) {
        return getHomes(p).size();
    }

    public Location getHomeLoc(OfflinePlayer p, String home) {
        String locS = getHome(p, home);
        return this.main.deserialize(locS);
    }

    public String getHomeWorld(OfflinePlayer p, String home) {
        String locS = getHome(p, home);
        String[] locP = locS.split(";");
        try {
            return locP[0];
        } catch (Exception exception) {
            return null;
        }
    }
}
