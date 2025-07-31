package me.yleoft.zHomes.utils;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.entity.Player;

public class GriefPreventionUtils {

    public boolean hasAccessTrust(Player p) {
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(p.getLocation(), false, null);
        if (claim == null) {
            return true;
        }
        return claim.allowAccess(p) == null;
    }

}
