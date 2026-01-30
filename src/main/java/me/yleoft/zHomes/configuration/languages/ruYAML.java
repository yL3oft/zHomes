package me.yleoft.zHomes.configuration.languages;

public class ruYAML extends LanguageBuilder {

    public ruYAML() {
        super("ru");
    }

    public void buildLang() {
        header("""
                ####################################################################################################
                # +----------------------------------------------------------------------------------------------+ #
                # |                                                                                              | #
                # |                                  zHomes – Языковой Файл                                      | #
                # |                                                                                              | #
                # |   • Wiki:        https://docs.yleoft.me/zhomes                                               | #
                # |   • Discord:     https://discord.gg/yCdhVDgn4K                                               | #
                # |   • GitHub:      https://github.com/yL3oft/zHomes                                            | #
                # |                                                                                              | #
                # +----------------------------------------------------------------------------------------------+ #
                ####################################################################################################
                """);

        commentSection("hooks", "Здесь вы можете управлять сообщениями хуков.");
        addDefault(formPath("hooks", "griefprevention", "cant-set-homes"),
                "%prefix% <red>Вы не можете устанавливать дома в этой области.");

        addDefault(formPath("hooks", "worldguard", "cant-use-homes"),
                "%prefix% <red>Вы не можете использовать дома здесь.");
        addDefault(formPath("hooks", "worldguard", "cant-set-homes"),
                "%prefix% <red>Вы не можете установить дом здесь.");

        addDefault(formPath("hooks", "vault", "cant-afford-command"),
                "%prefix% <red>Вам нужно <gold>$%cost% <red>для выполнения этой команды.");

        commentSection("teleport-warmup", "Сообщения, связанные с временем подготовки телепортации.");
        addDefault(formPath("teleport-warmup", "warmup"),
                "%prefix% <green>Телепортация через %time% секунд... Не двигайтесь!");
        addDefault(formPath("teleport-warmup", "warmup-actionbar"),
                "<green>Телепортация через %time% секунд...");
        addDefault(formPath("teleport-warmup", "cancelled"),
                "%prefix% <red>Вы сдвинулись! Телепортация отменена.");
        addDefault(formPath("teleport-warmup", "cancelled-actionbar"),
                "<red>Вы сдвинулись! Телепортация отменена.");

        commentSection("commands", "Сообщения, связанные с командами.");
        comment(false, "Здесь вы найдете сообщения, которые могут использоваться в нескольких командах.");
        addDefault(formPath("commands", "no-permission"),
                "%prefix% <red>У вас нет прав для выполнения этой команды.");
        addDefault(formPath("commands", "only-players"),
                "%prefix% <red>Только игроки могут выполнить эту команду.");
        addDefault(formPath("commands", "in-cooldown"),
                "%prefix% <red>Вы должны подождать %time% секунд перед повторным использованием этой команды.");
        addDefault(formPath("commands", "home-already-exist"),
                "%prefix% <red>У вас уже есть дом с таким именем.");
        addDefault(formPath("commands", "home-doesnt-exist"),
                "%prefix% <red>У вас нет дома с таким именем.");
        addDefault(formPath("commands", "home-doesnt-exist-others"),
                "%prefix% <yellow>%player% <red>не имеет дома с таким именем.");
        addDefault(formPath("commands", "cant-use-2dot"),
                "%prefix% <red>Вы не можете использовать <yellow>':' <red>в этой команде.");
        addDefault(formPath("commands", "cant-find-player"),
                "%prefix% <red>Этот игрок не был найден.");
        addDefault(formPath("commands", "unable-to-find-safe-location"),
                "%prefix% <red>Невозможно найти безопасное место для телепортации.");
        addDefault(formPath("commands", "world-restricted-sethome"),
                "%prefix% <red>Вы не можете устанавливать дома в этом мире.");
        addDefault(formPath("commands", "world-restricted-home"),
                "%prefix% <red>Вы не можете телепортироваться к домам в этом мире.");

        commentSection(formPath("commands", "main"), "Ниже вы найдете специфические сообщения для команд.");

        // commands.main.help
        addDefault(formPath("commands", "main", "help", "help-perm"), """
                %prefix% <aqua>Использование <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>help <gray>Показывает это сообщение помощи
                <red>-> <yellow>/%command% <green>info <gray>Показывает информацию о плагине
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                <red>-> <yellow>/%command% <green>(version|ver) <gold>[update]
                <red>-> <yellow>/%command% <green>nearhomes <gold>(<радиус>) <gray>Список домов рядом с вами в определенном радиусе
                <red>-> <yellow>/%command% <green>parse <gold>(Игрок) (Строка) <gray>Анализирует строку с плейсхолдерами для определенного игрока
                <red>-> <yellow>/%command% <green>converter (<тип-конвертера>) <gray>Конвертирует данные из одного места в другое
                <red>-> <yellow>/%command% <green>export <gray>Экспортирует все дома в один файл
                <red>-> <yellow>/%command% <green>import (<файл>) <gray>Импортирует дома из одного файла
                """);
        addDefault(formPath("commands", "main", "help", "help-noperm"), """
                %prefix% <aqua>Использование <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>(help|?) <gray>Показывает это сообщение помощи
                <red>-> <yellow>/%command% <green>(version|ver) <gray>Показывает версию плагина
                """);

        // commands.main.info
        addDefault(formPath("commands", "main", "info", "output"), """
                %prefix% <aqua>Запущен <dark_aqua>%name% v%version% <aqua>от <dark_aqua>%author%<aqua>:
                %prefix% <aqua>- Информация о сервере:
                %prefix% <dark_aqua>   Программа: <white>%server.software%
                %prefix% <dark_aqua>   Версия: <white>%server.version%
                %prefix% <dark_aqua>   Требуется обновление: <white>%requpdate%
                %prefix% <dark_aqua>   Язык: <white>%language%
                %prefix% <aqua>- Хранилище:
                %prefix% <dark_aqua>   Тип: <white>%storage.type%
                %prefix% <dark_aqua>   Пользователи: <white>%storage.users%
                %prefix% <dark_aqua>   Дома: <white>%storage.homes%
                %prefix% <aqua>- Хуки:
                %prefix% <dark_aqua>   PlaceholderAPI: <white>%use.placeholderapi%
                %prefix% <dark_aqua>   GriefPrevention: <white>%use.griefprevention%
                %prefix% <dark_aqua>   WorldGuard: <white>%use.worldguard%
                %prefix% <dark_aqua>   Vault: <white>%use.vault%
                """);
        addDefault(formPath("commands", "main", "info", "requpdate-yes"),
                "<red>Да <gray>(Используйте <yellow>/%command% version <gray>для дополнительной информации)");
        addDefault(formPath("commands", "main", "info", "requpdate-no"),
                "<green>Нет");

        // commands.main.version
        addDefault(formPath("commands", "main", "version", "output"),
                "%prefix% <aqua>Текущая версия: <green>%version%");
        addDefault(formPath("commands", "main", "version", "update", "output"),
                "%prefix% <green>zHomes обновлен до последней версии <yellow>(%update%)<green>, пожалуйста, перезагрузите сервер, чтобы применить изменения.");
        addDefault(formPath("commands", "main", "version", "update", "no-update"),
                "%prefix% <green>Вы уже используете последнюю версию zHomes.");

        // commands.main.reload
        addDefault(formPath("commands", "main", "reload", "usage"), """
                %prefix% <aqua>Использование <yellow>/%command% <green>(reload|rl)<aqua>:
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                """);
        addDefault(formPath("commands", "main", "reload", "output"),
                "%prefix% <green>Плагин перезагружен за <aqua>%time%ms<green>.");
        addDefault(formPath("commands", "main", "reload", "commands", "output"),
                "%prefix% <green>Все команды плагина перезагружены за <aqua>%time%ms<green>.");
        addDefault(formPath("commands", "main", "reload", "config", "output"),
                "%prefix% <green>Файл конфигурации плагина перезагружен за <aqua>%time%ms<green>.");
        addDefault(formPath("commands", "main", "reload", "languages", "output"), "%prefix% <green>Языки плагина перезагружены за <aqua>%time%ms<green>.");

        // commands.main.nearhomes
        addDefault(formPath("commands", "main", "nearhomes", "usage"),
                "<red>-> <yellow>/%command% nearhomes <green>[<Радиус>]");
        addDefault(formPath("commands", "main", "nearhomes", "output"),
                "%prefix% <gray>Дома рядом с вами в радиусе <yellow>%radius% <gray>блоков: <white>%homes%");
        addDefault(formPath("commands", "main", "nearhomes", "output-not-found"),
                "%prefix% <red>Дома не найдены в радиусе <yellow>%radius% <red>блоков.");
        addDefault(formPath("commands", "main", "nearhomes", "home-string"),
                "<yellow>%home% <gray>(%owner%)");

        // commands.main.parse
        addDefault(formPath("commands", "main", "parse", "usage"),
                "<red>-> <yellow>/%command% parse <gold>(Игрок) (Строка)");
        addDefault(formPath("commands", "main", "parse", "output"),
                "%prefix% <gray>Проанализированный текст: <white>%parsed%");

        // commands.main.converter
        addDefault(formPath("commands", "main", "converter", "usage"), """
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
        addDefault(formPath("commands", "main", "converter", "output"),
                "%prefix% <green>Все данные конвертированы!");
        addDefault(formPath("commands", "main", "converter", "error"),
                "%prefix% <red>Что-то пошло не так при конвертации данных, проверьте консоль вашего сервера.");

        // commands.main.export
        addDefault(formPath("commands", "main", "export", "output"),
                "%prefix% <green>Все дома экспортированы в <yellow>%file%<green>!");
        addDefault(formPath("commands", "main", "export", "error"),
                "%prefix% <red>Что-то пошло не так при экспорте данных, проверьте консоль вашего сервера.");

        // commands.main.import
        addDefault(formPath("commands", "main", "import", "usage"),
                "<red>-> <yellow>/%command% import <green>(<файл>)");
        addDefault(formPath("commands", "main", "import", "output"),
                "%prefix% <green>Все дома импортированы из <yellow>%file%<green>!");
        addDefault(formPath("commands", "main", "import", "file-not-found"),
                "%prefix% <red>Файл <yellow>%file% <red>не был найден.");
        addDefault(formPath("commands", "main", "import", "error"),
                "%prefix% <red>Что-то пошло не так при импорте данных, проверьте консоль вашего сервера.");

        // commands.sethome
        addDefault(formPath("commands", "sethome", "usage"),
                "<red>-> <yellow>/%command% <green>(Дом)");
        addDefault(formPath("commands", "sethome", "output"),
                "%prefix% <green>Дом <yellow>%home% <green>установлен на вашей позиции.");
        addDefault(formPath("commands", "sethome", "limit-reached"),
                "<red>Вы не можете установить больше домов, потому что достигли лимита <yellow>(%limit% домов)<red>!");

        // commands.delhome
        addDefault(formPath("commands", "delhome", "usage"),
                "<red>-> <yellow>/%command% <green>(Дом)");
        addDefault(formPath("commands", "delhome", "output"),
                "%prefix% <red>Дом <yellow>%home% <red>удален.");

        // commands.home
        addDefault(formPath("commands", "home", "usage"),
                "<red>-> <yellow>/%command% <green>(Дом)");
        addDefault(formPath("commands", "home", "output"),
                "%prefix% <green>Телепортация в <yellow>%home%<green>...");
        addDefault(formPath("commands", "home", "cant-dimensional-teleport"),
                "<red>Ваша телепортация была отменена! Межпространственная телепортация отключена.");

        // commands.home.rename
        addDefault(formPath("commands", "home", "rename", "usage"),
                "<red>-> <yellow>/%command% <green>rename (Дом) (НовоеИмя)");
        addDefault(formPath("commands", "home", "rename", "output"),
                "%prefix% <green>Дом <yellow>%home% <green>переименован в <yellow>%newname%<green>.");
        addDefault(formPath("commands", "home", "rename", "same-name"),
                "%prefix% <red>Вы не можете переименовать дом с тем же именем.");

        // commands.homes
        addDefault(formPath("commands", "homes", "output"),
                "%prefix% <gray>Ваши дома (%amount%): <white>%homes%");
        addDefault(formPath("commands", "homes", "invalid-page"),
                "%prefix% <red>Неверный номер страницы! Используйте число больше 0.");

        // commands.homes.others
        addDefault(formPath("commands", "homes", "others", "output"),
                "%prefix% <gray>Дома игрока <yellow>%player% <gray>(%amount%): <white>%homes%");

        build();
    }

}