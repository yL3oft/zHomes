package me.yleoft.zHomes.configuration.languages;

import java.util.HashMap;
import java.util.Map;

public class frYAML extends LanguageBuilder {

    public frYAML() {
        super("fr");
    }

    @Override
    protected Map<String, String> translations() {
        Map<String, String> t = new HashMap<>();

        // config comments
        t.put(formPath("config", "comment", "header1"), "# |                                  Liens du plugin & support                                   | #");
        t.put(formPath("config", "comment", "header2"), "# |   zHomes (c) yL3oft — publié sous la Licence MIT.                                            | #");
        t.put(formPath("config", "comment", "database"), "Modifiez vos paramètres de base de données ci-dessous");
        t.put(formPath("config", "comment", "database", "type"), """
        Ici vous pouvez définir comment stocker les données du plugin.
        OPTIONS :
        - H2 (Préféré à SQLite)
        - SQLite
        - MariaDB (Préféré à MySQL)
        - MySQL
        PAR DÉFAUT : H2
        """);
        t.put(formPath("config", "comment", "pool-size"), "# AVERTISSEMENT : NE MODIFIEZ RIEN EN DESSOUS SI VOUS NE SAVEZ PAS CE QUE VOUS FAITES");
        t.put(formPath("config", "comment", "general", "language"), """
        Ici vous pouvez définir la langue du plugin. Toutes les langues peuvent être trouvées, modifiées et créées dans le répertoire des langues.
        LANGUES DISPONIBLES : [de, en, es, fr, it, nl, pl, pt-br, ru, zhcn, <custom>]
        """);
        t.put(formPath("config", "comment", "general", "announce-update"), "Activer ou désactiver l'annonce des mises à jour disponibles dans la console et aux joueurs avec la permission appropriée.");
        t.put(formPath("config", "comment", "general", "metrics"), """
        Activer ou désactiver la collecte de métriques pour améliorer le plugin.
        Toutes les données collectées sont anonymes et utilisées uniquement à des fins statistiques.
        !AVERTISSEMENT : Nécessite un redémarrage du serveur pour prendre effet !
        """);
        t.put(formPath("config", "comment", "general", "debug-mode"), "Activer ou désactiver le mode débogage pour une journalisation plus détaillée.");
        t.put(formPath("config", "comment", "teleport-options"), "Paramètres liés au comportement de téléportation");
        t.put(formPath("config", "comment", "teleport-options", "enable-safe-teleport"), "Activer ou désactiver la téléportation sécurisée pour éviter que les joueurs soient téléportés dans des endroits dangereux.");
        t.put(formPath("config", "comment", "teleport-options", "dimensional-teleportation"), "Activer ou désactiver la téléportation dimensionnelle, permettant aux joueurs de se téléporter entre différents mondes ou dimensions.");
        t.put(formPath("config", "comment", "teleport-options", "play-sound"), "Jouer un effet sonore lorsqu'un joueur est téléporté.");
        t.put(formPath("config", "comment", "teleport-options", "restricted-worlds", "enable"), "Activer ou désactiver la restriction de téléportation vers certains mondes.");
        t.put(formPath("config", "comment", "teleport-options", "restricted-worlds", "mode"), """
        Définir le mode pour les mondes restreints.
        OPTIONS :
        - blacklist : Les joueurs ne peuvent pas se téléporter dans les mondes listés.
        - whitelist : Les joueurs ne peuvent se téléporter que dans les mondes listés.
        """);
        t.put(formPath("config", "comment", "teleport-options", "restricted-worlds", "worlds"), "Liste des mondes affectés par le paramètre de mondes restreints.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "enable"), "Activer ou désactiver la période de préchauffage de la téléportation.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "time"), "Définir le temps de préchauffage en secondes avant que la téléportation s'effectue.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "cancel-on-move"), "Annuler la téléportation si le joueur se déplace pendant la période de préchauffage.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "show-on-actionbar"), "Afficher le compte à rebours de préchauffage dans la barre d'action.");
        t.put(formPath("config", "comment", "limits", "enabled"), "Activer ou désactiver les limites de homes pour les joueurs.");
        t.put(formPath("config", "comment", "limits", "default"), "Nombre par défaut de homes qu'un joueur peut définir.");
        t.put(formPath("config", "comment", "limits", "examples"), "Exemples de limites basés sur les groupes de joueurs.");
        t.put(formPath("config", "comment", "commands"), "!AVERTISSEMENT : Presque tout ce qui suit nécessite un redémarrage pour s'appliquer.");
        t.put(formPath("config", "comment", "commands", "command-cost"), "command-cost nécessite Vault pour fonctionner.");
        t.put(formPath("config", "comment", "commands", "homes", "types"), """
        Définir comment les homes seront affichées au joueur.
        OPTIONS :
        - text : Affiche les homes dans un format de liste simple.
        - menu : Ouvre un menu graphique pour sélectionner les homes.
        """);
        t.put(formPath("config", "comment", "permissions"), "Nœuds de permission utilisés par le plugin");
        t.put(formPath("config", "comment", "permissions", "bypass", "limit"), "Permission pour contourner les limites de homes");
        t.put(formPath("config", "comment", "permissions", "bypass", "dimensional-teleportation"), "Permission pour contourner les restrictions de téléportation dimensionnelle");
        t.put(formPath("config", "comment", "permissions", "bypass", "safe-teleportation"), "Permission pour contourner les vérifications de téléportation sécurisée");
        t.put(formPath("config", "comment", "permissions", "bypass", "restricted-worlds"), "Permission pour contourner les vérifications de mondes restreints");
        t.put(formPath("config", "comment", "permissions", "bypass", "warmup"), "Permission pour contourner le préchauffage de la téléportation");
        t.put(formPath("config", "comment", "permissions", "bypass", "command-costs"), "Permission pour contourner les coûts de commandes");
        t.put(formPath("config", "comment", "permissions", "bypass", "command-cooldowns"), "Permission pour contourner les délais de recharge des commandes");

        t.put("header", """
                ####################################################################################################
                # +----------------------------------------------------------------------------------------------+ #
                # |                                                                                              | #
                # |                                 zHomes — Fichier de Langue                                   | #
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
                "Ici vous pouvez gérer les messages des hooks.");
        t.put(formPath("comments", "teleport-warmup"),
                "Messages liés au délai de téléportation.");
        t.put(formPath("comments", "commands"),
                "Messages liés aux commandes.");
        t.put(formPath("comments", "commands", "no-permission"),
                "Ici vous trouverez des messages utilisables dans plusieurs commandes.");
        t.put(formPath("comments", "commands", "main"),
                "Ci-dessous vous trouverez des messages spécifiques aux commandes.");

        // hooks
        t.put(formPath("hooks", "griefprevention", "cant-set-homes"),
                "%prefix% <red>Vous ne pouvez pas définir des homes dans cette zone.");
        t.put(formPath("hooks", "worldguard", "cant-use-homes"),
                "%prefix% <red>Vous ne pouvez pas utiliser des homes ici.");
        t.put(formPath("hooks", "worldguard", "cant-set-homes"),
                "%prefix% <red>Vous ne pouvez pas définir une home ici.");
        t.put(formPath("hooks", "vault", "cant-afford-command"),
                "%prefix% <red>Vous avez besoin de <gold>$%cost% <red>pour exécuter cette commande.");

        // teleport-warmup
        t.put(formPath("teleport-warmup", "warmup"),
                "%prefix% <green>Téléportation dans %time% secondes... Ne bougez pas !");
        t.put(formPath("teleport-warmup", "warmup-actionbar"),
                "<green>Téléportation dans %time% secondes...");
        t.put(formPath("teleport-warmup", "cancelled"),
                "%prefix% <red>Vous avez bougé ! Téléportation annulée.");
        t.put(formPath("teleport-warmup", "cancelled-actionbar"),
                "<red>Vous avez bougé ! Téléportation annulée.");

        // commands - general
        t.put(formPath("commands", "no-permission"),
                "%prefix% <red>Vous n'avez pas la permission d'exécuter cette commande.");
        t.put(formPath("commands", "only-players"),
                "%prefix% <red>Seuls les joueurs peuvent exécuter cette commande.");
        t.put(formPath("commands", "in-cooldown"),
                "%prefix% <red>Vous devez attendre %time% secondes avant de réutiliser cette commande.");
        t.put(formPath("commands", "home-already-exist"),
                "%prefix% <red>Vous avez déjà une home avec ce nom.");
        t.put(formPath("commands", "home-doesnt-exist"),
                "%prefix% <red>Vous n'avez aucune home avec ce nom.");
        t.put(formPath("commands", "home-doesnt-exist-others"),
                "%prefix% <yellow>%player% <red>n'a aucune home avec ce nom.");
        t.put(formPath("commands", "cant-use-2dot"),
                "%prefix% <red>Vous ne pouvez pas utiliser <yellow>':' <red>dans cette commande.");
        t.put(formPath("commands", "cant-find-player"),
                "%prefix% <red>Ce joueur est introuvable.");
        t.put(formPath("commands", "unable-to-find-safe-location"),
                "%prefix% <red>Impossible de trouver un emplacement sûr pour vous téléporter.");
        t.put(formPath("commands", "world-restricted-sethome"),
                "%prefix% <red>Vous ne pouvez pas définir des homes dans ce monde.");
        t.put(formPath("commands", "world-restricted-home"),
                "%prefix% <red>Vous ne pouvez pas vous téléporter vers des homes dans ce monde.");

        // commands.main.help
        t.put(formPath("commands", "main", "help", "help-perm"), """
                %prefix% <aqua>Utilisations de <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>help <gray>Affiche ce message d'aide
                <red>-> <yellow>/%command% <green>info <gray>Affiche les informations du plugin
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                <red>-> <yellow>/%command% <green>(version|ver) <gold>[update]
                <red>-> <yellow>/%command% <green>nearhomes <gold>(Rayon) <gray>Liste les homes proches dans un certain rayon
                <red>-> <yellow>/%command% <green>parse <gold>(Joueur) (Texte) <gray>Analyse un texte avec des placeholders pour un joueur spécifique
                <red>-> <yellow>/%command% <green>purge (<joueur>|*) <gold>[-world] [-startwith] [-endwith] [-player] <gray>Purge les maisons avec des filtres
                <red>-> <yellow>/%command% <green>converter (<converter-type>) <gray>Convertit des données d'un endroit à un autre
                <red>-> <yellow>/%command% <green>export <gray>Exporte toutes les homes dans un fichier unique
                <red>-> <yellow>/%command% <green>import (<file>) <gray>Importe des homes depuis un fichier unique
                """);
        t.put(formPath("commands", "main", "help", "help-noperm"), """
                %prefix% <aqua>Utilisations de <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>(help|?) <gray>Affiche ce message d'aide
                <red>-> <yellow>/%command% <green>(version|ver) <gray>Affiche la version du plugin
                """);

        // commands.main.info
        t.put(formPath("commands", "main", "info", "output"), """
                %prefix% <aqua>Exécution de <dark_aqua>%name% v%version% <aqua>par <dark_aqua>%author%<aqua>:
                %prefix% <aqua>- Info Serveur:
                %prefix% <dark_aqua>   Software: <white>%server.software%
                %prefix% <dark_aqua>   Version: <white>%server.version%
                %prefix% <dark_aqua>   Mise à jour requise: <white>%requpdate%
                %prefix% <dark_aqua>   Langue: <white>%language%
                %prefix% <aqua>- Stockage:
                %prefix% <dark_aqua>   Type: <white>%storage.type%
                %prefix% <dark_aqua>   Utilisateurs: <white>%storage.users%
                %prefix% <dark_aqua>   Homes: <white>%storage.homes%
                %prefix% <aqua>- Hooks:
                %prefix% <dark_aqua>   PlaceholderAPI: <white>%use.placeholderapi%
                %prefix% <dark_aqua>   MiniPlaceholders: <white>%use.miniplaceholders%
                %prefix% <dark_aqua>   GriefPrevention: <white>%use.griefprevention%
                %prefix% <dark_aqua>   WorldGuard: <white>%use.worldguard%
                %prefix% <dark_aqua>   Vault: <white>%use.vault%
                """);
        t.put(formPath("commands", "main", "info", "requpdate-yes"),
                "<red>Oui <gray>(Utilisez <yellow>/%command% version <gray>pour plus d'informations)");
        t.put(formPath("commands", "main", "info", "requpdate-no"),
                "<green>Non");

        // commands.main.version
        t.put(formPath("commands", "main", "version", "output"),
                "%prefix% <aqua>Version actuelle : <green>%version%");

        // commands.main.reload
        t.put(formPath("commands", "main", "reload", "usage"), """
                %prefix% <aqua>Utilisations de <yellow>/%command% <green>(reload|rl)<aqua>:
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                """);
        t.put(formPath("commands", "main", "reload", "output"),
                "%prefix% <green>Plugin rechargé en <aqua>%time%ms<green>.");
        t.put(formPath("commands", "main", "reload", "commands", "output"),
                "%prefix% <green>Toutes les commandes du plugin rechargées en <aqua>%time%ms<green>.");
        t.put(formPath("commands", "main", "reload", "config", "output"),
                "%prefix% <green>Fichier de configuration du plugin rechargé en <aqua>%time%ms<green>.");
        t.put(formPath("commands", "main", "reload", "languages", "output"),
                "%prefix% <green>Langues du plugin rechargées en <aqua>%time%ms<green>.");

        // commands.main.nearhomes
        t.put(formPath("commands", "main", "nearhomes", "usage"),
                "<red>-> <yellow>/%command% nearhomes <green>[<Rayon>]");
        t.put(formPath("commands", "main", "nearhomes", "output"),
                "%prefix% <gray>Homes proches de vous dans un rayon de <yellow>%radius% <gray>blocs : <white>%homes%");
        t.put(formPath("commands", "main", "nearhomes", "output-not-found"),
                "%prefix% <red>Aucune home trouvée dans un rayon de <yellow>%radius% <red>blocs.");
        t.put(formPath("commands", "main", "nearhomes", "home-string"),
                "<yellow><hover:show_text:'<green>Cliquer pour se téléporter.'><click:run_command:'/%homecommand% %owner%:%home%'>%home%</click></hover> <gray><hover:show_text:'<green>Cliquer pour filtrer par joueur.'><click:run_command:'/%maincommand% nearhomes %radius% -user %owner%'>(%owner%)</click></hover>");
        t.put(formPath("commands", "main", "nearhomes", "filtered-player", "output"),
                "%prefix% <gray>Homes de <yellow>%player% <gray>proches de vous dans un rayon de <yellow>%radius% <gray>blocs : <white>%homes%");
        t.put(formPath("commands", "main", "nearhomes", "filtered-player", "home-string"),
                "<yellow><hover:show_text:'<green>Cliquer pour se téléporter.'><click:run_command:'/%homecommand% %player%:%home%'>%home%</click></hover>");

        // commands.main.parse
        t.put(formPath("commands", "main", "parse", "usage"),
                "<red>-> <yellow>/%command% parse <gold>(Joueur) (Texte)");
        t.put(formPath("commands", "main", "parse", "output"),
                "%prefix% <gray>Texte analysé : <white>%parsed%");

        // commands.main.purge
        t.put(formPath("commands", "main", "purge", "usage"), """
                %prefix% <aqua>Utilisations de <yellow>/%command% purge<aqua>:
                <red>-> <yellow>/%command% purge <green>(<joueur>|*) <gray>Purge toutes les maisons d'un joueur ou de tous
                <red>-> <yellow>/%command% purge <green>(<joueur>|*) <gold>-world <green>(monde) <gray>Purge les maisons dans un monde spécifique
                <red>-> <yellow>/%command% purge <green>(<joueur>|*) <gold>-startwith <green>(préfixe) <gray>Purge les maisons commençant par un préfixe
                <red>-> <yellow>/%command% purge <green>(<joueur>|*) <gold>-endwith <green>(suffixe) <gray>Purge les maisons se terminant par un suffixe
                <red>-> <yellow>/%command% purge <green>* <gold>-player <green>(joueurs) <gray>Purge les maisons uniquement pour des joueurs spécifiques
                <aqua>Vous pouvez combiner plusieurs filtres:
                <red>-> <yellow>/%command% purge <green>* <gold>-world <green>world_nether <gold>-startwith <green>temp_
                <red>-> <yellow>/%command% purge <green>NomJoueur <gold>-world <green>world_the_end <gold>-endwith <green>_old
                """);
        t.put(formPath("commands", "main", "purge", "output"),
                "%prefix% <green>Purgé avec succès <yellow>%amount% <green>maison(s)!");

        // commands.main.converter
        t.put(formPath("commands", "main", "converter", "usage"), """
                %prefix% <aqua>Utilisations de <yellow>/%command% <green>converter<aqua>:
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
                "%prefix% <green>Toutes les données converties !");
        t.put(formPath("commands", "main", "converter", "error"),
                "%prefix% <red>Une erreur s'est produite lors de la conversion, vérifiez la console de votre serveur.");

        // commands.main.export
        t.put(formPath("commands", "main", "export", "output"),
                "%prefix% <green>Toutes les homes exportées vers <yellow>%file%<green> !");
        t.put(formPath("commands", "main", "export", "error"),
                "%prefix% <red>Une erreur s'est produite lors de l'exportation, vérifiez la console de votre serveur.");

        // commands.main.import
        t.put(formPath("commands", "main", "import", "usage"),
                "<red>-> <yellow>/%command% import <green>(<fichier>)");
        t.put(formPath("commands", "main", "import", "output"),
                "%prefix% <green>Toutes les homes importées depuis <yellow>%file%<green> !");
        t.put(formPath("commands", "main", "import", "file-not-found"),
                "%prefix% <red>Le fichier <yellow>%file% <red>est introuvable.");
        t.put(formPath("commands", "main", "import", "error"),
                "%prefix% <red>Une erreur s'est produite lors de l'importation, vérifiez la console de votre serveur.");

        // commands.sethome
        t.put(formPath("commands", "sethome", "output"),
                "%prefix% <green>Home <yellow>%home% <green>définie à votre position.");
        t.put(formPath("commands", "sethome", "limit-reached"),
                "<red>Vous ne pouvez pas définir plus de homes, vous avez atteint votre limite <yellow>(%limit% homes)<red> !");

        // commands.delhome
        t.put(formPath("commands", "delhome", "output"),
                "%prefix% <red>Home <yellow>%home% <red>supprimée.");

        // commands.home
        t.put(formPath("commands", "home", "output"),
                "%prefix% <green>Téléporté vers <yellow>%home%<green>...");
        t.put(formPath("commands", "home", "cant-dimensional-teleport"),
                "<red>Votre téléportation a été annulée ! La téléportation dimensionnelle est désactivée.");

        // commands.home.rename
        t.put(formPath("commands", "home", "rename", "usage"),
                "<red>-> <yellow>/%command% <green>rename (Home) (NouveauNom)");
        t.put(formPath("commands", "home", "rename", "output"),
                "%prefix% <green>Home <yellow>%home% <green>renommée en <yellow>%newname%<green>.");
        t.put(formPath("commands", "home", "rename", "same-name"),
                "%prefix% <red>Vous ne pouvez pas renommer une home avec le même nom.");

        // commands.homes
        t.put(formPath("commands", "homes", "output"),
                "%prefix% <gray>Vos homes (%amount%) : <white>%homes%");
        t.put(formPath("commands", "homes", "home-string"),
                "<reset><hover:show_text:'<green>Cliquer pour se téléporter.'><click:run_command:'/%homecommand% %home%'>%home%</click></hover>");
        t.put(formPath("commands", "homes", "invalid-page"),
                "%prefix% <red>Numéro de page invalide ! Veuillez utiliser un nombre supérieur à 0.");

        // commands.homes.others
        t.put(formPath("commands", "homes", "others", "output"),
                "%prefix% <gray>Homes de <yellow>%player% <gray>(%amount%) : <white>%homes%");
        t.put(formPath("commands", "homes", "others", "home-string"),
                "<reset><hover:show_text:'<green>Cliquer pour se téléporter.'><click:run_command:'/%homecommand% %player%:%home%'>%home%</click></hover>");

        return t;
    }

}