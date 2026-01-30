package me.yleoft.zHomes.configuration.languages;

public class itYAML extends LanguageBuilder {

    public itYAML() {
        super("it");
    }

    public void buildLang() {
        header("""
                ####################################################################################################
                # +----------------------------------------------------------------------------------------------+ #
                # |                                                                                              | #
                # |                                  zHomes – File di Lingua                                     | #
                # |                                                                                              | #
                # |   • Wiki:        https://docs.yleoft.me/zhomes                                               | #
                # |   • Discord:     https://discord.gg/yCdhVDgn4K                                               | #
                # |   • GitHub:      https://github.com/yL3oft/zHomes                                            | #
                # |                                                                                              | #
                # +----------------------------------------------------------------------------------------------+ #
                ####################################################################################################
                """);

        commentSection("hooks", "Qui puoi gestire i messaggi degli hook.");
        addDefault(formPath("hooks", "griefprevention", "cant-set-homes"),
                "%prefix% <red>Non puoi impostare case in quest'area.");

        addDefault(formPath("hooks", "worldguard", "cant-use-homes"),
                "%prefix% <red>Non puoi usare le case qui.");
        addDefault(formPath("hooks", "worldguard", "cant-set-homes"),
                "%prefix% <red>Non puoi impostare una casa qui.");

        addDefault(formPath("hooks", "vault", "cant-afford-command"),
                "%prefix% <red>Hai bisogno di <gold>$%cost% <red>per eseguire questo comando.");

        commentSection("teleport-warmup", "Messaggi relativi al tempo di preparazione del teletrasporto.");
        addDefault(formPath("teleport-warmup", "warmup"),
                "%prefix% <green>Teletrasporto tra %time% secondi... Non muoverti!");
        addDefault(formPath("teleport-warmup", "warmup-actionbar"),
                "<green>Teletrasporto tra %time% secondi...");
        addDefault(formPath("teleport-warmup", "cancelled"),
                "%prefix% <red>Ti sei mosso! Teletrasporto annullato.");
        addDefault(formPath("teleport-warmup", "cancelled-actionbar"),
                "<red>Ti sei mosso! Teletrasporto annullato.");

        commentSection("commands", "Messaggi relativi ai comandi.");
        comment(false, "Qui sopra troverai i messaggi che possono essere utilizzati in più comandi.");
        addDefault(formPath("commands", "no-permission"),
                "%prefix% <red>Non hai il permesso di eseguire questo comando.");
        addDefault(formPath("commands", "only-players"),
                "%prefix% <red>Solo i giocatori possono eseguire questo comando.");
        addDefault(formPath("commands", "in-cooldown"),
                "%prefix% <red>Devi aspettare %time% secondi prima di usare di nuovo questo comando.");
        addDefault(formPath("commands", "home-already-exist"),
                "%prefix% <red>Hai già una casa con questo nome.");
        addDefault(formPath("commands", "home-doesnt-exist"),
                "%prefix% <red>Non hai nessuna casa con questo nome.");
        addDefault(formPath("commands", "home-doesnt-exist-others"),
                "%prefix% <yellow>%player% <red>non ha nessuna casa con questo nome.");
        addDefault(formPath("commands", "cant-use-2dot"),
                "%prefix% <red>Non puoi usare <yellow>':' <red>in questo comando.");
        addDefault(formPath("commands", "cant-find-player"),
                "%prefix% <red>Questo giocatore non è stato trovato.");
        addDefault(formPath("commands", "unable-to-find-safe-location"),
                "%prefix% <red>Impossibile trovare una posizione sicura per teletrasportarti.");
        addDefault(formPath("commands", "world-restricted-sethome"),
                "%prefix% <red>Non puoi impostare case in questo mondo.");
        addDefault(formPath("commands", "world-restricted-home"),
                "%prefix% <red>Non puoi teletrasportarti alle case in quel mondo.");

        commentSection(formPath("commands", "main"), "Di seguito troverai messaggi specifici per i comandi.");

        // commands.main.help
        addDefault(formPath("commands", "main", "help", "help-perm"), """
                %prefix% <aqua>Utilizzi di <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>help <gray>Mostra esattamente questo messaggio di aiuto
                <red>-> <yellow>/%command% <green>info <gray>Mostra le informazioni del plugin
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                <red>-> <yellow>/%command% <green>(version|ver) <gold>[update]
                <red>-> <yellow>/%command% <green>nearhomes <gold>(<raggio>) <gray>Elenca le case vicino a te entro un certo raggio
                <red>-> <yellow>/%command% <green>parse <gold>(Giocatore) (Stringa) <gray>Analizza una stringa con segnaposto per un giocatore specifico
                <red>-> <yellow>/%command% <green>converter (<tipo-convertitore>) <gray>Converte i dati da un posto all'altro
                <red>-> <yellow>/%command% <green>export <gray>Esporta tutte le case in un singolo file
                <red>-> <yellow>/%command% <green>import (<file>) <gray>Importa le case da un singolo file
                """);
        addDefault(formPath("commands", "main", "help", "help-noperm"), """
                %prefix% <aqua>Utilizzi di <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>(help|?) <gray>Mostra esattamente questo messaggio di aiuto
                <red>-> <yellow>/%command% <green>(version|ver) <gray>Mostra la versione del plugin
                """);

        // commands.main.info
        addDefault(formPath("commands", "main", "info", "output"), """
                %prefix% <aqua>In esecuzione <dark_aqua>%name% v%version% <aqua>di <dark_aqua>%author%<aqua>:
                %prefix% <aqua>- Info Server:
                %prefix% <dark_aqua>   Software: <white>%server.software%
                %prefix% <dark_aqua>   Versione: <white>%server.version%
                %prefix% <dark_aqua>   Richiede aggiornamento: <white>%requpdate%
                %prefix% <dark_aqua>   Lingua: <white>%language%
                %prefix% <aqua>- Archiviazione:
                %prefix% <dark_aqua>   Tipo: <white>%storage.type%
                %prefix% <dark_aqua>   Utenti: <white>%storage.users%
                %prefix% <dark_aqua>   Case: <white>%storage.homes%
                %prefix% <aqua>- Hooks:
                %prefix% <dark_aqua>   PlaceholderAPI: <white>%use.placeholderapi%
                %prefix% <dark_aqua>   GriefPrevention: <white>%use.griefprevention%
                %prefix% <dark_aqua>   WorldGuard: <white>%use.worldguard%
                %prefix% <dark_aqua>   Vault: <white>%use.vault%
                """);
        addDefault(formPath("commands", "main", "info", "requpdate-yes"),
                "<red>Sì <gray>(Usa <yellow>/%command% version <gray>per maggiori informazioni)");
        addDefault(formPath("commands", "main", "info", "requpdate-no"),
                "<green>No");

        // commands.main.version
        addDefault(formPath("commands", "main", "version", "output"),
                "%prefix% <aqua>Versione attuale: <green>%version%");
        addDefault(formPath("commands", "main", "version", "update", "output"),
                "%prefix% <green>zHomes aggiornato all'ultima versione <yellow>(%update%)<green>, riavvia il server per applicare le modifiche.");
        addDefault(formPath("commands", "main", "version", "update", "no-update"),
                "%prefix% <green>Stai già usando l'ultima versione di zHomes.");

        // commands.main.reload
        addDefault(formPath("commands", "main", "reload", "usage"), """
                %prefix% <aqua>Utilizzi di <yellow>/%command% <green>(reload|rl)<aqua>:
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                """);
        addDefault(formPath("commands", "main", "reload", "output"),
                "%prefix% <green>Plugin ricaricato in <aqua>%time%ms<green>.");
        addDefault(formPath("commands", "main", "reload", "commands", "output"),
                "%prefix% <green>Tutti i comandi del plugin ricaricati in <aqua>%time%ms<green>.");
        addDefault(formPath("commands", "main", "reload", "config", "output"),
                "%prefix% <green>File di configurazione del plugin ricaricato in <aqua>%time%ms<green>.");
        addDefault(formPath("commands", "main", "reload", "languages", "output"), "%prefix% <green>Lingue del plugin ricaricate in <aqua>%time%ms<green>.");

        // commands.main.nearhomes
        addDefault(formPath("commands", "main", "nearhomes", "usage"),
                "<red>-> <yellow>/%command% nearhomes <green>[<Raggio>]");
        addDefault(formPath("commands", "main", "nearhomes", "output"),
                "%prefix% <gray>Case vicino a te entro <yellow>%radius% <gray>blocchi: <white>%homes%");
        addDefault(formPath("commands", "main", "nearhomes", "output-not-found"),
                "%prefix% <red>Nessuna casa trovata entro <yellow>%radius% <red>blocchi.");
        addDefault(formPath("commands", "main", "nearhomes", "home-string"),
                "<yellow>%home% <gray>(%owner%)");

        // commands.main.parse
        addDefault(formPath("commands", "main", "parse", "usage"),
                "<red>-> <yellow>/%command% parse <gold>(Giocatore) (Stringa)");
        addDefault(formPath("commands", "main", "parse", "output"),
                "%prefix% <gray>Testo analizzato: <white>%parsed%");

        // commands.main.converter
        addDefault(formPath("commands", "main", "converter", "usage"), """
                %prefix% <aqua>Utilizzi di <yellow>/%command% <green>converter<aqua>:
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
                "%prefix% <green>Tutti i dati convertiti!");
        addDefault(formPath("commands", "main", "converter", "error"),
                "%prefix% <red>Qualcosa è andato storto durante la conversione dei dati, controlla la console del server.");

        // commands.main.export
        addDefault(formPath("commands", "main", "export", "output"),
                "%prefix% <green>Tutte le case esportate in <yellow>%file%<green>!");
        addDefault(formPath("commands", "main", "export", "error"),
                "%prefix% <red>Qualcosa è andato storto durante l'esportazione dei dati, controlla la console del server.");

        // commands.main.import
        addDefault(formPath("commands", "main", "import", "usage"),
                "<red>-> <yellow>/%command% import <green>(<file>)");
        addDefault(formPath("commands", "main", "import", "output"),
                "%prefix% <green>Tutte le case importate da <yellow>%file%<green>!");
        addDefault(formPath("commands", "main", "import", "file-not-found"),
                "%prefix% <red>Il file <yellow>%file% <red>non è stato trovato.");
        addDefault(formPath("commands", "main", "import", "error"),
                "%prefix% <red>Qualcosa è andato storto durante l'importazione dei dati, controlla la console del server.");

        // commands.sethome
        addDefault(formPath("commands", "sethome", "usage"),
                "<red>-> <yellow>/%command% <green>(Casa)");
        addDefault(formPath("commands", "sethome", "output"),
                "%prefix% <green>Casa <yellow>%home% <green>impostata alla tua posizione.");
        addDefault(formPath("commands", "sethome", "limit-reached"),
                "<red>Non puoi impostare più case perché hai raggiunto il limite <yellow>(%limit% case)<red>!");

        // commands.delhome
        addDefault(formPath("commands", "delhome", "usage"),
                "<red>-> <yellow>/%command% <green>(Casa)");
        addDefault(formPath("commands", "delhome", "output"),
                "%prefix% <red>Casa <yellow>%home% <red>eliminata.");

        // commands.home
        addDefault(formPath("commands", "home", "usage"),
                "<red>-> <yellow>/%command% <green>(Casa)");
        addDefault(formPath("commands", "home", "output"),
                "%prefix% <green>Teletrasportato a <yellow>%home%<green>...");
        addDefault(formPath("commands", "home", "cant-dimensional-teleport"),
                "<red>Il tuo teletrasporto è stato annullato! Il teletrasporto dimensionale è disabilitato.");

        // commands.home.rename
        addDefault(formPath("commands", "home", "rename", "usage"),
                "<red>-> <yellow>/%command% <green>rename (Casa) (NuovoNome)");
        addDefault(formPath("commands", "home", "rename", "output"),
                "%prefix% <green>Casa <yellow>%home% <green>rinominata in <yellow>%newname%<green>.");
        addDefault(formPath("commands", "home", "rename", "same-name"),
                "%prefix% <red>Non puoi rinominare una casa con lo stesso nome.");

        // commands.homes
        addDefault(formPath("commands", "homes", "output"),
                "%prefix% <gray>Le tue case (%amount%): <white>%homes%");
        addDefault(formPath("commands", "homes", "invalid-page"),
                "%prefix% <red>Numero di pagina non valido! Usa un numero maggiore di 0.");

        // commands.homes.others
        addDefault(formPath("commands", "homes", "others", "output"),
                "%prefix% <gray>Case di <yellow>%player% <gray>(%amount%): <white>%homes%");

        build();
    }

}