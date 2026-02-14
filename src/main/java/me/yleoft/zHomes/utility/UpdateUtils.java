package me.yleoft.zHomes.utility;

import me.yleoft.zAPI.update.UpdateManager;
import me.yleoft.zHomes.zHomes;

public class UpdateUtils {

    public static String site = "https://modrinth.com/plugin/zhomes/version/latest";

    private final zHomes plugin;

    public UpdateManager checker;
    public boolean needsUpdate;
    public String updateVersion;

    public UpdateUtils(zHomes plugin) {
        this.plugin = plugin;
        updatePlugin();
    }

    public void updatePlugin() {
        checker = new UpdateManager(plugin, "https://api.github.com/repos/yL3oft/zHomes/tags", "zhomes");
        String version = checker.getVersion();

        if(!plugin.getPluginMeta().getVersion().equals(version)) {
            needsUpdate = true;
            updateVersion = version;
            plugin.getLoggerInstance().info("""
                    %bc| ------------------------------------------------------- |
                    %bc| %wc> A new version is available!                           %bc|
                    %bc| %wc> Your Version: <red>%v                          %bc|
                    %bc| %wc> Latest Version: <green>%nv                                 %bc|
                    %bc| %wc> Update your plugin in:                                %bc|
                    %bc| %wc> <aqua>%site     %bc|
                    """
                    .replace("%v", plugin.getPluginMeta().getVersion().contains("-SNAPSHOT") ?
                            plugin.getPluginMeta().getVersion() : plugin.getPluginMeta().getVersion()+"         ")
                    .replace("%nv", version)
                    .replace("%bc", zHomes.bc)
                    .replace("%wc", zHomes.wc)
                    .replace("%site", site));
        }
    }

}
