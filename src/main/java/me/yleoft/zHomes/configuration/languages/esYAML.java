package me.yleoft.zHomes.configuration.languages;

import java.util.HashMap;
import java.util.Map;

public class esYAML extends LanguageBuilder {

    public esYAML() {
        super("es");
    }

    @Override
    protected Map<String, String> translations() {
        Map<String, String> t = new HashMap<>();

        // config comments
        t.put(formPath("config", "comment", "header1"), "# |                                  Links del plugin & soporte                                  | #");
        t.put(formPath("config", "comment", "header2"), "# |   zHomes (c) yL3oft — publicado bajo la Licencia MIT.                                        | #");
        t.put(formPath("config", "comment", "database"), "Edita la configuración de la base de datos a continuación");
        t.put(formPath("config", "comment", "database", "type"), """
        Aquí puedes definir cómo almacenar los datos del plugin.
        OPCIONES:
        - H2 (Preferible sobre SQLite)
        - SQLite
        - MariaDB (Preferible sobre MySQL)
        - MySQL
        PREDETERMINADO: H2
        """);
        t.put(formPath("config", "comment", "pool-size"), "# ADVERTENCIA: NO CAMBIES NADA A CONTINUACIÓN SI NO SABES LO QUE ESTÁS HACIENDO");
        t.put(formPath("config", "comment", "general", "language"), """
        Aquí puedes definir el idioma del plugin. Todos los idiomas se pueden encontrar, editar y crear en el directorio de idiomas.
        IDIOMAS DISPONIBLES: [de, en, es, fr, it, nl, pl, pt-br, ru, zhcn, <custom>]
        """);
        t.put(formPath("config", "comment", "general", "auto-update"), "Activar o desactivar las actualizaciones automáticas del plugin.");
        t.put(formPath("config", "comment", "general", "announce-update"), "Activar o desactivar si el plugin debe anunciar actualizaciones disponibles en la consola y a jugadores con el permiso adecuado.");
        t.put(formPath("config", "comment", "general", "metrics"), """
        Activar o desactivar la recopilación de métricas para ayudar a mejorar el plugin.
        Todos los datos recopilados son anónimos y se usan exclusivamente con fines estadísticos.
        !ADVERTENCIA: Requiere reinicio del servidor para tener efecto!
        """);
        t.put(formPath("config", "comment", "general", "debug-mode"), "Activar o desactivar el modo de depuración para obtener registros más detallados.");
        t.put(formPath("config", "comment", "teleport-options"), "Configuraciones relacionadas con el comportamiento de teletransporte");
        t.put(formPath("config", "comment", "teleport-options", "enable-safe-teleport"), "Activar o desactivar el teletransporte seguro para evitar que los jugadores sean teletransportados a ubicaciones peligrosas.");
        t.put(formPath("config", "comment", "teleport-options", "dimensional-teleportation"), "Activar o desactivar la teletransportación dimensional, permitiendo a los jugadores teletransportarse entre diferentes mundos o dimensiones.");
        t.put(formPath("config", "comment", "teleport-options", "play-sound"), "Reproducir un efecto de sonido cuando un jugador es teletransportado.");
        t.put(formPath("config", "comment", "teleport-options", "restricted-worlds", "enable"), "Activar o desactivar la restricción de teletransporte a ciertos mundos.");
        t.put(formPath("config", "comment", "teleport-options", "restricted-worlds", "mode"), """
        Define el modo para mundos restringidos.
        OPCIONES:
        - blacklist: Los jugadores no pueden teletransportarse a los mundos listados.
        - whitelist: Los jugadores solo pueden teletransportarse a los mundos listados.
        """);
        t.put(formPath("config", "comment", "teleport-options", "restricted-worlds", "worlds"), "Lista de mundos afectados por la configuración de mundos restringidos.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "enable"), "Activar o desactivar el periodo de calentamiento del teletransporte.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "time"), "Definir el tiempo de calentamiento en segundos antes de que ocurra el teletransporte.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "cancel-on-move"), "Cancelar el teletransporte si el jugador se mueve durante el periodo de calentamiento.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "show-on-actionbar"), "Mostrar la cuenta regresiva del calentamiento en la barra de acción.");
        t.put(formPath("config", "comment", "limits", "enabled"), "Activar o desactivar los límites de homes para los jugadores.");
        t.put(formPath("config", "comment", "limits", "default"), "Número predeterminado de homes que un jugador puede establecer.");
        t.put(formPath("config", "comment", "limits", "examples"), "Ejemplos de límites basados en grupos de jugadores.");
        t.put(formPath("config", "comment", "commands"), "!ADVERTENCIA: Casi todo lo que está a continuación necesita un reinicio para aplicarse.");
        t.put(formPath("config", "comment", "commands", "command-cost"), "command-cost requiere Vault para funcionar.");
        t.put(formPath("config", "comment", "commands", "homes", "types"), """
        Define cómo se mostrarán las homes al jugador.
        OPCIONES:
        - text: Muestra las homes en un formato de lista simple.
        - menu: Abre un menú gráfico para seleccionar homes.
        """);
        t.put(formPath("config", "comment", "permissions"), "Nodos de permisos utilizados por el plugin");
        t.put(formPath("config", "comment", "permissions", "bypass", "limit"), "Permiso para omitir los límites de homes");
        t.put(formPath("config", "comment", "permissions", "bypass", "dimensional-teleportation"), "Permiso para omitir las restricciones de teletransportación dimensional");
        t.put(formPath("config", "comment", "permissions", "bypass", "safe-teleportation"), "Permiso para omitir las verificaciones de teletransporte seguro");
        t.put(formPath("config", "comment", "permissions", "bypass", "restricted-worlds"), "Permiso para omitir las verificaciones de mundos restringidos");
        t.put(formPath("config", "comment", "permissions", "bypass", "warmup"), "Permiso para omitir el calentamiento del teletransporte");
        t.put(formPath("config", "comment", "permissions", "bypass", "command-costs"), "Permiso para omitir los costes de comandos");
        t.put(formPath("config", "comment", "permissions", "bypass", "command-cooldowns"), "Permiso para omitir los tiempos de espera de comandos");

        t.put("header", """
                ####################################################################################################
                # +----------------------------------------------------------------------------------------------+ #
                # |                                                                                              | #
                # |                                zHomes — Archivo de Idioma                                    | #
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
                "Aquí puedes gestionar los mensajes de hooks.");
        t.put(formPath("comments", "teleport-warmup"),
                "Mensajes relacionados con el calentamiento de teletransporte.");
        t.put(formPath("comments", "commands"),
                "Mensajes relacionados con los comandos.");
        t.put(formPath("comments", "commands", "no-permission"),
                "Aquí encontrarás mensajes que se pueden usar en múltiples comandos.");
        t.put(formPath("comments", "commands", "main"),
                "Abajo encontrarás mensajes específicos para los comandos.");

        // hooks
        t.put(formPath("hooks", "griefprevention", "cant-set-homes"),
                "%prefix% <red>No puedes establecer homes en esta área.");
        t.put(formPath("hooks", "worldguard", "cant-use-homes"),
                "%prefix% <red>No puedes usar homes aquí.");
        t.put(formPath("hooks", "worldguard", "cant-set-homes"),
                "%prefix% <red>No puedes establecer una home aquí.");
        t.put(formPath("hooks", "vault", "cant-afford-command"),
                "%prefix% <red>Necesitas <gold>$%cost% <red>para ejecutar este comando.");

        // teleport-warmup
        t.put(formPath("teleport-warmup", "warmup"),
                "%prefix% <green>Teletransportando en %time% segundos... ¡No te muevas!");
        t.put(formPath("teleport-warmup", "warmup-actionbar"),
                "<green>Teletransportando en %time% segundos...");
        t.put(formPath("teleport-warmup", "cancelled"),
                "%prefix% <red>¡Te moviste! Teletransporte cancelado.");
        t.put(formPath("teleport-warmup", "cancelled-actionbar"),
                "<red>¡Te moviste! Teletransporte cancelado.");

        // commands - general
        t.put(formPath("commands", "no-permission"),
                "%prefix% <red>No tienes permiso para ejecutar este comando.");
        t.put(formPath("commands", "only-players"),
                "%prefix% <red>Solo los jugadores pueden ejecutar este comando.");
        t.put(formPath("commands", "in-cooldown"),
                "%prefix% <red>Debes esperar %time% segundos antes de usar este comando de nuevo.");
        t.put(formPath("commands", "home-already-exist"),
                "%prefix% <red>Ya tienes una home con este nombre.");
        t.put(formPath("commands", "home-doesnt-exist"),
                "%prefix% <red>No tienes ninguna home con este nombre.");
        t.put(formPath("commands", "home-doesnt-exist-others"),
                "%prefix% <yellow>%player% <red>no tiene ninguna home con este nombre.");
        t.put(formPath("commands", "cant-use-2dot"),
                "%prefix% <red>No puedes usar <yellow>':' <red>en este comando.");
        t.put(formPath("commands", "cant-find-player"),
                "%prefix% <red>Este jugador no fue encontrado.");
        t.put(formPath("commands", "unable-to-find-safe-location"),
                "%prefix% <red>No se pudo encontrar una ubicación segura para teletransportarte.");
        t.put(formPath("commands", "world-restricted-sethome"),
                "%prefix% <red>No puedes establecer homes en este mundo.");
        t.put(formPath("commands", "world-restricted-home"),
                "%prefix% <red>No puedes teletransportarte a homes en ese mundo.");

        // commands.main.help
        t.put(formPath("commands", "main", "help", "help-perm"), """
                %prefix% <aqua>Usos de <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>help <gray>Muestra este mensaje de ayuda
                <red>-> <yellow>/%command% <green>info <gray>Muestra información del plugin
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                <red>-> <yellow>/%command% <green>(version|ver) <gold>[update]
                <red>-> <yellow>/%command% <green>nearhomes <gold>(Radio) <gray>Lista homes cercanas dentro de un radio
                <red>-> <yellow>/%command% <green>parse <gold>(Jugador) (Texto) <gray>Analiza un texto con placeholders para un jugador específico
                <red>-> <yellow>/%command% <green>converter (<converter-type>) <gray>Convierte datos de un lugar a otro
                <red>-> <yellow>/%command% <green>export <gray>Exporta todas las homes a un único archivo
                <red>-> <yellow>/%command% <green>import (<file>) <gray>Importa homes desde un único archivo
                """);
        t.put(formPath("commands", "main", "help", "help-noperm"), """
                %prefix% <aqua>Usos de <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>(help|?) <gray>Muestra este mensaje de ayuda
                <red>-> <yellow>/%command% <green>(version|ver) <gray>Muestra la versión del plugin
                """);

        // commands.main.info
        t.put(formPath("commands", "main", "info", "output"), """
                %prefix% <aqua>Ejecutando <dark_aqua>%name% v%version% <aqua>por <dark_aqua>%author%<aqua>:
                %prefix% <aqua>- Info del Servidor:
                %prefix% <dark_aqua>   Software: <white>%server.software%
                %prefix% <dark_aqua>   Versión: <white>%server.version%
                %prefix% <dark_aqua>   Requiere actualización: <white>%requpdate%
                %prefix% <dark_aqua>   Idioma: <white>%language%
                %prefix% <aqua>- Almacenamiento:
                %prefix% <dark_aqua>   Tipo: <white>%storage.type%
                %prefix% <dark_aqua>   Usuarios: <white>%storage.users%
                %prefix% <dark_aqua>   Homes: <white>%storage.homes%
                %prefix% <aqua>- Hooks:
                %prefix% <dark_aqua>   PlaceholderAPI: <white>%use.placeholderapi%
                %prefix% <dark_aqua>   MiniPlaceholders: <white>%use.miniplaceholders%
                %prefix% <dark_aqua>   GriefPrevention: <white>%use.griefprevention%
                %prefix% <dark_aqua>   WorldGuard: <white>%use.worldguard%
                %prefix% <dark_aqua>   Vault: <white>%use.vault%
                """);
        t.put(formPath("commands", "main", "info", "requpdate-yes"),
                "<red>Sí <gray>(Usa <yellow>/%command% version <gray>para más información)");
        t.put(formPath("commands", "main", "info", "requpdate-no"),
                "<green>No");

        // commands.main.version
        t.put(formPath("commands", "main", "version", "output"),
                "%prefix% <aqua>Versión actual: <green>%version%");
        t.put(formPath("commands", "main", "version", "update", "output"),
                "%prefix% <green>zHomes actualizado a la última versión <yellow>(%update%)<green>, por favor reinicia tu servidor.");
        t.put(formPath("commands", "main", "version", "update", "no-update"),
                "%prefix% <green>Ya estás usando la última versión de zHomes.");

        // commands.main.reload
        t.put(formPath("commands", "main", "reload", "usage"), """
                %prefix% <aqua>Usos de <yellow>/%command% <green>(reload|rl)<aqua>:
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                """);
        t.put(formPath("commands", "main", "reload", "output"),
                "%prefix% <green>Plugin recargado en <aqua>%time%ms<green>.");
        t.put(formPath("commands", "main", "reload", "commands", "output"),
                "%prefix% <green>Todos los comandos del plugin recargados en <aqua>%time%ms<green>.");
        t.put(formPath("commands", "main", "reload", "config", "output"),
                "%prefix% <green>Archivo de configuración del plugin recargado en <aqua>%time%ms<green>.");
        t.put(formPath("commands", "main", "reload", "languages", "output"),
                "%prefix% <green>Idiomas del plugin recargados en <aqua>%time%ms<green>.");

        // commands.main.nearhomes
        t.put(formPath("commands", "main", "nearhomes", "usage"),
                "<red>-> <yellow>/%command% nearhomes <green>[<Radio>]");
        t.put(formPath("commands", "main", "nearhomes", "output"),
                "%prefix% <gray>Homes cerca de ti dentro de <yellow>%radius% <gray>bloques: <white>%homes%");
        t.put(formPath("commands", "main", "nearhomes", "output-not-found"),
                "%prefix% <red>No se encontraron homes dentro de <yellow>%radius% <red>bloques.");
        t.put(formPath("commands", "main", "nearhomes", "home-string"),
                "<yellow><hover:show_text:'<green>Clic para teletransportarse.'><click:run_command:'/%homecommand% %owner%:%home%'>%home%</click></hover> <gray><hover:show_text:'<green>Clic para filtrar por jugador.'><click:run_command:'/%maincommand% nearhomes %radius% -user %owner%'>(%owner%)</click></hover>");
        t.put(formPath("commands", "main", "nearhomes", "filtered-player", "output"),
                "%prefix% <gray>Homes de <yellow>%player% <gray>cerca de ti dentro de <yellow>%radius% <gray>bloques: <white>%homes%");
        t.put(formPath("commands", "main", "nearhomes", "filtered-player", "home-string"),
                "<yellow><hover:show_text:'<green>Clic para teletransportarse.'><click:run_command:'/%homecommand% %player%:%home%'>%home%</click></hover>");

        // commands.main.parse
        t.put(formPath("commands", "main", "parse", "usage"),
                "<red>-> <yellow>/%command% parse <gold>(Jugador) (Texto)");
        t.put(formPath("commands", "main", "parse", "output"),
                "%prefix% <gray>Texto analizado: <white>%parsed%");

        // commands.main.converter
        t.put(formPath("commands", "main", "converter", "usage"), """
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
        t.put(formPath("commands", "main", "converter", "output"),
                "%prefix% <green>¡Todos los datos convertidos!");
        t.put(formPath("commands", "main", "converter", "error"),
                "%prefix% <red>Algo salió mal al convertir los datos, revisa la consola de tu servidor.");

        // commands.main.export
        t.put(formPath("commands", "main", "export", "output"),
                "%prefix% <green>¡Todas las homes exportadas a <yellow>%file%<green>!");
        t.put(formPath("commands", "main", "export", "error"),
                "%prefix% <red>Algo salió mal al exportar los datos, revisa la consola de tu servidor.");

        // commands.main.import
        t.put(formPath("commands", "main", "import", "usage"),
                "<red>-> <yellow>/%command% import <green>(<archivo>)");
        t.put(formPath("commands", "main", "import", "output"),
                "%prefix% <green>¡Todas las homes importadas desde <yellow>%file%<green>!");
        t.put(formPath("commands", "main", "import", "file-not-found"),
                "%prefix% <red>El archivo <yellow>%file% <red>no fue encontrado.");
        t.put(formPath("commands", "main", "import", "error"),
                "%prefix% <red>Algo salió mal al importar los datos, revisa la consola de tu servidor.");

        // commands.sethome
        t.put(formPath("commands", "sethome", "output"),
                "%prefix% <green>Home <yellow>%home% <green>establecida en tu posición.");
        t.put(formPath("commands", "sethome", "limit-reached"),
                "<red>¡No puedes establecer más homes porque alcanzaste tu límite <yellow>(%limit% homes)<red>!");

        // commands.delhome
        t.put(formPath("commands", "delhome", "output"),
                "%prefix% <red>Home <yellow>%home% <red>eliminada.");

        // commands.home
        t.put(formPath("commands", "home", "output"),
                "%prefix% <green>Teletransportado a <yellow>%home%<green>...");
        t.put(formPath("commands", "home", "cant-dimensional-teleport"),
                "<red>¡Tu teletransporte fue cancelado! La teletransportación dimensional está desactivada.");

        // commands.home.rename
        t.put(formPath("commands", "home", "rename", "usage"),
                "<red>-> <yellow>/%command% <green>rename (Home) (NuevoNombre)");
        t.put(formPath("commands", "home", "rename", "output"),
                "%prefix% <green>Home <yellow>%home% <green>renombrada a <yellow>%newname%<green>.");
        t.put(formPath("commands", "home", "rename", "same-name"),
                "%prefix% <red>No puedes renombrar una home con el mismo nombre.");

        // commands.homes
        t.put(formPath("commands", "homes", "output"),
                "%prefix% <gray>Tus homes (%amount%): <white>%homes%");
        t.put(formPath("commands", "homes", "home-string"),
                "<reset><hover:show_text:'<green>Clic para teletransportarse.'><click:run_command:'/%homecommand% %home%'>%home%</click></hover>");
        t.put(formPath("commands", "homes", "invalid-page"),
                "%prefix% <red>¡Número de página inválido! Usa un número mayor que 0.");

        // commands.homes.others
        t.put(formPath("commands", "homes", "others", "output"),
                "%prefix% <gray>Homes de <yellow>%player% <gray>(%amount%): <white>%homes%");
        t.put(formPath("commands", "homes", "others", "home-string"),
                "<reset><hover:show_text:'<green>Clic para teletransportarse.'><click:run_command:'/%homecommand% %player%:%home%'>%home%</click></hover>");

        return t;
    }

}