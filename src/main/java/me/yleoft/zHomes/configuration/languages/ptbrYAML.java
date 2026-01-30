package me.yleoft.zHomes.configuration.languages;

public class ptbrYAML extends LanguageBuilder {

    public ptbrYAML() {
        super("pt-br");
    }

    public void buildLang() {
        header("""
                ####################################################################################################
                # +----------------------------------------------------------------------------------------------+ #
                # |                                                                                              | #
                # |                                 zHomes – Arquivo de Idioma                                   | #
                # |                                                                                              | #
                # |   • Wiki:        https://docs.yleoft.me/zhomes                                               | #
                # |   • Discord:     https://discord.gg/yCdhVDgn4K                                               | #
                # |   • GitHub:      https://github.com/yL3oft/zHomes                                            | #
                # |                                                                                              | #
                # +----------------------------------------------------------------------------------------------+ #
                ####################################################################################################
                """);

        commentSection("hooks", "Aqui você pode gerenciar mensagens de hooks.");
        addDefault(formPath("hooks", "griefprevention", "cant-set-homes"),
                "%prefix% <red>Você não pode definir homes nesta área.");

        addDefault(formPath("hooks", "worldguard", "cant-use-homes"),
                "%prefix% <red>Você não pode usar homes aqui.");
        addDefault(formPath("hooks", "worldguard", "cant-set-homes"),
                "%prefix% <red>Você não pode definir uma home aqui.");

        addDefault(formPath("hooks", "vault", "cant-afford-command"),
                "%prefix% <red>Você precisa de <gold>$%cost% <red>para executar este comando.");

        commentSection("teleport-warmup", "Mensagens relacionadas ao tempo de aquecimento do teletransporte.");
        addDefault(formPath("teleport-warmup", "warmup"),
                "%prefix% <green>Teletransportando em %time% segundos... Não se mova!");
        addDefault(formPath("teleport-warmup", "warmup-actionbar"),
                "<green>Teletransportando em %time% segundos...");
        addDefault(formPath("teleport-warmup", "cancelled"),
                "%prefix% <red>Você se moveu! Teletransporte cancelado.");
        addDefault(formPath("teleport-warmup", "cancelled-actionbar"),
                "<red>Você se moveu! Teletransporte cancelado.");

        commentSection("commands", "Mensagens relacionadas aos comandos.");
        comment(false, "Aqui acima você encontrará mensagens que podem ser usadas em vários comandos.");
        addDefault(formPath("commands", "no-permission"),
                "%prefix% <red>Você não tem permissão para executar este comando.");
        addDefault(formPath("commands", "only-players"),
                "%prefix% <red>Apenas jogadores podem executar este comando.");
        addDefault(formPath("commands", "in-cooldown"),
                "%prefix% <red>Você deve esperar %time% segundos antes de usar este comando novamente.");
        addDefault(formPath("commands", "home-already-exist"),
                "%prefix% <red>Você já tem uma home com este nome.");
        addDefault(formPath("commands", "home-doesnt-exist"),
                "%prefix% <red>Você não tem nenhuma home com este nome.");
        addDefault(formPath("commands", "home-doesnt-exist-others"),
                "%prefix% <yellow>%player% <red>não tem nenhuma home com este nome.");
        addDefault(formPath("commands", "cant-use-2dot"),
                "%prefix% <red>Você não pode usar <yellow>':' <red>neste comando.");
        addDefault(formPath("commands", "cant-find-player"),
                "%prefix% <red>Este jogador não foi encontrado.");
        addDefault(formPath("commands", "unable-to-find-safe-location"),
                "%prefix% <red>Não foi possível encontrar um local seguro para teletransportá-lo.");
        addDefault(formPath("commands", "world-restricted-sethome"),
                "%prefix% <red>Você não pode definir homes neste mundo.");
        addDefault(formPath("commands", "world-restricted-home"),
                "%prefix% <red>Você não pode se teletransportar para homes nesse mundo.");

        commentSection(formPath("commands", "main"), "Abaixo você encontrará mensagens específicas para os comandos.");

        // commands.main.help
        addDefault(formPath("commands", "main", "help", "help-perm"), """
                %prefix% <aqua>Usos de <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>help <gray>Mostra exatamente esta mensagem de ajuda
                <red>-> <yellow>/%command% <green>info <gray>Mostra informações do plugin
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                <red>-> <yellow>/%command% <green>(version|ver) <gold>[update]
                <red>-> <yellow>/%command% <green>nearhomes <gold>(<raio>) <gray>Lista homes perto de você dentro de um certo raio
                <red>-> <yellow>/%command% <green>parse <gold>(Jogador) (Texto) <gray>Analisa um texto com placeholders para um jogador específico
                <red>-> <yellow>/%command% <green>converter (<tipo-de-conversor>) <gray>Converte dados de um lugar para outro
                <red>-> <yellow>/%command% <green>export <gray>Exporta todas as homes para um único arquivo
                <red>-> <yellow>/%command% <green>import (<arquivo>) <gray>Importa homes de um único arquivo
                """);
        addDefault(formPath("commands", "main", "help", "help-noperm"), """
                %prefix% <aqua>Usos de <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>(help|?) <gray>Mostra exatamente esta mensagem de ajuda
                <red>-> <yellow>/%command% <green>(version|ver) <gray>Mostra a versão do plugin
                """);

        // commands.main.info
        addDefault(formPath("commands", "main", "info", "output"), """
                %prefix% <aqua>Executando <dark_aqua>%name% v%version% <aqua>por <dark_aqua>%author%<aqua>:
                %prefix% <aqua>- Info do Servidor:
                %prefix% <dark_aqua>   Software: <white>%server.software%
                %prefix% <dark_aqua>   Versão: <white>%server.version%
                %prefix% <dark_aqua>   Requer atualização: <white>%requpdate%
                %prefix% <dark_aqua>   Idioma: <white>%language%
                %prefix% <aqua>- Armazenamento:
                %prefix% <dark_aqua>   Tipo: <white>%storage.type%
                %prefix% <dark_aqua>   Usuários: <white>%storage.users%
                %prefix% <dark_aqua>   Homes: <white>%storage.homes%
                %prefix% <aqua>- Hooks:
                %prefix% <dark_aqua>   PlaceholderAPI: <white>%use.placeholderapi%
                %prefix% <dark_aqua>   GriefPrevention: <white>%use.griefprevention%
                %prefix% <dark_aqua>   WorldGuard: <white>%use.worldguard%
                %prefix% <dark_aqua>   Vault: <white>%use.vault%
                """);
        addDefault(formPath("commands", "main", "info", "requpdate-yes"),
                "<red>Sim <gray>(Use <yellow>/%command% version <gray>para mais informações)");
        addDefault(formPath("commands", "main", "info", "requpdate-no"),
                "<green>Não");

        // commands.main.version
        addDefault(formPath("commands", "main", "version", "output"),
                "%prefix% <aqua>Versão atual: <green>%version%");
        addDefault(formPath("commands", "main", "version", "update", "output"),
                "%prefix% <green>zHomes atualizado para a versão mais recente <yellow>(%update%)<green>, reinicie seu servidor para aplicar as mudanças.");
        addDefault(formPath("commands", "main", "version", "update", "no-update"),
                "%prefix% <green>Você já está usando a versão mais recente do zHomes.");

        // commands.main.reload
        addDefault(formPath("commands", "main", "reload", "usage"), """
                %prefix% <aqua>Usos de <yellow>/%command% <green>(reload|rl)<aqua>:
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                """);
        addDefault(formPath("commands", "main", "reload", "output"),
                "%prefix% <green>Plugin recarregado em <aqua>%time%ms<green>.");
        addDefault(formPath("commands", "main", "reload", "commands", "output"),
                "%prefix% <green>Todos os comandos do plugin recarregados em <aqua>%time%ms<green>.");
        addDefault(formPath("commands", "main", "reload", "config", "output"),
                "%prefix% <green>Arquivo de configuração do plugin recarregado em <aqua>%time%ms<green>.");
        addDefault(formPath("commands", "main", "reload", "languages", "output"), "%prefix% <green>Idiomas do plugin recarregados em <aqua>%time%ms<green>.");

        // commands.main.nearhomes
        addDefault(formPath("commands", "main", "nearhomes", "usage"),
                "<red>-> <yellow>/%command% nearhomes <green>[<Raio>]");
        addDefault(formPath("commands", "main", "nearhomes", "output"),
                "%prefix% <gray>Homes perto de você dentro de <yellow>%radius% <gray>blocos: <white>%homes%");
        addDefault(formPath("commands", "main", "nearhomes", "output-not-found"),
                "%prefix% <red>Nenhuma home encontrada dentro de <yellow>%radius% <red>blocos.");
        addDefault(formPath("commands", "main", "nearhomes", "home-string"),
                "<yellow>%home% <gray>(%owner%)");

        // commands.main.parse
        addDefault(formPath("commands", "main", "parse", "usage"),
                "<red>-> <yellow>/%command% parse <gold>(Jogador) (Texto)");
        addDefault(formPath("commands", "main", "parse", "output"),
                "%prefix% <gray>Texto analisado: <white>%parsed%");

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
                "%prefix% <green>Todos os dados convertidos!");
        addDefault(formPath("commands", "main", "converter", "error"),
                "%prefix% <red>Algo deu errado ao converter os dados, verifique o console do seu servidor.");

