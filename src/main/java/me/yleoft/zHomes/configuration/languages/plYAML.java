package me.yleoft.zHomes.configuration.languages;

public class plYAML extends LanguageBuilder {

    public plYAML() {
        super("pl");
    }

    public void buildLang() {
        header("""
                ####################################################################################################
                # +----------------------------------------------------------------------------------------------+ #
                # |                                                                                              | #
                # |                                  zHomes – Plik Językowy                                      | #
                # |                                                                                              | #
                # |   • Wiki:        https://docs.yleoft.me/zhomes                                               | #
                # |   • Discord:     https://discord.gg/yCdhVDgn4K                                               | #
                # |   • GitHub:      https://github.com/yL3oft/zHomes                                            | #
                # |                                                                                              | #
                # +----------------------------------------------------------------------------------------------+ #
                ####################################################################################################
                """);

        commentSection("hooks", "Tutaj możesz zarządzać wiadomościami hooków.");
        addDefault(formPath("hooks", "griefprevention", "cant-set-homes"),
                "%prefix% <red>Nie możesz ustawiać domów w tym obszarze.");

        addDefault(formPath("hooks", "worldguard", "cant-use-homes"),
                "%prefix% <red>Nie możesz używać domów tutaj.");
        addDefault(formPath("hooks", "worldguard", "cant-set-homes"),
                "%prefix% <red>Nie możesz ustawić domu tutaj.");

        addDefault(formPath("hooks", "vault", "cant-afford-command"),
                "%prefix% <red>Potrzebujesz <gold>$%cost% <red>aby wykonać to polecenie.");

        commentSection("teleport-warmup", "Wiadomości związane z czasem przygotowania teleportacji.");
        addDefault(formPath("teleport-warmup", "warmup"),
                "%prefix% <green>Teleportacja za %time% sekund... Nie ruszaj się!");
        addDefault(formPath("teleport-warmup", "warmup-actionbar"),
                "<green>Teleportacja za %time% sekund...");
        addDefault(formPath("teleport-warmup", "cancelled"),
                "%prefix% <red>Poruszyłeś się! Teleportacja anulowana.");
        addDefault(formPath("teleport-warmup", "cancelled-actionbar"),
                "<red>Poruszyłeś się! Teleportacja anulowana.");

        commentSection("commands", "Wiadomości związane z poleceniami.");
        comment(false, "Tutaj znajdziesz wiadomości, które mogą być używane w wielu poleceniach.");
        addDefault(formPath("commands", "no-permission"),
                "%prefix% <red>Nie masz uprawnień do wykonania tego polecenia.");
        addDefault(formPath("commands", "only-players"),
                "%prefix% <red>Tylko gracze mogą wykonać to polecenie.");
        addDefault(formPath("commands", "in-cooldown"),
                "%prefix% <red>Musisz poczekać %time% sekund przed ponownym użyciem tego polecenia.");
        addDefault(formPath("commands", "home-already-exist"),
                "%prefix% <red>Masz już dom o tej nazwie.");
        addDefault(formPath("commands", "home-doesnt-exist"),
                "%prefix% <red>Nie masz żadnego domu o tej nazwie.");
        addDefault(formPath("commands", "home-doesnt-exist-others"),
                "%prefix% <yellow>%player% <red>nie ma żadnego domu o tej nazwie.");
        addDefault(formPath("commands", "cant-use-2dot"),
                "%prefix% <red>Nie możesz używać <yellow>':' <red>w tym poleceniu.");
        addDefault(formPath("commands", "cant-find-player"),
                "%prefix% <red>Ten gracz nie został znaleziony.");
        addDefault(formPath("commands", "unable-to-find-safe-location"),
                "%prefix% <red>Nie można znaleźć bezpiecznej lokalizacji do teleportacji.");
        addDefault(formPath("commands", "world-restricted-sethome"),
                "%prefix% <red>Nie możesz ustawiać domów w tym świecie.");
        addDefault(formPath("commands", "world-restricted-home"),
                "%prefix% <red>Nie możesz teleportować się do domów w tym świecie.");

        commentSection(formPath("commands", "main"), "Poniżej znajdziesz specyficzne wiadomości dla poleceń.");

        // commands.main.help
        addDefault(formPath("commands", "main", "help", "help-perm"), """
                %prefix% <aqua>Użycie <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>help <gray>Pokazuje dokładnie tę wiadomość pomocy
                <red>-> <yellow>/%command% <green>info <gray>Pokazuje informacje o wtyczce
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                <red>-> <yellow>/%command% <green>(version|ver) <gold>[update]
                <red>-> <yellow>/%command% <green>nearhomes <gold>(<promień>) <gray>Wyświetla domy w pobliżu w określonym promieniu
                <red>-> <yellow>/%command% <green>parse <gold>(Gracz) (Tekst) <gray>Parsuje tekst z placeholderami dla określonego gracza
                <red>-> <yellow>/%command% <green>converter (<typ-konwertera>) <gray>Konwertuje dane z jednego miejsca do drugiego
                <red>-> <yellow>/%command% <green>export <gray>Eksportuje wszystkie domy do jednego pliku
                <red>-> <yellow>/%command% <green>import (<plik>) <gray>Importuje domy z jednego pliku
                """);
        addDefault(formPath("commands", "main", "help", "help-noperm"), """
                %prefix% <aqua>Użycie <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>(help|?) <gray>Pokazuje dokładnie tę wiadomość pomocy
                <red>-> <yellow>/%command% <green>(version|ver) <gray>Pokazuje wersję wtyczki
                """);

        // commands.main.info
        addDefault(formPath("commands", "main", "info", "output"), """
                %prefix% <aqua>Uruchomiono <dark_aqua>%name% v%version% <aqua>przez <dark_aqua>%author%<aqua>:
                %prefix% <aqua>- Informacje o serwerze:
                %prefix% <dark_aqua>   Oprogramowanie: <white>%server.software%
                %prefix% <dark_aqua>   Wersja: <white>%server.version%
                %prefix% <dark_aqua>   Wymaga aktualizacji: <white>%requpdate%
                %prefix% <dark_aqua>   Język: <white>%language%
                %prefix% <aqua>- Przechowywanie:
                %prefix% <dark_aqua>   Typ: <white>%storage.type%
                %prefix% <dark_aqua>   Użytkownicy: <white>%storage.users%
                %prefix% <dark_aqua>   Domy: <white>%storage.homes%
                %prefix% <aqua>- Hooki:
                %prefix% <dark_aqua>   PlaceholderAPI: <white>%use.placeholderapi%
                %prefix% <dark_aqua>   GriefPrevention: <white>%use.griefprevention%
                %prefix% <dark_aqua>   WorldGuard: <white>%use.worldguard%
                %prefix% <dark_aqua>   Vault: <white>%use.vault%
                """);
        addDefault(formPath("commands", "main", "info", "requpdate-yes"),
                "<red>Tak <gray>(Użyj <yellow>/%command% version <gray>aby uzyskać więcej informacji)");
        addDefault(formPath("commands", "main", "info", "requpdate-no"),
                "<green>Nie");

        // commands.main.version
        addDefault(formPath("commands", "main", "version", "output"),
                "%prefix% <aqua>Aktualna wersja: <green>%version%");
        addDefault(formPath("commands", "main", "version", "update", "output"),
                "%prefix% <green>zHomes zaktualizowano do najnowszej wersji <yellow>(%update%)<green>, zrestartuj serwer, aby zastosować zmiany.");
        addDefault(formPath("commands", "main", "version", "update", "no-update"),
                "%prefix% <green>Używasz już najnowszej wersji zHomes.");

        // commands.main.reload
        addDefault(formPath("commands", "main", "reload", "usage"), """
                %prefix% <aqua>Użycie <yellow>/%command% <green>(reload|rl)<aqua>:
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                """);
        addDefault(formPath("commands", "main", "reload", "output"),
                "%prefix% <green>Przeładowano wtyczkę w <aqua>%time%ms<green>.");
        addDefault(formPath("commands", "main", "reload", "commands", "output"),
                "%prefix% <green>Przeładowano wszystkie polecenia wtyczki w <aqua>%time%ms<green>.");
        addDefault(formPath("commands", "main", "reload", "config", "output"),
                "%prefix% <green>Przeładowano plik konfiguracyjny wtyczki w <aqua>%time%ms<green>.");
        addDefault(formPath("commands", "main", "reload", "languages", "output"), "%prefix% <green>Przeładowano języki wtyczki w <aqua>%time%ms<green>.");

        // commands.main.nearhomes
        addDefault(formPath("commands", "main", "nearhomes", "usage"),
                "<red>-> <yellow>/%command% nearhomes <green>[<Promień>]");
        addDefault(formPath("commands", "main", "nearhomes", "output"),
                "%prefix% <gray>Domy w pobliżu w odległości <yellow>%radius% <gray>bloków: <white>%homes%");
        addDefault(formPath("commands", "main", "nearhomes", "output-not-found"),
                "%prefix% <red>Nie znaleziono domów w odległości <yellow>%radius% <red>bloków.");
        addDefault(formPath("commands", "main", "nearhomes", "home-string"),
                "<yellow>%home% <gray>(%owner%)");

        // commands.main.parse
        addDefault(formPath("commands", "main", "parse", "usage"),
                "<red>-> <yellow>/%command% parse <gold>(Gracz) (Tekst)");
        addDefault(formPath("commands", "main", "parse", "output"),
                "%prefix% <gray>Sparsowany tekst: <white>%parsed%");

        // commands.main.converter
        addDefault(formPath("commands", "main", "converter", "usage"), """
                %prefix% <aqua>Użycie <yellow>/%command% <green>converter<aqua>:
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
                "%prefix% <green>Wszystkie dane zostały skonwertowane!");
        addDefault(formPath("commands", "main", "converter", "error"),
                "%prefix% <red>Coś poszło nie tak podczas konwersji danych, sprawdź konsolę serwera.");

        // commands.main.export
        addDefault(formPath("commands", "main", "export", "output"),
                "%prefix% <green>Wszystkie domy zostały wyeksportowane do <yellow>%file%<green>!");
        addDefault(formPath("commands", "main", "export", "error"),
                "%prefix% <red>Coś poszło nie tak podczas eksportowania danych, sprawdź konsolę serwera.");

        // commands.main.import
        addDefault(formPath("commands", "main", "import", "usage"),
                "<red>-> <yellow>/%command% import <green>(<plik>)");
        addDefault(formPath("commands", "main", "import", "output"),
                "%prefix% <green>Wszystkie domy zostały zaimportowane z <yellow>%file%<green>!");
        addDefault(formPath("commands", "main", "import", "file-not-found"),
                "%prefix% <red>Plik <yellow>%file% <red>nie został znaleziony.");
        addDefault(formPath("commands", "main", "import", "error"),
                "%prefix% <red>Coś poszło nie tak podczas importowania danych, sprawdź konsolę serwera.");

        // commands.sethome
        addDefault(formPath("commands", "sethome", "usage"),
                "<red>-> <yellow>/%command% <green>(Dom)");
        addDefault(formPath("commands", "sethome", "output"),
                "%prefix% <green>Dom <yellow>%home% <green>ustawiony w twojej pozycji.");
        addDefault(formPath("commands", "sethome", "limit-reached"),
                "<red>Nie możesz ustawić więcej domów, ponieważ osiągnąłeś limit <yellow>(%limit% domów)<red>!");

        // commands.delhome
        addDefault(formPath("commands", "delhome", "usage"),
                "<red>-> <yellow>/%command% <green>(Dom)");
        addDefault(formPath("commands", "delhome", "output"),
                "%prefix% <red>Dom <yellow>%home% <red>usunięty.");

        // commands.home
        addDefault(formPath("commands", "home", "usage"),
                "<red>-> <yellow>/%command% <green>(Dom)");
        addDefault(formPath("commands", "home", "output"),
                "%prefix% <green>Przeteleportowano do <yellow>%home%<green>...");
        addDefault(formPath("commands", "home", "cant-dimensional-teleport"),
                "<red>Twoja teleportacja została anulowana! Teleportacja wymiarowa jest wyłączona.");

        // commands.home.rename
        addDefault(formPath("commands", "home", "rename", "usage"),
                "<red>-> <yellow>/%command% <green>rename (Dom) (NowaNazwa)");
        addDefault(formPath("commands", "home", "rename", "output"),
                "%prefix% <green>Dom <yellow>%home% <green>przemianowany na <yellow>%newname%<green>.");
        addDefault(formPath("commands", "home", "rename", "same-name"),
                "%prefix% <red>Nie możesz przemianować domu na tę samą nazwę.");

        // commands.homes
        addDefault(formPath("commands", "homes", "output"),
                "%prefix% <gray>Twoje domy (%amount%): <white>%homes%");
        addDefault(formPath("commands", "homes", "invalid-page"),
                "%prefix% <red>Nieprawidłowy numer strony! Użyj liczby większej niż 0.");

        // commands.homes.others
        addDefault(formPath("commands", "homes", "others", "output"),
                "%prefix% <gray>Domy gracza <yellow>%player% <gray>(%amount%): <white>%homes%");

        build();
    }

}