package me.yleoft.zHomes.hooks;

import me.yleoft.zAPI.utils.StringUtils;
import me.yleoft.zHomes.Main;
import me.yleoft.zHomes.utils.HomesUtils;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class PlaceholderAPIHandler extends me.yleoft.zAPI.handlers.PlaceholderAPIHandler {

    @Override
    public String applyHookPlaceholders(OfflinePlayer p, String params) {
        HomesUtils hu = Main.hu;
        String[] split = params.split("_");

        switch (params) {
            case "version": {
                return (Main.getInstance()).pluginVer;
            }

            case "numberofhomes":
            case "set": {
                return p == null ? "0" : String.valueOf(hu.numberOfHomes(p));
            }

            case "limit":
            case "max": {
                if(!Main.cfgu.needsLimit()) {
                    return "Infinite";
                }
                return p == null ? "0" : String.valueOf(hu.getMaxLimit(p));
            }

            case "homes": {
                return p == null ? "" : hu.homes(p);
            }

            case "numberofhomes/limit":
            case "set/max": {
                if(!Main.cfgu.needsLimit()) {
                    return "Disabled";
                }
                return p == null ? "" : hu.numberOfHomes(p) + "/" + hu.getMaxLimit(p);
            }
        }

        if(params.startsWith("has_home") || params.startsWith("hashome")) {
            return p == null ? "false" : String.valueOf(hu.hasHome(p, split[split.length-1]));
        }

        if(params.startsWith("home")) {
            if(p == null || split.length < 2 || !StringUtils.isInteger(split[1])) return "";
            int id = Integer.parseInt(split[1]);
            List<String> homes = hu.getHomes(p);
            if(id > homes.size()) return "";
            String home = homes.get(id-1);
            if(home == null) return "";
            if(split.length >= 3) {
                Location loc = hu.getHomeLoc(p, home);
                if(loc == null) return "";
                String format = split.length == 4 ? split[3] : null;
                switch (split[2]) {
                    case "w":
                    case "world": {
                        return Objects.requireNonNull(loc.getWorld()).getName();
                    }
                    case "x": {
                        return getParsedDouble(format, loc.getX());
                    }
                    case "y": {
                        return getParsedDouble(format, loc.getY());
                    }
                    case "z": {
                        return getParsedDouble(format, loc.getZ());
                    }
                    case "pitch": {
                        return getParsedDouble(format, loc.getPitch());
                    }
                    case "yaw": {
                        return getParsedDouble(format, loc.getYaw());
                    }
                }
            }
            return home;
        }

        return "";
    }

    public String getParsedDouble(@Nullable String format, double d) {
        DecimalFormat df = new DecimalFormat("0.###", new DecimalFormatSymbols(Locale.US));
        if(format == null) return df.format(d);
        if (format.equals("full")) {
            return String.valueOf(d);
        }
        if (!StringUtils.isInteger(format)) return df.format(d);
        int decimals = Integer.parseInt(format);
        if (decimals >= 15) decimals = 14;
        if (decimals < 0) decimals = 0;

        StringBuilder pattern = new StringBuilder("0");
        if (decimals > 0) {
            pattern.append(".");
            for (int i = 0; i < decimals; i++) {
                pattern.append("#");
            }
        }
        df = new DecimalFormat(pattern.toString(), new DecimalFormatSymbols(Locale.US));
        return df.format(d);
    }

}
