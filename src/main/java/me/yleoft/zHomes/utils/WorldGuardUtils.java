package me.yleoft.zHomes.utils;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.entity.Player;

import static me.yleoft.zHomes.Main.useWorldGuard;

public abstract class WorldGuardUtils {

    public static boolean getFlagStateAtPlayer(Player p, StateFlag flag) {
        if(!useWorldGuard) return true;
        try {
            LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(p);
            boolean canBypass = WorldGuard.getInstance().getPlatform().getSessionManager().hasBypass(localPlayer, localPlayer.getWorld());
            if (canBypass) return true;
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionQuery query = container.createQuery();
            ApplicableRegionSet set = query.getApplicableRegions(localPlayer.getLocation());
            return set.testState(localPlayer, flag);
        }catch (Exception e) {
            return true;
        }
    }

}
