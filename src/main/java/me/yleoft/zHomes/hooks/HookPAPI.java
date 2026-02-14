package me.yleoft.zHomes.hooks;

import me.yleoft.zAPI.handlers.PlaceholderDefinition;
import me.yleoft.zAPI.handlers.PlaceholderType;
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
import java.util.*;

public class HookPAPI implements PlaceholdersHandler {

    @Override
    public @NotNull String getIdentifier() {
        return "zhomes";
    }

    // ──────────────────────────────────────────────
    //  MiniPlaceholders registration definitions
    //
    //  Types:
    //    GLOBAL           → no player needed, registered as globalPlaceholder
    //    AUDIENCE         → viewing player is the target
    //    PLAYER_TARGETED  → first Mini arg is the target player name,
    //                       falls back to viewing player if omitted
    //
    //  PAPI side:
    //    AUDIENCE         → %zhomes_<key>_<args>%
    //    PLAYER_TARGETED  → %zhomes_<player>_<key>_<args>%
    //    GLOBAL           → %zhomes_<key>%
    //
    //  Mini side:
    //    AUDIENCE         → <zhomes_<key>:<args>>
    //    PLAYER_TARGETED  → <zhomes_<key>:<player>:<args>>  (or <zhomes_<key>:<args>> to use viewing player)
    //    GLOBAL           → <zhomes_<key>>
    // ──────────────────────────────────────────────
    @Override
    public @NotNull List<PlaceholderDefinition> getPlaceholders() {
        return List.of(
                // ─── GLOBAL (no player needed) ───

                // PAPI: %zhomes_version%
                // Mini: <zhomes_version>
                new PlaceholderDefinition("version", 0, PlaceholderType.GLOBAL),

                // ─── AUDIENCE (viewing player) ───

                // PAPI: %zhomes_set%  /  %zhomes_numberofhomes%
                // Mini: <zhomes_set>  /  <zhomes_numberofhomes>
                new PlaceholderDefinition("set", 0, PlaceholderType.AUDIENCE),
                new PlaceholderDefinition("numberofhomes", 0, PlaceholderType.AUDIENCE),

                // PAPI: %zhomes_limit%  /  %zhomes_max%
                // Mini: <zhomes_limit>  /  <zhomes_max>
                new PlaceholderDefinition("limit", 0, PlaceholderType.AUDIENCE),
                new PlaceholderDefinition("max", 0, PlaceholderType.AUDIENCE),

                // PAPI: %zhomes_homes%
                // Mini: <zhomes_homes>
                new PlaceholderDefinition("homes", 0, PlaceholderType.AUDIENCE),

                // PAPI: %zhomes_set/max%  (handled in applyHookPlaceholders)
                // Mini: <zhomes_set_max>
                new PlaceholderDefinition("set_max", 0, PlaceholderType.AUDIENCE),
                new PlaceholderDefinition("numberofhomes_limit", 0, PlaceholderType.AUDIENCE),

                // PAPI: %zhomes_has_home_<name>%  /  %zhomes_hashome_<name>%
                // Mini: <zhomes_has_home:mybase>
                new PlaceholderDefinition("has_home", 1, PlaceholderType.AUDIENCE),

                // PAPI: %zhomes_home_<id>%
                // Mini: <zhomes_home:1>
                new PlaceholderDefinition("home", 1, PlaceholderType.AUDIENCE),

                // PAPI: %zhomes_home_<id>_world%
                // Mini: <zhomes_home_world:1>
                new PlaceholderDefinition("home_world", 1, PlaceholderType.AUDIENCE),

                // PAPI: %zhomes_home_<id>_x%  /  %zhomes_home_<id>_x_<format>%
                // Mini: <zhomes_home_x:1>     /  <zhomes_home_x:1:2>
                new PlaceholderDefinition("home_x", 2, PlaceholderType.AUDIENCE),
                new PlaceholderDefinition("home_y", 2, PlaceholderType.AUDIENCE),
                new PlaceholderDefinition("home_z", 2, PlaceholderType.AUDIENCE),
                new PlaceholderDefinition("home_pitch", 2, PlaceholderType.AUDIENCE),
                new PlaceholderDefinition("home_yaw", 2, PlaceholderType.AUDIENCE),

                // ─── PLAYER_TARGETED (first arg = target player) ─��─
                // These mirror all AUDIENCE placeholders but allow targeting another player.
                //
                // PAPI: %zhomes_Steve_set%            → player "Steve", key "set"
                // Mini: <zhomes_p_set:Steve>           → player "Steve", key "set"
                //
                // PAPI: %zhomes_Steve_home_1%          → player "Steve", home #1
                // Mini: <zhomes_p_home:Steve:1>        → player "Steve", home #1
                //
                // PAPI: %zhomes_Steve_home_1_x_2%      → player "Steve", home #1, x, format 2
                // Mini: <zhomes_p_home_x:Steve:1:2>    → player "Steve", home #1, x, format 2

                new PlaceholderDefinition("p_set", 0, PlaceholderType.PLAYER_TARGETED),
                new PlaceholderDefinition("p_numberofhomes", 0, PlaceholderType.PLAYER_TARGETED),
                new PlaceholderDefinition("p_limit", 0, PlaceholderType.PLAYER_TARGETED),
                new PlaceholderDefinition("p_max", 0, PlaceholderType.PLAYER_TARGETED),
                new PlaceholderDefinition("p_homes", 0, PlaceholderType.PLAYER_TARGETED),
                new PlaceholderDefinition("p_set_max", 0, PlaceholderType.PLAYER_TARGETED),
                new PlaceholderDefinition("p_numberofhomes_limit", 0, PlaceholderType.PLAYER_TARGETED),
                new PlaceholderDefinition("p_has_home", 1, PlaceholderType.PLAYER_TARGETED),
                new PlaceholderDefinition("p_home", 1, PlaceholderType.PLAYER_TARGETED),
                new PlaceholderDefinition("p_home_world", 1, PlaceholderType.PLAYER_TARGETED),
                new PlaceholderDefinition("p_home_x", 2, PlaceholderType.PLAYER_TARGETED),
                new PlaceholderDefinition("p_home_y", 2, PlaceholderType.PLAYER_TARGETED),
                new PlaceholderDefinition("p_home_z", 2, PlaceholderType.PLAYER_TARGETED),
                new PlaceholderDefinition("p_home_pitch", 2, PlaceholderType.PLAYER_TARGETED),
                new PlaceholderDefinition("p_home_yaw", 2, PlaceholderType.PLAYER_TARGETED)
        );
    }

