package me.leonardo.zhomes.expansions;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.leonardo.zhomes.Main;
import me.leonardo.zhomes.utils.storage.HomesUtilsYAML;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlaceHolderAPIExpansion extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "zhomes";
    }

    @Override
    public @NotNull String getAuthor() {
        return "yLeoft";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
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
    public @Nullable String onPlaceholderRequest(Player p, @NotNull String params) {
        String[] split = params.split("_");
        HomesUtilsYAML hu = Main.hu;

        if(params.startsWith("hashome_")) {
            return String.valueOf(hu.hasHome(p, split[1]));
        }
        if(params.equals("numberofhomes")) {
            return String.valueOf(hu.numberOfHomes(p));
        }
        if(params.equals("homes")) {
            return String.valueOf(hu.homes(p));
        }

        return null;
    }
}
