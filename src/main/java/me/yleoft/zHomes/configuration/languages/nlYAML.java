package me.yleoft.zHomes.configuration.languages;

public class nlYAML extends LanguageBuilder {

    public nlYAML() {
        super("nl");
    }

    public void buildLang() {
        header("""
                ####################################################################################################
                # +----------------------------------------------------------------------------------------------+ #
                # |                                                                                              | #
                # |                                  zHomes – Taalbestand                                        | #
                # |                                                                                              | #
                # |   • Wiki:        https://docs.yleoft.me/zhomes                                               | #
                # |   • Discord:     https://discord.gg/yCdhVDgn4K                                               | #
                # |   • GitHub:      https://github.com/yL3oft/zHomes                                            | #
                # |                                                                                              | #
                # +----------------------------------------------------------------------------------------------+ #
                ####################################################################################################
                """);

        commentSection("hooks", "Hier kun je hook-berichten beheren.");
        addDefault(formPath("hooks", "griefprevention", "cant-set-homes"),
                "%prefix% <red>Je kunt geen huizen instellen in dit gebied.");

        addDefault(formPath("hooks", "worldguard", "cant-use-homes"),
                "%prefix% <red>Je kunt hier geen huizen gebruiken.");
        addDefault(formPath("hooks", "worldguard", "cant-set-homes"),
                "%prefix% <red>Je kunt hier geen huis instellen.");

        addDefault(formPath("hooks", "vault", "cant-afford-command"),
                "%prefix% <red>Je hebt <gold>$%cost% <red>nodig om dit commando uit te voeren.");

        commentSection("teleport-warmup", "Berichten met betrekking tot de teleport-opwarmtijd.");
        addDefault(formPath("teleport-warmup", "warmup"),
                "%prefix% <green>Teleporteren over %time% seconden... Beweeg niet!");
        addDefault(formPath("teleport-warmup", "warmup-actionbar"),
                "<green>Teleporteren over %time% seconden...");
        addDefault(formPath("teleport-warmup", "cancelled"),
                "%prefix% <red>Je hebt bewogen! Teleportatie geannuleerd.");
        addDefault(formPath("teleport-warmup", "cancelled-actionbar"),
                "<red>Je hebt bewogen! Teleportatie geannuleerd.");

        commentSection("commands", "Berichten met betrekking tot commando's.");
        comment(false, "Hier vind je berichten die in meerdere commando's kunnen worden gebruikt.");
        addDefault(formPath("commands", "no-permission"),
                "%prefix% <red>Je hebt geen toestemming om dit commando uit te voeren.");
        addDefault(formPath("commands", "only-players"),
                "%prefix% <red>Alleen spelers kunnen dit commando uitvoeren.");
        addDefault(formPath("commands", "in-cooldown"),
                "%prefix% <red>Je moet %time% seconden wachten voordat je dit commando opnieuw kunt gebruiken.");
        addDefault(formPath("commands", "home-already-exist"),
                "%prefix% <red>Je hebt al een huis met deze naam.");
        addDefault(formPath("commands", "home-doesnt-exist"),
                "%prefix% <red>Je hebt geen huis met deze naam.");
        addDefault(formPath("commands", "home-doesnt-exist-others"),
                "%prefix% <yellow>%player% <red>heeft geen huis met deze naam.");
        addDefault(formPath("commands", "cant-use-2dot"),
                "%prefix% <red>Je kunt <yellow>':' <red>niet gebruiken in dit commando.");
        addDefault(formPath("commands", "cant-find-player"),
                "%prefix% <red>Deze speler is niet gevonden.");
        addDefault(formPath("commands", "unable-to-find-safe-location"),
                "%prefix% <red>Kan geen veilige locatie vinden om je naartoe te teleporteren.");
        addDefault(formPath("commands", "world-restricted-sethome"),
                "%prefix% <red>Je kunt geen huizen instellen in deze wereld.");
        addDefault(formPath("commands", "world-restricted-home"),
                "%prefix% <red>Je kunt niet teleporteren naar huizen in die wereld.");

        commentSection(formPath("commands", "main"), "Hieronder vind je specifieke berichten voor de commando's.");

        // commands.main.help
        addDefault(formPath("commands", "main", "help", "help-perm"), """
                %prefix% <aqua>Gebruik van <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>help <gray>Toont dit exacte helpbericht
                <red>-> <yellow>/%command% <green>info <gray>Toont plugin-informatie
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                <red>-> <yellow>/%command% <green>(version|ver) <gold>[update]
                <red>-> <yellow>/%command% <green>nearhomes <gold>(<straal>) <gray>Lijst huizen bij je in de buurt binnen een bepaalde straal
                <red>-> <yellow>/%command% <green>parse <gold>(Speler) (Tekst) <gray>Parseert een tekst met placeholders voor een specifieke speler
                <red>-> <yellow>/%command% <green>converter (<converter-type>) <gray>Converteer gegevens van de ene plaats naar de andere
                <red>-> <yellow>/%command% <green>export <gray>Exporteert alle huizen naar één bestand
                <red>-> <yellow>/%command% <green>import (<bestand>) <gray>Importeert huizen uit één bestand
                """);
        addDefault(formPath("commands", "main", "help", "help-noperm"), """
                %prefix% <aqua>Gebruik van <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>(help|?) <gray>Toont dit exacte helpbericht
                <red>-> <yellow>/%command% <green>(version|ver) <gray>Toont plugin-versie
                """);

        // commands.main.info
        addDefault(formPath("commands", "main", "info", "output"), """
                %prefix% <aqua>Draait <dark_aqua>%name% v%version% <aqua>door <dark_aqua>%author%<aqua>:
                %prefix% <aqua>- Server Info:
                %prefix% <dark_aqua>   Software: <white>%server.software%
                %prefix% <dark_aqua>   Versie: <white>%server.version%
                %prefix% <dark_aqua>   Update vereist: <white>%requpdate%
                %prefix% <dark_aqua>   Taal: <white>%language%
                %prefix% <aqua>- Opslag:
                %prefix% <dark_aqua>   Type: <white>%storage.type%
                %prefix% <dark_aqua>   Gebruikers: <white>%storage.users%
                %prefix% <dark_aqua>   Huizen: <white>%storage.homes%
                %prefix% <aqua>- Hooks:
                %prefix% <dark_aqua>   PlaceholderAPI: <white>%use.placeholderapi%
                %prefix% <dark_aqua>   GriefPrevention: <white>%use.griefprevention%
                %prefix% <dark_aqua>   WorldGuard: <white>%use.worldguard%
                %prefix% <dark_aqua>   Vault: <white>%use.vault%
                """);
        addDefault(formPath("commands", "main", "info", "requpdate-yes"),
                "<red>Ja <gray>(Gebruik <yellow>/%command% version <gray>voor meer informatie)");
        addDefault(formPath("commands", "main", "info", "requpdate-no"),
                "<green>Nee");

        // commands.main.version
        addDefault(formPath("commands", "main", "version", "output"),
                "%prefix% <aqua>Huidige versie: <green>%version%");
        addDefault(formPath("commands", "main", "version", "update", "output"),
                "%prefix% <green>zHomes bijgewerkt naar de nieuwste versie <yellow>(%update%)<green>, herstart je server om de wijzigingen toe te passen.");
        addDefault(formPath("commands", "main", "version", "update", "no-update"),
                "%prefix% <green>Je gebruikt al de nieuwste versie van zHomes.");

        // commands.main.reload
        addDefault(formPath("commands", "main", "reload", "usage"), """
                %prefix% <aqua>Gebruik van <yellow>/%command% <green>(reload|rl)<aqua>:
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                """);
        addDefault(formPath("commands", "main", "reload", "output"),
                "%prefix% <green>Plugin herladen in <aqua>%time%ms<green>.");
        addDefault(formPath("commands", "main", "reload", "commands", "output"),
                "%prefix% <green>Alle plugin-commando's herladen in <aqua>%time%ms<green>.");
        addDefault(formPath("commands", "main", "reload", "config", "output"),
                "%prefix% <green>Plugin-configuratiebestand herladen in <aqua>%time%ms<green>.");
        addDefault(formPath("commands", "main", "reload", "languages", "output"), "%prefix% <green>Plugin-talen herladen in <aqua>%time%ms<green>.");

        // commands.main.nearhomes
        addDefault(formPath("commands", "main", "nearhomes", "usage"),
                "<red>-> <yellow>/%command% nearhomes <green>[<Straal>]");
        addDefault(formPath("commands", "main", "nearhomes", "output"),
                "%prefix% <gray>Huizen bij je in de buurt binnen <yellow>%radius% <gray>blokken: <white>%homes%");
        addDefault(formPath("commands", "main", "nearhomes", "output-not-found"),
                "%prefix% <red>Geen huizen gevonden binnen <yellow>%radius% <red>blokken.");
        addDefault(formPath("commands", "main", "nearhomes", "home-string"),
                "<yellow>%home% <gray>(%owner%)");

        // commands.main.parse
        addDefault(formPath("commands", "main", "parse", "usage"),
                "<red>-> <yellow>/%command% parse <gold>(Speler) (Tekst)");
        addDefault(formPath("commands", "main", "parse", "output"),
                "%prefix% <gray>Geparseerde tekst: <white>%parsed%");

        // commands.main.converter
        addDefault(formPath("commands", "main", "converter", "usage"), """
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
        addDefault(formPath("commands", "main", "converter", "output"),
                "%prefix% <green>Alle gegevens geconverteerd!");
        addDefault(formPath("commands", "main", "converter", "error"),
                "%prefix% <red>Er ging iets mis bij het converteren van de gegevens, controleer je serverconsole.");

        // commands.main.export
        addDefault(formPath("commands", "main", "export", "output"),
                "%prefix% <green>Alle huizen geëxporteerd naar <yellow>%file%<green>!");
        addDefault(formPath("commands", "main", "export", "error"),
                "%prefix% <red>Er ging iets mis bij het exporteren van de gegevens, controleer je serverconsole.");

        // commands.main.import
        addDefault(formPath("commands", "main", "import", "usage"),
                "<red>-> <yellow>/%command% import <green>(<bestand>)");
        addDefault(formPath("commands", "main", "import", "output"),
                "%prefix% <green>Alle huizen geïmporteerd uit <yellow>%file%<green>!");
        addDefault(formPath("commands", "main", "import", "file-not-found"),
                "%prefix% <red>Het bestand <yellow>%file% <red>is niet gevonden.");
        addDefault(formPath("commands", "main", "import", "error"),
                "%prefix% <red>Er ging iets mis bij het importeren van de gegevens, controleer je serverconsole.");

        // commands.sethome
        addDefault(formPath("commands", "sethome", "usage"),
                "<red>-> <yellow>/%command% <green>(Huis)");
        addDefault(formPath("commands", "sethome", "output"),
                "%prefix% <green>Huis <yellow>%home% <green>ingesteld op je positie.");
        addDefault(formPath("commands", "sethome", "limit-reached"),
                "<red>Je kunt geen huizen meer instellen omdat je je limiet hebt bereikt <yellow>(%limit% huizen)<red>!");

        // commands.delhome
        addDefault(formPath("commands", "delhome", "usage"),
                "<red>-> <yellow>/%command% <green>(Huis)");
        addDefault(formPath("commands", "delhome", "output"),
                "%prefix% <red>Huis <yellow>%home% <red>verwijderd.");

        // commands.home
        addDefault(formPath("commands", "home", "usage"),
                "<red>-> <yellow>/%command% <green>(Huis)");
        addDefault(formPath("commands", "home", "output"),
                "%prefix% <green>Geteleporteerd naar <yellow>%home%<green>...");
        addDefault(formPath("commands", "home", "cant-dimensional-teleport"),
                "<red>Je teleportatie is geannuleerd! Dimensionale teleportatie is uitgeschakeld.");

        // commands.home.rename
        addDefault(formPath("commands", "home", "rename", "usage"),
                "<red>-> <yellow>/%command% <green>rename (Huis) (NieuweNaam)");
        addDefault(formPath("commands", "home", "rename", "output"),
                "%prefix% <green>Huis <yellow>%home% <green>hernoemd naar <yellow>%newname%<green>.");
        addDefault(formPath("commands", "home", "rename", "same-name"),
                "%prefix% <red>Je kunt een huis niet hernoemen naar dezelfde naam.");

        // commands.homes
        addDefault(formPath("commands", "homes", "output"),
                "%prefix% <gray>Je huizen (%amount%): <white>%homes%");
        addDefault(formPath("commands", "homes", "invalid-page"),
                "%prefix% <red>Ongeldig paginanummer! Gebruik een nummer hoger dan 0.");

        // commands.homes.others
        addDefault(formPath("commands", "homes", "others", "output"),
                "%prefix% <gray>Huizen van <yellow>%player% <gray>(%amount%): <white>%homes%");

        build();
    }

}