    // ──────────────────────────────────────────────
    //  MiniPlaceholders entry point
    //
    //  Called for both AUDIENCE and PLAYER_TARGETED.
    //  For PLAYER_TARGETED keys (prefixed with "p_"), the player
    //  has already been resolved by HookMiniPlaceholders from the
    //  first colon-separated argument.
    // ──────────────────────────────────────────────
    @Override
    public @Nullable String onPlaceholderRequest(@Nullable OfflinePlayer player, @NotNull String key, @NotNull List<String> parameters) {
        // Strip "p_" prefix for player-targeted keys → route to same logic
        String resolvedKey = key.startsWith("p_") ? key.substring(2) : key;

        switch (resolvedKey) {
            case "version":
                return zHomes.getInstance().getPluginMeta().getVersion();

            case "numberofhomes":
            case "set":
                return player == null ? "0" : String.valueOf(HomesUtils.numberOfHomes(player));

            case "limit":
            case "max": {
                if (!zHomes.getConfigYAML().isLimitsEnabled()) return "Infinite";
                return player == null ? "0" : String.valueOf(HomesUtils.getMaxLimit(player));
            }

            case "homes":
                return player == null ? "" : HomesUtils.homes(player);

            case "set_max":
            case "numberofhomes_limit": {
                if (!zHomes.getConfigYAML().isLimitsEnabled()) return "Disabled";
                return player == null ? "" : HomesUtils.numberOfHomes(player) + "/" + HomesUtils.getMaxLimit(player);
            }

            case "has_home": {
                if (player == null || parameters.isEmpty()) return "false";
                return String.valueOf(HomesUtils.hasHome(player, parameters.get(0)));
            }

            case "home": {
                if (player == null || parameters.isEmpty()) return "";
                return getHomeNameByIndex(player, parameters.get(0));
            }

            case "home_world":
            case "home_x":
            case "home_y":
            case "home_z":
            case "home_pitch":
            case "home_yaw": {
                if (player == null || parameters.isEmpty()) return "";
                String coordinate = resolvedKey.substring("home_".length());
                String id = parameters.get(0);
                String format = parameters.size() > 1 ? parameters.get(1) : null;
                return getHomeCoordinate(player, id, coordinate, format);
            }

            default:
                return null;
        }
    }

