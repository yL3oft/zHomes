package me.yleoft.zHomes.configuration.languages;

import java.util.HashMap;
import java.util.Map;

public class deYAML extends LanguageBuilder {

    public deYAML() {
        super("de");
    }

    @Override
    protected Map<String, String> translations() {
        Map<String, String> t = new HashMap<>();

        // config comments
        t.put(formPath("config", "comment", "header1"), "# |                                    Plugin-Links & Support                                    | #");
        t.put(formPath("config", "comment", "header2"), "# |   zHomes (c) yL3oft — veröffentlicht unter der MIT-Lizenz.                                   | #");
        t.put(formPath("config", "comment", "database"), "Datenbankeinstellungen unten bearbeiten");
        t.put(formPath("config", "comment", "database", "type"), """
        Hier kannst du festlegen, wie die Plugin-Daten gespeichert werden.
        OPTIONEN:
        - H2 (Bevorzugt gegenüber SQLite)
        - SQLite
        - MariaDB (Bevorzugt gegenüber MySQL)
        - MySQL
        STANDARD: H2
        """);
        t.put(formPath("config", "comment", "pool-size"), "# WARNUNG: ÄNDERE NICHTS UNTEN, WENN DU NICHT WEISST, WAS DU TUST");
        t.put(formPath("config", "comment", "general", "language"), """
        Hier kannst du die Sprache des Plugins festlegen. Alle Sprachen können im Sprachen-Verzeichnis gefunden, bearbeitet und erstellt werden.
        VERFÜGBARE SPRACHEN: [de, en, es, fr, it, nl, pl, pt-br, ru, zhcn, <custom>]
        """);
        t.put(formPath("config", "comment", "general", "announce-update"), "Aktivieren oder deaktivieren, ob das Plugin verfügbare Updates in der Konsole und für Spieler mit der entsprechenden Berechtigung ankündigt.");
        t.put(formPath("config", "comment", "general", "metrics"), """
        Metriken-Erfassung aktivieren oder deaktivieren, um das Plugin zu verbessern.
        Alle gesammelten Daten sind anonym und werden ausschließlich für statistische Zwecke verwendet.
        !WARNUNG: Erfordert einen Server-Neustart, um wirksam zu werden!
        """);
        t.put(formPath("config", "comment", "general", "debug-mode"), "Debug-Modus für detailliertere Protokollausgabe aktivieren oder deaktivieren.");
        t.put(formPath("config", "comment", "teleport-options"), "Einstellungen für das Teleportationsverhalten");
        t.put(formPath("config", "comment", "teleport-options", "enable-safe-teleport"), "Sicheres Teleportieren aktivieren oder deaktivieren, um zu verhindern, dass Spieler an gefährliche Orte teleportiert werden.");
        t.put(formPath("config", "comment", "teleport-options", "dimensional-teleportation"), "Dimensionale Teleportation aktivieren oder deaktivieren, sodass Spieler zwischen verschiedenen Welten oder Dimensionen reisen können.");
        t.put(formPath("config", "comment", "teleport-options", "play-sound"), "Einen Soundeffekt abspielen, wenn ein Spieler teleportiert wird.");
        t.put(formPath("config", "comment", "teleport-options", "restricted-worlds", "enable"), "Teleportationsbeschränkung für bestimmte Welten aktivieren oder deaktivieren.");
        t.put(formPath("config", "comment", "teleport-options", "restricted-worlds", "mode"), """
        Modus für beschränkte Welten festlegen.
        OPTIONEN:
        - blacklist: Spieler können sich nicht in die unten aufgeführten Welten teleportieren.
        - whitelist: Spieler können sich nur in die unten aufgeführten Welten teleportieren.
        """);
        t.put(formPath("config", "comment", "teleport-options", "restricted-worlds", "worlds"), "Liste der Welten, die von der Weltbeschränkungseinstellung betroffen sind.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "enable"), "Teleportations-Aufwärmphase aktivieren oder deaktivieren.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "time"), "Die Aufwärmzeit in Sekunden vor der Teleportation festlegen.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "cancel-on-move"), "Teleportation abbrechen, wenn der Spieler sich während der Aufwärmphase bewegt.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "show-on-actionbar"), "Aufwärm-Countdown in der Aktionsleiste anzeigen.");
        t.put(formPath("config", "comment", "limits", "enabled"), "Home-Limits für Spieler aktivieren oder deaktivieren.");
        t.put(formPath("config", "comment", "limits", "default"), "Standardanzahl an Homes, die ein Spieler setzen kann.");
        t.put(formPath("config", "comment", "limits", "examples"), "Limit-Beispiele basierend auf Spielergruppen.");
        t.put(formPath("config", "comment", "commands"), "!WARNUNG: Fast alles unten erfordert einen Neustart zur Anwendung.");
        t.put(formPath("config", "comment", "commands", "command-cost"), "command-cost benötigt Vault, um zu funktionieren.");
        t.put(formPath("config", "comment", "commands", "homes", "types"), """
        Festlegen, wie die Homes dem Spieler angezeigt werden.
        OPTIONEN:
        - text: Zeigt Homes in einem einfachen Listenformat an.
        - menu: Öffnet ein grafisches Menü zur Auswahl von Homes.
        """);
        t.put(formPath("config", "comment", "permissions"), "Berechtigungsknoten des Plugins");
        t.put(formPath("config", "comment", "permissions", "bypass", "limit"), "Berechtigung zum Umgehen von Home-Limits");
        t.put(formPath("config", "comment", "permissions", "bypass", "dimensional-teleportation"), "Berechtigung zum Umgehen von Einschränkungen der dimensionalen Teleportation");
        t.put(formPath("config", "comment", "permissions", "bypass", "safe-teleportation"), "Berechtigung zum Umgehen von Sicherheitsprüfungen der Teleportation");
        t.put(formPath("config", "comment", "permissions", "bypass", "restricted-worlds"), "Berechtigung zum Umgehen von Weltbeschränkungsprüfungen");
        t.put(formPath("config", "comment", "permissions", "bypass", "warmup"), "Berechtigung zum Umgehen der Teleportations-Aufwärmphase");
        t.put(formPath("config", "comment", "permissions", "bypass", "command-costs"), "Berechtigung zum Umgehen von Befehlskosten");
        t.put(formPath("config", "comment", "permissions", "bypass", "command-cooldowns"), "Berechtigung zum Umgehen von Befehlsabklingzeiten");

        t.put("header", """
                ####################################################################################################
                # +----------------------------------------------------------------------------------------------+ #
                # |                                                                                              | #
                # |                                    zHomes — Sprachdatei                                      | #
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
                "Hier können Sie Hook-Nachrichten verwalten.");
        t.put(formPath("comments", "teleport-warmup"),
                "Nachrichten zum Teleport-Aufwärmen.");
        t.put(formPath("comments", "commands"),
                "Nachrichten zu Befehlen.");
        t.put(formPath("comments", "commands", "no-permission"),
                "Hier finden Sie Nachrichten, die in mehreren Befehlen verwendet werden können.");
        t.put(formPath("comments", "commands", "main"),
                "Unten finden Sie spezifische Nachrichten für die Befehle.");

        // hooks
        t.put(formPath("hooks", "griefprevention", "cant-set-homes"),
                "%prefix% <red>Du kannst in diesem Bereich keine Homes setzen.");
        t.put(formPath("hooks", "worldguard", "cant-use-homes"),
                "%prefix% <red>Du kannst hier keine Homes verwenden.");
        t.put(formPath("hooks", "worldguard", "cant-set-homes"),
                "%prefix% <red>Du kannst hier keine Home setzen.");
        t.put(formPath("hooks", "vault", "cant-afford-command"),
                "%prefix% <red>Du benötigst <gold>$%cost% <red>um diesen Befehl auszuführen.");

        // teleport-warmup
        t.put(formPath("teleport-warmup", "warmup"),
                "%prefix% <green>Teleportiere in %time% Sekunden... Nicht bewegen!");
        t.put(formPath("teleport-warmup", "warmup-actionbar"),
                "<green>Teleportiere in %time% Sekunden...");
        t.put(formPath("teleport-warmup", "cancelled"),
                "%prefix% <red>Du hast dich bewegt! Teleportation abgebrochen.");
        t.put(formPath("teleport-warmup", "cancelled-actionbar"),
                "<red>Du hast dich bewegt! Teleportation abgebrochen.");

        // commands - general
        t.put(formPath("commands", "no-permission"),
                "%prefix% <red>Du hast keine Berechtigung diesen Befehl auszuführen.");
        t.put(formPath("commands", "only-players"),
                "%prefix% <red>Nur Spieler können diesen Befehl ausführen.");
        t.put(formPath("commands", "in-cooldown"),
                "%prefix% <red>Du musst %time% Sekunden warten, bevor du diesen Befehl erneut verwendest.");
        t.put(formPath("commands", "home-already-exist"),
                "%prefix% <red>Du hast bereits eine Home mit diesem Namen.");
        t.put(formPath("commands", "home-doesnt-exist"),
                "%prefix% <red>Du hast keine Home mit diesem Namen.");
        t.put(formPath("commands", "home-doesnt-exist-others"),
                "%prefix% <yellow>%player% <red>hat keine Home mit diesem Namen.");
        t.put(formPath("commands", "cant-use-2dot"),
                "%prefix% <red>Du kannst <yellow>':' <red>in diesem Befehl nicht verwenden.");
        t.put(formPath("commands", "cant-find-player"),
                "%prefix% <red>Dieser Spieler wurde nicht gefunden.");
        t.put(formPath("commands", "unable-to-find-safe-location"),
                "%prefix% <red>Es konnte kein sicherer Ort für die Teleportation gefunden werden.");
        t.put(formPath("commands", "world-restricted-sethome"),
                "%prefix% <red>Du kannst in dieser Welt keine Homes setzen.");
        t.put(formPath("commands", "world-restricted-home"),
                "%prefix% <red>Du kannst dich nicht zu Homes in dieser Welt teleportieren.");

        // commands.main.help
        t.put(formPath("commands", "main", "help", "help-perm"), """
                %prefix% <aqua>Verwendung von <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>help <gray>Zeigt diese Hilfenachricht
                <red>-> <yellow>/%command% <green>info <gray>Zeigt Plugin-Informationen
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                <red>-> <yellow>/%command% <green>(version|ver) <gold>[update]
                <red>-> <yellow>/%command% <green>nearhomes <gold>(Radius) <gray>Listet Homes in einem bestimmten Radius in deiner Nähe auf
                <red>-> <yellow>/%command% <green>parse <gold>(Spieler) (Text) <gray>Analysiert einen Text mit Platzhaltern für einen bestimmten Spieler
                <red>-> <yellow>/%command% <green>converter (<converter-type>) <gray>Konvertiert Daten von einem Ort zu einem anderen
                <red>-> <yellow>/%command% <green>export <gray>Exportiert alle Homes in eine einzelne Datei
                <red>-> <yellow>/%command% <green>import (<file>) <gray>Importiert Homes aus einer einzelnen Datei
                """);
        t.put(formPath("commands", "main", "help", "help-noperm"), """
                %prefix% <aqua>Verwendung von <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>(help|?) <gray>Zeigt diese Hilfenachricht
                <red>-> <yellow>/%command% <green>(version|ver) <gray>Zeigt die Plugin-Version
                """);

        // commands.main.info
        t.put(formPath("commands", "main", "info", "output"), """
                %prefix% <aqua>Läuft <dark_aqua>%name% v%version% <aqua>von <dark_aqua>%author%<aqua>:
                %prefix% <aqua>- Server-Info:
                %prefix% <dark_aqua>   Software: <white>%server.software%
                %prefix% <dark_aqua>   Version: <white>%server.version%
                %prefix% <dark_aqua>   Update erforderlich: <white>%requpdate%
                %prefix% <dark_aqua>   Sprache: <white>%language%
                %prefix% <aqua>- Speicher:
                %prefix% <dark_aqua>   Typ: <white>%storage.type%
                %prefix% <dark_aqua>   Nutzer: <white>%storage.users%
                %prefix% <dark_aqua>   Homes: <white>%storage.homes%
                %prefix% <aqua>- Hooks:
                %prefix% <dark_aqua>   PlaceholderAPI: <white>%use.placeholderapi%
                %prefix% <dark_aqua>   MiniPlaceholders: <white>%use.miniplaceholders%
                %prefix% <dark_aqua>   GriefPrevention: <white>%use.griefprevention%
                %prefix% <dark_aqua>   WorldGuard: <white>%use.worldguard%
                %prefix% <dark_aqua>   Vault: <white>%use.vault%
                """);
        t.put(formPath("commands", "main", "info", "requpdate-yes"),
                "<red>Ja <gray>(Verwende <yellow>/%command% version <gray>für mehr Informationen)");
        t.put(formPath("commands", "main", "info", "requpdate-no"),
                "<green>Nein");

        // commands.main.version
        t.put(formPath("commands", "main", "version", "output"),
                "%prefix% <aqua>Aktuelle Version: <green>%version%");
        t.put(formPath("commands", "main", "version", "update", "output"),
                "%prefix% <green>zHomes auf die neueste Version <yellow>(%update%)<green> aktualisiert, bitte starte deinen Server neu.");
        t.put(formPath("commands", "main", "version", "update", "no-update"),
                "%prefix% <green>Du verwendest bereits die neueste Version von zHomes.");

        // commands.main.reload
        t.put(formPath("commands", "main", "reload", "usage"), """
                %prefix% <aqua>Verwendung von <yellow>/%command% <green>(reload|rl)<aqua>:
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                """);
        t.put(formPath("commands", "main", "reload", "output"),
                "%prefix% <green>Plugin in <aqua>%time%ms<green> neu geladen.");
        t.put(formPath("commands", "main", "reload", "commands", "output"),
                "%prefix% <green>Alle Plugin-Befehle in <aqua>%time%ms<green> neu geladen.");
        t.put(formPath("commands", "main", "reload", "config", "output"),
                "%prefix% <green>Plugin-Konfigurationsdatei in <aqua>%time%ms<green> neu geladen.");
        t.put(formPath("commands", "main", "reload", "languages", "output"),
                "%prefix% <green>Plugin-Sprachen in <aqua>%time%ms<green> neu geladen.");

        // commands.main.nearhomes
        t.put(formPath("commands", "main", "nearhomes", "usage"),
                "<red>-> <yellow>/%command% nearhomes <green>[<Radius>]");
        t.put(formPath("commands", "main", "nearhomes", "output"),
                "%prefix% <gray>Homes in deiner Nähe innerhalb von <yellow>%radius% <gray>Blöcken: <white>%homes%");
        t.put(formPath("commands", "main", "nearhomes", "output-not-found"),
                "%prefix% <red>Keine Homes innerhalb von <yellow>%radius% <red>Blöcken gefunden.");
        t.put(formPath("commands", "main", "nearhomes", "home-string"),
                "<yellow><hover:show_text:'<green>Klicken zum Teleportieren.'><click:run_command:'/%homecommand% %owner%:%home%'>%home%</click></hover> <gray><hover:show_text:'<green>Klicken um nach Spieler zu filtern.'><click:run_command:'/%maincommand% nearhomes %radius% -user %owner%'>(%owner%)</click></hover>");
        t.put(formPath("commands", "main", "nearhomes", "filtered-player", "output"),
                "%prefix% <gray>Homes von <yellow>%player% <gray>in deiner Nähe innerhalb von <yellow>%radius% <gray>Blöcken: <white>%homes%");
        t.put(formPath("commands", "main", "nearhomes", "filtered-player", "home-string"),
                "<yellow><hover:show_text:'<green>Klicken zum Teleportieren.'><click:run_command:'/%homecommand% %player%:%home%'>%home%</click></hover>");

        // commands.main.parse
        t.put(formPath("commands", "main", "parse", "usage"),
                "<red>-> <yellow>/%command% parse <gold>(Spieler) (Text)");
        t.put(formPath("commands", "main", "parse", "output"),
                "%prefix% <gray>Geparster Text: <white>%parsed%");

        // commands.main.converter
        t.put(formPath("commands", "main", "converter", "usage"), """
                %prefix% <aqua>Verwendung von <yellow>/%command% <green>converter<aqua>:
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
                "%prefix% <green>Alle Daten konvertiert!");
        t.put(formPath("commands", "main", "converter", "error"),
                "%prefix% <red>Beim Konvertieren der Daten ist ein Fehler aufgetreten, bitte prüfe die Server-Konsole.");

        // commands.main.export
        t.put(formPath("commands", "main", "export", "output"),
                "%prefix% <green>Alle Homes nach <yellow>%file%<green> exportiert!");
        t.put(formPath("commands", "main", "export", "error"),
                "%prefix% <red>Beim Exportieren der Daten ist ein Fehler aufgetreten, bitte prüfe die Server-Konsole.");

        // commands.main.import
        t.put(formPath("commands", "main", "import", "usage"),
                "<red>-> <yellow>/%command% import <green>(<Datei>)");
        t.put(formPath("commands", "main", "import", "output"),
                "%prefix% <green>Alle Homes aus <yellow>%file%<green> importiert!");
        t.put(formPath("commands", "main", "import", "file-not-found"),
                "%prefix% <red>Die Datei <yellow>%file% <red>wurde nicht gefunden.");
        t.put(formPath("commands", "main", "import", "error"),
                "%prefix% <red>Beim Importieren der Daten ist ein Fehler aufgetreten, bitte prüfe die Server-Konsole.");

        // commands.sethome
        t.put(formPath("commands", "sethome", "output"),
                "%prefix% <green>Home <yellow>%home% <green>an deiner Position gesetzt.");
        t.put(formPath("commands", "sethome", "limit-reached"),
                "<red>Du kannst keine weiteren Homes setzen, da du dein Limit erreicht hast <yellow>(%limit% Homes)<red>!");

        // commands.delhome
        t.put(formPath("commands", "delhome", "output"),
                "%prefix% <red>Home <yellow>%home% <red>gelöscht.");

        // commands.home
        t.put(formPath("commands", "home", "output"),
                "%prefix% <green>Zu <yellow>%home%<green> teleportiert...");
        t.put(formPath("commands", "home", "cant-dimensional-teleport"),
                "<red>Deine Teleportation wurde abgebrochen! Dimensionale Teleportation ist deaktiviert.");

        // commands.home.rename
        t.put(formPath("commands", "home", "rename", "usage"),
                "<red>-> <yellow>/%command% <green>rename (Home) (NeuerName)");
        t.put(formPath("commands", "home", "rename", "output"),
                "%prefix% <green>Home <yellow>%home% <green>in <yellow>%newname%<green> umbenannt.");
        t.put(formPath("commands", "home", "rename", "same-name"),
                "%prefix% <red>Du kannst eine Home nicht in denselben Namen umbenennen.");

        // commands.homes
        t.put(formPath("commands", "homes", "output"),
                "%prefix% <gray>Deine Homes (%amount%): <white>%homes%");
        t.put(formPath("commands", "homes", "home-string"),
                "<reset><hover:show_text:'<green>Klicken zum Teleportieren.'><click:run_command:'/%homecommand% %home%'>%home%</click></hover>");
        t.put(formPath("commands", "homes", "invalid-page"),
                "%prefix% <red>Ungültige Seitenzahl! Bitte verwende eine Zahl größer als 0.");

        // commands.homes.others
        t.put(formPath("commands", "homes", "others", "output"),
                "%prefix% <gray>Homes von <yellow>%player% <gray>(%amount%): <white>%homes%");
        t.put(formPath("commands", "homes", "others", "home-string"),
                "<reset><hover:show_text:'<green>Klicken zum Teleportieren.'><click:run_command:'/%homecommand% %player%:%home%'>%home%</click></hover>");

        return t;
    }

}