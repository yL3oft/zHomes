package me.yleoft.zHomes.configuration.languages;

import java.util.HashMap;
import java.util.Map;

public class ruYAML extends LanguageBuilder {

    public ruYAML() {
        super("ru");
    }

    @Override
    protected Map<String, String> translations() {
        Map<String, String> t = new HashMap<>();

        // config comments
        t.put(formPath("config", "comment", "header1"), "# |                                 Ссылки на плагин и поддержка                                 | #");
        t.put(formPath("config", "comment", "header2"), "# |   zHomes (c) yL3oft — выпущено под лицензией MIT.                                            | #");
        t.put(formPath("config", "comment", "database"), "Редактируйте настройки базы данных ниже");
        t.put(formPath("config", "comment", "database", "type"), """
        Здесь вы можете указать способ хранения данных плагина.
        ВАРИАНТЫ:
        - H2 (Предпочтительнее SQLite)
        - SQLite
        - MariaDB (Предпочтительнее MySQL)
        - MySQL
        ПО УМОЛЧАНИЮ: H2
        """);
        t.put(formPath("config", "comment", "pool-size"), "# ВНИМАНИЕ: НЕ ИЗМЕНЯЙТЕ НИЧЕГО НИЖЕ, ЕСЛИ НЕ ЗНАЕТЕ, ЧТО ДЕЛАЕТЕ");
        t.put(formPath("config", "comment", "general", "language"), """
        Здесь вы можете задать язык плагина. Все языки можно найти, редактировать и создавать в директории языков.
        ДОСТУПНЫЕ ЯЗЫКИ: [de, en, es, fr, it, nl, pl, pt-br, ru, zhcn, <custom>]
        """);
        t.put(formPath("config", "comment", "general", "announce-update"), "Включить или отключить объявление доступных обновлений в консоли и игрокам с соответствующим разрешением.");
        t.put(formPath("config", "comment", "general", "metrics"), """
        Включить или отключить сбор метрик для улучшения плагина.
        Все собираемые данные анонимны и используются исключительно в статистических целях.
        !ВНИМАНИЕ: Требует перезапуска сервера для вступления в силу!
        """);
        t.put(formPath("config", "comment", "general", "debug-mode"), "Включить или отключить режим отладки для более подробного вывода журнала.");
        t.put(formPath("config", "comment", "teleport-options"), "Настройки, связанные с поведением телепортации");
        t.put(formPath("config", "comment", "teleport-options", "enable-safe-teleport"), "Включить или отключить безопасную телепортацию, чтобы предотвратить телепортацию игроков в опасные места.");
        t.put(formPath("config", "comment", "teleport-options", "dimensional-teleportation"), "Включить или отключить межмировую телепортацию, позволяя игрокам перемещаться между различными мирами или измерениями.");
        t.put(formPath("config", "comment", "teleport-options", "play-sound"), "Воспроизводить звуковой эффект при телепортации игрока.");
        t.put(formPath("config", "comment", "teleport-options", "restricted-worlds", "enable"), "Включить или отключить ограничение телепортации в определённые миры.");
        t.put(formPath("config", "comment", "teleport-options", "restricted-worlds", "mode"), """
        Определить режим для ограниченных миров.
        ВАРИАНТЫ:
        - blacklist: Игроки не могут телепортироваться в перечисленные миры.
        - whitelist: Игроки могут телепортироваться только в перечисленные миры.
        """);
        t.put(formPath("config", "comment", "teleport-options", "restricted-worlds", "worlds"), "Список миров, на которые распространяется настройка ограниченных миров.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "enable"), "Включить или отключить период разогрева перед телепортацией.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "time"), "Определить время разогрева в секундах перед телепортацией.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "cancel-on-move"), "Отменить телепортацию, если игрок двигается в период разогрева.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "show-on-actionbar"), "Отображать обратный отсчёт разогрева на панели действий.");
        t.put(formPath("config", "comment", "limits", "enabled"), "Включить или отключить лимиты домов для игроков.");
        t.put(formPath("config", "comment", "limits", "default"), "Стандартное количество домов, которые может установить игрок.");
        t.put(formPath("config", "comment", "limits", "examples"), "Примеры лимитов на основе групп игроков.");
        t.put(formPath("config", "comment", "commands"), "!ВНИМАНИЕ: Почти всё ниже требует перезапуска для применения.");
        t.put(formPath("config", "comment", "commands", "command-cost"), "command-cost требует Vault для работы.");
        t.put(formPath("config", "comment", "commands", "homes", "types"), """
        Определить, как дома будут отображаться игроку.
        ВАРИАНТЫ:
        - text: Отображает дома в простом формате списка.
        - menu: Открывает графическое меню для выбора домов.
        """);
        t.put(formPath("config", "comment", "permissions"), "Узлы разрешений, используемые плагином");
        t.put(formPath("config", "comment", "permissions", "bypass", "limit"), "Разрешение на обход лимитов домов");
        t.put(formPath("config", "comment", "permissions", "bypass", "dimensional-teleportation"), "Разрешение на обход ограничений межмировой телепортации");
        t.put(formPath("config", "comment", "permissions", "bypass", "safe-teleportation"), "Разрешение на обход проверок безопасной телепортации");
        t.put(formPath("config", "comment", "permissions", "bypass", "restricted-worlds"), "Разрешение на обход проверок ограниченных миров");
        t.put(formPath("config", "comment", "permissions", "bypass", "warmup"), "Разрешение на обход разогрева телепортации");
        t.put(formPath("config", "comment", "permissions", "bypass", "command-costs"), "Разрешение на обход стоимости команд");
        t.put(formPath("config", "comment", "permissions", "bypass", "command-cooldowns"), "Разрешение на обход времени восстановления команд");

        t.put("header", """
                ####################################################################################################
                # +----------------------------------------------------------------------------------------------+ #
                # |                                                                                              | #
                # |                                   zHomes — Языковой Файл                                     | #
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
                "Здесь вы можете управлять сообщениями хуков.");
        t.put(formPath("comments", "teleport-warmup"),
                "Сообщения, связанные с отожкой телепортации.");
        t.put(formPath("comments", "commands"),
                "Сообщения, связанные с командами.");
        t.put(formPath("comments", "commands", "no-permission"),
                "Здесь вы найдёте сообщения, которые можно использовать в нескольких командах.");
        t.put(formPath("comments", "commands", "main"),
                "Ниже вы найдёте сообщения, специфичные для каждой команды.");

        // hooks
        t.put(formPath("hooks", "griefprevention", "cant-set-homes"),
                "%prefix% <red>Вы не можете устанавливать home в этой области.");
        t.put(formPath("hooks", "worldguard", "cant-use-homes"),
                "%prefix% <red>Вы не можете использовать home здесь.");
        t.put(formPath("hooks", "worldguard", "cant-set-homes"),
                "%prefix% <red>Вы не можете установить home здесь.");
        t.put(formPath("hooks", "vault", "cant-afford-command"),
                "%prefix% <red>Вам нужно <gold>$%cost% <red>для выполнения этой команды.");

        // teleport-warmup
        t.put(formPath("teleport-warmup", "warmup"),
                "%prefix% <green>Телепортация через %time% сек... Не двигайтесь!");
        t.put(formPath("teleport-warmup", "warmup-actionbar"),
                "<green>Телепортация через %time% сек...");
        t.put(formPath("teleport-warmup", "cancelled"),
                "%prefix% <red>Вы двинулись! Телепортация отменена.");
        t.put(formPath("teleport-warmup", "cancelled-actionbar"),
                "<red>Вы двинулись! Телепортация отменена.");

        // commands - general
        t.put(formPath("commands", "no-permission"),
                "%prefix% <red>У вас нет прав для выполнения этой команды.");
        t.put(formPath("commands", "only-players"),
                "%prefix% <red>Только игроки могут выполнять эту команду.");
        t.put(formPath("commands", "in-cooldown"),
                "%prefix% <red>Вы должны подождать %time% секунд перед повторным использованием.");
        t.put(formPath("commands", "home-already-exist"),
                "%prefix% <red>У вас уже есть home с таким названием.");
        t.put(formPath("commands", "home-doesnt-exist"),
                "%prefix% <red>У вас нет home с таким названием.");
        t.put(formPath("commands", "home-doesnt-exist-others"),
                "%prefix% <yellow>%player% <red>нет home с таким названием.");
        t.put(formPath("commands", "cant-use-2dot"),
                "%prefix% <red>Вы не можете использовать <yellow>':' <red>в этой команде.");
        t.put(formPath("commands", "cant-find-player"),
                "%prefix% <red>Игрок не найден.");
        t.put(formPath("commands", "unable-to-find-safe-location"),
                "%prefix% <red>Не удалось найти безопасное место для телепортации.");
        t.put(formPath("commands", "world-restricted-sethome"),
                "%prefix% <red>Вы не можете устанавливать home в этом мире.");
        t.put(formPath("commands", "world-restricted-home"),
                "%prefix% <red>Вы не можете телепортироваться к home в том мире.");

        // commands.main.help
        t.put(formPath("commands", "main", "help", "help-perm"), """
                %prefix% <aqua>Использование <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>help <gray>Показывает это сообщение помощи
                <red>-> <yellow>/%command% <green>info <gray>Показывает информацию о плагине
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                <red>-> <yellow>/%command% <green>(version|ver) <gold>[update]
                <red>-> <yellow>/%command% <green>nearhomes <gold>(Радиус) <gray>Список home поблизости в заданном радиусе
                <red>-> <yellow>/%command% <green>parse <gold>(Игрок) (Текст) <gray>Обрабатывает текст с плейсхолдерами для определённого игрока
                <red>-> <yellow>/%command% <green>purge (<игрок>|*) <gold>[-world] [-startwith] [-endwith] [-player] <gray>Очистка домов с фильтрами
                <red>-> <yellow>/%command% <green>converter (<converter-type>) <gray>Конвертирует данные из одного места в другое
                <red>-> <yellow>/%command% <green>export <gray>Экспортирует все home в один файл
                <red>-> <yellow>/%command% <green>import (<file>) <gray>Импортирует home из одного файла
                """);
        t.put(formPath("commands", "main", "help", "help-noperm"), """
                %prefix% <aqua>Использование <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>(help|?) <gray>Показывает это сообщение помощи
                <red>-> <yellow>/%command% <green>(version|ver) <gray>Показывает версию плагина
                """);

        // commands.main.info
        t.put(formPath("commands", "main", "info", "output"), """
                %prefix% <aqua>Запущен <dark_aqua>%name% v%version% <aqua>от <dark_aqua>%author%<aqua>:
                %prefix% <aqua>- Инфо сервера:
                %prefix% <dark_aqua>   Software: <white>%server.software%
                %prefix% <dark_aqua>   Версия: <white>%server.version%
                %prefix% <dark_aqua>   Требуется обновление: <white>%requpdate%
                %prefix% <dark_aqua>   Язык: <white>%language%
                %prefix% <aqua>- Хранилище:
                %prefix% <dark_aqua>   Тип: <white>%storage.type%
                %prefix% <dark_aqua>   Пользователи: <white>%storage.users%
                %prefix% <dark_aqua>   Homes: <white>%storage.homes%
                %prefix% <aqua>- Hooks:
                %prefix% <dark_aqua>   PlaceholderAPI: <white>%use.placeholderapi%
                %prefix% <dark_aqua>   MiniPlaceholders: <white>%use.miniplaceholders%
                %prefix% <dark_aqua>   GriefPrevention: <white>%use.griefprevention%
                %prefix% <dark_aqua>   WorldGuard: <white>%use.worldguard%
                %prefix% <dark_aqua>   Vault: <white>%use.vault%
                """);
        t.put(formPath("commands", "main", "info", "requpdate-yes"),
                "<red>Да <gray>(Используйте <yellow>/%command% version <gray>для подробностей)");
        t.put(formPath("commands", "main", "info", "requpdate-no"),
                "<green>Нет");

        // commands.main.version
        t.put(formPath("commands", "main", "version", "output"),
                "%prefix% <aqua>Текущая версия: <green>%version%");

        // commands.main.reload
        t.put(formPath("commands", "main", "reload", "usage"), """
                %prefix% <aqua>Использование <yellow>/%command% <green>(reload|rl)<aqua>:
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                """);
        t.put(formPath("commands", "main", "reload", "output"),
                "%prefix% <green>Плагин перезагружен за <aqua>%time%ms<green>.");
        t.put(formPath("commands", "main", "reload", "commands", "output"),
                "%prefix% <green>Все команды плагина перезагружены за <aqua>%time%ms<green>.");
        t.put(formPath("commands", "main", "reload", "config", "output"),
                "%prefix% <green>Файл конфигурации плагина перезагружен за <aqua>%time%ms<green>.");
        t.put(formPath("commands", "main", "reload", "languages", "output"),
                "%prefix% <green>Языки плагина перезагружены за <aqua>%time%ms<green>.");

        // commands.main.nearhomes
        t.put(formPath("commands", "main", "nearhomes", "usage"),
                "<red>-> <yellow>/%command% nearhomes <green>[<Радиус>]");
        t.put(formPath("commands", "main", "nearhomes", "output"),
                "%prefix% <gray>Home в вашей окрестности в <yellow>%radius% <gray>блоках: <white>%homes%");
        t.put(formPath("commands", "main", "nearhomes", "output-not-found"),
                "%prefix% <red>Home в радиусе <yellow>%radius% <red>блоков не найдены.");
        t.put(formPath("commands", "main", "nearhomes", "home-string"),
                "<yellow><hover:show_text:'<green>Нажмите для телепортации.'><click:run_command:'/%homecommand% %owner%:%home%'>%home%</click></hover> <gray><hover:show_text:'<green>Нажмите для фильтрации по игроку.'><click:run_command:'/%maincommand% nearhomes %radius% -user %owner%'>(%owner%)</click></hover>");
        t.put(formPath("commands", "main", "nearhomes", "filtered-player", "output"),
                "%prefix% <gray>Home игрока <yellow>%player% <gray>в вашей окрестности в <yellow>%radius% <gray>блоках: <white>%homes%");
        t.put(formPath("commands", "main", "nearhomes", "filtered-player", "home-string"),
                "<yellow><hover:show_text:'<green>Нажмите для телепортации.'><click:run_command:'/%homecommand% %player%:%home%'>%home%</click></hover>");

        // commands.main.parse
        t.put(formPath("commands", "main", "parse", "usage"),
                "<red>-> <yellow>/%command% parse <gold>(Игрок) (Текст)");
        t.put(formPath("commands", "main", "parse", "output"),
                "%prefix% <gray>Обработанный текст: <white>%parsed%");

        // commands.main.purge
        t.put(formPath("commands", "main", "purge", "usage"), """
                %prefix% <aqua>Использование <yellow>/%command% purge<aqua>:
                <red>-> <yellow>/%command% purge <green>(<игрок>|*) <gray>Очистить все дома игрока или всех
                <red>-> <yellow>/%command% purge <green>(<игрок>|*) <gold>-world <green>(мир) <gray>Очистить дома в определенном мире
                <red>-> <yellow>/%command% purge <green>(<игрок>|*) <gold>-startwith <green>(префикс) <gray>Очистить дома, начинающиеся с префикса
                <red>-> <yellow>/%command% purge <green>(<игрок>|*) <gold>-endwith <green>(суффикс) <gray>Очистить дома, заканчивающиеся суффиксом
                <red>-> <yellow>/%command% purge <green>* <gold>-player <green>(игроки) <gray>Очистить дома только для определенных игроков
                <aqua>Вы можете комбинировать несколько фильтров:
                <red>-> <yellow>/%command% purge <green>* <gold>-world <green>world_nether <gold>-startwith <green>temp_
                <red>-> <yellow>/%command% purge <green>ИмяИгрока <gold>-world <green>world_the_end <gold>-endwith <green>_old
                """);
        t.put(formPath("commands", "main", "purge", "output"),
                "%prefix% <green>Успешно очищено <yellow>%amount% <green>дом(ов)!");

        // commands.main.converter
        t.put(formPath("commands", "main", "converter", "usage"), """
                %prefix% <aqua>Использование <yellow>/%command% <green>converter<aqua>:
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
                "%prefix% <green>Все данные конвертированы!");
        t.put(formPath("commands", "main", "converter", "error"),
                "%prefix% <red>Произошла ошибка при конвертации, проверьте консоль сервера.");

        // commands.main.export
        t.put(formPath("commands", "main", "export", "output"),
                "%prefix% <green>Все home экспортированы в <yellow>%file%<green>!");
        t.put(formPath("commands", "main", "export", "error"),
                "%prefix% <red>Произошла ошибка при экспорте, проверьте консоль сервера.");

        // commands.main.import
        t.put(formPath("commands", "main", "import", "usage"),
                "<red>-> <yellow>/%command% import <green>(<файл>)");
        t.put(formPath("commands", "main", "import", "output"),
                "%prefix% <green>Все home импортированы из <yellow>%file%<green>!");
        t.put(formPath("commands", "main", "import", "file-not-found"),
                "%prefix% <red>Файл <yellow>%file% <red>не найден.");
        t.put(formPath("commands", "main", "import", "error"),
                "%prefix% <red>Произошла ошибка при импорте, проверьте консоль сервера.");

        // commands.sethome
        t.put(formPath("commands", "sethome", "output"),
                "%prefix% <green>Home <yellow>%home% <green>установлена в вашей позиции.");
        t.put(formPath("commands", "sethome", "limit-reached"),
                "<red>Вы не можете установить больше home, достигнут лимит <yellow>(%limit% homes)<red>!");

        // commands.delhome
        t.put(formPath("commands", "delhome", "output"),
                "%prefix% <red>Home <yellow>%home% <red>удалена.");

        // commands.home
        t.put(formPath("commands", "home", "output"),
                "%prefix% <green>Телепортирование к <yellow>%home%<green>...");
        t.put(formPath("commands", "home", "cant-dimensional-teleport"),
                "<red>Телепортация отменена! Межмирная телепортация отключена.");

        // commands.home.rename
        t.put(formPath("commands", "home", "rename", "usage"),
                "<red>-> <yellow>/%command% <green>rename (Home) (НовоеИмя)");
        t.put(formPath("commands", "home", "rename", "output"),
                "%prefix% <green>Home <yellow>%home% <green>переименована в <yellow>%newname%<green>.");
        t.put(formPath("commands", "home", "rename", "same-name"),
                "%prefix% <red>Нельзя переименовать home в то же имя.");

        // commands.homes
        t.put(formPath("commands", "homes", "output"),
                "%prefix% <gray>Ваши home (%amount%): <white>%homes%");
        t.put(formPath("commands", "homes", "home-string"),
                "<reset><hover:show_text:'<green>Нажмите для телепортации.'><click:run_command:'/%homecommand% %home%'>%home%</click></hover>");
        t.put(formPath("commands", "homes", "invalid-page"),
                "%prefix% <red>Неверный номер страницы! Используйте число больше 0.");

        // commands.homes.others
        t.put(formPath("commands", "homes", "others", "output"),
                "%prefix% <gray>Home игрока <yellow>%player% <gray>(%amount%): <white>%homes%");
        t.put(formPath("commands", "homes", "others", "home-string"),
                "<reset><hover:show_text:'<green>Нажмите для телепортации.'><click:run_command:'/%homecommand% %player%:%home%'>%home%</click></hover>");

        return t;
    }

}