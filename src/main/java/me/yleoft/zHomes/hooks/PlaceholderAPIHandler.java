package me.yleoft.zHomes.hooks;

import me.yleoft.zAPI.zAPI;
import me.yleoft.zHomes.Main;
import me.yleoft.zHomes.utils.HomesUtils;
import org.bukkit.OfflinePlayer;

public class PlaceholderAPIHandler extends me.yleoft.zAPI.handlers.PlaceholderAPIHandler {

    @Override
    public String applyHookPlaceholders(OfflinePlayer p, String params) {
        HomesUtils hu = Main.hu;
        String[] split = params.split("_");

        switch (split[0]) {
            case "version":
                return (Main.getInstance()).pluginVer;
            case "hashome":
                return p == null ? "" : String.valueOf(hu.hasHome(p, split[1]));
            case "numberofhomes":
                return p == null ? "" : String.valueOf(hu.numberOfHomes(p));
            case "homes":
                return p == null ? "" : hu.homes(p);
            case "limit": {
                if(!Main.cfgu.needsLimit()) {
                    return "Infinite";
                }
                return p == null ? "" : String.valueOf(hu.getMaxLimit(p));
            }
            case "numberofhomes/limit": {
                if(!Main.cfgu.needsLimit()) {
                    return "Disabled";
                }
                return p == null ? "" : hu.numberOfHomes(p) + "/" + hu.getMaxLimit(p);
            }
        }
        return "";
    }

}
