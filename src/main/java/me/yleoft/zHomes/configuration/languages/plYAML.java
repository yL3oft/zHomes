package me.yleoft.zHomes.configuration.languages;

import java.util.HashMap;
import java.util.Map;

public class plYAML extends LanguageBuilder {

    public plYAML() {
        super("pl");
    }

    @Override
    protected Map<String, String> translations() {
        Map<String, String> t = new HashMap<>();

        // config comments
        t.put(formPath("config", "comment", "header1"), "# |                                   Linki wtyczki & wsparcie                                   | #");
        t.put(formPath("config", "comment", "header2"), "# |   zHomes (c) yL3oft — wydany na licencji MIT.                                                | #");
        t.put(formPath("config", "comment", "database"), "Edytuj ustawienia bazy danych poniżej");
        t.put(formPath("config", "comment", "database", "type"), """
        Tutaj możesz określić, jak przechowywać dane wtyczki.
        OPCJE:
        - H2 (Preferowane nad SQLite)
        - SQLite
        - MariaDB (Preferowane nad MySQL)
        - MySQL
        DOMYŚLNIE: H2
        """);
        t.put(formPath("config", "comment", "pool-size"), "# OSTRZEŻENIE: NIE ZMIENIAJ NIC PONIŻEJ, JEŚLI NIE WIESZ CO ROBISZ");
        t.put(formPath("config", "comment", "general", "language"), """
        Tutaj możesz ustawić język wtyczki. Wszystkie języki można znaleźć, edytować i tworzyć w katalogu języków.
        DOSTĘPNE JĘZYKI: [de, en, es, fr, it, nl, pl, pt-br, ru, zhcn, <custom>]
        """);
        t.put(formPath("config", "comment", "general", "announce-update"), "Przełącz, czy wtyczka powinna ogłaszać dostępne aktualizacje w konsoli i graczom z odpowiednim uprawnieniem.");
        t.put(formPath("config", "comment", "general", "metrics"), """
        Włącz lub wyłącz zbieranie danych statystycznych w celu ulepszenia wtyczki.
        Wszystkie zbierane dane są anonimowe i używane wyłącznie do celów statystycznych.
        !OSTRZEŻENIE: Wymaga restartu serwera, aby wejść w życie!
        """);
        t.put(formPath("config", "comment", "general", "debug-mode"), "Włącz lub wyłącz tryb debugowania dla bardziej szczegółowego rejestrowania.");
        t.put(formPath("config", "comment", "teleport-options"), "Ustawienia związane z zachowaniem teleportacji");
        t.put(formPath("config", "comment", "teleport-options", "enable-safe-teleport"), "Włącz lub wyłącz bezpieczną teleportację, aby zapobiec teleportowaniu graczy w niebezpieczne miejsca.");
        t.put(formPath("config", "comment", "teleport-options", "dimensional-teleportation"), "Włącz lub wyłącz teleportację wymiarową, umożliwiając graczom teleportowanie między różnymi światami lub wymiarami.");
        t.put(formPath("config", "comment", "teleport-options", "play-sound"), "Odtwórz efekt dźwiękowy po teleportacji gracza.");
        t.put(formPath("config", "comment", "teleport-options", "restricted-worlds", "enable"), "Włącz lub wyłącz ograniczenie teleportacji do określonych światów.");
        t.put(formPath("config", "comment", "teleport-options", "restricted-worlds", "mode"), """
        Określ tryb dla ograniczonych światów.
        OPCJE:
        - blacklist: Gracze nie mogą teleportować się do światów wymienionych poniżej.
        - whitelist: Gracze mogą teleportować się tylko do światów wymienionych poniżej.
        """);
        t.put(formPath("config", "comment", "teleport-options", "restricted-worlds", "worlds"), "Lista światów objętych ustawieniem ograniczonych światów.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "enable"), "Włącz lub wyłącz okres rozgrzewki przed teleportacją.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "time"), "Określ czas rozgrzewki w sekundach przed teleportacją.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "cancel-on-move"), "Anuluj teleportację, jeśli gracz poruszy się podczas okresu rozgrzewki.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "show-on-actionbar"), "Wyświetlaj odliczanie rozgrzewki na pasku akcji.");
        t.put(formPath("config", "comment", "limits", "enabled"), "Włącz lub wyłącz limity domów dla graczy.");
        t.put(formPath("config", "comment", "limits", "default"), "Domyślna liczba domów, jaką może ustawić gracz.");
        t.put(formPath("config", "comment", "limits", "examples"), "Przykłady limitów oparte na grupach graczy.");
        t.put(formPath("config", "comment", "commands"), "!OSTRZEŻENIE: Większość poniższych ustawień wymaga restartu do zastosowania.");
        t.put(formPath("config", "comment", "commands", "command-cost"), "command-cost wymaga Vault do działania.");
        t.put(formPath("config", "comment", "commands", "homes", "types"), """
        Określ, jak domy będą wyświetlane graczowi.
        OPCJE:
        - text: Wyświetla domy w prostym formacie listy.
        - menu: Otwiera graficzne menu do wyboru domów.
        """);
        t.put(formPath("config", "comment", "permissions"), "Węzły uprawnień używane przez wtyczkę");
        t.put(formPath("config", "comment", "permissions", "bypass", "limit"), "Uprawnienie do pomijania limitów domów");
        t.put(formPath("config", "comment", "permissions", "bypass", "dimensional-teleportation"), "Uprawnienie do pomijania ograniczeń teleportacji wymiarowej");
        t.put(formPath("config", "comment", "permissions", "bypass", "safe-teleportation"), "Uprawnienie do pomijania sprawdzania bezpiecznej teleportacji");
        t.put(formPath("config", "comment", "permissions", "bypass", "restricted-worlds"), "Uprawnienie do pomijania sprawdzania ograniczonych światów");
        t.put(formPath("config", "comment", "permissions", "bypass", "warmup"), "Uprawnienie do pomijania rozgrzewki teleportacji");
        t.put(formPath("config", "comment", "permissions", "bypass", "command-costs"), "Uprawnienie do pomijania kosztów poleceń");
        t.put(formPath("config", "comment", "permissions", "bypass", "command-cooldowns"), "Uprawnienie do pomijania czasów odnowienia poleceń");

        t.put("header", """
                ####################################################################################################
                # +----------------------------------------------------------------------------------------------+ #
                # |                                                                                              | #
                # |                                  zHomes — Plik Językowy                                      | #
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
                "Tutaj możesz zarządzać wiadomościami hooków.");
        t.put(formPath("comments", "teleport-warmup"),
                "Wiadomości związane z rozgrzewką teleportu.");
        t.put(formPath("comments", "commands"),
                "Wiadomości związane z komendami.");
        t.put(formPath("comments", "commands", "no-permission"),
                "Tutaj znajdziesz wiadomości, które mogą być używane w wielu komendach.");
        t.put(formPath("comments", "commands", "main"),
                "Poniżej znajdziesz wiadomości specyficzne dla komend.");

        // hooks
        t.put(formPath("hooks", "griefprevention", "cant-set-homes"),
                "%prefix% <red>Nie możesz ustawiać home w tym obszarze.");
        t.put(formPath("hooks", "worldguard", "cant-use-homes"),
                "%prefix% <red>Nie możesz używać home tutaj.");
        t.put(formPath("hooks", "worldguard", "cant-set-homes"),
                "%prefix% <red>Nie możesz ustawić home tutaj.");
        t.put(formPath("hooks", "vault", "cant-afford-command"),
                "%prefix% <red>Potrzebujesz <gold>$%cost% <red>aby wykonać tę komendę.");

        // teleport-warmup
        t.put(formPath("teleport-warmup", "warmup"),
                "%prefix% <green>Teleportacja za %time% sekund... Nie ruszaj się!");
        t.put(formPath("teleport-warmup", "warmup-actionbar"),
                "<green>Teleportacja za %time% sekund...");
        t.put(formPath("teleport-warmup", "cancelled"),
                "%prefix% <red>Ruszyłeś się! Teleportacja anulowana.");
        t.put(formPath("teleport-warmup", "cancelled-actionbar"),
                "<red>Ruszyłeś się! Teleportacja anulowana.");

        // commands - general
        t.put(formPath("commands", "no-permission"),
                "%prefix% <red>Nie masz uprawnień do wykonania tej komendy.");
        t.put(formPath("commands", "only-players"),
                "%prefix% <red>Tylko gracze mogą wykonywać tę komendę.");
        t.put(formPath("commands", "in-cooldown"),
                "%prefix% <red>Musisz poczekać %time% sekund przed ponownym użyciem tej komendy.");
        t.put(formPath("commands", "home-already-exist"),
                "%prefix% <red>Masz już home o tej nazwie.");
        t.put(formPath("commands", "home-doesnt-exist"),
                "%prefix% <red>Nie masz żadnej home o tej nazwie.");
        t.put(formPath("commands", "home-doesnt-exist-others"),
                "%prefix% <yellow>%player% <red>nie ma żadnej home o tej nazwie.");
        t.put(formPath("commands", "cant-use-2dot"),
                "%prefix% <red>Nie możesz używać <yellow>':' <red>w tej komendzie.");
        t.put(formPath("commands", "cant-find-player"),
                "%prefix% <red>Ten gracz nie został znaleziony.");
        t.put(formPath("commands", "unable-to-find-safe-location"),
                "%prefix% <red>Nie można znaleźć bezpiecznego miejsca do teleportacji.");
        t.put(formPath("commands", "world-restricted-sethome"),
                "%prefix% <red>Nie możesz ustawiać home w tym świecie.");
        t.put(formPath("commands", "world-restricted-home"),
                "%prefix% <red>Nie możesz teleportować się do home w tym świecie.");

        // commands.main.help
        t.put(formPath("commands", "main", "help", "help-perm"), """
                %prefix% <aqua>Użycia komendy <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>help <gray>Pokazuje tę wiadomość pomocy
                <red>-> <yellow>/%command% <green>info <gray>Pokazuje informacje o pluginie
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                <red>-> <yellow>/%command% <green>(version|ver) <gold>[update]
                <red>-> <yellow>/%command% <green>nearhomes <gold>(Promień) <gray>Wypisuje home w pobliżu w określonym promieniu
                <red>-> <yellow>/%command% <green>parse <gold>(Gracz) (Tekst) <gray>Przetwarza tekst z placeholderami dla konkretnego gracza
                <red>-> <yellow>/%command% <green>purge (<gracz>|*) <gold>[-world] [-startwith] [-endwith] [-player] <gray>Wyczyść domy za pomocą filtrów
                <red>-> <yellow>/%command% <green>converter (<converter-type>) <gray>Konwertuje dane z jednego miejsca do drugiego
                <red>-> <yellow>/%command% <green>export <gray>Eksportuje wszystkie home do jednego pliku
                <red>-> <yellow>/%command% <green>import (<file>) <gray>Importuje home z jednego pliku
                """);
        t.put(formPath("commands", "main", "help", "help-noperm"), """
                %prefix% <aqua>Użycia komendy <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>(help|?) <gray>Pokazuje tę wiadomość pomocy
                <red>-> <yellow>/%command% <green>(version|ver) <gray>Pokazuje wersję pluginu
                """);

        // commands.main.info
        t.put(formPath("commands", "main", "info", "output"), """
                %prefix% <aqua>Uruchomiono <dark_aqua>%name% v%version% <aqua>przez <dark_aqua>%author%<aqua>:
                %prefix% <aqua>- Info serwera:
                %prefix% <dark_aqua>   Software: <white>%server.software%
                %prefix% <dark_aqua>   Wersja: <white>%server.version%
                %prefix% <dark_aqua>   Wymagana aktualizacja: <white>%requpdate%
                %prefix% <dark_aqua>   Język: <white>%language%
                %prefix% <aqua>- Przechowywanie:
                %prefix% <dark_aqua>   Typ: <white>%storage.type%
                %prefix% <dark_aqua>   Użytkownicy: <white>%storage.users%
                %prefix% <dark_aqua>   Homes: <white>%storage.homes%
                %prefix% <aqua>- Hooks:
                %prefix% <dark_aqua>   PlaceholderAPI: <white>%use.placeholderapi%
                %prefix% <dark_aqua>   MiniPlaceholders: <white>%use.miniplaceholders%
                %prefix% <dark_aqua>   GriefPrevention: <white>%use.griefprevention%
                %prefix% <dark_aqua>   WorldGuard: <white>%use.worldguard%
                %prefix% <dark_aqua>   Vault: <white>%use.vault%
                """);
        t.put(formPath("commands", "main", "info", "requpdate-yes"),
                "<red>Tak <gray>(Użyj <yellow>/%command% version <gray>aby uzyskać więcej informacji)");
        t.put(formPath("commands", "main", "info", "requpdate-no"),
                "<green>Nie");

        // commands.main.version
        t.put(formPath("commands", "main", "version", "output"),
                "%prefix% <aqua>Aktualna wersja: <green>%version%");

        // commands.main.reload
        t.put(formPath("commands", "main", "reload", "usage"), """
                %prefix% <aqua>Użycia komendy <yellow>/%command% <green>(reload|rl)<aqua>:
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                """);
        t.put(formPath("commands", "main", "reload", "output"),
                "%prefix% <green>Plugin przeładowany w <aqua>%time%ms<green>.");
        t.put(formPath("commands", "main", "reload", "commands", "output"),
                "%prefix% <green>Wszystkie komendy pluginu przeładowane w <aqua>%time%ms<green>.");
        t.put(formPath("commands", "main", "reload", "config", "output"),
                "%prefix% <green>Plik konfiguracyjny pluginu przeładowany w <aqua>%time%ms<green>.");
        t.put(formPath("commands", "main", "reload", "languages", "output"),
                "%prefix% <green>Języki pluginu przeładowane w <aqua>%time%ms<green>.");

        // commands.main.nearhomes
        t.put(formPath("commands", "main", "nearhomes", "usage"),
                "<red>-> <yellow>/%command% nearhomes <green>[<Promień>]");
        t.put(formPath("commands", "main", "nearhomes", "output"),
                "%prefix% <gray>Home w pobliżu w promieniu <yellow>%radius% <gray>bloków: <white>%homes%");
        t.put(formPath("commands", "main", "nearhomes", "output-not-found"),
                "%prefix% <red>Nie znaleziono home w promieniu <yellow>%radius% <red>bloków.");
        t.put(formPath("commands", "main", "nearhomes", "home-string"),
                "<yellow><hover:show_text:'<green>Kliknij aby się teleportować.'><click:run_command:'/%homecommand% %owner%:%home%'>%home%</click></hover> <gray><hover:show_text:'<green>Kliknij aby filtrować po graczu.'><click:run_command:'/%maincommand% nearhomes %radius% -user %owner%'>(%owner%)</click></hover>");
        t.put(formPath("commands", "main", "nearhomes", "filtered-player", "output"),
                "%prefix% <gray>Home gracza <yellow>%player% <gray>w pobliżu w promieniu <yellow>%radius% <gray>bloków: <white>%homes%");
        t.put(formPath("commands", "main", "nearhomes", "filtered-player", "home-string"),
                "<yellow><hover:show_text:'<green>Kliknij aby się teleportować.'><click:run_command:'/%homecommand% %player%:%home%'>%home%</click></hover>");

        // commands.main.parse
        t.put(formPath("commands", "main", "parse", "usage"),
                "<red>-> <yellow>/%command% parse <gold>(Gracz) (Tekst)");
        t.put(formPath("commands", "main", "parse", "output"),
                "%prefix% <gray>Przetworzony tekst: <white>%parsed%");

        // commands.main.purge
        t.put(formPath("commands", "main", "purge", "usage"), """
                %prefix% <aqua>Użycie <yellow>/%command% purge<aqua>:
                <red>-> <yellow>/%command% purge <green>(<gracz>|*) <gray>Wyczyść wszystkie domy gracza lub wszystkich
                <red>-> <yellow>/%command% purge <green>(<gracz>|*) <gold>-world <green>(świat) <gray>Wyczyść domy w określonym świecie
                <red>-> <yellow>/%command% purge <green>(<gracz>|*) <gold>-startwith <green>(prefiks) <gray>Wyczyść domy zaczynające się od prefiksu
                <red>-> <yellow>/%command% purge <green>(<gracz>|*) <gold>-endwith <green>(sufiks) <gray>Wyczyść domy kończące się sufiksem
                <red>-> <yellow>/%command% purge <green>* <gold>-player <green>(gracze) <gray>Wyczyść domy tylko dla określonych graczy
                <aqua>Możesz łączyć wiele filtrów:
                <red>-> <yellow>/%command% purge <green>* <gold>-world <green>world_nether <gold>-startwith <green>temp_
                <red>-> <yellow>/%command% purge <green>NazwaGracza <gold>-world <green>world_the_end <gold>-endwith <green>_old
                """);
        t.put(formPath("commands", "main", "purge", "output"),
                "%prefix% <green>Pomyślnie wyczyszczono <yellow>%amount% <green>dom(ów)!");

        // commands.main.converter
        t.put(formPath("commands", "main", "converter", "usage"), """
                %prefix% <aqua>Użycia komendy <yellow>/%command% <green>converter<aqua>:
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
                "%prefix% <green>Wszystkie dane skonwertowane!");
        t.put(formPath("commands", "main", "converter", "error"),
                "%prefix% <red>Coś poszło nie tak podczas konwersji, sprawdź konsolę serwera.");

        // commands.main.export
        t.put(formPath("commands", "main", "export", "output"),
                "%prefix% <green>Wszystkie home wyeksportowane do <yellow>%file%<green>!");
        t.put(formPath("commands", "main", "export", "error"),
                "%prefix% <red>Coś poszło nie tak podczas eksportu, sprawdź konsolę serwera.");

        // commands.main.import
        t.put(formPath("commands", "main", "import", "usage"),
                "<red>-> <yellow>/%command% import <green>(<plik>)");
        t.put(formPath("commands", "main", "import", "output"),
                "%prefix% <green>Wszystkie home zaimportowane z <yellow>%file%<green>!");
        t.put(formPath("commands", "main", "import", "file-not-found"),
                "%prefix% <red>Plik <yellow>%file% <red>nie został znaleziony.");
        t.put(formPath("commands", "main", "import", "error"),
                "%prefix% <red>Coś poszło nie tak podczas importu, sprawdź konsolę serwera.");

        // commands.sethome
        t.put(formPath("commands", "sethome", "output"),
                "%prefix% <green>Home <yellow>%home% <green>ustawiona w twojej pozycji.");
        t.put(formPath("commands", "sethome", "limit-reached"),
                "<red>Nie możesz ustawiać więcej home, ponieważ osiągnąłeś swój limit <yellow>(%limit% homes)<red>!");

        // commands.delhome
        t.put(formPath("commands", "delhome", "output"),
                "%prefix% <red>Home <yellow>%home% <red>usunięta.");

        // commands.home
        t.put(formPath("commands", "home", "output"),
                "%prefix% <green>Przeteleportowano do <yellow>%home%<green>...");
        t.put(formPath("commands", "home", "cant-dimensional-teleport"),
                "<red>Twoja teleportacja została anulowana! Teleportacja wymiarowa jest wyłączona.");

        // commands.home.rename
        t.put(formPath("commands", "home", "rename", "usage"),
                "<red>-> <yellow>/%command% <green>rename (Home) (NowaNazwa)");
        t.put(formPath("commands", "home", "rename", "output"),
                "%prefix% <green>Home <yellow>%home% <green>przemianowana na <yellow>%newname%<green>.");
        t.put(formPath("commands", "home", "rename", "same-name"),
                "%prefix% <red>Nie możesz przemianować home na taką samą nazwę.");

        // commands.homes
        t.put(formPath("commands", "homes", "output"),
                "%prefix% <gray>Twoje home (%amount%): <white>%homes%");
        t.put(formPath("commands", "homes", "home-string"),
                "<reset><hover:show_text:'<green>Kliknij aby się teleportować.'><click:run_command:'/%homecommand% %home%'>%home%</click></hover>");
        t.put(formPath("commands", "homes", "invalid-page"),
                "%prefix% <red>Nieprawidłowy numer strony! Użyj liczby większej niż 0.");

        // commands.homes.others
        t.put(formPath("commands", "homes", "others", "output"),
                "%prefix% <gray>Home gracza <yellow>%player% <gray>(%amount%): <white>%homes%");
        t.put(formPath("commands", "homes", "others", "home-string"),
                "<reset><hover:show_text:'<green>Kliknij aby się teleportować.'><click:run_command:'/%homecommand% %player%:%home%'>%home%</click></hover>");

        return t;
    }

}