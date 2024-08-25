package me.leonardo.zhomes.utils;

import me.leonardo.zhomes.Main;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class ConfigUtils {

    Main main = Main.main;
    String svo = "save-options";
    String pls = "player-saves";
    String lim = "limits";
    String tpo = "teleport-options";
    protected String databasePath = svo+"database.";

    public boolean isSaveAsTypeUuid() {
        if(main.getConfig().getString(svo+"."+pls+".save-as").equalsIgnoreCase("name")) {
            return false;
        }
        return true;
    }

    public boolean isAutoUpdate() {
        return main.getConfig().getBoolean("auto-update");
    }

    public boolean canDimensionalTeleport() {
        return main.getConfig().getBoolean(tpo+".dimensional-teleportation");
    }

    public boolean needsLimit() {
        return main.getConfig().getBoolean(svo+"."+pls+"."+lim+".enabled");
    }

    public int getMaxLimit(Player p) {
        String path = svo+"."+pls+"."+lim;
        int returned = main.getConfig().getInt(svo+"."+pls+"."+lim+".default");

        for(String str : main.getConfig().getConfigurationSection(path).getKeys(false)) {
            if(str.equals("enabled") || str.equals("default")) continue;
            try {
                int i = Integer.parseInt(str);
                for(String perm : main.getConfig().getStringList(path+"."+i)) {
                    if(p.hasPermission(perm)) {
                        if(i >= returned) returned = i;
                        break;
                    }
                }
            }catch (Exception e) {
                System.out.println("[zHomes] Somethings off in config.yml");
            }
        }

        return returned;
    }

    public String saveAsType() {
        if(!isSaveAsTypeUuid()) {
            return "name";
        }
        return "uuid";
    }

    public static String langType() {
        return Main.main.getConfig().getString("language");
    }

    //<editor-fold desc="Database">
    public Boolean databaseEnabled() {
        return Main.main.getConfig().getBoolean(databasePath+"enabled");
    }
    public String databaseHost() {
        return Main.main.getConfig().getString(databasePath+"host");
    }
    public Integer databasePort() {
        return Main.main.getConfig().getInt(databasePath+"port");
    }
    public String databaseDatabase() {
        return Main.main.getConfig().getString(databasePath+"database");
    }
    public String databaseUsername() {
        return Main.main.getConfig().getString(databasePath+"username");
    }
    public String databasePassword() {
        return Main.main.getConfig().getString(databasePath+"password");
    }
    public String databaseTable() {
        return Main.main.getConfig().getString(databasePath+"table").toUpperCase();
    }
    public boolean isLocal() {
        return Main.main.getConfig().getBoolean(databasePath+"local")==true;
    }
    //</editor-fold>

}
