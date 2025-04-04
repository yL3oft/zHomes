package me.yleoft.zHomes.utils;

import java.util.List;
import me.yleoft.zHomes.Main;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

public class ConfigUtils {
    private static Main main = Main.getInstance();

    protected String cmdPath = "commands.";
    protected String databasePath = "database.";
    protected String lim = "limits.";
    protected String tpo = "teleport-options.";

    public ConfigUtilsExtras cfguExtras = new ConfigUtilsExtras();

    public static String langType() {
        return main.getConfig().getString("general.language");
    }
    public Boolean isAutoUpdate() {
        return main.getConfig().getBoolean("general.auto-update");
    }
    public Boolean hasMetrics() {
        return main.getConfig().getBoolean("general.metrics");
    }
    public String prefix() {
        return main.getConfig().getString("prefix");
    }

    //<editor-fold desc="Plugin Information">
    public boolean canDimensionalTeleport() {
        return main.getConfig().getBoolean(this.tpo + "dimensional-teleportation");
    }
    public boolean needsLimit() {
        return main.getConfig().getBoolean(this.lim + "enabled");
    }
    public int getMaxLimit(Player p) {
        int l = -1;
        for (String str : main.getConfig().getConfigurationSection(lim.split("\\.")[0]).getKeys(false)) {
            if (str.equals("enabled") || str.equals("default")) continue;
            try {
                int i = Integer.parseInt(str);
                for (String perm : main.getConfig().getStringList(lim+str)) {
                    if (p.hasPermission(perm) && i > l) l = i;
                }
            } catch (Exception e) {
                main.getServer().getConsoleSender().sendMessage(main.coloredPluginName + "§cSomething's off in config.yml");
            }
        }
        return (l == -1) ? main.getConfig().getInt(lim+"default") : l;
    }
    //</editor-fold>

    //<editor-fold desc="Main Command">
    public String CmdMainCommand() {
        return main.getConfig().getString(this.cmdPath + "main.command");
    }
    public String CmdMainPermission() {
        return main.getConfig().getString(this.cmdPath + "main.permission");
    }
    public String CmdMainDescription() {
        return main.getConfig().getString(this.cmdPath + "main.description");
    }
    public List<String> CmdMainAliases() {
        return main.getConfig().getStringList(this.cmdPath + "main.aliases");
    }
    public String CmdMainHelpPermission() {
        return main.getConfig().getString(this.cmdPath + "main.help.permission");
    }
    public String CmdMainVersionPermission() {
        return main.getConfig().getString(this.cmdPath + "main.version.permission");
    }
    public String CmdMainReloadPermission() {
        return main.getConfig().getString(this.cmdPath + "main.reload.permission");
    }
    public String CmdMainConverterPermission() {
        return main.getConfig().getString(this.cmdPath + "main.converter.permission");
    }
    //</editor-fold>
    //<editor-fold desc="Sethome Command">
    public String CmdSethomeCommand() {
        return main.getConfig().getString(this.cmdPath + "sethome.command");
    }
    public String CmdSethomePermission() {
        return main.getConfig().getString(this.cmdPath + "sethome.permission");
    }
    public String CmdSethomeDescription() {
        return main.getConfig().getString(this.cmdPath + "sethome.description");
    }
    public List<String> CmdSethomeAliases() {
        return main.getConfig().getStringList(this.cmdPath + "sethome.aliases");
    }
    public Float CmdSethomeCost() {
        return (float) main.getConfig().getDouble(this.cmdPath + "sethome.command-cost");
    }
    //</editor-fold>
    //<editor-fold desc="Delhome Command">
    public String CmdDelhomeCommand() {
        return main.getConfig().getString(this.cmdPath + "delhome.command");
    }
    public String CmdDelhomePermission() {
        return main.getConfig().getString(this.cmdPath + "delhome.permission");
    }
    public String CmdDelhomeDescription() {
        return main.getConfig().getString(this.cmdPath + "delhome.description");
    }
    public List<String> CmdDelhomeAliases() {
        return main.getConfig().getStringList(this.cmdPath + "delhome.aliases");
    }
    public Float CmdDelhomeCost() {
        return (float) main.getConfig().getDouble(this.cmdPath + "delhome.command-cost");
    }
    public String CmdDelhomeOthersPermission() {
        return main.getConfig().getString(this.cmdPath + "delhome.others.permission");
    }
    //</editor-fold>
    //<editor-fold desc="Homes Command">
    public String CmdHomesCommand() {
        return main.getConfig().getString(this.cmdPath + "homes.command");
    }
    public String CmdHomesPermission() {
        return main.getConfig().getString(this.cmdPath + "homes.permission");
    }
    public String CmdHomesDescription() {
        return main.getConfig().getString(this.cmdPath + "homes.description");
    }
    public List<String> CmdHomesAliases() {
        return main.getConfig().getStringList(this.cmdPath + "homes.aliases");
    }
    public Float CmdHomesCost() {
        return (float) main.getConfig().getDouble(this.cmdPath + "homes.command-cost");
    }
    public String CmdHomesOthersPermission() {
        return main.getConfig().getString(this.cmdPath + "homes.others.permission");
    }
    //</editor-fold>
    //<editor-fold desc="Home Command">
    public String CmdHomeCommand() {
        return main.getConfig().getString(this.cmdPath + "home.command");
    }
    public String CmdHomePermission() {
        return main.getConfig().getString(this.cmdPath + "home.permission");
    }
    public String CmdHomeDescription() {
        return main.getConfig().getString(this.cmdPath + "home.description");
    }
    public List<String> CmdHomeAliases() {
        return main.getConfig().getStringList(this.cmdPath + "home.aliases");
    }
    public Float CmdHomeCost() {
        return (float) main.getConfig().getDouble(this.cmdPath + "home.command-cost");
    }
    public String CmdHomeOthersPermission() {
        return main.getConfig().getString(this.cmdPath + "home.others.permission");
    }
    //</editor-fold>

    //<editor-fold desc="Permissions">
    public String PermissionBypassDT() {
        return "zhomes.bypass.dimensionalteleportation";
    }
    //</editor-fold>

    //<editor-fold desc="Database">
    public String databaseType() {
        return main.getConfig().getString(this.databasePath + "type");
    }
    public String databaseHost() {
        return main.getConfig().getString(this.databasePath + "host");
    }
    public Integer databasePort() {
        return Integer.valueOf(main.getConfig().getInt(this.databasePath + "port"));
    }
    public String databaseDatabase() {
        return main.getConfig().getString(this.databasePath + "database");
    }
    public String databaseUsername() {
        return main.getConfig().getString(this.databasePath + "username");
    }
    public String databasePassword() {
        return main.getConfig().getString(this.databasePath + "password");
    }
    public int databasePoolsize() {
        return main.getConfig().getInt(this.databasePath + "pool-size");
    }
    public String databaseTablePrefix() {
        return main.getConfig().getString(this.databasePath + "table-prefix").toLowerCase();
    }
    public String databaseTable() {
        return databaseTablePrefix()+"_homes";
    }
    //</editor-fold>

    protected static class ConfigUtilsExtras {

        public boolean canAfford(Player p, Float cost) {
            Economy economy = Main.economy;
            LanguageUtils.CommandsMSG cmdm = new LanguageUtils.CommandsMSG();
            if (Main.getInstance().getServer().getPluginManager().isPluginEnabled("Vault")) {
                if(economy.has(p, cost)) {
                    economy.withdrawPlayer(p, cost);
                    return true;
                }
                cmdm.sendMsg(p, cmdm.getCantAffod(cost));
                return false;
            }
            return true;
        }

    }

}
