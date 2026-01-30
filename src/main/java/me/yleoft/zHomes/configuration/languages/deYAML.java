package me.yleoft.zHomes.configuration.languages;

public class deYAML extends LanguageBuilder {

    public deYAML() {
        super("de");
    }

    public void buildLang() {
        header("""
                ####################################################################################################
                # +----------------------------------------------------------------------------------------------+ #
                # |                                                                                              | #
                # |                                   zHomes – Sprachdatei                                       | #
                # |                                                                                              | #
                # |   • Wiki:        https://docs.yleoft.me/zhomes                                               | #
                # |   • Discord:     https://discord.gg/yCdhVDgn4K                                               | #
                # |   • GitHub:      https://github.com/yL3oft/zHomes                                            | #
                # |                                                                                              | #
                # +----------------------------------------------------------------------------------------------+ #
                ####################################################################################################
                """);

        commentSection("hooks", "Hier können Sie Hook-Nachrichten verwalten.");
        addDefault(formPath("hooks", "griefprevention", "cant-set-homes"),
                "%prefix% <red>Du kannst in diesem Bereich keine Häuser setzen.");

        addDefault(formPath("hooks", "worldguard", "cant-use-homes"),
                "%prefix% <red>Du kannst hier keine Häuser verwenden.");
        addDefault(formPath("hooks", "worldguard", "cant-set-homes"),
                "%prefix% <red>Du kannst hier kein Haus setzen.");

        addDefault(formPath("hooks", "vault", "cant-afford-command"),
                "%prefix% <red>Du benötigst <gold>$%cost% <red>um diesen Befehl auszuführen.");

        commentSection("teleport-warmup", "Nachrichten im Zusammenhang mit Teleport-Aufwärmzeit.");
        addDefault(formPath("teleport-warmup", "warmup"),
                "%prefix% <green>Teleportiere in %time% Sekunden... Beweg dich nicht!");
        addDefault(formPath("teleport-warmup", "warmup-actionbar"),
                "<green>Teleportiere in %time% Sekunden...");
        addDefault(formPath("teleport-warmup", "cancelled"),
                "%prefix% <red>Du hast dich bewegt! Teleportation abgebrochen.");
        addDefault(formPath("teleport-warmup", "cancelled-actionbar"),
                "<red>Du hast dich bewegt! Teleportation abgebrochen.");

        commentSection("commands", "Nachrichten im Zusammenhang mit Befehlen.");
        comment(false, "Hier oben finden Sie Nachrichten, die in mehreren Befehlen verwendet werden können.");
        addDefault(formPath("commands", "no-permission"),
                "%prefix% <red>Du hast keine Berechtigung, diesen Befehl auszuführen.");
        addDefault(formPath("commands", "only-players"),
                "%prefix% <red>Nur Spieler können diesen Befehl ausführen.");
        addDefault(formPath("commands", "in-cooldown"),
                "%prefix% <red>Du musst %time% Sekunden warten, bevor du diesen Befehl erneut verwenden kannst.");
        addDefault(formPath("commands", "home-already-exist"),
                "%prefix% <red>Du hast bereits ein Haus mit diesem Namen.");
        addDefault(formPath("commands", "home-doesnt-exist"),
                "%prefix% <red>Du hast kein Haus mit diesem Namen.");
        addDefault(formPath("commands", "home-doesnt-exist-others"),
                "%prefix% <yellow>%player% <red>hat kein Haus mit diesem Namen.");
        addDefault(formPath("commands", "cant-use-2dot"),
                "%prefix% <red>Du kannst <yellow>':' <red>in diesem Befehl nicht verwenden.");
        addDefault(formPath("commands", "cant-find-player"),
                "%prefix% <red>Dieser Spieler wurde nicht gefunden.");
        addDefault(formPath("commands", "unable-to-find-safe-location"),
                "%prefix% <red>Es konnte kein sicherer Ort zum Teleportieren gefunden werden.");
        addDefault(formPath("commands", "world-restricted-sethome"),
                "%prefix% <red>Du kannst in dieser Welt keine Häuser setzen.");
        addDefault(formPath("commands", "world-restricted-home"),
                "%prefix% <red>Du kannst dich nicht zu Häusern in dieser Welt teleportieren.");

        commentSection(formPath("commands", "main"), "Im Folgenden finden Sie spezifische Nachrichten für die Befehle.");

        // commands.main.help
        addDefault(formPath("commands", "main", "help", "help-perm"), """
                %prefix% <aqua>Verwendung von <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>help <gray>Zeigt genau diese Hilfenachricht
                <red>-> <yellow>/%command% <green>info <gray>Zeigt Plugin-Informationen
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                <red>-> <yellow>/%command% <green>(version|ver) <gold>[update]
                <red>-> <yellow>/%command% <green>nearhomes <gold>(<Radius>) <gray>Listet Häuser in deiner Nähe innerhalb eines bestimmten Radius auf
                <red>-> <yellow>/%command% <green>parse <gold>(Spieler) (Zeichenkette) <gray>Analysiert eine Zeichenkette mit Platzhaltern für einen bestimmten Spieler
                <red>-> <yellow>/%command% <green>converter (<Konverter-Typ>) <gray>Konvertiert Daten von einem Ort zum anderen
                <red>-> <yellow>/%command% <green>export <gray>Exportiert alle Häuser in eine einzelne Datei
                <red>-> <yellow>/%command% <green>import (<Datei>) <gray>Importiert Häuser aus einer einzelnen Datei
                """);
        addDefault(formPath("commands", "main", "help", "help-noperm"), """
                %prefix% <aqua>Verwendung von <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>(help|?) <gray>Zeigt genau diese Hilfenachricht
                <red>-> <yellow>/%command% <green>(version|ver) <gray>Zeigt die Plugin-Version
                """);

        // commands.main.info
        addDefault(formPath("commands", "main", "info", "output"), """
                %prefix% <aqua>Läuft <dark_aqua>%name% v%version% <aqua>von <dark_aqua>%author%<aqua>:
                %prefix% <aqua>- Server-Info:
                %prefix% <dark_aqua>   Software: <white>%server.software%
                %prefix% <dark_aqua>   Version: <white>%server.version%
                %prefix% <dark_aqua>   Update erforderlich: <white>%requpdate%
                %prefix% <dark_aqua>   Sprache: <white>%language%
                %prefix% <aqua>- Speicher:
                %prefix% <dark_aqua>   Typ: <white>%storage.type%
                %prefix% <dark_aqua>   Benutzer: <white>%storage.users%
                %prefix% <dark_aqua>   Häuser: <white>%storage.homes%
                %prefix% <aqua>- Hooks:
                %prefix% <dark_aqua>   PlaceholderAPI: <white>%use.placeholderapi%
                %prefix% <dark_aqua>   GriefPrevention: <white>%use.griefprevention%
                %prefix% <dark_aqua>   WorldGuard: <white>%use.worldguard%
                %prefix% <dark_aqua>   Vault: <white>%use.vault%
                """);
        addDefault(formPath("commands", "main", "info", "requpdate-yes"),
                "<red>Ja <gray>(Verwende <yellow>/%command% version <gray>für weitere Informationen)");
        addDefault(formPath("commands", "main", "info", "requpdate-no"),
                "<green>Nein");

        // commands.main.version
        addDefault(formPath("commands", "main", "version", "output"),
                "%prefix% <aqua>Aktuelle Version: <green>%version%");
        addDefault(formPath("commands", "main", "version", "update", "output"),
                "%prefix% <green>zHomes auf die neueste Version aktualisiert <yellow>(%update%)<green>, bitte starte deinen Server neu, um die Änderungen anzuwenden.");
        addDefault(formPath("commands", "main", "version", "update", "no-update"),
                "%prefix% <green>Du verwendest bereits die neueste Version von zHomes.");

        // commands.main.reload
        addDefault(formPath("commands", "main", "reload", "usage"), """
                %prefix% <aqua>Verwendung von <yellow>/%command% <green>(reload|rl)<aqua>:
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                """);
        addDefault(formPath("commands", "main", "reload", "output"),
                "%prefix% <green>Plugin in <aqua>%time%ms <green>neu geladen.");
        addDefault(formPath("commands", "main", "reload", "commands", "output"),
                "%prefix% <green>Alle Plugin-Befehle in <aqua>%time%ms <green>neu geladen.");
        addDefault(formPath("commands", "main", "reload", "config", "output"),
                "%prefix% <green>Plugin-Konfigurationsdatei in <aqua>%time%ms <green>neu geladen.");
        addDefault(formPath("commands", "main", "reload", "languages", "output"), "%prefix% <green>Plugin-Sprachen in <aqua>%time%ms <green>neu geladen.");

        // commands.main.nearhomes
        addDefault(formPath("commands", "main", "nearhomes", "usage"),
                "<red>-> <yellow>/%command% nearhomes <green>[<Radius>]");
        addDefault(formPath("commands", "main", "nearhomes", "output"),
                "%prefix% <gray>Häuser in deiner Nähe innerhalb von <yellow>%radius% <gray>Blöcken: <white>%homes%");
        addDefault(formPath("commands", "main", "nearhomes", "output-not-found"),
                "%prefix% <red>Keine Häuser innerhalb von <yellow>%radius% <red>Blöcken gefunden.");
        addDefault(formPath("commands", "main", "nearhomes", "home-string"),
                "<yellow>%home% <gray>(%owner%)");

        // commands.main.parse
        addDefault(formPath("commands", "main", "parse", "usage"),
                "<red>-> <yellow>/%command% parse <gold>(Spieler) (Zeichenkette)");
        addDefault(formPath("commands", "main", "parse", "output"),
                "%prefix% <gray>Analysierter Text: <white>%parsed%");

        // commands.main.converter
        addDefault(formPath("commands", "main", "converter", "usage"), """
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
        addDefault(formPath("commands", "main", "converter", "output"),
                "%prefix% <green>Alle Daten konvertiert!");
        addDefault(formPath("commands", "main", "converter", "error"),
                "%prefix% <red>Beim Konvertieren der Daten ist ein Fehler aufgetreten, bitte überprüfe deine Server-Konsole.");

        // commands.main.export
        addDefault(formPath("commands", "main", "export", "output"),
                "%prefix% <green>Alle Häuser nach <yellow>%file% <green>exportiert!");
        addDefault(formPath("commands", "main", "export", "error"),
                "%prefix% <red>Beim Exportieren der Daten ist ein Fehler aufgetreten, bitte überprüfe deine Server-Konsole.");

        // commands.main.import
        addDefault(formPath("commands", "main", "import", "usage"),
                "<red>-> <yellow>/%command% import <green>(<Datei>)");
        addDefault(formPath("commands", "main", "import", "output"),
                "%prefix% <green>Alle Häuser aus <yellow>%file% <green>importiert!");
        addDefault(formPath("commands", "main", "import", "file-not-found"),
                "%prefix% <red>Die Datei <yellow>%file% <red>wurde nicht gefunden.");
        addDefault(formPath("commands", "main", "import", "error"),
                "%prefix% <red>Beim Importieren der Daten ist ein Fehler aufgetreten, bitte überprüfe deine Server-Konsole.");

        // commands.sethome
        addDefault(formPath("commands", "sethome", "usage"),
                "<red>-> <yellow>/%command% <green>(Haus)");
        addDefault(formPath("commands", "sethome", "output"),
                "%prefix% <green>Haus <yellow>%home% <green>auf deine Position gesetzt.");
        addDefault(formPath("commands", "sethome", "limit-reached"),
                "<red>Du kannst keine weiteren Häuser setzen, da du dein Limit erreicht hast <yellow>(%limit% Häuser)<red>!");

        // commands.delhome
        addDefault(formPath("commands", "delhome", "usage"),
                "<red>-> <yellow>/%command% <green>(Haus)");
        addDefault(formPath("commands", "delhome", "output"),
                "%prefix% <red>Haus <yellow>%home% <red>gelöscht.");

        // commands.home
        addDefault(formPath("commands", "home", "usage"),
                "<red>-> <yellow>/%command% <green>(Haus)");
        addDefault(formPath("commands", "home", "output"),
                "%prefix% <green>Zu <yellow>%home% <green>teleportiert...");
        addDefault(formPath("commands", "home", "cant-dimensional-teleport"),
                "<red>Deine Teleportation wurde abgebrochen! Dimensionale Teleportation ist deaktiviert.");

        // commands.home.rename
        addDefault(formPath("commands", "home", "rename", "usage"),
                "<red>-> <yellow>/%command% <green>rename (Haus) (NeuerName)");
        addDefault(formPath("commands", "home", "rename", "output"),
                "%prefix% <green>Haus <yellow>%home% <green>in <yellow>%newname% <green>umbenannt.");
        addDefault(formPath("commands", "home", "rename", "same-name"),
                "%prefix% <red>Du kannst ein Haus nicht auf den gleichen Namen umbenennen.");

        // commands.homes
        addDefault(formPath("commands", "homes", "output"),
                "%prefix% <gray>Deine Häuser (%amount%): <white>%homes%");
        addDefault(formPath("commands", "homes", "invalid-page"),
                "%prefix% <red>Ungültige Seitenzahl! Bitte verwende eine Zahl größer als 0.");

        // commands.homes.others
        addDefault(formPath("commands", "homes", "others", "output"),
                "%prefix% <yellow>%player%s <gray>Häuser (%amount%): <white>%homes%");

        build();
    }

}