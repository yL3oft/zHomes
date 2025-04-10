package me.yleoft.zHomes.hooks;

import java.util.ArrayList;
import java.util.List;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.yleoft.zHomes.Main;
import me.yleoft.zHomes.utils.HomesUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderAPIExpansion extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return Main.getInstance().getDescription().getName().toLowerCase();
    }

    @Override
    public @NotNull String getAuthor() {
        return "yLeoft";
    }

    @Override
    public @NotNull String getVersion() {
        return Main.getInstance().pluginVer;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @NotNull List<String> getPlaceholders() {
        List<String> placeholders = new ArrayList<>();
        placeholders.add("version");
        placeholders.add("hashome_<home>");
        placeholders.add("numberofhomes");
        placeholders.add("homes");
        placeholders.add("limit");
        return placeholders;
    }

    @Override
    public String onPlaceholderRequest(Player p, @NotNull String params) {
        HomesUtils hu = Main.hu;
        String[] split = params.split("_");
        switch (split[0]) {
            case "version":
                return (Main.getInstance()).pluginVer;
            case "hashome":
                return String.valueOf(hu.hasHome(p, split[1]));
            case "numberofhomes":
                return String.valueOf(hu.numberOfHomes(p));
            case "homes":
                return hu.homes(p);
            case "limit":
                return String.valueOf(hu.getMaxLimit(p));
        }
        return null;
    }

}
