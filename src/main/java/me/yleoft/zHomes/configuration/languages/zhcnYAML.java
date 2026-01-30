package me.yleoft.zHomes.configuration.languages;

public class zhcnYAML extends LanguageBuilder {

    public zhcnYAML() {
        super("zhcn");
    }

    public void buildLang() {
        header("""
                ####################################################################################################
                # +----------------------------------------------------------------------------------------------+ #
                # |                                                                                              | #
                # |                                      zHomes – 语言文件                                         | #
                # |                                                                                              | #
                # |   • Wiki:        https://docs.yleoft.me/zhomes                                               | #
                # |   • Discord:     https://discord.gg/yCdhVDgn4K                                               | #
                # |   • GitHub:      https://github.com/yL3oft/zHomes                                            | #
                # |                                                                                              | #
                # +----------------------------------------------------------------------------------------------+ #
                ####################################################################################################
                """);

        commentSection("hooks", "您可以在此处管理钩子消息。");
        addDefault(formPath("hooks", "griefprevention", "cant-set-homes"),
                "%prefix% <red>您无法在此区域设置家园。");

        addDefault(formPath("hooks", "worldguard", "cant-use-homes"),
                "%prefix% <red>您无法在此处使用家园。");
        addDefault(formPath("hooks", "worldguard", "cant-set-homes"),
                "%prefix% <red>您无法在此处设置家园。");

        addDefault(formPath("hooks", "vault", "cant-afford-command"),
                "%prefix% <red>您需要 <gold>$%cost% <red>才能执行此命令。");

        commentSection("teleport-warmup", "与传送预热相关的消息。");
        addDefault(formPath("teleport-warmup", "warmup"),
                "%prefix% <green>%time% 秒后传送...请勿移动！");
        addDefault(formPath("teleport-warmup", "warmup-actionbar"),
                "<green>%time% 秒后传送...");
        addDefault(formPath("teleport-warmup", "cancelled"),
                "%prefix% <red>您移动了！传送已取消。");
        addDefault(formPath("teleport-warmup", "cancelled-actionbar"),
                "<red>您移动了！传送已取消。");

        commentSection("commands", "与命令相关的消息。");
        comment(false, "此处您将找到可在多个命令中使用的消息。");
        addDefault(formPath("commands", "no-permission"),
                "%prefix% <red>您没有权限执行此命令。");
        addDefault(formPath("commands", "only-players"),
                "%prefix% <red>只有玩家可以执行此命令。");
        addDefault(formPath("commands", "in-cooldown"),
                "%prefix% <red>您必须等待 %time% 秒才能再次使用此命令。");
        addDefault(formPath("commands", "home-already-exist"),
                "%prefix% <red>您已经有一个使用此名称的家园。");
        addDefault(formPath("commands", "home-doesnt-exist"),
                "%prefix% <red>您没有使用此名称的家园。");
        addDefault(formPath("commands", "home-doesnt-exist-others"),
                "%prefix% <yellow>%player% <red>没有使用此名称的家园。");
        addDefault(formPath("commands", "cant-use-2dot"),
                "%prefix% <red>您不能在此命令中使用 <yellow>':'<red>。");
        addDefault(formPath("commands", "cant-find-player"),
                "%prefix% <red>未找到此玩家。");
        addDefault(formPath("commands", "unable-to-find-safe-location"),
                "%prefix% <red>无法找到安全的位置传送您。");
        addDefault(formPath("commands", "world-restricted-sethome"),
                "%prefix% <red>您无法在此世界设置家园。");
        addDefault(formPath("commands", "world-restricted-home"),
                "%prefix% <red>您无法传送到该世界的家园。");

        commentSection(formPath("commands", "main"), "以下是命令的特定消息。");

        // commands.main.help
        addDefault(formPath("commands", "main", "help", "help-perm"), """
                %prefix% <aqua><yellow>/%command% <aqua>的用法:
                <red>-> <yellow>/%command% <green>help <gray>显示此帮助消息
                <red>-> <yellow>/%command% <green>info <gray>显示插件信息
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                <red>-> <yellow>/%command% <green>(version|ver) <gold>[update]
                <red>-> <yellow>/%command% <green>nearhomes <gold>(<半径>) <gray>列出特定半径内您附近的家园
                <red>-> <yellow>/%command% <green>parse <gold>(玩家) (字符串) <gray>为特定玩家解析带有占位符的字符串
                <red>-> <yellow>/%command% <green>converter (<转换器类型>) <gray>将数据从一个位置转换到另一个位置
                <red>-> <yellow>/%command% <green>export <gray>将所有家园导出到单个文件
                <red>-> <yellow>/%command% <green>import (<文件>) <gray>从单个文件导入家园
                """);
        addDefault(formPath("commands", "main", "help", "help-noperm"), """
                %prefix% <aqua><yellow>/%command% <aqua>的用法:
                <red>-> <yellow>/%command% <green>(help|?) <gray>显示此帮助消息
                <red>-> <yellow>/%command% <green>(version|ver) <gray>显示插件版本
                """);

        // commands.main.info
        addDefault(formPath("commands", "main", "info", "output"), """
                %prefix% <aqua>正在运行 <dark_aqua>%name% v%version% <aqua>作者 <dark_aqua>%author%<aqua>:
                %prefix% <aqua>- 服务器信息:
                %prefix% <dark_aqua>   软件: <white>%server.software%
                %prefix% <dark_aqua>   版本: <white>%server.version%
                %prefix% <dark_aqua>   需要更新: <white>%requpdate%
                %prefix% <dark_aqua>   语言: <white>%language%
                %prefix% <aqua>- 存储:
                %prefix% <dark_aqua>   类型: <white>%storage.type%
                %prefix% <dark_aqua>   用户: <white>%storage.users%
                %prefix% <dark_aqua>   家园: <white>%storage.homes%
                %prefix% <aqua>- 钩子:
                %prefix% <dark_aqua>   PlaceholderAPI: <white>%use.placeholderapi%
                %prefix% <dark_aqua>   GriefPrevention: <white>%use.griefprevention%
                %prefix% <dark_aqua>   WorldGuard: <white>%use.worldguard%
                %prefix% <dark_aqua>   Vault: <white>%use.vault%
                """);
        addDefault(formPath("commands", "main", "info", "requpdate-yes"),
                "<red>是 <gray>(使用 <yellow>/%command% version <gray>获取更多信息)");
        addDefault(formPath("commands", "main", "info", "requpdate-no"),
                "<green>否");

        // commands.main.version
        addDefault(formPath("commands", "main", "version", "output"),
                "%prefix% <aqua>当前版本: <green>%version%");
        addDefault(formPath("commands", "main", "version", "update", "output"),
                "%prefix% <green>zHomes 已更新到最新版本 <yellow>(%update%)<green>,请重启您的服务器以应用更改。");
        addDefault(formPath("commands", "main", "version", "update", "no-update"),
                "%prefix% <green>您已经在使用最新版本的 zHomes。");

        // commands.main.reload
        addDefault(formPath("commands", "main", "reload", "usage"), """
                %prefix% <aqua><yellow>/%command% <green>(reload|rl) <aqua>的用法:
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                """);
        addDefault(formPath("commands", "main", "reload", "output"),
                "%prefix% <green>已在 <aqua>%time%ms <green>内重新加载插件。");
        addDefault(formPath("commands", "main", "reload", "commands", "output"),
                "%prefix% <green>已在 <aqua>%time%ms <green>内重新加载所有插件命令。");
        addDefault(formPath("commands", "main", "reload", "config", "output"),
                "%prefix% <green>已在 <aqua>%time%ms <green>内重新加载插件配置文件。");
        addDefault(formPath("commands", "main", "reload", "languages", "output"), "%prefix% <green>已在 <aqua>%time%ms <green>内重新加载插件语言。");

        // commands.main.nearhomes
        addDefault(formPath("commands", "main", "nearhomes", "usage"),
                "<red>-> <yellow>/%command% nearhomes <green>[<半径>]");
        addDefault(formPath("commands", "main", "nearhomes", "output"),
                "%prefix% <gray>您附近 <yellow>%radius% <gray>格内的家园: <white>%homes%");
        addDefault(formPath("commands", "main", "nearhomes", "output-not-found"),
                "%prefix% <red>在 <yellow>%radius% <red>格内未找到家园。");
        addDefault(formPath("commands", "main", "nearhomes", "home-string"),
                "<yellow>%home% <gray>(%owner%)");

        // commands.main.parse
        addDefault(formPath("commands", "main", "parse", "usage"),
                "<red>-> <yellow>/%command% parse <gold>(玩家) (字符串)");
        addDefault(formPath("commands", "main", "parse", "output"),
                "%prefix% <gray>已解析文本: <white>%parsed%");

        // commands.main.converter
        addDefault(formPath("commands", "main", "converter", "usage"), """
                %prefix% <aqua><yellow>/%command% <green>converter <aqua>的用法:
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
                "%prefix% <green>所有数据已转换！");
        addDefault(formPath("commands", "main", "converter", "error"),
                "%prefix% <red>转换数据时出错,请检查您的服务器控制台。");

        // commands.main.export
        addDefault(formPath("commands", "main", "export", "output"),
                "%prefix% <green>所有家园已导出到 <yellow>%file%<green>！");
        addDefault(formPath("commands", "main", "export", "error"),
                "%prefix% <red>导出数据时出错,请检查您的服务器控制台。");

        // commands.main.import
        addDefault(formPath("commands", "main", "import", "usage"),
                "<red>-> <yellow>/%command% import <green>(<文件>)");
        addDefault(formPath("commands", "main", "import", "output"),
                "%prefix% <green>所有家园已从 <yellow>%file% <green>导入！");
        addDefault(formPath("commands", "main", "import", "file-not-found"),
                "%prefix% <red>未找到文件 <yellow>%file%<red>。");
        addDefault(formPath("commands", "main", "import", "error"),
                "%prefix% <red>导入数据时出错,请检查您的服务器控制台。");

        // commands.sethome
        addDefault(formPath("commands", "sethome", "usage"),
                "<red>-> <yellow>/%command% <green>(家园)");
        addDefault(formPath("commands", "sethome", "output"),
                "%prefix% <green>家园 <yellow>%home% <green>已设置到您的位置。");
        addDefault(formPath("commands", "sethome", "limit-reached"),
                "<red>您无法设置更多家园,因为您已达到限制 <yellow>(%limit% 个家园)<red>！");

        // commands.delhome
        addDefault(formPath("commands", "delhome", "usage"),
                "<red>-> <yellow>/%command% <green>(家园)");
        addDefault(formPath("commands", "delhome", "output"),
                "%prefix% <red>家园 <yellow>%home% <red>已删除。");

        // commands.home
        addDefault(formPath("commands", "home", "usage"),
                "<red>-> <yellow>/%command% <green>(家园)");
        addDefault(formPath("commands", "home", "output"),
                "%prefix% <green>已传送到 <yellow>%home%<green>...");
        addDefault(formPath("commands", "home", "cant-dimensional-teleport"),
                "<red>您的传送已取消！跨维度传送已禁用。");

        // commands.home.rename
        addDefault(formPath("commands", "home", "rename", "usage"),
                "<red>-> <yellow>/%command% <green>rename (家园) (新名称)");
        addDefault(formPath("commands", "home", "rename", "output"),
                "%prefix% <green>家园 <yellow>%home% <green>已重命名为 <yellow>%newname%<green>。");
        addDefault(formPath("commands", "home", "rename", "same-name"),
                "%prefix% <red>您不能将家园重命名为相同的名称。");

        // commands.homes
        addDefault(formPath("commands", "homes", "output"),
                "%prefix% <gray>您的家园 (%amount%): <white>%homes%");
        addDefault(formPath("commands", "homes", "invalid-page"),
                "%prefix% <red>无效的页码！请使用大于 0 的数字。");

        // commands.homes.others
        addDefault(formPath("commands", "homes", "others", "output"),
                "%prefix% <yellow>%player% <gray>的家园 (%amount%): <white>%homes%");

        build();
    }

}