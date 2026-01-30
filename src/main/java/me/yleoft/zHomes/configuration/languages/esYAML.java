package me.yleoft.zHomes.configuration.languages;

public class esYAML extends LanguageBuilder {

    public esYAML() {
        super("es");
    }

    public void buildLang() {
        header("""
                ####################################################################################################
                # +----------------------------------------------------------------------------------------------+ #
                # |                                                                                              | #
                # |                                 zHomes – Archivo de Idioma                                   | #
                # |                                                                                              | #
                # |   • Wiki:        https://docs.yleoft.me/zhomes                                               | #
                # |   • Discord:     https://discord.gg/yCdhVDgn4K                                               | #
                # |   • GitHub:      https://github.com/yL3oft/zHomes                                            | #
                # |                                                                                              | #
                # +----------------------------------------------------------------------------------------------+ #
                ####################################################################################################
                """);

        commentSection("hooks", "Aquí puedes gestionar los mensajes de hooks.");
        addDefault(formPath("hooks", "griefprevention", "cant-set-homes"),
                "%prefix% <red>No puedes establecer casas en esta área.");

        addDefault(formPath("hooks", "worldguard", "cant-use-homes"),
                "%prefix% <red>No puedes usar casas aquí.");
        addDefault(formPath("hooks", "worldguard", "cant-set-homes"),
                "%prefix% <red>No puedes establecer una casa aquí.");

        addDefault(formPath("hooks", "vault", "cant-afford-command"),
                "%prefix% <red>Necesitas <gold>$%cost% <red>para ejecutar este comando.");

        commentSection("teleport-warmup", "Mensajes relacionados con el tiempo de preparación de teletransporte.");
        addDefault(formPath("teleport-warmup", "warmup"),
                "%prefix% <green>Teletransportando en %time% segundos... ¡No te muevas!");
        addDefault(formPath("teleport-warmup", "warmup-actionbar"),
                "<green>Teletransportando en %time% segundos...");
        addDefault(formPath("teleport-warmup", "cancelled"),
                "%prefix% <red>¡Te moviste! Teletransporte cancelado.");
        addDefault(formPath("teleport-warmup", "cancelled-actionbar"),
                "<red>¡Te moviste! Teletransporte cancelado.");

        commentSection("commands", "Mensajes relacionados con los comandos.");
        comment(false, "Aquí arriba encontrarás mensajes que se pueden usar en múltiples comandos.");
        addDefault(formPath("commands", "no-permission"),
                "%prefix% <red>No tienes permiso para ejecutar este comando.");
        addDefault(formPath("commands", "only-players"),
                "%prefix% <red>Solo los jugadores pueden ejecutar este comando.");
        addDefault(formPath("commands", "in-cooldown"),
                "%prefix% <red>Debes esperar %time% segundos antes de usar este comando nuevamente.");
        addDefault(formPath("commands", "home-already-exist"),
                "%prefix% <red>Ya tienes una casa con este nombre.");
        addDefault(formPath("commands", "home-doesnt-exist"),
                "%prefix% <red>No tienes ninguna casa con este nombre.");
        addDefault(formPath("commands", "home-doesnt-exist-others"),
                "%prefix% <yellow>%player% <red>no tiene ninguna casa con este nombre.");
        addDefault(formPath("commands", "cant-use-2dot"),
                "%prefix% <red>No puedes usar <yellow>':' <red>en este comando.");
        addDefault(formPath("commands", "cant-find-player"),
                "%prefix% <red>Este jugador no fue encontrado.");
        addDefault(formPath("commands", "unable-to-find-safe-location"),
                "%prefix% <red>No se pudo encontrar una ubicación segura para teletransportarte.");
        addDefault(formPath("commands", "world-restricted-sethome"),
                "%prefix% <red>No puedes establecer casas en este mundo.");
        addDefault(formPath("commands", "world-restricted-home"),
                "%prefix% <red>No puedes teletransportarte a casas en ese mundo.");

        commentSection(formPath("commands", "main"), "A continuación encontrarás mensajes específicos para los comandos.");

        // commands.main.help
        addDefault(formPath("commands", "main", "help", "help-perm"), """
                %prefix% <aqua>Usos de <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>help <gray>Muestra este mensaje de ayuda
                <red>-> <yellow>/%command% <green>info <gray>Muestra información del plugin
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                <red>-> <yellow>/%command% <green>(version|ver) <gold>[update]
                <red>-> <yellow>/%command% <green>nearhomes <gold>(<radio>) <gray>Lista casas cerca de ti dentro de un radio determinado
                <red>-> <yellow>/%command% <green>parse <gold>(Jugador) (Cadena) <gray>Analiza una cadena con marcadores de posición para un jugador específico
                <red>-> <yellow>/%command% <green>converter (<tipo-de-convertidor>) <gray>Convierte datos de un lugar a otro
                <red>-> <yellow>/%command% <green>export <gray>Exporta todas las casas a un solo archivo
                <red>-> <yellow>/%command% <green>import (<archivo>) <gray>Importa casas desde un solo archivo
                """);
        addDefault(formPath("commands", "main", "help", "help-noperm"), """
                %prefix% <aqua>Usos de <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>(help|?) <gray>Muestra este mensaje de ayuda
                <red>-> <yellow>/%command% <green>(version|ver) <gray>Muestra la versión del plugin
                """);

        // commands.main.info
        addDefault(formPath("commands", "main", "info", "output"), """
                %prefix% <aqua>Ejecutando <dark_aqua>%name% v%version% <aqua>por <dark_aqua>%author%<aqua>:
                %prefix% <aqua>- Información del Servidor:
                %prefix% <dark_aqua>   Software: <white>%server.software%
                %prefix% <dark_aqua>   Versión: <white>%server.version%
                %prefix% <dark_aqua>   Requiere actualización: <white>%requpdate%
                %prefix% <dark_aqua>   Idioma: <white>%language%
                %prefix% <aqua>- Almacenamiento:
                %prefix% <dark_aqua>   Tipo: <white>%storage.type%
                %prefix% <dark_aqua>   Usuarios: <white>%storage.users%
                %prefix% <dark_aqua>   Casas: <white>%storage.homes%
                %prefix% <aqua>- Hooks:
                %prefix% <dark_aqua>   PlaceholderAPI: <white>%use.placeholderapi%
                %prefix% <dark_aqua>   GriefPrevention: <white>%use.griefprevention%
                %prefix% <dark_aqua>   WorldGuard: <white>%use.worldguard%
                %prefix% <dark_aqua>   Vault: <white>%use.vault%
                """);
        addDefault(formPath("commands", "main", "info", "requpdate-yes"),
                "<red>Sí <gray>(Usa <yellow>/%command% version <gray>para más información)");
        addDefault(formPath("commands", "main", "info", "requpdate-no"),
                "<green>No");

        // commands.main.version
        addDefault(formPath("commands", "main", "version", "output"),
                "%prefix% <aqua>Versión actual: <green>%version%");
        addDefault(formPath("commands", "main", "version", "update", "output"),
                "%prefix% <green>zHomes actualizado a la última versión <yellow>(%update%)<green>, por favor reinicia tu servidor para aplicar los cambios.");
        addDefault(formPath("commands", "main", "version", "update", "no-update"),
                "%prefix% <green>Ya estás usando la última versión de zHomes.");

        // commands.main.reload
        addDefault(formPath("commands", "main", "reload", "usage"), """
                %prefix% <aqua>Usos de <yellow>/%command% <green>(reload|rl)<aqua>:
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                """);
        addDefault(formPath("commands", "main", "reload", "output"),
                "%prefix% <green>Plugin recargado en <aqua>%time%ms<green>.");
        addDefault(formPath("commands", "main", "reload", "commands", "output"),
                "%prefix% <green>Todos los comandos del plugin recargados en <aqua>%time%ms<green>.");
        addDefault(formPath("commands", "main", "reload", "config", "output"),
                "%prefix% <green>Archivo de configuración del plugin recargado en <aqua>%time%ms<green>.");
        addDefault(formPath("commands", "main", "reload", "languages", "output"), "%prefix% <green>Idiomas del plugin recargados en <aqua>%time%ms<green>.");

        // commands.main.nearhomes
        addDefault(formPath("commands", "main", "nearhomes", "usage"),
                "<red>-> <yellow>/%command% nearhomes <green>[<Radio>]");
        addDefault(formPath("commands", "main", "nearhomes", "output"),
                "%prefix% <gray>Casas cerca de ti dentro de <yellow>%radius% <gray>bloques: <white>%homes%");
        addDefault(formPath("commands", "main", "nearhomes", "output-not-found"),
                "%prefix% <red>No se encontraron casas dentro de <yellow>%radius% <red>bloques.");
        addDefault(formPath("commands", "main", "nearhomes", "home-string"),
                "<yellow>%home% <gray>(%owner%)");

        // commands.main.parse
        addDefault(formPath("commands", "main", "parse", "usage"),
                "<red>-> <yellow>/%command% parse <gold>(Jugador) (Cadena)");
        addDefault(formPath("commands", "main", "parse", "output"),
                "%prefix% <gray>Texto analizado: <white>%parsed%");

        // commands.main.converter
        addDefault(formPath("commands", "main", "converter", "usage"), """
                %prefix% <aqua>Usos de <yellow>/%command% <green>converter<aqua>:
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
                "%prefix% <green>¡Todos los datos convertidos!");
        addDefault(formPath("commands", "main", "converter", "error"),
                "%prefix% <red>Algo salió mal al convertir los datos, por favor revisa la consola de tu servidor.");

        // commands.main.export
        addDefault(formPath("commands", "main", "export", "output"),
                "%prefix% <green>¡Todas las casas exportadas a <yellow>%file%<green>!");
        addDefault(formPath("commands", "main", "export", "error"),
                "%prefix% <red>Algo salió mal al exportar los datos, por favor revisa la consola de tu servidor.");

        // commands.main.import
        addDefault(formPath("commands", "main", "import", "usage"),
                "<red>-> <yellow>/%command% import <green>(<archivo>)");
        addDefault(formPath("commands", "main", "import", "output"),
                "%prefix% <green>¡Todas las casas importadas desde <yellow>%file%<green>!");
        addDefault(formPath("commands", "main", "import", "file-not-found"),
                "%prefix% <red>El archivo <yellow>%file% <red>no fue encontrado.");
        addDefault(formPath("commands", "main", "import", "error"),
                "%prefix% <red>Algo salió mal al importar los datos, por favor revisa la consola de tu servidor.");

        // commands.sethome
        addDefault(formPath("commands", "sethome", "usage"),
                "<red>-> <yellow>/%command% <green>(Casa)");
        addDefault(formPath("commands", "sethome", "output"),
                "%prefix% <green>Casa <yellow>%home% <green>establecida en tu posición.");
        addDefault(formPath("commands", "sethome", "limit-reached"),
                "<red>¡No puedes establecer más casas porque alcanzaste tu límite <yellow>(%limit% casas)<red>!");

        // commands.delhome
        addDefault(formPath("commands", "delhome", "usage"),
                "<red>-> <yellow>/%command% <green>(Casa)");
        addDefault(formPath("commands", "delhome", "output"),
                "%prefix% <red>Casa <yellow>%home% <red>eliminada.");

        // commands.home
        addDefault(formPath("commands", "home", "usage"),
                "<red>-> <yellow>/%command% <green>(Casa)");
        addDefault(formPath("commands", "home", "output"),
                "%prefix% <green>Teletransportado a <yellow>%home%<green>...");
        addDefault(formPath("commands", "home", "cant-dimensional-teleport"),
                "<red>¡Tu teletransporte fue cancelado! El teletransporte dimensional está desactivado.");

        // commands.home.rename
        addDefault(formPath("commands", "home", "rename", "usage"),
                "<red>-> <yellow>/%command% <green>rename (Casa) (NuevoNombre)");
        addDefault(formPath("commands", "home", "rename", "output"),
                "%prefix% <green>Casa <yellow>%home% <green>renombrada a <yellow>%newname%<green>.");
        addDefault(formPath("commands", "home", "rename", "same-name"),
                "%prefix% <red>No puedes renombrar una casa con el mismo nombre.");

        // commands.homes
        addDefault(formPath("commands", "homes", "output"),
                "%prefix% <gray>Tus casas (%amount%): <white>%homes%");
        addDefault(formPath("commands", "homes", "invalid-page"),
                "%prefix% <red>¡Número de página inválido! Por favor usa un número mayor que 0.");

        // commands.homes.others
        addDefault(formPath("commands", "homes", "others", "output"),
                "%prefix% <gray>Casas de <yellow>%player% <gray>(%amount%): <white>%homes%");

        build();
    }

}