package me.yleoft.zHomes.configuration.languages;

public class frYAML extends LanguageBuilder {

    public frYAML() {
        super("fr");
    }

    public void buildLang() {
        header("""
                ####################################################################################################
                # +----------------------------------------------------------------------------------------------+ #
                # |                                                                                              | #
                # |                                 zHomes – Fichier de Langue                                   | #
                # |                                                                                              | #
                # |   • Wiki:        https://docs.yleoft.me/zhomes                                               | #
                # |   • Discord:     https://discord.gg/yCdhVDgn4K                                               | #
                # |   • GitHub:      https://github.com/yL3oft/zHomes                                            | #
                # |                                                                                              | #
                # +----------------------------------------------------------------------------------------------+ #
                ####################################################################################################
                """);

        commentSection("hooks", "Ici, vous pouvez gérer les messages des hooks.");
        addDefault(formPath("hooks", "griefprevention", "cant-set-homes"),
                "%prefix% <red>Vous ne pouvez pas définir de maisons dans cette zone.");

        addDefault(formPath("hooks", "worldguard", "cant-use-homes"),
                "%prefix% <red>Vous ne pouvez pas utiliser de maisons ici.");
        addDefault(formPath("hooks", "worldguard", "cant-set-homes"),
                "%prefix% <red>Vous ne pouvez pas définir de maison ici.");

        addDefault(formPath("hooks", "vault", "cant-afford-command"),
                "%prefix% <red>Vous avez besoin de <gold>$%cost% <red>pour exécuter cette commande.");

        commentSection("teleport-warmup", "Messages liés au temps de préparation de la téléportation.");
        addDefault(formPath("teleport-warmup", "warmup"),
                "%prefix% <green>Téléportation dans %time% secondes... Ne bougez pas !");
        addDefault(formPath("teleport-warmup", "warmup-actionbar"),
                "<green>Téléportation dans %time% secondes...");
        addDefault(formPath("teleport-warmup", "cancelled"),
                "%prefix% <red>Vous avez bougé ! Téléportation annulée.");
        addDefault(formPath("teleport-warmup", "cancelled-actionbar"),
                "<red>Vous avez bougé ! Téléportation annulée.");

        commentSection("commands", "Messages liés aux commandes.");
        comment(false, "Ici, vous trouverez des messages qui peuvent être utilisés dans plusieurs commandes.");
        addDefault(formPath("commands", "no-permission"),
                "%prefix% <red>Vous n'avez pas la permission d'exécuter cette commande.");
        addDefault(formPath("commands", "only-players"),
                "%prefix% <red>Seuls les joueurs peuvent exécuter cette commande.");
        addDefault(formPath("commands", "in-cooldown"),
                "%prefix% <red>Vous devez attendre %time% secondes avant de réutiliser cette commande.");
        addDefault(formPath("commands", "home-already-exist"),
                "%prefix% <red>Vous avez déjà une maison avec ce nom.");
        addDefault(formPath("commands", "home-doesnt-exist"),
                "%prefix% <red>Vous n'avez aucune maison avec ce nom.");
        addDefault(formPath("commands", "home-doesnt-exist-others"),
                "%prefix% <yellow>%player% <red>n'a aucune maison avec ce nom.");
        addDefault(formPath("commands", "cant-use-2dot"),
                "%prefix% <red>Vous ne pouvez pas utiliser <yellow>':' <red>dans cette commande.");
        addDefault(formPath("commands", "cant-find-player"),
                "%prefix% <red>Ce joueur n'a pas été trouvé.");
        addDefault(formPath("commands", "unable-to-find-safe-location"),
                "%prefix% <red>Impossible de trouver un emplacement sûr pour vous téléporter.");
        addDefault(formPath("commands", "world-restricted-sethome"),
                "%prefix% <red>Vous ne pouvez pas définir de maisons dans ce monde.");
        addDefault(formPath("commands", "world-restricted-home"),
                "%prefix% <red>Vous ne pouvez pas vous téléporter vers des maisons dans ce monde.");

        commentSection(formPath("commands", "main"), "Ci-dessous, vous trouverez des messages spécifiques pour les commandes.");

        // commands.main.help
        addDefault(formPath("commands", "main", "help", "help-perm"), """
                %prefix% <aqua>Utilisations de <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>help <gray>Affiche ce message d'aide exact
                <red>-> <yellow>/%command% <green>info <gray>Affiche les informations du plugin
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                <red>-> <yellow>/%command% <green>(version|ver) <gold>[update]
                <red>-> <yellow>/%command% <green>nearhomes <gold>(<rayon>) <gray>Liste les maisons près de vous dans un certain rayon
                <red>-> <yellow>/%command% <green>parse <gold>(Joueur) (Chaîne) <gray>Analyse une chaîne avec des placeholders pour un joueur spécifique
                <red>-> <yellow>/%command% <green>converter (<type-de-convertisseur>) <gray>Convertit les données d'un endroit à un autre
                <red>-> <yellow>/%command% <green>export <gray>Exporte toutes les maisons dans un seul fichier
                <red>-> <yellow>/%command% <green>import (<fichier>) <gray>Importe les maisons depuis un seul fichier
                """);
        addDefault(formPath("commands", "main", "help", "help-noperm"), """
                %prefix% <aqua>Utilisations de <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>(help|?) <gray>Affiche ce message d'aide exact
                <red>-> <yellow>/%command% <green>(version|ver) <gray>Affiche la version du plugin
                """);

        // commands.main.info
        addDefault(formPath("commands", "main", "info", "output"), """
                %prefix% <aqua>Exécution de <dark_aqua>%name% v%version% <aqua>par <dark_aqua>%author%<aqua>:
                %prefix% <aqua>- Info Serveur:
                %prefix% <dark_aqua>   Logiciel: <white>%server.software%
                %prefix% <dark_aqua>   Version: <white>%server.version%
                %prefix% <dark_aqua>   Mise à jour requise: <white>%requpdate%
                %prefix% <dark_aqua>   Langue: <white>%language%
                %prefix% <aqua>- Stockage:
                %prefix% <dark_aqua>   Type: <white>%storage.type%
                %prefix% <dark_aqua>   Utilisateurs: <white>%storage.users%
                %prefix% <dark_aqua>   Maisons: <white>%storage.homes%
                %prefix% <aqua>- Hooks:
                %prefix% <dark_aqua>   PlaceholderAPI: <white>%use.placeholderapi%
                %prefix% <dark_aqua>   GriefPrevention: <white>%use.griefprevention%
                %prefix% <dark_aqua>   WorldGuard: <white>%use.worldguard%
                %prefix% <dark_aqua>   Vault: <white>%use.vault%
                """);
        addDefault(formPath("commands", "main", "info", "requpdate-yes"),
                "<red>Oui <gray>(Utilisez <yellow>/%command% version <gray>pour plus d'informations)");
        addDefault(formPath("commands", "main", "info", "requpdate-no"),
                "<green>Non");

        // commands.main.version
        addDefault(formPath("commands", "main", "version", "output"),
                "%prefix% <aqua>Version actuelle: <green>%version%");
        addDefault(formPath("commands", "main", "version", "update", "output"),
                "%prefix% <green>zHomes mis à jour vers la dernière version <yellow>(%update%)<green>, veuillez redémarrer votre serveur pour appliquer les modifications.");
        addDefault(formPath("commands", "main", "version", "update", "no-update"),
                "%prefix% <green>Vous utilisez déjà la dernière version de zHomes.");

        // commands.main.reload
        addDefault(formPath("commands", "main", "reload", "usage"), """
                %prefix% <aqua>Utilisations de <yellow>/%command% <green>(reload|rl)<aqua>:
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                """);
        addDefault(formPath("commands", "main", "reload", "output"),
                "%prefix% <green>Plugin rechargé en <aqua>%time%ms<green>.");
        addDefault(formPath("commands", "main", "reload", "commands", "output"),
                "%prefix% <green>Toutes les commandes du plugin rechargées en <aqua>%time%ms<green>.");
        addDefault(formPath("commands", "main", "reload", "config", "output"),
                "%prefix% <green>Fichier de configuration du plugin rechargé en <aqua>%time%ms<green>.");
        addDefault(formPath("commands", "main", "reload", "languages", "output"), "%prefix% <green>Langues du plugin rechargées en <aqua>%time%ms<green>.");

        // commands.main.nearhomes
        addDefault(formPath("commands", "main", "nearhomes", "usage"),
                "<red>-> <yellow>/%command% nearhomes <green>[<Rayon>]");
        addDefault(formPath("commands", "main", "nearhomes", "output"),
                "%prefix% <gray>Maisons près de vous dans un rayon de <yellow>%radius% <gray>blocs: <white>%homes%");
        addDefault(formPath("commands", "main", "nearhomes", "output-not-found"),
                "%prefix% <red>Aucune maison trouvée dans un rayon de <yellow>%radius% <red>blocs.");
        addDefault(formPath("commands", "main", "nearhomes", "home-string"),
                "<yellow>%home% <gray>(%owner%)");

        // commands.main.parse
        addDefault(formPath("commands", "main", "parse", "usage"),
                "<red>-> <yellow>/%command% parse <gold>(Joueur) (Chaîne)");
        addDefault(formPath("commands", "main", "parse", "output"),
                "%prefix% <gray>Texte analysé: <white>%parsed%");

        // commands.main.converter
        addDefault(formPath("commands", "main", "converter", "usage"), """
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
        addDefault(formPath("commands", "main", "converter", "output"),
                "%prefix% <green>Toutes les données converties !");
        addDefault(formPath("commands", "main", "converter", "error"),
                "%prefix% <red>Une erreur s'est produite lors de la conversion des données, veuillez vérifier la console de votre serveur.");

        // commands.main.export
        addDefault(formPath("commands", "main", "export", "output"),
                "%prefix% <green>Toutes les maisons exportées vers <yellow>%file%<green> !");
        addDefault(formPath("commands", "main", "export", "error"),
                "%prefix% <red>Une erreur s'est produite lors de l'exportation des données, veuillez vérifier la console de votre serveur.");

        // commands.main.import
        addDefault(formPath("commands", "main", "import", "usage"),
                "<red>-> <yellow>/%command% import <green>(<fichier>)");
        addDefault(formPath("commands", "main", "import", "output"),
                "%prefix% <green>Toutes les maisons importées depuis <yellow>%file%<green> !");
        addDefault(formPath("commands", "main", "import", "file-not-found"),
                "%prefix% <red>Le fichier <yellow>%file% <red>n'a pas été trouvé.");
        addDefault(formPath("commands", "main", "import", "error"),
                "%prefix% <red>Une erreur s'est produite lors de l'importation des données, veuillez vérifier la console de votre serveur.");

        // commands.sethome
        addDefault(formPath("commands", "sethome", "usage"),
                "<red>-> <yellow>/%command% <green>(Maison)");
        addDefault(formPath("commands", "sethome", "output"),
                "%prefix% <green>Maison <yellow>%home% <green>définie à votre position.");
        addDefault(formPath("commands", "sethome", "limit-reached"),
                "<red>Vous ne pouvez pas définir plus de maisons car vous avez atteint votre limite <yellow>(%limit% maisons)<red> !");

        // commands.delhome
        addDefault(formPath("commands", "delhome", "usage"),
                "<red>-> <yellow>/%command% <green>(Maison)");
        addDefault(formPath("commands", "delhome", "output"),
                "%prefix% <red>Maison <yellow>%home% <red>supprimée.");

        // commands.home
        addDefault(formPath("commands", "home", "usage"),
                "<red>-> <yellow>/%command% <green>(Maison)");
        addDefault(formPath("commands", "home", "output"),
                "%prefix% <green>Téléporté à <yellow>%home%<green>...");
        addDefault(formPath("commands", "home", "cant-dimensional-teleport"),
                "<red>Votre téléportation a été annulée ! La téléportation dimensionnelle est désactivée.");

        // commands.home.rename
        addDefault(formPath("commands", "home", "rename", "usage"),
                "<red>-> <yellow>/%command% <green>rename (Maison) (NouveauNom)");
        addDefault(formPath("commands", "home", "rename", "output"),
                "%prefix% <green>Maison <yellow>%home% <green>renommée en <yellow>%newname%<green>.");
        addDefault(formPath("commands", "home", "rename", "same-name"),
                "%prefix% <red>Vous ne pouvez pas renommer une maison avec le même nom.");

        // commands.homes
        addDefault(formPath("commands", "homes", "output"),
                "%prefix% <gray>Vos maisons (%amount%): <white>%homes%");
        addDefault(formPath("commands", "homes", "invalid-page"),
                "%prefix% <red>Numéro de page invalide ! Veuillez utiliser un nombre supérieur à 0.");

        // commands.homes.others
        addDefault(formPath("commands", "homes", "others", "output"),
                "%prefix% <gray>Maisons de <yellow>%player% <gray>(%amount%): <white>%homes%");

        build();
    }

}