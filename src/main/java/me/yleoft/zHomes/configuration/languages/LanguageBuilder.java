package me.yleoft.zHomes.configuration.languages;

import me.yleoft.zAPI.configuration.YAMLBuilder;
import me.yleoft.zAPI.hooks.HookRegistry;
import me.yleoft.zAPI.utility.TextFormatter;
import me.yleoft.zHomes.storage.DatabaseEditor;
import me.yleoft.zHomes.utility.HomesUtils;
import me.yleoft.zHomes.zHomes;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class LanguageBuilder extends YAMLBuilder {

    private final static File languageFolder = new File(zHomes.getInstance().getDataFolder(), "languages");

    public LanguageBuilder(String langFileName) {
        super(new File(languageFolder, langFileName+".yml"));
        migrateLegacyColors(true);
        buildLang();
    }

    public void buildLang() {
        header("""
                ####################################################################################################
                # +----------------------------------------------------------------------------------------------+ #
                # |                                                                                              | #
                # |                                   zHomes — Language File                                     | #
                # |                                                                                              | #
                # |   • Wiki:        https://docs.yleoft.me/zhomes                                               | #
                # |   • Discord:     https://discord.gg/yCdhVDgn4K                                               | #
                # |   • GitHub:      https://github.com/yL3oft/zHomes                                            | #
                # |                                                                                              | #
                # +----------------------------------------------------------------------------------------------+ #
                ####################################################################################################
                """);

        commentSection("hooks", "Here you can manage hook messages.");
        addDefault(formPath("hooks", "griefprevention", "cant-set-homes"),
                "%prefix% <red>You can't set homes in this area.");

        addDefault(formPath("hooks", "worldguard", "cant-use-homes"),
                "%prefix% <red>You can't use homes here.");
        addDefault(formPath("hooks", "worldguard", "cant-set-homes"),
                "%prefix% <red>You can't set a home here.");

        addDefault(formPath("hooks", "vault", "cant-afford-command"),
                "%prefix% <red>You need <gold>$%cost% <red>in order to execute this command.");

        commentSection("teleport-warmup", "Messages related to teleport warmup.");
        addDefault(formPath("teleport-warmup", "warmup"),
                "%prefix% <green>Teleporting in %time% seconds... Don't move!");
        addDefault(formPath("teleport-warmup", "warmup-actionbar"),
                "<green>Teleporting in %time% seconds...");
        addDefault(formPath("teleport-warmup", "cancelled"),
                "%prefix% <red>You moved! Teleportation cancelled.");
        addDefault(formPath("teleport-warmup", "cancelled-actionbar"),
                "<red>You moved! Teleportation cancelled.");

        commentSection("commands", "Messages related to commands.");
        comment(false, "Up here you will find messages that can be used in multiple commands.");
        addDefault(formPath("commands", "no-permission"),
                "%prefix% <red>You don't have permission to execute this command.");
        addDefault(formPath("commands", "only-players"),
                "%prefix% <red>Only players can execute this command.");
        addDefault(formPath("commands", "in-cooldown"),
                "%prefix% <red>You must wait %time% seconds before using this command again.");
        addDefault(formPath("commands", "home-already-exist"),
                "%prefix% <red>You already have a home with this name.");
        addDefault(formPath("commands", "home-doesnt-exist"),
                "%prefix% <red>You don't have any home with this name.");
        addDefault(formPath("commands", "home-doesnt-exist-others"),
                "%prefix% <yellow>%player% <red>don't have any home with this name.");
        addDefault(formPath("commands", "cant-use-2dot"),
                "%prefix% <red>You can't use <yellow>':' <red>in this command.");
        addDefault(formPath("commands", "cant-find-player"),
                "%prefix% <red>This player was not found.");
        addDefault(formPath("commands", "unable-to-find-safe-location"),
                "%prefix% <red>Unable to find a safe location to teleport you.");
        addDefault(formPath("commands", "world-restricted-sethome"),
                "%prefix% <red>You can't set homes in this world.");
        addDefault(formPath("commands", "world-restricted-home"),
                "%prefix% <red>You can't teleport to homes in that world.");

        commentSection(formPath("commands", "main"), "Below you will find messages specific for the commands.");

        // commands.main.help
        addDefault(formPath("commands", "main", "help", "help-perm"), """
                %prefix% <aqua>Usages of <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>help <gray>Shows this exact help message
                <red>-> <yellow>/%command% <green>info <gray>Shows plugin information
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                <red>-> <yellow>/%command% <green>(version|ver) <gold>[update]
                <red>-> <yellow>/%command% <green>nearhomes <gold>(<radius>) <gray>List homes near you within a certain radius
                <red>-> <yellow>/%command% <green>parse <gold>(Player) (String) <gray>Parses a string with placeholders for a specific player
                <red>-> <yellow>/%command% <green>converter (<converter-type>) <gray>Convert data from one place to another
                <red>-> <yellow>/%command% <green>export <gray>Exports all homes to a single file
                <red>-> <yellow>/%command% <green>import (<file>) <gray>Imports homes from a single file
                """);
        addDefault(formPath("commands", "main", "help", "help-noperm"), """
                %prefix% <aqua>Usages of <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>(help|?) <gray>Shows this exact help message
                <red>-> <yellow>/%command% <green>(version|ver) <gray>Shows plugin version
                """);

        // commands.main.info
        addDefault(formPath("commands", "main", "info", "output"), """
                %prefix% <aqua>Running <dark_aqua>%name% v%version% <aqua>by <dark_aqua>%author%<aqua>:
                %prefix% <aqua>- Server Info:
                %prefix% <dark_aqua>   Software: <white>%server.software%
                %prefix% <dark_aqua>   Version: <white>%server.version%
                %prefix% <dark_aqua>   Require update: <white>%requpdate%
                %prefix% <dark_aqua>   Language: <white>%language%
                %prefix% <aqua>- Storage:
                %prefix% <dark_aqua>   Type: <white>%storage.type%
                %prefix% <dark_aqua>   Users: <white>%storage.users%
                %prefix% <dark_aqua>   Homes: <white>%storage.homes%
                %prefix% <aqua>- Hooks:
                %prefix% <dark_aqua>   PlaceholderAPI: <white>%use.placeholderapi%
                %prefix% <dark_aqua>   GriefPrevention: <white>%use.griefprevention%
                %prefix% <dark_aqua>   WorldGuard: <white>%use.worldguard%
                %prefix% <dark_aqua>   Vault: <white>%use.vault%
                """);
        addDefault(formPath("commands", "main", "info", "requpdate-yes"),
                "<red>Yes <gray>(Use <yellow>/%command% version <gray>for more info)");
        addDefault(formPath("commands", "main", "info", "requpdate-no"),
                "<green>No");

        // commands.main.version
        addDefault(formPath("commands", "main", "version", "output"),
                "%prefix% <aqua>Current version: <green>%version%");
        addDefault(formPath("commands", "main", "version", "update", "output"),
                "%prefix% <green>zHomes updated to the latest version <yellow>(%update%)<green>, please restart your server to apply the changes.");
        addDefault(formPath("commands", "main", "version", "update", "no-update"),
                "%prefix% <green>You are already using the latest version of zHomes.");

        // commands.main.reload
        addDefault(formPath("commands", "main", "reload", "usage"), """
                %prefix% <aqua>Usages of <yellow>/%command% <green>(reload|rl)<aqua>:
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                """);
        addDefault(formPath("commands", "main", "reload", "output"),
                "%prefix% <green>Reloaded plugin in <aqua>%time%ms<green>.");
        addDefault(formPath("commands", "main", "reload", "commands", "output"),
                "%prefix% <green>Reloaded all plugin commands in <aqua>%time%ms<green>.");
        addDefault(formPath("commands", "main", "reload", "config", "output"),
                "%prefix% <green>Reloaded plugin config file in <aqua>%time%ms<green>.");
        addDefault(formPath("commands", "main", "reload", "languages", "output"), "%prefix% <green>Reloaded plugin languages in <aqua>%time%ms<green>.");

        // commands.main.nearhomes
        addDefault(formPath("commands", "main", "nearhomes", "usage"),
                "<red>-> <yellow>/%command% nearhomes <green>[<Radius>]");
        addDefault(formPath("commands", "main", "nearhomes", "output"),
                "%prefix% <gray>Homes near you within <yellow>%radius% <gray>blocks: <white>%homes%");
        addDefault(formPath("commands", "main", "nearhomes", "output-not-found"),
                "%prefix% <red>No homes found within <yellow>%radius% <red>blocks.");
        addDefault(formPath("commands", "main", "nearhomes", "home-string"),
                "<yellow>%home% <gray>(%owner%)");

        // commands.main.parse
        addDefault(formPath("commands", "main", "parse", "usage"),
                "<red>-> <yellow>/%command% parse <gold>(Player) (String)");
        addDefault(formPath("commands", "main", "parse", "output"),
                "%prefix% <gray>Parsed text: <white>%parsed%");

        // commands.main.converter
        addDefault(formPath("commands", "main", "converter", "usage"), """
                %prefix% <aqua>Usages of <yellow>/%command% <green>converter<aqua>:
                <red>-> <yellow>/%command% <green>converter sqlitetoh2
                <red>-> <yellow>/%command% <green>converter sqlitetomysql
                <red>-> <yellow>/%command% <green>converter sqlitetomariadb
                <red>-> <yellow>/%command% <green>converter mysqltosqlite
                <red>-> <yellow>/%command% <green>converter mysqltoh2
                <red>-> <yellow>/%command% <green>converter mariadbtosqlite
                <red>-> <yellow>/%command% <green>converter mariadbtoh2
                <red>-> <yellow>/%command% <green>converter h2tosqlite
                <red>-> <yellow>/%command% <green>converter h2tomysql
                <red>-> <yellow>/%command% <green>converter h2tomariadb
                <red>-> <yellow>/%command% <green>converter essentials
                <red>-> <yellow>/%command% <green>converter sethome
                <red>-> <yellow>/%command% <green>converter ultimatehomes
                <red>-> <yellow>/%command% <green>converter xhomes
                <red>-> <yellow>/%command% <green>converter zhome
                """);
        addDefault(formPath("commands", "main", "converter", "output"),
                "%prefix% <green>All data converted!");
        addDefault(formPath("commands", "main", "converter", "error"),
                "%prefix% <red>Something went wrong converting the data, please check your server console.");

        // commands.main.export
        addDefault(formPath("commands", "main", "export", "output"),
                "%prefix% <green>All homes exported to <yellow>%file%<green>!");
        addDefault(formPath("commands", "main", "export", "error"),
                "%prefix% <red>Something went wrong exporting the data, please check your server console.");

        // commands.main.import
        addDefault(formPath("commands", "main", "import", "usage"),
                "<red>-> <yellow>/%command% import <green>(<file>)");
        addDefault(formPath("commands", "main", "import", "output"),
                "%prefix% <green>All homes imported from <yellow>%file%<green>!");
        addDefault(formPath("commands", "main", "import", "file-not-found"),
                "%prefix% <red>The file <yellow>%file% <red>was not found.");
        addDefault(formPath("commands", "main", "import", "error"),
                "%prefix% <red>Something went wrong importing the data, please check your server console.");

        // commands.sethome
        addDefault(formPath("commands", "sethome", "usage"),
                "<red>-> <yellow>/%command% <green>(Home)");
        addDefault(formPath("commands", "sethome", "output"),
                "%prefix% <green>Home <yellow>%home% <green>set to your position.");
        addDefault(formPath("commands", "sethome", "limit-reached"),
                "<red>You can't set more homes because you reached your limit <yellow>(%limit% homes)<red>!");

        // commands.delhome
        addDefault(formPath("commands", "delhome", "usage"),
                "<red>-> <yellow>/%command% <green>(Home)");
        addDefault(formPath("commands", "delhome", "output"),
                "%prefix% <red>Home <yellow>%home% <red>deleted.");

        // commands.home
        addDefault(formPath("commands", "home", "usage"),
                "<red>-> <yellow>/%command% <green>(Home)");
        addDefault(formPath("commands", "home", "output"),
                "%prefix% <green>Teleported to <yellow>%home%<green>...");
        addDefault(formPath("commands", "home", "cant-dimensional-teleport"),
                "<red>Your teleport was cancelled! Dimensional teleportation is disabled.");

        // commands.home.rename
        addDefault(formPath("commands", "home", "rename", "usage"),
                "<red>-> <yellow>/%command% <green>rename (Home) (NewName)");
        addDefault(formPath("commands", "home", "rename", "output"),
                "%prefix% <green>Home <yellow>%home% <green>renamed to <yellow>%newname%<green>.");
        addDefault(formPath("commands", "home", "rename", "same-name"),
                "%prefix% <red>You can't rename a home to the same name.");

        // commands.homes
        addDefault(formPath("commands", "homes", "output"),
                "%prefix% <gray>Your homes (%amount%): <white>%homes%");
        addDefault(formPath("commands", "homes", "invalid-page"),
                "%prefix% <red>Invalid page number! Please use a number higher than 0.");

        // commands.homes.others
        addDefault(formPath("commands", "homes", "others", "output"),
                "%prefix% <yellow>%player%'s <gray>homes (%amount%): <white>%homes%");

        build();
    }

    //<editor-fold desc="Hooks">
    public String getGFCantSetHomes() {
        return getString(formPath("hooks", "griefprevention", "cant-set-homes"));
    }
    public String getWGCantUseHomes() {
        return getString(formPath("hooks", "worldguard", "cant-use-homes"));
    }
    public String getWGCantSetHomes() {
        return getString(formPath("hooks", "worldguard", "cant-set-homes"));
    }
    public String getVaultCantAffordCommand() {
        return getString(formPath("hooks", "vault", "cant-afford-command"));
    }
    //</editor-fold>

    //<editor-fold desc="Teleport Warmup">
    public String getWarmup(int time) {
        return getString(formPath("teleport-warmup", "warmup"))
                .replace("%time%", String.valueOf(time));
    }
    public String getWarmupActionbar(int time) {
        return getString(formPath("teleport-warmup", "warmup-actionbar"))
                .replace("%time%", String.valueOf(time));
    }
    public String getWarmupCancelled() {
        return getString(formPath("teleport-warmup", "cancelled"));
    }
    public String getWarmupCancelledActionbar() {
        return getString(formPath("teleport-warmup", "cancelled-actionbar"));
    }
    //</editor-fold>

    //<editor-fold desc="Commands">
    //<editor-fold desc="General">
    public String getNoPermission() {
        return getString(formPath("commands", "no-permission"));
    }
    public String getOnlyPlayers() {
        return getString(formPath("commands", "only-players"));
    }
    public String getInCooldown() {
        return getString(formPath("commands", "in-cooldown"));
    }
    public String getHomeAlreadyExist() {
        return getString(formPath("commands", "home-already-exist"));
    }
    public String getHomeDoesntExist() {
        return getString(formPath("commands", "home-doesnt-exist"));
    }
    public String getHomeDoesntExistOthers(String player) {
        return getString(formPath("commands", "home-doesnt-exist-others"))
                .replace("%player%", player);
    }
    public String getCantUse2Dot() {
        return getString(formPath("commands", "cant-use-2dot"));
    }
    public String getCantFindPlayer() {
        return getString(formPath("commands", "cant-find-player"));
    }
    public String getUnableToFindSafeLocation() {
        return getString(formPath("commands", "unable-to-find-safe-location"));
    }
    public String getWorldRestrictedSethome() {
        return getString(formPath("commands", "world-restricted-sethome"));
    }
    public String getWorldRestrictedHome() {
        return getString(formPath("commands", "world-restricted-home"));
    }
    //</editor-fold>
    //<editor-fold desc="Main">
    public String getMainHelpPerm() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "help", "help-perm"));
    }
    public String getMainHelpNoPerm() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "help", "help-noperm"));
    }
    public String getMainInfoOutput() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "info", "output"))
                .replace("%name%", zHomes.getInstance().getPluginMeta().getName())
                .replace("%version%", zHomes.getInstance().getPluginMeta().getVersion())
                .replace("%author%", String.join(", ", zHomes.getInstance().getPluginMeta().getAuthors()))
                .replace("%server.software%", Bukkit.getServer().getName())
                .replace("%server.version%", Bukkit.getServer().getVersion() + " - " + Bukkit.getServer().getBukkitVersion())
                .replace("%requpdate%", zHomes.updateUtils.needsUpdate ? getRequireUpdateYes() : getRequireUpdateNo())
                .replace("%language%", zHomes.getConfigYAML().getLanguageCode())
                .replace("%storage.type%", zHomes.getConfigYAML().getDatabaseType())
                .replace("%storage.users%", String.valueOf(DatabaseEditor.getTotalUsers()))
                .replace("%storage.homes%", String.valueOf(DatabaseEditor.getTotalHomes()))
                .replace("%use.placeholderapi%", HookRegistry.PAPI.exists() ? "<green>Yes" : "<red>No")
                .replace("%use.griefprevention%", me.yleoft.zHomes.hooks.HookRegistry.GRIEF_PREVENTION.exists() ? "<green>Yes" : "<red>No")
                .replace("%use.worldguard%", me.yleoft.zHomes.hooks.HookRegistry.WORLDGUARD.exists() ? "<green>Yes" : "<red>No")
                .replace("%use.vault%", me.yleoft.zHomes.hooks.HookRegistry.VAULT.exists() ? "<green>Yes" : "<red>No");
    }
    public String getRequireUpdateYes() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "info", "requpdate-yes"));
    }
    public String getRequireUpdateNo() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "info", "requpdate-no"));
    }
    public String getMainVersionOutput() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "version", "output"))
                .replace("%version%", zHomes.getInstance().getPluginMeta().getVersion());
    }
    public String getMainVersionUpdateOutput() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "version", "update", "output"))
                .replace("%update%", zHomes.updateUtils.updateVersion);
    }
    public String getMainVersionNoUpdate() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "version", "update", "no-update"));
    }
    public String getMainReloadUsage() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "reload", "usage"));
    }
    public String getMainReloadOutput(long time) {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "reload", "output"))
                .replace("%time%", String.valueOf(time));
    }
    public String getMainReloadCommandsOutput(long time) {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "reload", "commands", "output"))
                .replace("%time%", String.valueOf(time));
    }
    public String getMainReloadConfigOutput(long time) {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "reload", "config", "output"))
                .replace("%time%", String.valueOf(time));
    }
    public String getMainReloadLanguagesOutput(long time) {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "reload", "languages", "output"))
                .replace("%time%", String.valueOf(time));
    }
    public String getMainNearhomesUsage() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "nearhomes", "usage"));
    }
    public String getMainNearhomesOutput(double radius, HashMap<OfflinePlayer, List<String>> homes, @Nullable String playerName, @Nullable String homeName) {
        StringBuilder homesList = new StringBuilder();
        for (OfflinePlayer p : homes.keySet()) {
            if(playerName != null && p.getName() != null && !p.getName().equalsIgnoreCase(playerName)) continue;
            for(String home : homes.get(p)) {
                if(homeName != null && !home.startsWith(homeName)) continue;
                if(!homesList.toString().isEmpty()) homesList.append(", ");
                homesList.append(getHomeString(p, home));
            }
        }
        if(homesList.toString().isEmpty()) {
            return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "nearhomes", "output-not-found"))
                    .replace("%radius%", String.valueOf(radius));
        }
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "nearhomes", "output"))
                .replace("%radius%", String.valueOf(radius))
                .replace("%homes%", homesList.toString());
    }
    public String getMainNearhomesOutput(double radius, HashMap<OfflinePlayer, List<String>> homes) {
        return getMainNearhomesOutput(radius, homes, null, null);
    }
    public String getHomeString(OfflinePlayer player, String home) {
        return getString(formPath("commands", "main", "nearhomes", "home-string"))
                .replace("%home%", home)
                .replace("%owner%", (player == null || player.getName() == null) ? "----" : player.getName());
    }
    public String getMainParseUsage() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "parse", "usage"));
    }
    public String getMainParseOutput(String parsed) {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "parse", "output"))
                .replace("%parsed%", parsed);
    }
    public String getMainConverterUsage() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "converter", "usage"));
    }
    public String getMainConverterOutput() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "converter", "output"));
    }
    public String getMainConverterError() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "converter", "error"));
    }
    public String getMainExportOutput(String file) {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "export", "output"))
                .replace("%file%", file);
    }
    public String getMainExportError() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "export", "error"));
    }
    public String getMainImportUsage() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "import", "usage"));
    }
    public String getMainImportOutput(String file) {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "import", "output"))
                .replace("%file%", file);
    }
    public String getMainImportFileNotFound(String file) {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "import", "file-not-found"))
                .replace("%file%", file);
    }
    public String getMainImportError() {
        return getString(zHomes.getConfigYAML().getMainCommand(), formPath("commands", "main", "import", "error"));
    }
    //</editor-fold>
    //<editor-fold desc="Sethome">
    public String getSethomeUsage() {
        return getString(zHomes.getConfigYAML().getSetHomeCommand(), formPath("commands", "sethome", "usage"));
    }
    public String getSethomeOutput(String home) {
        return getString(zHomes.getConfigYAML().getSetHomeCommand(), formPath("commands", "sethome", "output"))
                .replace("%home%", home);
    }
    public String getSethomeLimitReached(OfflinePlayer player) {
        return getString(zHomes.getConfigYAML().getSetHomeCommand(), formPath("commands", "sethome", "limit-reached"))
                .replace("%limit%", String.valueOf(HomesUtils.getLimit(player)));
    }
    //</editor-fold>
    //<editor-fold desc="Delhome">
    public String getDelhomeUsage() {
        return getString(zHomes.getConfigYAML().getDelHomeCommand(), formPath("commands", "delhome", "usage"));
    }
    public String getDelhomeOutput(String home) {
        return getString(zHomes.getConfigYAML().getDelHomeCommand(), formPath("commands", "delhome", "output"))
                .replace("%home%", home);
    }
    //</editor-fold>
    //<editor-fold desc="Home">
    public String getHomeUsage() {
        return getString(zHomes.getConfigYAML().getHomeCommand(), formPath("commands", "home", "usage"));
    }
    public String getHomeOutput(String home) {
        return getString(zHomes.getConfigYAML().getHomeCommand(), formPath("commands", "home", "output"))
                .replace("%home%", home);
    }
    public String getHomeCantDimensionalTeleport() {
        return getString(zHomes.getConfigYAML().getHomeCommand(), formPath("commands", "home", "cant-dimensional-teleport"));
    }
    public String getHomeRenameUsage() {
        return getString(zHomes.getConfigYAML().getHomeCommand(), formPath("commands", "home", "rename", "usage"));
    }
    public String getHomeRenameOutput(String home, String newName) {
        return getString(zHomes.getConfigYAML().getHomeCommand(), formPath("commands", "home", "rename", "output"))
                .replace("%home%", home)
                .replace("%newname%", newName);
    }
    public String getHomeRenameSameName() {
        return getString(zHomes.getConfigYAML().getHomeCommand(), formPath("commands", "home", "rename", "same-name"));
    }
    //</editor-fold>
    //<editor-fold desc="Homes">
    public String getHomesOutputSelf(Player player) {
        return getString(zHomes.getConfigYAML().getHomesCommand(), formPath("commands", "homes", "output"))
                .replace("%homes%", HomesUtils.homes(player))
                .replace("%amount%", String.valueOf(HomesUtils.numberOfHomes(player)));
    }
    public String getHomesInvalidPage() {
        return getString(zHomes.getConfigYAML().getHomesCommand(), formPath("commands", "homes", "invalid-page"));
    }
    public String getHomesOutputOthers(OfflinePlayer player) {
        return getString(zHomes.getConfigYAML().getHomesCommand(), formPath("commands", "homes", "others", "output"))
                .replace("%homes%", HomesUtils.homes(player))
                .replace("%amount%", String.valueOf(HomesUtils.numberOfHomes(player)))
                .replace("%player%", (player == null || player.getName() == null) ? "----" : player.getName());
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold desc="LanguageBuilder Methods">
    public static File getLanguageFolder() {
        return languageFolder;
    }
    public String getLanguageFileName() {
        return getFile().getName().replace(".yml", "");
    }
    //</editor-fold>

    public static void sendMessage(@Nullable CommandSender sender, String message) {
        if(sender != null) sender.sendMessage(TextFormatter.transform(sender instanceof Player ? (Player)sender : null, message));
    }

    public static void sendActionBar(CommandSender sender, String message) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(TextFormatter.transform(message));
            return;
        }
        player.sendActionBar(TextFormatter.transform(player, message));
    }

    //<editor-fold desc="Overrides">
    public String getString(String command, String path) {
        return getString(path)
                .replace("%command%", command);
    }
    //</editor-fold>

}
