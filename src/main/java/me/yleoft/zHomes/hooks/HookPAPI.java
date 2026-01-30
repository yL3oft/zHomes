package me.yleoft.zHomes.hooks;

import me.yleoft.zAPI.handlers.PlaceholdersHandler;
import me.yleoft.zAPI.player.PlayerHandler;
import me.yleoft.zAPI.utility.TextFormatter;
import me.yleoft.zHomes.utility.HomesUtils;
import me.yleoft.zHomes.zHomes;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class HookPAPI implements PlaceholdersHandler {

    @Override
    public @NotNull String getIdentifier() {
        return "zhomes";
    }

    @Override
    public String applyHookPlaceholders(@Nullable OfflinePlayer player, @NotNull String params) {
        boolean addName = false;
        if(params.startsWith("addname_")) {
            addName = true;
            params = params.substring(8);
        }
        String[] split = params.split("_");

        switch (params) {
            case "version": {
                return zHomes.getInstance().getPluginMeta().getVersion();
            }
            case "numberofhomes":
            case "set": {
                return player == null ? "0" : String.valueOf(HomesUtils.numberOfHomes(player));
            }

            case "limit":
            case "max": {
                if(!zHomes.getConfigYAML().isLimitsEnabled()) {
                    return "Infinite";
                }
                return player == null ? "0" : String.valueOf(HomesUtils.getMaxLimit(player));
            }

            case "homes": {
                return player == null ? "" : HomesUtils.homes(player);
            }

            case "numberofhomes/limit":
            case "set/max": {
                if(!zHomes.getConfigYAML().isLimitsEnabled()) {
                    return "Disabled";
                }
                return player == null ? "" : HomesUtils.numberOfHomes(player) + "/" + HomesUtils.getMaxLimit(player);
            }
        }

        if(params.startsWith("has_home") || params.startsWith("hashome")) {
            return player == null ? "false" : String.valueOf(HomesUtils.hasHome(player, split[split.length-1]));
        }

        if(params.startsWith("home")) {
            if(player == null || split.length < 2 || !TextFormatter.isInteger(split[1])) return "";
            int id = Integer.parseInt(split[1]);
            List<String> homes = HomesUtils.getHomes(player);
            if(id > homes.size()) return "";
            String home = homes.get(id-1);
            if(home == null) return "";
            if(split.length >= 3) {
                Location loc = HomesUtils.getHomeLoc(player, home);
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
            return addName ? player.getName() + ":" + home : home;
        }

        if(split.length >= 2) {
            String tName = split[0];
            if(player != null && tName.equals(player.getName())) {
                return applyPlaceholders(player, "%" + getIdentifier() + "_" + params.substring(tName.length() + 1) + "%");
            }
            OfflinePlayer t = PlayerHandler.getOfflinePlayer(tName);
            if(t == null) return "";
            return applyPlaceholders(t, "%" + getIdentifier() + "_addname_" + params.substring(tName.length() + 1) + "%");
        }

        return "";
    }

    public String getParsedDouble(@Nullable String format, double d) {
        DecimalFormat df = new DecimalFormat("0.###", new DecimalFormatSymbols(Locale.US));
        if(format == null) return df.format(d);
        if (format.equals("full")) {
            return String.valueOf(d);
        }
        if (!TextFormatter.isInteger(format)) return df.format(d);
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

    public List<String> getPlaceholderSuggestions(String partial) {
        String withoutPercent = partial.substring(1);

        if(!withoutPercent.startsWith(getIdentifier()) || withoutPercent.equals(getIdentifier())) {
            return List.of("%"+getIdentifier()+"_");
        }

        if(withoutPercent.startsWith(getIdentifier()+"_")) {
            String afterIdentifier = withoutPercent.substring(getIdentifier().length() + 1);
            String[] parts = afterIdentifier.split("_");

            // Check if first part is a player name (not a known placeholder)
            if(parts.length >= 1) {
                String firstPart = parts[0];
                List<String> knownPlaceholders = Arrays.asList("version", "set", "limit", "max",
                        "homes", "has", "hashome", "home");

                // If it's not a known placeholder and not empty, assume it's a player name
                boolean isPlayerName = !knownPlaceholders.contains(firstPart) &&
                        !firstPart.isEmpty() &&
                        !firstPart.startsWith("<");

                if(isPlayerName) {
                    // Remove the player name and process the rest
                    String afterPlayer = parts.length > 1 ?
                            afterIdentifier.substring(firstPart.length() + 1) : "";
                    String[] playerParts = afterPlayer.isEmpty() ? new String[0] : afterPlayer.split("_");

                    // Check if they're typing home_<id>_<coordinate> for a player
                    if(playerParts.length >= 2 && playerParts[0].equals("home") && TextFormatter.isInteger(playerParts[1])) {
                        if(playerParts.length == 2 || (playerParts.length == 3 && !playerParts[2].isEmpty())) {
                            String currentPart = playerParts.length == 3 ? playerParts[2] : "";
                            List<String> coordinateOptions = Arrays.asList("world", "x", "y", "z", "pitch", "yaw");

                            List<String> suggestions = coordinateOptions.stream()
                                    .filter(opt -> opt.startsWith(currentPart))
                                    .map(opt -> "%"+getIdentifier()+"_"+firstPart+"_home_" + playerParts[1] + "_" + opt + "%")
                                    .toList();

                            return suggestions.isEmpty() ? List.of() : suggestions;
                        }

                        if(playerParts.length == 4 && (playerParts[2].equals("x") || playerParts[2].equals("y") ||
                                playerParts[2].equals("z") || playerParts[2].equals("pitch") || playerParts[2].equals("yaw"))) {
                            List<String> formatOptions = Arrays.asList("0", "1", "2", "3", "full");
                            return formatOptions.stream()
                                    .map(fmt -> "%"+getIdentifier()+"_"+firstPart+"_home_" + playerParts[1] + "_" + playerParts[2] + "_" + fmt + "%")
                                    .toList();
                        }

                        if(playerParts.length >= 5) {
                            return List.of();
                        }
                    }

                    // Suggest player-specific placeholders
                    List<String> playerPlaceholders = Arrays.asList(
                            "version",
                            "set",
                            "limit",
                            "max",
                            "homes",
                            "set/max",
                            "has_home_<name>",
                            "hashome_<name>",
                            "home_<id>",
                            "home_<id>_world",
                            "home_<id>_x",
                            "home_<id>_y",
                            "home_<id>_z",
                            "home_<id>_pitch",
                            "home_<id>_yaw",
                            "home_<id>_x_<format>",
                            "home_<id>_y_<format>",
                            "home_<id>_z_<format>"
                    );

                    List<String> suggestions = playerPlaceholders.stream()
                            .filter(p -> p.startsWith(afterPlayer))
                            .map(p -> "%"+getIdentifier()+"_" + firstPart + "_" + p + "%")
                            .toList();

                    return suggestions.isEmpty() ? List.of() : suggestions;
                }
            }

            // Check if we're typing home_<id>_<coordinate> (without player prefix)
            if(parts.length >= 2 && parts[0].equals("home") && TextFormatter.isInteger(parts[1])) {
                if(parts.length == 2 || (parts.length == 3 && !parts[2].isEmpty())) {
                    String currentPart = parts.length == 3 ? parts[2] : "";
                    List<String> coordinateOptions = Arrays.asList("world", "x", "y", "z", "pitch", "yaw");

                    List<String> suggestions = coordinateOptions.stream()
                            .filter(opt -> opt.startsWith(currentPart))
                            .map(opt -> "%"+getIdentifier()+"_"+parts[0]+"_" + parts[1] + "_" + opt + "%")
                            .toList();

                    return suggestions.isEmpty() ? List.of() : suggestions;
                }

                if(parts.length == 4 && (parts[2].equals("x") || parts[2].equals("y") ||
                        parts[2].equals("z") || parts[2].equals("pitch") || parts[2].equals("yaw"))) {
                    List<String> formatOptions = Arrays.asList("0", "1", "2", "3", "full");
                    return formatOptions.stream()
                            .map(fmt -> "%"+getIdentifier()+"_"+parts[0]+"_" + parts[1] + "_" + parts[2] + "_" + fmt + "%")
                            .toList();
                }

                if(parts.length >= 5) {
                    return List.of();
                }
            }

            // Base placeholder options (for when they just typed %zhomes_)
            List<String> placeholders = Arrays.asList(
                    "version",
                    "set",
                    "limit",
                    "max",
                    "homes",
                    "set/max",
                    "has_home_<name>",
                    "hashome_<name>",
                    "home_<id>",
                    "home_<id>_world",
                    "home_<id>_x",
                    "home_<id>_y",
                    "home_<id>_z",
                    "home_<id>_pitch",
                    "home_<id>_yaw",
                    "home_<id>_x_<format>",
                    "home_<id>_y_<format>",
                    "home_<id>_z_<format>",
                    "<player>_<placeholder>"
            );

            List<String> suggestions = placeholders.stream()
                    .filter(p -> p.startsWith(afterIdentifier))
                    .map(p -> "%"+getIdentifier()+"_" + p + "%")
                    .toList();

            return suggestions.isEmpty() ? List.of() : suggestions;
        }

        return List.of();
    }

}