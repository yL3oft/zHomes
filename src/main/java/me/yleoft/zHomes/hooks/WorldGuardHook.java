package me.yleoft.zHomes.hooks;

import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.yleoft.zHomes.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Method;

import static me.yleoft.zHomes.Main.isWG7;

public class WorldGuardHook {

    public static void setupFlags() {
        FlagRegistry registry = getFlagRegistry();
        if(registry == null) return;
        // use-homes flag
        try {
            StateFlag useHomesFlag = new StateFlag("use-homes", true);
            registry.register(useHomesFlag);
            Main.useHomesFlag = useHomesFlag;
        } catch (FlagConflictException | IllegalStateException e) {
            Flag<?> existinguseHomesFlag = registry.get("use-homes");
            if (existinguseHomesFlag instanceof StateFlag) {
                Main.useHomesFlag = (StateFlag) existinguseHomesFlag;
            }
        }
        // set-homes flag
        try {
            StateFlag setHomesFlag = new StateFlag("set-homes", true);
            registry.register(setHomesFlag);
            Main.setHomesFlag = setHomesFlag;
        } catch (FlagConflictException | IllegalStateException e) {
            Flag<?> existingsetHomesFlag = registry.get("set-homes");
            if (existingsetHomesFlag instanceof StateFlag) {
                Main.setHomesFlag = (StateFlag) existingsetHomesFlag;
            }
        }
    }

    public static boolean hasBypass(Object sessionManager, Player player) {
        try {
            if (isWG7) {
                LocalPlayer localPlayer = getWorldGuard().inst().wrapPlayer(player);
                Method method = sessionManager.getClass().getDeclaredMethod("hasBypass", LocalPlayer.class, World.class);
                method.setAccessible(true);
                return (boolean) method.invoke(sessionManager, localPlayer, localPlayer.getWorld());
            }else {
                Method method = sessionManager.getClass().getDeclaredMethod("hasBypass", Player.class, org.bukkit.World.class);
                method.setAccessible(true);
                return (boolean) method.invoke(sessionManager, player, player.getWorld());
            }
        }catch (Exception e) {
            Main.getInstance().getLogger().severe("Unable to access WorldGuard region container! Disabling WorldGuard hook.");
            e.printStackTrace();
            Main.useWorldGuard = false;
            return false;
        }
    }

    public static Object getRegionContainer() {
        if(isWG7) {
            return WorldGuard.getInstance().getPlatform().getRegionContainer();
        }else {
            try {
                WorldGuardPlugin wg = getWorldGuard();
                Method method = wg.getClass().getDeclaredMethod("getRegionContainer");
                method.setAccessible(true);
                return method.invoke(wg);
            }catch(Exception e) {
                Main.getInstance().getLogger().severe("Unable to access WorldGuard region container! Disabling WorldGuard hook.");
                e.printStackTrace();
                Main.useWorldGuard = false;
                return null;
            }
        }
    }

    public static Object createQuery(Object regionContainer) {
        if(isWG7) {
            RegionContainer container = (RegionContainer) regionContainer;
            return container.createQuery();
        }else {
            try {
                Method method = regionContainer.getClass().getDeclaredMethod("createQuery");
                method.setAccessible(true);
                return method.invoke(regionContainer);
            } catch (Exception e) {
                Main.getInstance().getLogger().severe("Unable to create query in WorldGuard with region container! Disabling WorldGuard hook.");
                e.printStackTrace();
                Main.useWorldGuard = false;
                return null;
            }
        }
    }

    public static ApplicableRegionSet getApplicableRegions(Object query, Player player) {
        try {
            if(isWG7) {
                LocalPlayer localPlayer = getWorldGuard().inst().wrapPlayer(player);
                Method method = query.getClass().getDeclaredMethod("getApplicableRegions", Location.class);
                method.setAccessible(true);
                return (ApplicableRegionSet) method.invoke(query, localPlayer.getLocation());
            }else {
                Method method = query.getClass().getDeclaredMethod("getApplicableRegions", org.bukkit.Location.class);
                method.setAccessible(true);
                return (ApplicableRegionSet) method.invoke(query, player.getLocation());
            }
        }catch(Exception e) {
            Main.getInstance().getLogger().severe("Unable to access WorldGuard applicable regions! Disabling WorldGuard hook.");
            e.printStackTrace();
            Main.useWorldGuard = false;
            return null;
        }
    }

    public static Object getSessionManager() {
        if(isWG7) {
            return WorldGuard.getInstance().getPlatform().getSessionManager();
        }else {
            try {
                WorldGuardPlugin wg = getWorldGuard();
                Method method = wg.getClass().getDeclaredMethod("getSessionManager");
                method.setAccessible(true);
                return method.invoke(wg);
            }catch(Exception e) {
                Main.getInstance().getLogger().severe("Unable to access WorldGuard session manager! Disabling WorldGuard hook.");
                e.printStackTrace();
                Main.useWorldGuard = false;
                return null;
            }
        }
    }

    public static FlagRegistry getFlagRegistry() {
        if(isWG7) {
            return WorldGuard.getInstance().getFlagRegistry();
        }else {
            try {
                WorldGuardPlugin wg = getWorldGuard();
                Method method = wg.getClass().getDeclaredMethod("getFlagRegistry");
                method.setAccessible(true);
                return (FlagRegistry) method.invoke(wg);
            }catch(Exception e) {
                Main.getInstance().getLogger().severe("Unable to access WorldGuard flag registry! Disabling WorldGuard hook.");
                e.printStackTrace();
                Main.useWorldGuard = false;
                return null;
            }
        }
    }

    public static WorldGuardPlugin getWorldGuard() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }
        return (WorldGuardPlugin) plugin;
    }

}
