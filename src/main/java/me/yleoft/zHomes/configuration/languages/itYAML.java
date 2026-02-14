package me.yleoft.zHomes.configuration.languages;

import java.util.HashMap;
import java.util.Map;

public class itYAML extends LanguageBuilder {

    public itYAML() {
        super("it");
    }

    @Override
    protected Map<String, String> translations() {
        Map<String, String> t = new HashMap<>();

        // config comments
        t.put(formPath("config", "comment", "header1"), "# |                                  Link del plugin & supporto                                  | #");
        t.put(formPath("config", "comment", "header2"), "# |   zHomes (c) yL3oft — rilasciato sotto la Licenza MIT.                                       | #");
        t.put(formPath("config", "comment", "database"), "Modifica le impostazioni del database qui sotto");
        t.put(formPath("config", "comment", "database", "type"), """
        Qui puoi definire come archiviare i dati del plugin.
        OPZIONI:
        - H2 (Preferito rispetto a SQLite)
        - SQLite
        - MariaDB (Preferito rispetto a MySQL)
        - MySQL
        PREDEFINITO: H2
        """);
        t.put(formPath("config", "comment", "pool-size"), "# ATTENZIONE: NON MODIFICARE NULLA QUI SOTTO SE NON SAI COSA STAI FACENDO");
        t.put(formPath("config", "comment", "general", "language"), """
        Qui puoi definire la lingua del plugin. Tutte le lingue possono essere trovate, modificate e create nella directory delle lingue.
        LINGUE DISPONIBILI: [de, en, es, fr, it, nl, pl, pt-br, ru, zhcn, <custom>]
        """);
        t.put(formPath("config", "comment", "general", "auto-update"), "Abilitare o disabilitare gli aggiornamenti automatici per il plugin.");
        t.put(formPath("config", "comment", "general", "announce-update"), "Attivare o disattivare se il plugin deve annunciare gli aggiornamenti disponibili nella console e ai giocatori con il permesso appropriato.");
        t.put(formPath("config", "comment", "general", "metrics"), """
        Abilitare o disabilitare la raccolta di metriche per migliorare il plugin.
        Tutti i dati raccolti sono anonimi e utilizzati esclusivamente a scopi statistici.
        !ATTENZIONE: Richiede il riavvio del server per avere effetto!
        """);
        t.put(formPath("config", "comment", "general", "debug-mode"), "Abilitare o disabilitare la modalità di debug per un output di log più dettagliato.");
        t.put(formPath("config", "comment", "teleport-options"), "Impostazioni relative al comportamento di teletrasporto");
        t.put(formPath("config", "comment", "teleport-options", "enable-safe-teleport"), "Abilitare o disabilitare il teletrasporto sicuro per evitare che i giocatori vengano teletrasportati in luoghi pericolosi.");
        t.put(formPath("config", "comment", "teleport-options", "dimensional-teleportation"), "Abilitare o disabilitare il teletrasporto dimensionale, consentendo ai giocatori di teletrasportarsi tra mondi o dimensioni diverse.");
        t.put(formPath("config", "comment", "teleport-options", "play-sound"), "Riprodurre un effetto sonoro quando un giocatore viene teletrasportato.");
        t.put(formPath("config", "comment", "teleport-options", "restricted-worlds", "enable"), "Abilitare o disabilitare la restrizione del teletrasporto verso certi mondi.");
        t.put(formPath("config", "comment", "teleport-options", "restricted-worlds", "mode"), """
        Definire la modalità per i mondi con restrizioni.
        OPZIONI:
        - blacklist: I giocatori non possono teletrasportarsi nei mondi elencati.
        - whitelist: I giocatori possono teletrasportarsi solo nei mondi elencati.
        """);
        t.put(formPath("config", "comment", "teleport-options", "restricted-worlds", "worlds"), "Elenco dei mondi interessati dall'impostazione dei mondi con restrizioni.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "enable"), "Abilitare o disabilitare il periodo di riscaldamento del teletrasporto.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "time"), "Definire il tempo di riscaldamento in secondi prima che avvenga il teletrasporto.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "cancel-on-move"), "Annullare il teletrasporto se il giocatore si muove durante il periodo di riscaldamento.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "show-on-actionbar"), "Mostrare il conto alla rovescia del riscaldamento nella barra delle azioni.");
        t.put(formPath("config", "comment", "limits", "enabled"), "Abilitare o disabilitare i limiti di home per i giocatori.");
        t.put(formPath("config", "comment", "limits", "default"), "Numero predefinito di home che un giocatore può impostare.");
        t.put(formPath("config", "comment", "limits", "examples"), "Esempi di limiti basati sui gruppi di giocatori.");
        t.put(formPath("config", "comment", "commands"), "!ATTENZIONE: Quasi tutto ciò che segue richiede un riavvio per essere applicato.");
        t.put(formPath("config", "comment", "commands", "command-cost"), "command-cost richiede Vault per funzionare.");
        t.put(formPath("config", "comment", "commands", "homes", "types"), """
        Definire come le home verranno visualizzate al giocatore.
        OPZIONI:
        - text: Mostra le home in un semplice formato elenco.
        - menu: Apre un menu grafico per selezionare le home.
        """);
        t.put(formPath("config", "comment", "permissions"), "Nodi di permesso utilizzati dal plugin");
        t.put(formPath("config", "comment", "permissions", "bypass", "limit"), "Permesso per aggirare i limiti di home");
        t.put(formPath("config", "comment", "permissions", "bypass", "dimensional-teleportation"), "Permesso per aggirare le restrizioni di teletrasporto dimensionale");
        t.put(formPath("config", "comment", "permissions", "bypass", "safe-teleportation"), "Permesso per aggirare i controlli di teletrasporto sicuro");
        t.put(formPath("config", "comment", "permissions", "bypass", "restricted-worlds"), "Permesso per aggirare i controlli dei mondi con restrizioni");
        t.put(formPath("config", "comment", "permissions", "bypass", "warmup"), "Permesso per aggirare il riscaldamento del teletrasporto");
        t.put(formPath("config", "comment", "permissions", "bypass", "command-costs"), "Permesso per aggirare i costi dei comandi");
        t.put(formPath("config", "comment", "permissions", "bypass", "command-cooldowns"), "Permesso per aggirare i tempi di recupero dei comandi");

        t.put("header", """
                ####################################################################################################
                # +----------------------------------------------------------------------------------------------+ #
                # |                                                                                              | #
                # |                                   zHomes — File di Lingua                                    | #
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
                "Qui puoi gestire i messaggi degli hook.");
        t.put(formPath("comments", "teleport-warmup"),
                "Messaggi relativi al pre-riscaldamento del teletrasporto.");
        t.put(formPath("comments", "commands"),
                "Messaggi relativi ai comandi.");
        t.put(formPath("comments", "commands", "no-permission"),
                "Qui troverai messaggi utilizzabili in più comandi.");
        t.put(formPath("comments", "commands", "main"),
                "Qui sotto troverai messaggi specifici per i comandi.");

        // hooks
        t.put(formPath("hooks", "griefprevention", "cant-set-homes"),
                "%prefix% <red>Non puoi impostare home in quest'area.");
        t.put(formPath("hooks", "worldguard", "cant-use-homes"),
                "%prefix% <red>Non puoi usare home qui.");
        t.put(formPath("hooks", "worldguard", "cant-set-homes"),
                "%prefix% <red>Non puoi impostare una home qui.");
        t.put(formPath("hooks", "vault", "cant-afford-command"),
                "%prefix% <red>Hai bisogno di <gold>$%cost% <red>per eseguire questo comando.");

        // teleport-warmup
        t.put(formPath("teleport-warmup", "warmup"),
                "%prefix% <green>Teletrasporto tra %time% secondi... Non muoverti!");
        t.put(formPath("teleport-warmup", "warmup-actionbar"),
                "<green>Teletrasporto tra %time% secondi...");
        t.put(formPath("teleport-warmup", "cancelled"),
                "%prefix% <red>Ti sei mosso! Teletrasporto annullato.");
        t.put(formPath("teleport-warmup", "cancelled-actionbar"),
                "<red>Ti sei mosso! Teletrasporto annullato.");

        // commands - general
        t.put(formPath("commands", "no-permission"),
                "%prefix% <red>Non hai il permesso di eseguire questo comando.");
        t.put(formPath("commands", "only-players"),
                "%prefix% <red>Solo i giocatori possono eseguire questo comando.");
        t.put(formPath("commands", "in-cooldown"),
                "%prefix% <red>Devi aspettare %time% secondi prima di usare questo comando di nuovo.");
        t.put(formPath("commands", "home-already-exist"),
                "%prefix% <red>Hai già una home con questo nome.");
        t.put(formPath("commands", "home-doesnt-exist"),
                "%prefix% <red>Non hai nessuna home con questo nome.");
        t.put(formPath("commands", "home-doesnt-exist-others"),
                "%prefix% <yellow>%player% <red>non ha nessuna home con questo nome.");
        t.put(formPath("commands", "cant-use-2dot"),
                "%prefix% <red>Non puoi usare <yellow>':' <red>in questo comando.");
        t.put(formPath("commands", "cant-find-player"),
                "%prefix% <red>Questo giocatore non è stato trovato.");
        t.put(formPath("commands", "unable-to-find-safe-location"),
                "%prefix% <red>Impossibile trovare una posizione sicura per teletrasportarti.");
        t.put(formPath("commands", "world-restricted-sethome"),
                "%prefix% <red>Non puoi impostare home in questo mondo.");
        t.put(formPath("commands", "world-restricted-home"),
                "%prefix% <red>Non puoi teletrasportarti alle home in quel mondo.");

        // commands.main.help
        t.put(formPath("commands", "main", "help", "help-perm"), """
                %prefix% <aqua>Usi di <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>help <gray>Mostra questo messaggio di aiuto
                <red>-> <yellow>/%command% <green>info <gray>Mostra le informazioni del plugin
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                <red>-> <yellow>/%command% <green>(version|ver) <gold>[update]
                <red>-> <yellow>/%command% <green>nearhomes <gold>(Raggio) <gray>Elenca le home vicine entro un certo raggio
                <red>-> <yellow>/%command% <green>parse <gold>(Giocatore) (Testo) <gray>Analizza un testo con placeholder per un giocatore specifico
                <red>-> <yellow>/%command% <green>converter (<converter-type>) <gray>Converte i dati da un posto a un altro
                <red>-> <yellow>/%command% <green>export <gray>Esporta tutte le home in un singolo file
                <red>-> <yellow>/%command% <green>import (<file>) <gray>Importa home da un singolo file
                """);
        t.put(formPath("commands", "main", "help", "help-noperm"), """
                %prefix% <aqua>Usi di <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>(help|?) <gray>Mostra questo messaggio di aiuto
                <red>-> <yellow>/%command% <green>(version|ver) <gray>Mostra la versione del plugin
                """);

        // commands.main.info
        t.put(formPath("commands", "main", "info", "output"), """
                %prefix% <aqua>In esecuzione <dark_aqua>%name% v%version% <aqua>di <dark_aqua>%author%<aqua>:
                %prefix% <aqua>- Info Server:
                %prefix% <dark_aqua>   Software: <white>%server.software%
                %prefix% <dark_aqua>   Versione: <white>%server.version%
                %prefix% <dark_aqua>   Aggiornamento richiesto: <white>%requpdate%
                %prefix% <dark_aqua>   Lingua: <white>%language%
                %prefix% <aqua>- Archiviazione:
                %prefix% <dark_aqua>   Tipo: <white>%storage.type%
                %prefix% <dark_aqua>   Utenti: <white>%storage.users%
                %prefix% <dark_aqua>   Homes: <white>%storage.homes%
                %prefix% <aqua>- Hooks:
                %prefix% <dark_aqua>   PlaceholderAPI: <white>%use.placeholderapi%
                %prefix% <dark_aqua>   MiniPlaceholders: <white>%use.miniplaceholders%
                %prefix% <dark_aqua>   GriefPrevention: <white>%use.griefprevention%
                %prefix% <dark_aqua>   WorldGuard: <white>%use.worldguard%
                %prefix% <dark_aqua>   Vault: <white>%use.vault%
                """);
        t.put(formPath("commands", "main", "info", "requpdate-yes"),
                "<red>Sì <gray>(Usa <yellow>/%command% version <gray>per maggiori informazioni)");
        t.put(formPath("commands", "main", "info", "requpdate-no"),
                "<green>No");

        // commands.main.version
        t.put(formPath("commands", "main", "version", "output"),
                "%prefix% <aqua>Versione attuale: <green>%version%");
        t.put(formPath("commands", "main", "version", "update", "output"),
                "%prefix% <green>zHomes aggiornato all'ultima versione <yellow>(%update%)<green>, riavvia il server per applicare le modifiche.");
        t.put(formPath("commands", "main", "version", "update", "no-update"),
                "%prefix% <green>Stai già usando l'ultima versione di zHomes.");

        // commands.main.reload
        t.put(formPath("commands", "main", "reload", "usage"), """
                %prefix% <aqua>Usi di <yellow>/%command% <green>(reload|rl)<aqua>:
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                """);
        t.put(formPath("commands", "main", "reload", "output"),
                "%prefix% <green>Plugin ricaricato in <aqua>%time%ms<green>.");
        t.put(formPath("commands", "main", "reload", "commands", "output"),
                "%prefix% <green>Tutti i comandi del plugin ricaricati in <aqua>%time%ms<green>.");
        t.put(formPath("commands", "main", "reload", "config", "output"),
                "%prefix% <green>File di configurazione del plugin ricaricato in <aqua>%time%ms<green>.");
        t.put(formPath("commands", "main", "reload", "languages", "output"),
                "%prefix% <green>Lingue del plugin ricaricate in <aqua>%time%ms<green>.");

        // commands.main.nearhomes
        t.put(formPath("commands", "main", "nearhomes", "usage"),
                "<red>-> <yellow>/%command% nearhomes <green>[<Raggio>]");
        t.put(formPath("commands", "main", "nearhomes", "output"),
                "%prefix% <gray>Home vicine a te entro <yellow>%radius% <gray>blocchi: <white>%homes%");
        t.put(formPath("commands", "main", "nearhomes", "output-not-found"),
                "%prefix% <red>Nessuna home trovata entro <yellow>%radius% <red>blocchi.");
        t.put(formPath("commands", "main", "nearhomes", "home-string"),
                "<yellow><hover:show_text:'<green>Clicca per teletrasportarti.'><click:run_command:'/%homecommand% %owner%:%home%'>%home%</click></hover> <gray><hover:show_text:'<green>Clicca per filtrare per giocatore.'><click:run_command:'/%maincommand% nearhomes %radius% -user %owner%'>(%owner%)</click></hover>");
        t.put(formPath("commands", "main", "nearhomes", "filtered-player", "output"),
                "%prefix% <gray>Home di <yellow>%player% <gray>vicine a te entro <yellow>%radius% <gray>blocchi: <white>%homes%");
        t.put(formPath("commands", "main", "nearhomes", "filtered-player", "home-string"),
                "<yellow><hover:show_text:'<green>Clicca per teletrasportarti.'><click:run_command:'/%homecommand% %player%:%home%'>%home%</click></hover>");

        // commands.main.parse
        t.put(formPath("commands", "main", "parse", "usage"),
                "<red>-> <yellow>/%command% parse <gold>(Giocatore) (Testo)");
        t.put(formPath("commands", "main", "parse", "output"),
                "%prefix% <gray>Testo analizzato: <white>%parsed%");

        // commands.main.converter
        t.put(formPath("commands", "main", "converter", "usage"), """
                %prefix% <aqua>Usi di <yellow>/%command% <green>converter<aqua>:
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
                "%prefix% <green>Tutti i dati convertiti!");
        t.put(formPath("commands", "main", "converter", "error"),
                "%prefix% <red>Qualcosa è andato storto durante la conversione, controlla la console del server.");

        // commands.main.export
        t.put(formPath("commands", "main", "export", "output"),
                "%prefix% <green>Tutte le home esportate in <yellow>%file%<green>!");
        t.put(formPath("commands", "main", "export", "error"),
                "%prefix% <red>Qualcosa è andato storto durante l'esportazione, controlla la console del server.");

        // commands.main.import
        t.put(formPath("commands", "main", "import", "usage"),
                "<red>-> <yellow>/%command% import <green>(<file>)");
        t.put(formPath("commands", "main", "import", "output"),
                "%prefix% <green>Tutte le home importate da <yellow>%file%<green>!");
        t.put(formPath("commands", "main", "import", "file-not-found"),
                "%prefix% <red>Il file <yellow>%file% <red>non è stato trovato.");
        t.put(formPath("commands", "main", "import", "error"),
                "%prefix% <red>Qualcosa è andato storto durante l'importazione, controlla la console del server.");

        // commands.sethome
        t.put(formPath("commands", "sethome", "output"),
                "%prefix% <green>Home <yellow>%home% <green>impostata nella tua posizione.");
        t.put(formPath("commands", "sethome", "limit-reached"),
                "<red>Non puoi impostare altre home perché hai raggiunto il limite <yellow>(%limit% homes)<red>!");

        // commands.delhome
        t.put(formPath("commands", "delhome", "output"),
                "%prefix% <red>Home <yellow>%home% <red>eliminata.");

        // commands.home
        t.put(formPath("commands", "home", "output"),
                "%prefix% <green>Teletrasportato a <yellow>%home%<green>...");
        t.put(formPath("commands", "home", "cant-dimensional-teleport"),
                "<red>Il tuo teletrasporto è stato annullato! Il teletrasporto dimensionale è disabilitato.");

        // commands.home.rename
        t.put(formPath("commands", "home", "rename", "usage"),
                "<red>-> <yellow>/%command% <green>rename (Home) (NuovoNome)");
        t.put(formPath("commands", "home", "rename", "output"),
                "%prefix% <green>Home <yellow>%home% <green>rinominata in <yellow>%newname%<green>.");
        t.put(formPath("commands", "home", "rename", "same-name"),
                "%prefix% <red>Non puoi rinominare una home con lo stesso nome.");

        // commands.homes
        t.put(formPath("commands", "homes", "output"),
                "%prefix% <gray>Le tue home (%amount%): <white>%homes%");
        t.put(formPath("commands", "homes", "home-string"),
                "<reset><hover:show_text:'<green>Clicca per teletrasportarti.'><click:run_command:'/%homecommand% %home%'>%home%</click></hover>");
        t.put(formPath("commands", "homes", "invalid-page"),
                "%prefix% <red>Numero di pagina non valido! Usa un numero maggiore di 0.");

        // commands.homes.others
        t.put(formPath("commands", "homes", "others", "output"),
                "%prefix% <gray>Home di <yellow>%player% <gray>(%amount%): <white>%homes%");
        t.put(formPath("commands", "homes", "others", "home-string"),
                "<reset><hover:show_text:'<green>Clicca per teletrasportarti.'><click:run_command:'/%homecommand% %player%:%home%'>%home%</click></hover>");

        return t;
    }

}