        // commands.main.export
        addDefault(formPath("commands", "main", "export", "output"),
                "%prefix% <green>Todas as homes exportadas para <yellow>%file%<green>!");
        addDefault(formPath("commands", "main", "export", "error"),
                "%prefix% <red>Algo deu errado ao exportar os dados, verifique o console do seu servidor.");

        // commands.main.import
        addDefault(formPath("commands", "main", "import", "usage"),
                "<red>-> <yellow>/%command% import <green>(<arquivo>)");
        addDefault(formPath("commands", "main", "import", "output"),
                "%prefix% <green>Todas as homes importadas de <yellow>%file%<green>!");
        addDefault(formPath("commands", "main", "import", "file-not-found"),
                "%prefix% <red>O arquivo <yellow>%file% <red>não foi encontrado.");
        addDefault(formPath("commands", "main", "import", "error"),
                "%prefix% <red>Algo deu errado ao importar os dados, verifique o console do seu servidor.");

        // commands.sethome
        addDefault(formPath("commands", "sethome", "usage"),
                "<red>-> <yellow>/%command% <green>(Home)");
        addDefault(formPath("commands", "sethome", "output"),
                "%prefix% <green>Home <yellow>%home% <green>definida na sua posição.");
        addDefault(formPath("commands", "sethome", "limit-reached"),
                "<red>Você não pode definir mais homes porque atingiu seu limite <yellow>(%limit% homes)<red>!");