    // ──────────────────────────────────────────────
    //  PAPI entry point — handles the complex raw param parsing
    //  (player prefixes, addname_ flag, variable-depth structure)
    //
    //  Supports:
    //    %zhomes_version%
    //    %zhomes_set%  %zhomes_numberofhomes%
    //    %zhomes_limit%  %zhomes_max%
    //    %zhomes_homes%
    //    %zhomes_set/max%  %zhomes_numberofhomes/limit%
    //    %zhomes_has_home_<name>%  %zhomes_hashome_<name>%
    //    %zhomes_home_<id>%
    //    %zhomes_home_<id>_<coord>%
    //    %zhomes_home_<id>_<coord>_<format>%
    //    %zhomes_<player>_<any of the above>%
    // ──────────────────────────────────────────────
    @Override
    public @Nullable String applyHookPlaceholders(@Nullable OfflinePlayer player, @NotNull String params) {
        boolean addName = false;
        if (params.startsWith("addname_")) {
            addName = true;
            params = params.substring(8);
        }
        String[] split = params.split("_");

        // Simple key matches
        switch (params) {
            case "version":
                return onPlaceholderRequest(player, "version", Collections.emptyList());
            case "numberofhomes":
            case "set":
                return onPlaceholderRequest(player, params, Collections.emptyList());
            case "limit":
            case "max":
                return onPlaceholderRequest(player, params, Collections.emptyList());
            case "homes":
                return onPlaceholderRequest(player, "homes", Collections.emptyList());
            case "numberofhomes/limit":
                return onPlaceholderRequest(player, "numberofhomes_limit", Collections.emptyList());
            case "set/max":
                return onPlaceholderRequest(player, "set_max", Collections.emptyList());
        }

        // %zhomes_has_home_<name>% or %zhomes_hashome_<name>%
        if (params.startsWith("has_home") || params.startsWith("hashome")) {
            String homeName = split[split.length - 1];
            return onPlaceholderRequest(player, "has_home", List.of(homeName));
        }

        // %zhomes_home_<id>% / %zhomes_home_<id>_<coord>% / %zhomes_home_<id>_<coord>_<format>%
        if (params.startsWith("home")) {
            if (player == null || split.length < 2 || !TextFormatter.isInteger(split[1])) return "";
            String id = split[1];

            if (split.length >= 3) {
                String coordinate = split[2];
                String format = split.length == 4 ? split[3] : null;
                String key = "home_" + coordinate;
                List<String> args = format != null ? List.of(id, format) : List.of(id);
                String result = onPlaceholderRequest(player, key, args);
                return result != null ? result : "";
            }

            String homeName = getHomeNameByIndex(player, id);
            if (homeName == null || homeName.isEmpty()) return "";
            return addName ? player.getName() + ":" + homeName : homeName;
        }

        // Player prefix: %zhomes_<player>_<placeholder>%
        if (split.length >= 2) {
            String tName = split[0];
            if (player != null && tName.equals(player.getName())) {
                return applyPlaceholders(player, "%" + getIdentifier() + "_" + params.substring(tName.length() + 1) + "%");
            }
            OfflinePlayer t = PlayerHandler.getOfflinePlayer(tName);
            if (t == null) return "";
            return applyPlaceholders(t, "%" + getIdentifier() + "_addname_" + params.substring(tName.length() + 1) + "%");
        }

        return "";
    }

