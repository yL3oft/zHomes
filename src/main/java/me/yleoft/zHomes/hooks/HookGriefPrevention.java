package me.yleoft.zHomes.hooks;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.yleoft.zAPI.hooks.HookInstance;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class HookGriefPrevention implements HookInstance {

    private GriefPrevention griefPrevention;

    @Override
    public boolean exists() {
        return griefPrevention != null;
    }

    @Override
    public void load() {
        if (!Bukkit.getServer().getPluginManager().isPluginEnabled("GriefPrevention")) return;
        griefPrevention = GriefPrevention.instance;
    }

    @Override
    public String message() {
        return "<yellow>GriefPrevention hooked successfully.";
    }

    public boolean hasAccessTrust(Player player) {
        if(!exists()) return true;
        Claim claim = griefPrevention.dataStore.getClaimAt(player.getLocation(), false, null);
        if (claim == null) {
            return true;
        }
        return claim.allowAccess(player) == null;
    }

}
