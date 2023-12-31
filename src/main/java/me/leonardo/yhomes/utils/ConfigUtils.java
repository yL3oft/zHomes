package me.leonardo.yhomes.utils;

import me.leonardo.yhomes.Main;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigUtils {

    Main main = Main.main;
    String svo = "save-options";
    String pls = "player-saves";
    String tpo = "teleport-options";

    public boolean isSaveAsTypeUuid() {
        if(main.getConfig().getString(svo+"."+pls+".save-as").equalsIgnoreCase("name")) {
            return false;
        }
        return true;
    }

    public boolean canDimensionalTeleport() {
        return main.getConfig().getBoolean(tpo+".dimensional-teleportation");
    }

    public String saveAsType() {
        if(!isSaveAsTypeUuid()) {
            return "name";
        }
        return "uuid";
    }

}
