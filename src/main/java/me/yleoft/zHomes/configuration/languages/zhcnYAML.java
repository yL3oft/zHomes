package me.yleoft.zHomes.configuration.languages;

import java.util.HashMap;
import java.util.Map;

public class zhcnYAML extends LanguageBuilder {

    public zhcnYAML() {
        super("zhcn");
    }

    @Override
    protected Map<String, String> translations() {
        Map<String, String> t = new HashMap<>();

        // config comments
        t.put(formPath("config", "comment", "header1"), "# |                                           插件链接与支持                                            | #");
        t.put(formPath("config", "comment", "header2"), "# |   zHomes (c) yL3oft — 根据MIT许可证发布。                                                            | #");
        t.put(formPath("config", "comment", "database"), "在下方编辑您的数据库设置");
        t.put(formPath("config", "comment", "database", "type"), """
        在这里您可以定义如何存储插件数据。
        选项：
        - H2（优先于SQLite）
        - SQLite
        - MariaDB（优先于MySQL）
        - MySQL
        默认：H2
        """);
        t.put(formPath("config", "comment", "pool-size"), "# 警告：如果您不知道自己在做什么，请勿更改以下内容");
        t.put(formPath("config", "comment", "general", "language"), """
        在这里您可以定义插件的语言。所有语言均可在语言目录中找到、编辑和创建。
        当前可用语言：[de, en, es, fr, it, nl, pl, pt-br, ru, zhcn, <custom>]
        """);
        t.put(formPath("config", "comment", "general", "announce-update"), "切换是否让插件在控制台和拥有相应权限的玩家中公告可用更新。");
        t.put(formPath("config", "comment", "general", "metrics"), """
        启用或禁用数据统计收集以帮助改进插件。
        所有收集的数据均为匿名，仅用于统计目的。
        !警告：需要重启服务器才能生效！
        """);
        t.put(formPath("config", "comment", "general", "debug-mode"), "启用或禁用调试模式以获取更详细的日志输出。");
        t.put(formPath("config", "comment", "teleport-options"), "与传送行为相关的设置");
        t.put(formPath("config", "comment", "teleport-options", "enable-safe-teleport"), "启用或禁用安全传送，以防止玩家被传送到危险位置。");
        t.put(formPath("config", "comment", "teleport-options", "dimensional-teleportation"), "启用或禁用跨维度传送，允许玩家在不同世界或维度之间传送。");
        t.put(formPath("config", "comment", "teleport-options", "play-sound"), "在玩家传送时播放音效。");
        t.put(formPath("config", "comment", "teleport-options", "restricted-worlds", "enable"), "启用或禁用限制传送到某些世界的功能。");
        t.put(formPath("config", "comment", "teleport-options", "restricted-worlds", "mode"), """
        定义受限世界的模式。
        选项：
        - blacklist：玩家无法传送到下列世界。
        - whitelist：玩家只能传送到下列世界。
        """);
        t.put(formPath("config", "comment", "teleport-options", "restricted-worlds", "worlds"), "受限世界设置影响的世界列表。");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "enable"), "启用或禁用传送预热期。");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "time"), "定义传送前的预热时间（秒）。");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "cancel-on-move"), "如果玩家在预热期间移动，则取消传送。");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "show-on-actionbar"), "在动作栏显示预热倒计时。");
        t.put(formPath("config", "comment", "limits", "enabled"), "启用或禁用玩家的家园限制。");
        t.put(formPath("config", "comment", "limits", "default"), "玩家可以设置的默认家园数量。");
        t.put(formPath("config", "comment", "limits", "examples"), "基于玩家组的限制示例。");
        t.put(formPath("config", "comment", "commands"), "!警告：下方大部分内容需要重启才能生效。");
        t.put(formPath("config", "comment", "commands", "command-cost"), "command-cost 需要 Vault 才能工作。");
        t.put(formPath("config", "comment", "commands", "homes", "types"), """
        定义家园向玩家显示的方式。
        选项：
        - text：以简单列表格式显示家园。
        - menu：打开图形菜单以选择家园。
        """);
        t.put(formPath("config", "comment", "permissions"), "插件使用的权限节点");
        t.put(formPath("config", "comment", "permissions", "bypass", "limit"), "绕过家园限制的权限");
        t.put(formPath("config", "comment", "permissions", "bypass", "dimensional-teleportation"), "绕过跨维度传送限制的权限");
        t.put(formPath("config", "comment", "permissions", "bypass", "safe-teleportation"), "绕过安全传送检查的权限");
        t.put(formPath("config", "comment", "permissions", "bypass", "restricted-worlds"), "绕过受限世界检查的权限");
        t.put(formPath("config", "comment", "permissions", "bypass", "warmup"), "绕过传送预热的权限");
        t.put(formPath("config", "comment", "permissions", "bypass", "command-costs"), "绕过命令费用的权限");
        t.put(formPath("config", "comment", "permissions", "bypass", "command-cooldowns"), "绕过命令冷却时间的权限");

        t.put("header", """
                ####################################################################################################
                # +----------------------------------------------------------------------------------------------+ #
                # |                                                                                              | #
                # |                                       zHomes — 语言文件                                        | #
                # |                                                                                              | #
                # |   • Wiki:        https://docs.yleoft.me/zhomes                                               | #
                # |   • Discord:     https://discord.gg/yCdhVDgn4K                                               | #
                # |   • GitHub:      https://github.com/yL3oft/zHomes                                            | #
                # |                                                                                              | #
                # +----------------------------------------------------------------------------------------------+ #
                ####################################################################################################
                """);

        // comments
        t.put(formPath("comments", "hooks"),
                "在这里您可以管理 Hook 消息。");
        t.put(formPath("comments", "teleport-warmup"),
                "与传送准备时间相关的消息。");
        t.put(formPath("comments", "commands"),
                "与命令相关的消息。");
        t.put(formPath("comments", "commands", "no-permission"),
                "这里是可在多个命令中使用的消息。");
        t.put(formPath("comments", "commands", "main"),
                "以下是各命令的专属消息。");

        // hooks
        t.put(formPath("hooks", "griefprevention", "cant-set-homes"),
                "%prefix% <red>您无法在此区域设置 home。");
        t.put(formPath("hooks", "worldguard", "cant-use-homes"),
                "%prefix% <red>您无法在此处使用 home。");
        t.put(formPath("hooks", "worldguard", "cant-set-homes"),
                "%prefix% <red>您无法在此处设置 home。");
        t.put(formPath("hooks", "vault", "cant-afford-command"),
                "%prefix% <red>您需要 <gold>$%cost% <red>才能执行此命令。");

        // teleport-warmup
        t.put(formPath("teleport-warmup", "warmup"),
                "%prefix% <green>将在 %time% 秒后传送！请勿移动！");
        t.put(formPath("teleport-warmup", "warmup-actionbar"),
                "<green>将在 %time% 秒后传送…");
        t.put(formPath("teleport-warmup", "cancelled"),
                "%prefix% <red>您移动了！传送已取消。");
        t.put(formPath("teleport-warmup", "cancelled-actionbar"),
                "<red>您移动了！传送已取消。");

        // commands - general
        t.put(formPath("commands", "no-permission"),
                "%prefix% <red>您没有权限执行此命令。");
        t.put(formPath("commands", "only-players"),
                "%prefix% <red>只有玩家才能执行此命令。");
        t.put(formPath("commands", "in-cooldown"),
                "%prefix% <red>您需等待 %time% 秒后才能再次使用此命令。");
        t.put(formPath("commands", "home-already-exist"),
                "%prefix% <red>您已有同名 home。");
        t.put(formPath("commands", "home-doesnt-exist"),
                "%prefix% <red>您没有该名称的 home。");
        t.put(formPath("commands", "home-doesnt-exist-others"),
                "%prefix% <yellow>%player% <red>没有该名称的 home。");
        t.put(formPath("commands", "cant-use-2dot"),
                "%prefix% <red>此命令中不能使用 <yellow>':'。");
        t.put(formPath("commands", "cant-find-player"),
                "%prefix% <red>未找到该玩家。");
        t.put(formPath("commands", "unable-to-find-safe-location"),
                "%prefix% <red>无法找到安全的传送地点。");
        t.put(formPath("commands", "world-restricted-sethome"),
                "%prefix% <red>您无法在此世界设置 home。");
        t.put(formPath("commands", "world-restricted-home"),
                "%prefix% <red>您无法传送到该世界的 home。");

        // commands.main.help
        t.put(formPath("commands", "main", "help", "help-perm"), """
                %prefix% <aqua>用法 <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>help <gray>显示此帮助信息
                <red>-> <yellow>/%command% <green>info <gray>显示插件信息
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                <red>-> <yellow>/%command% <green>(version|ver) <gold>[update]
                <red>-> <yellow>/%command% <green>nearhomes <gold>(半径) <gray>列出指定半径内的附近 home
                <red>-> <yellow>/%command% <green>parse <gold>(玩家) (文本) <gray>解析指定玩家的占位符文本
                <red>-> <yellow>/%command% <green>purge (<玩家>|*) <gold>[-world] [-startwith] [-endwith] [-player] <gray>使用过滤器清除家
                <red>-> <yellow>/%command% <green>converter (<converter-type>) <gray>将数据从一个地方转移到另一个地方
                <red>-> <yellow>/%command% <green>export <gray>将所有 home 导出到一个文件
                <red>-> <yellow>/%command% <green>import (<file>) <gray>从一个文件导入 home
                """);
        t.put(formPath("commands", "main", "help", "help-noperm"), """
                %prefix% <aqua>用法 <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>(help|?) <gray>显示此帮助信息
                <red>-> <yellow>/%command% <green>(version|ver) <gray>显示插件版本
                """);

        // commands.main.info
        t.put(formPath("commands", "main", "info", "output"), """
                %prefix% <aqua>运行中 <dark_aqua>%name% v%version% <aqua>作者 <dark_aqua>%author%<aqua>:
                %prefix% <aqua>- 服务器信息:
                %prefix% <dark_aqua>   Software: <white>%server.software%
                %prefix% <dark_aqua>   版本: <white>%server.version%
                %prefix% <dark_aqua>   需要更新: <white>%requpdate%
                %prefix% <dark_aqua>   语言: <white>%language%
                %prefix% <aqua>- 存储:
                %prefix% <dark_aqua>   类型: <white>%storage.type%
                %prefix% <dark_aqua>   用户: <white>%storage.users%
                %prefix% <dark_aqua>   Homes: <white>%storage.homes%
                %prefix% <aqua>- Hooks:
                %prefix% <dark_aqua>   PlaceholderAPI: <white>%use.placeholderapi%
                %prefix% <dark_aqua>   MiniPlaceholders: <white>%use.miniplaceholders%
                %prefix% <dark_aqua>   GriefPrevention: <white>%use.griefprevention%
                %prefix% <dark_aqua>   WorldGuard: <white>%use.worldguard%
                %prefix% <dark_aqua>   Vault: <white>%use.vault%
                """);
        t.put(formPath("commands", "main", "info", "requpdate-yes"),
                "<red>是 <gray>(使用 <yellow>/%command% version <gray>查看更多信息)");
        t.put(formPath("commands", "main", "info", "requpdate-no"),
                "<green>否");

        // commands.main.version
        t.put(formPath("commands", "main", "version", "output"),
                "%prefix% <aqua>当前版本：<green>%version%");

        // commands.main.reload
        t.put(formPath("commands", "main", "reload", "usage"), """
                %prefix% <aqua>用法 <yellow>/%command% <green>(reload|rl)<aqua>:
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                """);
        t.put(formPath("commands", "main", "reload", "output"),
                "%prefix% <green>插件已在 <aqua>%time%ms<green> 内重载。");
        t.put(formPath("commands", "main", "reload", "commands", "output"),
                "%prefix% <green>所有插件命令已在 <aqua>%time%ms<green> 内重载。");
        t.put(formPath("commands", "main", "reload", "config", "output"),
                "%prefix% <green>插件配置文件已在 <aqua>%time%ms<green> 内重载。");
        t.put(formPath("commands", "main", "reload", "languages", "output"),
                "%prefix% <green>插件语言已在 <aqua>%time%ms<green> 内重载。");

        // commands.main.nearhomes
        t.put(formPath("commands", "main", "nearhomes", "usage"),
                "<red>-> <yellow>/%command% nearhomes <green>[<半径>]");
        t.put(formPath("commands", "main", "nearhomes", "output"),
                "%prefix% <gray>您附近 <yellow>%radius% <gray>格内的 home：<white>%homes%");
        t.put(formPath("commands", "main", "nearhomes", "output-not-found"),
                "%prefix% <red>未在 <yellow>%radius% <red>格内找到 home。");
        t.put(formPath("commands", "main", "nearhomes", "home-string"),
                "<yellow><hover:show_text:'<green>点击传送.'><click:run_command:'/%homecommand% %owner%:%home%'>%home%</click></hover> <gray><hover:show_text:'<green>点击按玩家筛选.'><click:run_command:'/%maincommand% nearhomes %radius% -user %owner%'>(%owner%)</click></hover>");
        t.put(formPath("commands", "main", "nearhomes", "filtered-player", "output"),
                "%prefix% <gray>玩家 <yellow>%player% <gray>在您附近 <yellow>%radius% <gray>格内的 home：<white>%homes%");
        t.put(formPath("commands", "main", "nearhomes", "filtered-player", "home-string"),
                "<yellow><hover:show_text:'<green>点击传送.'><click:run_command:'/%homecommand% %player%:%home%'>%home%</click></hover>");

        // commands.main.parse
        t.put(formPath("commands", "main", "parse", "usage"),
                "<red>-> <yellow>/%command% parse <gold>(玩家) (文本)");
        t.put(formPath("commands", "main", "parse", "output"),
                "%prefix% <gray>解析文本：<white>%parsed%");

        // commands.main.purge
        t.put(formPath("commands", "main", "purge", "usage"), """
                %prefix% <aqua><yellow>/%command% purge<aqua>的用法:
                <red>-> <yellow>/%command% purge <green>(<玩家>|*) <gray>清除玩家或所有人的所有家
                <red>-> <yellow>/%command% purge <green>(<玩家>|*) <gold>-world <green>(世界) <gray>清除特定世界中的家
                <red>-> <yellow>/%command% purge <green>(<玩家>|*) <gold>-startwith <green>(前缀) <gray>清除以前缀开头的家
                <red>-> <yellow>/%command% purge <green>(<玩家>|*) <gold>-endwith <green>(后缀) <gray>清除以后缀结尾的家
                <red>-> <yellow>/%command% purge <green>* <gold>-player <green>(玩家) <gray>仅清除特定玩家的家
                <aqua>您可以组合多个过滤器:
                <red>-> <yellow>/%command% purge <green>* <gold>-world <green>world_nether <gold>-startwith <green>temp_
                <red>-> <yellow>/%command% purge <green>玩家名 <gold>-world <green>world_the_end <gold>-endwith <green>_old
                """);
        t.put(formPath("commands", "main", "purge", "output"),
                "%prefix% <green>成功清除了 <yellow>%amount% <green>个家!");

        // commands.main.converter
        t.put(formPath("commands", "main", "converter", "usage"), """
                %prefix% <aqua>用法 <yellow>/%command% <green>converter<aqua>:
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
        t.put(formPath("commands", "main", "converter", "output"),
                "%prefix% <green>所有数据已转换！");
        t.put(formPath("commands", "main", "converter", "error"),
                "%prefix% <red>转换数据时出错，请检查服务器控制台。");

        // commands.main.export
        t.put(formPath("commands", "main", "export", "output"),
                "%prefix% <green>所有 home 已导出到 <yellow>%file%<green>！");
        t.put(formPath("commands", "main", "export", "error"),
                "%prefix% <red>导出数据时出错，请检查服务器控制台。");

        // commands.main.import
        t.put(formPath("commands", "main", "import", "usage"),
                "<red>-> <yellow>/%command% import <green>(<文件>)");
        t.put(formPath("commands", "main", "import", "output"),
                "%prefix% <green>已从 <yellow>%file%<green> 导入所有 home！");
        t.put(formPath("commands", "main", "import", "file-not-found"),
                "%prefix% <red>未找到文件 <yellow>%file%。");
        t.put(formPath("commands", "main", "import", "error"),
                "%prefix% <red>导入数据时出错，请检查服务器控制台。");

        // commands.sethome
        t.put(formPath("commands", "sethome", "output"),
                "%prefix% <green>Home <yellow>%home% <green>已设置在您的位置。");
        t.put(formPath("commands", "sethome", "limit-reached"),
                "<red>您已达到 home 上限，无法再设置更多 home <yellow>(%limit% homes)<red>！");

        // commands.delhome
        t.put(formPath("commands", "delhome", "output"),
                "%prefix% <red>Home <yellow>%home% <red>已删除。");

        // commands.home
        t.put(formPath("commands", "home", "output"),
                "%prefix% <green>已传送至 <yellow>%home%<green>…");
        t.put(formPath("commands", "home", "cant-dimensional-teleport"),
                "<red>传送已取消！跨维传送已禁用。");

        // commands.home.rename
        t.put(formPath("commands", "home", "rename", "usage"),
                "<red>-> <yellow>/%command% <green>rename (Home) (新名称)");
        t.put(formPath("commands", "home", "rename", "output"),
                "%prefix% <green>Home <yellow>%home% <green>已重命名为 <yellow>%newname%<green>。");
        t.put(formPath("commands", "home", "rename", "same-name"),
                "%prefix% <red>不能将 home 重命名为相同的名称。");

        // commands.homes
        t.put(formPath("commands", "homes", "output"),
                "%prefix% <gray>您的 home (%amount%)：<white>%homes%");
        t.put(formPath("commands", "homes", "home-string"),
                "<reset><hover:show_text:'<green>点击传送.'><click:run_command:'/%homecommand% %home%'>%home%</click></hover>");
        t.put(formPath("commands", "homes", "invalid-page"),
                "%prefix% <red>页码无效！请输入大于 0 的数字。");

        // commands.homes.others
        t.put(formPath("commands", "homes", "others", "output"),
                "%prefix% <gray>玩家 <yellow>%player% <gray>的 home (%amount%)：<white>%homes%");
        t.put(formPath("commands", "homes", "others", "home-string"),
                "<reset><hover:show_text:'<green>点击传送.'><click:run_command:'/%homecommand% %player%:%home%'>%home%</click></hover>");

        return t;
    }

}