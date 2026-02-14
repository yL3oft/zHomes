package me.yleoft.zHomes.configuration.languages;

import java.util.HashMap;
import java.util.Map;

public class nlYAML extends LanguageBuilder {

    public nlYAML() {
        super("nl");
    }

    @Override
    protected Map<String, String> translations() {
        Map<String, String> t = new HashMap<>();

        // config comments
        t.put(formPath("config", "comment", "header1"), "# |                                 Plugin-links & ondersteuning                                 | #");
        t.put(formPath("config", "comment", "header2"), "# |   zHomes (c) yL3oft — uitgebracht onder de MIT-Licentie.                                     | #");
        t.put(formPath("config", "comment", "database"), "Bewerk uw database-instellingen hieronder");
        t.put(formPath("config", "comment", "database", "type"), """
        Hier kunt u bepalen hoe de plugin-gegevens worden opgeslagen.
        OPTIES:
        - H2 (Voorkeur boven SQLite)
        - SQLite
        - MariaDB (Voorkeur boven MySQL)
        - MySQL
        STANDAARD: H2
        """);
        t.put(formPath("config", "comment", "pool-size"), "# WAARSCHUWING: VERANDER HIERONDER NIETS ALS U NIET WEET WAT U DOET");
        t.put(formPath("config", "comment", "general", "language"), """
        Hier kunt u de taal van de plugin instellen. Alle talen kunnen worden gevonden, bewerkt en aangemaakt in de talenmap.
        BESCHIKBARE TALEN: [de, en, es, fr, it, nl, pl, pt-br, ru, zhcn, <custom>]
        """);
        t.put(formPath("config", "comment", "general", "auto-update"), "Automatische updates voor de plugin in- of uitschakelen.");
        t.put(formPath("config", "comment", "general", "announce-update"), "In- of uitschakelen of de plugin beschikbare updates in de console en aan spelers met de juiste toestemming aankondigt.");
        t.put(formPath("config", "comment", "general", "metrics"), """
        Metriekverzameling in- of uitschakelen om de plugin te verbeteren.
        Alle verzamelde gegevens zijn anoniem en worden uitsluitend voor statistische doeleinden gebruikt.
        !WAARSCHUWING: Vereist een serverherstart om van kracht te worden!
        """);
        t.put(formPath("config", "comment", "general", "debug-mode"), "Foutopsporingsmodus in- of uitschakelen voor meer gedetailleerde loguitvoer.");
        t.put(formPath("config", "comment", "teleport-options"), "Instellingen met betrekking tot teleporteergedrag");
        t.put(formPath("config", "comment", "teleport-options", "enable-safe-teleport"), "Veilig teleporteren in- of uitschakelen om te voorkomen dat spelers naar gevaarlijke locaties worden geteleporteerd.");
        t.put(formPath("config", "comment", "teleport-options", "dimensional-teleportation"), "Dimensionaal teleporteren in- of uitschakelen, waardoor spelers tussen verschillende werelden of dimensies kunnen teleporteren.");
        t.put(formPath("config", "comment", "teleport-options", "play-sound"), "Een geluidseffect afspelen wanneer een speler wordt geteleporteerd.");
        t.put(formPath("config", "comment", "teleport-options", "restricted-worlds", "enable"), "De beperking van teleportatie naar bepaalde werelden in- of uitschakelen.");
        t.put(formPath("config", "comment", "teleport-options", "restricted-worlds", "mode"), """
        De modus voor beperkte werelden instellen.
        OPTIES:
        - blacklist: Spelers kunnen niet teleporteren naar de hieronder vermelde werelden.
        - whitelist: Spelers kunnen alleen teleporteren naar de hieronder vermelde werelden.
        """);
        t.put(formPath("config", "comment", "teleport-options", "restricted-worlds", "worlds"), "Lijst van werelden die worden beïnvloed door de instelling voor beperkte werelden.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "enable"), "De opwarmperiode voor teleportatie in- of uitschakelen.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "time"), "De opwarmtijd in seconden vóór de teleportatie instellen.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "cancel-on-move"), "Teleportatie annuleren als de speler beweegt tijdens de opwarmperiode.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "show-on-actionbar"), "Aftelling van de opwarmtijd weergeven in de actiebalk.");
        t.put(formPath("config", "comment", "limits", "enabled"), "Home-limieten voor spelers in- of uitschakelen.");
        t.put(formPath("config", "comment", "limits", "default"), "Standaard aantal homes dat een speler kan instellen.");
        t.put(formPath("config", "comment", "limits", "examples"), "Limieten voorbeelden gebaseerd op spelergroepen.");
        t.put(formPath("config", "comment", "commands"), "!WAARSCHUWING: Bijna alles hieronder vereist een herstart om te worden toegepast.");
        t.put(formPath("config", "comment", "commands", "command-cost"), "command-cost vereist Vault om te werken.");
        t.put(formPath("config", "comment", "commands", "homes", "types"), """
        Bepalen hoe de homes aan de speler worden weergegeven.
        OPTIES:
        - text: Geeft homes weer in een eenvoudig lijstformaat.
        - menu: Opent een grafisch menu om homes te selecteren.
        """);
        t.put(formPath("config", "comment", "permissions"), "Toestemmingsknooppunten gebruikt door de plugin");
        t.put(formPath("config", "comment", "permissions", "bypass", "limit"), "Toestemming om home-limieten te omzeilen");
        t.put(formPath("config", "comment", "permissions", "bypass", "dimensional-teleportation"), "Toestemming om dimensionale teleportatiebeperkingen te omzeilen");
        t.put(formPath("config", "comment", "permissions", "bypass", "safe-teleportation"), "Toestemming om veilige teleportatie-controles te omzeilen");
        t.put(formPath("config", "comment", "permissions", "bypass", "restricted-worlds"), "Toestemming om controles voor beperkte werelden te omzeilen");
        t.put(formPath("config", "comment", "permissions", "bypass", "warmup"), "Toestemming om de opwarmtijd voor teleportatie te omzeilen");
        t.put(formPath("config", "comment", "permissions", "bypass", "command-costs"), "Toestemming om opdrachten kosten te omzeilen");
        t.put(formPath("config", "comment", "permissions", "bypass", "command-cooldowns"), "Toestemming om afkoeltijden van opdrachten te omzeilen");

        t.put("header", """
                ####################################################################################################
                # +----------------------------------------------------------------------------------------------+ #
                # |                                                                                              | #
                # |                                    zHomes — Taalbestand                                      | #
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
                "Hier kun je hook-berichten beheren.");
        t.put(formPath("comments", "teleport-warmup"),
                "Berichten gerelateerd aan teleport-opwarming.");
        t.put(formPath("comments", "commands"),
                "Berichten gerelateerd aan commando's.");
        t.put(formPath("comments", "commands", "no-permission"),
                "Hier vind je berichten die in meerdere commando's gebruikt kunnen worden.");
        t.put(formPath("comments", "commands", "main"),
                "Hieronder vind je berichten die specifiek zijn voor de commando's.");

        // hooks
        t.put(formPath("hooks", "griefprevention", "cant-set-homes"),
                "%prefix% <red>Je kunt geen homes instellen in dit gebied.");
        t.put(formPath("hooks", "worldguard", "cant-use-homes"),
                "%prefix% <red>Je kunt hier geen homes gebruiken.");
        t.put(formPath("hooks", "worldguard", "cant-set-homes"),
                "%prefix% <red>Je kunt hier geen home instellen.");
        t.put(formPath("hooks", "vault", "cant-afford-command"),
                "%prefix% <red>Je hebt <gold>$%cost% <red>nodig om dit commando uit te voeren.");

        // teleport-warmup
        t.put(formPath("teleport-warmup", "warmup"),
                "%prefix% <green>Teleporteren in %time% seconden... Niet bewegen!");
        t.put(formPath("teleport-warmup", "warmup-actionbar"),
                "<green>Teleporteren in %time% seconden...");
        t.put(formPath("teleport-warmup", "cancelled"),
                "%prefix% <red>Je bewoog! Teleportatie geannuleerd.");
        t.put(formPath("teleport-warmup", "cancelled-actionbar"),
                "<red>Je bewoog! Teleportatie geannuleerd.");

        // commands - general
        t.put(formPath("commands", "no-permission"),
                "%prefix% <red>Je hebt geen toestemming om dit commando uit te voeren.");
        t.put(formPath("commands", "only-players"),
                "%prefix% <red>Alleen spelers kunnen dit commando uitvoeren.");
        t.put(formPath("commands", "in-cooldown"),
                "%prefix% <red>Je moet %time% seconden wachten voordat je dit commando opnieuw gebruikt.");
        t.put(formPath("commands", "home-already-exist"),
                "%prefix% <red>Je hebt al een home met deze naam.");
        t.put(formPath("commands", "home-doesnt-exist"),
                "%prefix% <red>Je hebt geen home met deze naam.");
        t.put(formPath("commands", "home-doesnt-exist-others"),
                "%prefix% <yellow>%player% <red>heeft geen home met deze naam.");
        t.put(formPath("commands", "cant-use-2dot"),
                "%prefix% <red>Je kunt <yellow>':' <red>niet gebruiken in dit commando.");
        t.put(formPath("commands", "cant-find-player"),
                "%prefix% <red>Deze speler werd niet gevonden.");
        t.put(formPath("commands", "unable-to-find-safe-location"),
                "%prefix% <red>Er kon geen veilige locatie worden gevonden om je naartoe te teleporteren.");
        t.put(formPath("commands", "world-restricted-sethome"),
                "%prefix% <red>Je kunt geen homes instellen in deze wereld.");
        t.put(formPath("commands", "world-restricted-home"),
                "%prefix% <red>Je kunt niet teleporteren naar homes in die wereld.");

        // commands.main.help
        t.put(formPath("commands", "main", "help", "help-perm"), """
                %prefix% <aqua>Gebruik van <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>help <gray>Toont dit hulpbericht
                <red>-> <yellow>/%command% <green>info <gray>Toont plugin-informatie
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                <red>-> <yellow>/%command% <green>(version|ver) <gold>[update]
                <red>-> <yellow>/%command% <green>nearhomes <gold>(Straal) <gray>Lijst homes in de buurt binnen een straal
                <red>-> <yellow>/%command% <green>parse <gold>(Speler) (Tekst) <gray>Verwerkt een tekst met placeholders voor een specifieke speler
                <red>-> <yellow>/%command% <green>converter (<converter-type>) <gray>Converteert gegevens van de ene naar de andere plek
                <red>-> <yellow>/%command% <green>export <gray>Exporteert alle homes naar één bestand
                <red>-> <yellow>/%command% <green>import (<file>) <gray>Importeert homes vanuit één bestand
                """);
        t.put(formPath("commands", "main", "help", "help-noperm"), """
                %prefix% <aqua>Gebruik van <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>(help|?) <gray>Toont dit hulpbericht
                <red>-> <yellow>/%command% <green>(version|ver) <gray>Toont de plugin-versie
                """);

        // commands.main.info
        t.put(formPath("commands", "main", "info", "output"), """
                %prefix% <aqua>Actief <dark_aqua>%name% v%version% <aqua>door <dark_aqua>%author%<aqua>:
                %prefix% <aqua>- Server-info:
                %prefix% <dark_aqua>   Software: <white>%server.software%
                %prefix% <dark_aqua>   Versie: <white>%server.version%
                %prefix% <dark_aqua>   Update vereist: <white>%requpdate%
                %prefix% <dark_aqua>   Taal: <white>%language%
                %prefix% <aqua>- Opslag:
                %prefix% <dark_aqua>   Type: <white>%storage.type%
                %prefix% <dark_aqua>   Gebruikers: <white>%storage.users%
                %prefix% <dark_aqua>   Homes: <white>%storage.homes%
                %prefix% <aqua>- Hooks:
                %prefix% <dark_aqua>   PlaceholderAPI: <white>%use.placeholderapi%
                %prefix% <dark_aqua>   MiniPlaceholders: <white>%use.miniplaceholders%
                %prefix% <dark_aqua>   GriefPrevention: <white>%use.griefprevention%
                %prefix% <dark_aqua>   WorldGuard: <white>%use.worldguard%
                %prefix% <dark_aqua>   Vault: <white>%use.vault%
                """);
        t.put(formPath("commands", "main", "info", "requpdate-yes"),
                "<red>Ja <gray>(Gebruik <yellow>/%command% version <gray>voor meer informatie)");
        t.put(formPath("commands", "main", "info", "requpdate-no"),
                "<green>Nee");

        // commands.main.version
        t.put(formPath("commands", "main", "version", "output"),
                "%prefix% <aqua>Huidige versie: <green>%version%");
        t.put(formPath("commands", "main", "version", "update", "output"),
                "%prefix% <green>zHomes bijgewerkt naar de nieuwste versie <yellow>(%update%)<green>, herstart je server om de wijzigingen toe te passen.");
        t.put(formPath("commands", "main", "version", "update", "no-update"),
                "%prefix% <green>Je gebruikt al de nieuwste versie van zHomes.");

        // commands.main.reload
        t.put(formPath("commands", "main", "reload", "usage"), """
                %prefix% <aqua>Gebruik van <yellow>/%command% <green>(reload|rl)<aqua>:
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                """);
        t.put(formPath("commands", "main", "reload", "output"),
                "%prefix% <green>Plugin herladen in <aqua>%time%ms<green>.");
        t.put(formPath("commands", "main", "reload", "commands", "output"),
                "%prefix% <green>Alle plugin-commando's herladen in <aqua>%time%ms<green>.");
        t.put(formPath("commands", "main", "reload", "config", "output"),
                "%prefix% <green>Plugin-configuratiebestand herladen in <aqua>%time%ms<green>.");
        t.put(formPath("commands", "main", "reload", "languages", "output"),
                "%prefix% <green>Plugin-talen herladen in <aqua>%time%ms<green>.");

        // commands.main.nearhomes
        t.put(formPath("commands", "main", "nearhomes", "usage"),
                "<red>-> <yellow>/%command% nearhomes <green>[<Straal>]");
        t.put(formPath("commands", "main", "nearhomes", "output"),
                "%prefix% <gray>Homes bij jou in de buurt binnen <yellow>%radius% <gray>blokken: <white>%homes%");
        t.put(formPath("commands", "main", "nearhomes", "output-not-found"),
                "%prefix% <red>Geen homes gevonden binnen <yellow>%radius% <red>blokken.");
        t.put(formPath("commands", "main", "nearhomes", "home-string"),
                "<yellow><hover:show_text:'<green>Klik om te teleporteren.'><click:run_command:'/%homecommand% %owner%:%home%'>%home%</click></hover> <gray><hover:show_text:'<green>Klik om op speler te filteren.'><click:run_command:'/%maincommand% nearhomes %radius% -user %owner%'>(%owner%)</click></hover>");
        t.put(formPath("commands", "main", "nearhomes", "filtered-player", "output"),
                "%prefix% <gray>Homes van <yellow>%player% <gray>bij jou in de buurt binnen <yellow>%radius% <gray>blokken: <white>%homes%");
        t.put(formPath("commands", "main", "nearhomes", "filtered-player", "home-string"),
                "<yellow><hover:show_text:'<green>Klik om te teleporteren.'><click:run_command:'/%homecommand% %player%:%home%'>%home%</click></hover>");

        // commands.main.parse
        t.put(formPath("commands", "main", "parse", "usage"),
                "<red>-> <yellow>/%command% parse <gold>(Speler) (Tekst)");
        t.put(formPath("commands", "main", "parse", "output"),
                "%prefix% <gray>Verwerkte tekst: <white>%parsed%");

        // commands.main.converter
        t.put(formPath("commands", "main", "converter", "usage"), """
                %prefix% <aqua>Gebruik van <yellow>/%command% <green>converter<aqua>:
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
                "%prefix% <green>Alle gegevens geconverteerd!");
        t.put(formPath("commands", "main", "converter", "error"),
                "%prefix% <red>Er is iets misgegaan bij het converteren, controleer je serverconsole.");

        // commands.main.export
        t.put(formPath("commands", "main", "export", "output"),
                "%prefix% <green>Alle homes geëxporteerd naar <yellow>%file%<green>!");
        t.put(formPath("commands", "main", "export", "error"),
                "%prefix% <red>Er is iets misgegaan bij het exporteren, controleer je serverconsole.");

        // commands.main.import
        t.put(formPath("commands", "main", "import", "usage"),
                "<red>-> <yellow>/%command% import <green>(<bestand>)");
        t.put(formPath("commands", "main", "import", "output"),
                "%prefix% <green>Alle homes geïmporteerd vanuit <yellow>%file%<green>!");
        t.put(formPath("commands", "main", "import", "file-not-found"),
                "%prefix% <red>Het bestand <yellow>%file% <red>werd niet gevonden.");
        t.put(formPath("commands", "main", "import", "error"),
                "%prefix% <red>Er is iets misgegaan bij het importeren, controleer je serverconsole.");

        // commands.sethome
        t.put(formPath("commands", "sethome", "output"),
                "%prefix% <green>Home <yellow>%home% <green>ingesteld op je positie.");
        t.put(formPath("commands", "sethome", "limit-reached"),
                "<red>Je kunt geen homes meer instellen omdat je je limiet hebt bereikt <yellow>(%limit% homes)<red>!");

        // commands.delhome
        t.put(formPath("commands", "delhome", "output"),
                "%prefix% <red>Home <yellow>%home% <red>verwijderd.");

        // commands.home
        t.put(formPath("commands", "home", "output"),
                "%prefix% <green>Geteleporteerd naar <yellow>%home%<green>...");
        t.put(formPath("commands", "home", "cant-dimensional-teleport"),
                "<red>Je teleportatie is geannuleerd! Dimensionale teleportatie is uitgeschakeld.");

        // commands.home.rename
        t.put(formPath("commands", "home", "rename", "usage"),
                "<red>-> <yellow>/%command% <green>rename (Home) (NieuweNaam)");
        t.put(formPath("commands", "home", "rename", "output"),
                "%prefix% <green>Home <yellow>%home% <green>hernoemd naar <yellow>%newname%<green>.");
        t.put(formPath("commands", "home", "rename", "same-name"),
                "%prefix% <red>Je kunt een home niet hernoemen naar dezelfde naam.");

        // commands.homes
        t.put(formPath("commands", "homes", "output"),
                "%prefix% <gray>Jouw homes (%amount%): <white>%homes%");
        t.put(formPath("commands", "homes", "home-string"),
                "<reset><hover:show_text:'<green>Klik om te teleporteren.'><click:run_command:'/%homecommand% %home%'>%home%</click></hover>");
        t.put(formPath("commands", "homes", "invalid-page"),
                "%prefix% <red>Ongeldig paginanummer! Gebruik een getal hoger dan 0.");

        // commands.homes.others
        t.put(formPath("commands", "homes", "others", "output"),
                "%prefix% <gray>Homes van <yellow>%player% <gray>(%amount%): <white>%homes%");
        t.put(formPath("commands", "homes", "others", "home-string"),
                "<reset><hover:show_text:'<green>Klik om te teleporteren.'><click:run_command:'/%homecommand% %player%:%home%'>%home%</click></hover>");

        return t;
    }

}