    // ──────────────────────────────────────────────
    //  Shared helpers
    // ──────────────────────────────────────────────

    private @Nullable String getHomeNameByIndex(@NotNull OfflinePlayer player, @NotNull String idStr) {
        if (!TextFormatter.isInteger(idStr)) return null;
        int id = Integer.parseInt(idStr);
        List<String> homes = HomesUtils.getHomes(player);
        if (id > homes.size() || id < 1) return null;
        return homes.get(id - 1);
    }

    private @Nullable String getHomeCoordinate(@NotNull OfflinePlayer player, @NotNull String idStr,
                                               @NotNull String coordinate, @Nullable String format) {
        String homeName = getHomeNameByIndex(player, idStr);
        if (homeName == null) return null;

        Location loc = HomesUtils.getHomeLoc(player, homeName);
        if (loc == null) return null;

        return switch (coordinate) {
            case "w", "world" -> Objects.requireNonNull(loc.getWorld()).getName();
            case "x" -> getParsedDouble(format, loc.getX());
            case "y" -> getParsedDouble(format, loc.getY());
            case "z" -> getParsedDouble(format, loc.getZ());
            case "pitch" -> getParsedDouble(format, loc.getPitch());
            case "yaw" -> getParsedDouble(format, loc.getYaw());
            default -> null;
        };
    }

    public String getParsedDouble(@Nullable String format, double d) {
        DecimalFormat df = new DecimalFormat("0.###", new DecimalFormatSymbols(Locale.US));
        if (format == null) return df.format(d);
        if (format.equals("full")) return String.valueOf(d);
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

            if(parts.length >= 1) {
                String firstPart = parts[0];
                List<String> knownPlaceholders = Arrays.asList("version", "set", "limit", "max",
                        "homes", "has", "hashome", "home");

                boolean isPlayerName = !knownPlaceholders.contains(firstPart) &&
                        !firstPart.isEmpty() &&
                        !firstPart.startsWith("<");

                if(isPlayerName) {
                    String afterPlayer = parts.length > 1 ?
                            afterIdentifier.substring(firstPart.length() + 1) : "";
                    String[] playerParts = afterPlayer.isEmpty() ? new String[0] : afterPlayer.split("_");

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

                    List<String> playerPlaceholders = Arrays.asList(
                            "version", "set", "limit", "max", "homes", "set/max",
                            "has_home_<name>", "hashome_<name>",
                            "home_<id>", "home_<id>_world",
                            "home_<id>_x", "home_<id>_y", "home_<id>_z",
                            "home_<id>_pitch", "home_<id>_yaw",
                            "home_<id>_x_<format>", "home_<id>_y_<format>", "home_<id>_z_<format>"
                    );

                    List<String> suggestions = playerPlaceholders.stream()
                            .filter(p -> p.startsWith(afterPlayer))
                            .map(p -> "%"+getIdentifier()+"_" + firstPart + "_" + p + "%")
                            .toList();

                    return suggestions.isEmpty() ? List.of() : suggestions;
                }
            }

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

            List<String> placeholders = Arrays.asList(
                    "version", "set", "limit", "max", "homes", "set/max",
                    "has_home_<name>", "hashome_<name>",
                    "home_<id>", "home_<id>_world",
                    "home_<id>_x", "home_<id>_y", "home_<id>_z",
                    "home_<id>_pitch", "home_<id>_yaw",
                    "home_<id>_x_<format>", "home_<id>_y_<format>", "home_<id>_z_<format>",
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