package me.yleoft.zHomes.utils;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import me.yleoft.zHomes.hooks.WorldGuardHook;
import org.bukkit.entity.Player;

import static me.yleoft.zHomes.Main.useWorldGuard;

public abstract class WorldGuardUtils extends WorldGuardHook {

    public static boolean getFlagStateAtPlayer(Player p, StateFlag flag) {
        if(!useWorldGuard) return true;
        try {
            LocalPlayer localPlayer = getWorldGuard().inst().wrapPlayer(p);
            Object sessionManager = getSessionManager();
            if(sessionManager == null) return true;
            boolean canBypass = hasBypass(sessionManager, p);
            if (canBypass) return true;
            Object container = getRegionContainer();
            if(container == null) return true;
            Object query = createQuery(container);
            if(query == null) return true;
            ApplicableRegionSet set = getApplicableRegions(query, p);
            if(set == null) return true;
            return set.testState(localPlayer, flag);
        }catch (Exception e) {
            return true;
        }
    }

}
