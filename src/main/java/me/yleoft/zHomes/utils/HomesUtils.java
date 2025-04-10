package me.yleoft.zHomes.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import me.yleoft.zHomes.Main;
import com.zhomes.api.event.player.TeleportToHomeEvent;
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
        return !w.equals(w2);
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
        Location loc = getHomeLoc(p, home);
        boolean isDimensionalTeleport = inSameWorld(getHomeWorld(p, home), p);
        TeleportToHomeEvent event = new TeleportToHomeEvent(p, home, p.getLocation(), loc, isDimensionalTeleport);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        if (isDimensionalTeleport && !canDimensionalTeleport(p)) {
            LanguageUtils.Home lang = new LanguageUtils.Home();
            lang.sendMsg(p, lang.getCantDimensionalTeleport());
            return;
        }
        if(Main.getInstance().getServer().getName().contains("Folia")) {
            try {
                Method teleportAsyncMethod = Player.class.getMethod("teleportAsync", Location.class);
                teleportAsyncMethod.invoke(p, loc);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("Unable to teleport player to home", e);
            }
            return;
        }
        p.teleport(loc);
    }

    public void teleportPlayer(Player p, OfflinePlayer t, String home) {
        Location loc = getHomeLoc(t, home);
        boolean isDimensionalTeleport = inSameWorld(getHomeWorld(t, home), p);
        TeleportToHomeEvent event = new TeleportToHomeEvent(p, home, p.getLocation(), loc, isDimensionalTeleport, false, t);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        if (isDimensionalTeleport && !canDimensionalTeleport(p)) {
            LanguageUtils.Home lang = new LanguageUtils.Home();
            lang.sendMsg(p, lang.getCantDimensionalTeleport());
            return;
        }
        if(Main.getInstance().getServer().getName().contains("Folia")) {
            try {
                Method teleportAsyncMethod = Player.class.getMethod("teleportAsync", Location.class);
                teleportAsyncMethod.invoke(p, loc);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("Unable to teleport player to home", e);
            }
            return;
        }
        p.teleport(loc);
    }

    public String homes(OfflinePlayer p) {
        StringBuilder returned = new StringBuilder();
        try {
            List<String> homes = getHomes(p);
            if(!homes.isEmpty()) {
                for (String home : homes) {
                    if (returned.length() == 0) {
                        returned = new StringBuilder(home);
                        continue;
                    }
                    returned.append(", ").append(home);
                }
            }else {
                returned = new StringBuilder("None");
            }
        }catch (Exception e) {
            returned = new StringBuilder("None");
        }

        return returned.toString();
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
