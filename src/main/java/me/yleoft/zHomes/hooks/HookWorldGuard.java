package me.yleoft.zHomes.hooks;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.association.RegionAssociable;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import com.sk89q.worldguard.session.SessionManager;
import me.yleoft.zAPI.hooks.HookInstance;
import me.yleoft.zHomes.zHomes;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HookWorldGuard implements HookInstance {

    public StateFlag useHomesFlag;
    public StateFlag setHomesFlag;

    private WorldGuardPlugin plugin;

    @Override
    public boolean exists() {
        return plugin != null;
    }

    @Override
    public void preload() {
        Plugin wg = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        if (wg != null) {
            setupFlags();
        }
    }

    @Override
    public void load() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return;
        }
        this.plugin = (WorldGuardPlugin) plugin;
    }

    @Override
    public String preloadMessage() {
        return "<yellow>WorldGuard found, flags loaded!";
    }

    @Override
    public String message() {
        return "<yellow>WorldGuard hooked successfully!";
    }

    public void setupFlags() {
        FlagRegistry registry = getFlagRegistry();
        this.useHomesFlag = registerStateFlag(registry, "use-homes", true);
        this.setHomesFlag = registerStateFlag(registry, "set-homes", true);
    }

    public FlagRegistry getFlagRegistry() {
        return WorldGuard.getInstance().getFlagRegistry();
    }

    private StateFlag registerStateFlag(Object registry, String name, boolean def) {
        try {
            Class<?> stateFlagClass = Class.forName(
                    "com.sk89q.worldguard.protection.flags.StateFlag"
            );
            Class<?> flagClass = Class.forName(
                    "com.sk89q.worldguard.protection.flags.Flag"
            );
            Object flag = stateFlagClass
                    .getConstructor(String.class, boolean.class)
                    .newInstance(name, def);
            Method registerMethod = registry
                    .getClass()
                    .getMethod("register", flagClass);
            registerMethod.invoke(registry, flag);
            return (StateFlag) flag;
        } catch (InvocationTargetException e) {
            try {
                Method getMethod = registry.getClass().getMethod("get", String.class);
                Object existing = getMethod.invoke(registry, name);
                if (Class.forName(
                        "com.sk89q.worldguard.protection.flags.StateFlag"
                ).isInstance(existing)) {
                    return (StateFlag) existing;
                }
                throw new IllegalStateException(
                        "Flag " + name + " already exists and is not a StateFlag"
                );
            } catch (ReflectiveOperationException ex) {
                throw new RuntimeException("Failed to resolve existing flag", ex);
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Reflection failure", e);
        }
    }


    public boolean getFlagStateAtPlayer(Player p, StateFlag flag) {
        if(!exists()) return true;
        try {
            LocalPlayer localPlayer = getLocalPlayer(p);
            SessionManager sessionManager = getSessionManager();
            boolean canBypass = hasBypass(sessionManager, p);
            if (canBypass) return true;
            RegionContainer container = getRegionContainer();
            RegionQuery query = createQuery(container);
            ApplicableRegionSet set = getApplicableRegions(query, p);
            Method testStateMethod = set.getClass().getMethod(
                    "testState",
                    RegionAssociable.class,
                    StateFlag[].class
            );
            return (boolean) testStateMethod.invoke(set, localPlayer, (Object) new StateFlag[]{ flag });
        }catch (Exception e) {
            zHomes.getInstance().getLoggerInstance().debug("Failed to get WorldGuard flag state for player: " + p.getName(), e);
            return true;
        }
    }

    public boolean hasBypass(SessionManager sessionManager, Player player) {
        LocalPlayer localPlayer = getLocalPlayer(player);
        return sessionManager.hasBypass(localPlayer, localPlayer.getWorld());
    }

    public RegionContainer getRegionContainer() {
        return WorldGuard.getInstance().getPlatform().getRegionContainer();
    }

    public RegionQuery createQuery(RegionContainer regionContainer) {
        return regionContainer.createQuery();
    }

    public ApplicableRegionSet getApplicableRegions(RegionQuery query, Player player) {
        LocalPlayer localPlayer = getLocalPlayer(player);
        return query.getApplicableRegions(localPlayer.getLocation());
    }

    public SessionManager getSessionManager() {
        return WorldGuard.getInstance().getPlatform().getSessionManager();
    }

    public LocalPlayer getLocalPlayer(Player player) {
        return WorldGuardPlugin.inst().wrapPlayer(player);
    }


}