        // commands.delhome
        addDefault(formPath("commands", "delhome", "usage"),
                "<red>-> <yellow>/%command% <green>(Home)");
        addDefault(formPath("commands", "delhome", "output"),
                "%prefix% <red>Home <yellow>%home% <red>deletada.");

        // commands.home
        addDefault(formPath("commands", "home", "usage"),
                "<red>-> <yellow>/%command% <green>(Home)");
        addDefault(formPath("commands", "home", "output"),
                "%prefix% <green>Teletransportado para <yellow>%home%<green>...");
        addDefault(formPath("commands", "home", "cant-dimensional-teleport"),
                "<red>Seu teletransporte foi cancelado! Teletransporte dimensional está desativado.");

        // commands.home.rename
        addDefault(formPath("commands", "home", "rename", "usage"),
                "<red>-> <yellow>/%command% <green>rename (Home) (NovoNome)");
        addDefault(formPath("commands", "home", "rename", "output"),
                "%prefix% <green>Home <yellow>%home% <green>renomeada para <yellow>%newname%<green>.");
        addDefault(formPath("commands", "home", "rename", "same-name"),
                "%prefix% <red>Você não pode renomear uma home para o mesmo nome.");

        // commands.homes
        addDefault(formPath("commands", "homes", "output"),
                "%prefix% <gray>Suas homes (%amount%): <white>%homes%");
        addDefault(formPath("commands", "homes", "invalid-page"),
                "%prefix% <red>Número de página inválido! Use um número maior que 0.");

        // commands.homes.others
        addDefault(formPath("commands", "homes", "others", "output"),
                "%prefix% <gray>Homes de <yellow>%player% <gray>(%amount%): <white>%homes%");

        build();
    }

}