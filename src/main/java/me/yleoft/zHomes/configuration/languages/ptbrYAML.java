package me.yleoft.zHomes.configuration.languages;

import java.util.HashMap;
import java.util.Map;

public class ptbrYAML extends LanguageBuilder {

    public ptbrYAML() {
        super("pt-br");
    }

    @Override
    protected Map<String, String> translations() {
        Map<String, String> t = new HashMap<>();

        // config comments
        t.put(formPath("config", "comment", "header1"), "# |                                  Links do plugin & suporte                                   | #");
        t.put(formPath("config", "comment", "header2"), "# |   zHomes (c) yL3oft — lançado sob a Licença MIT.                                             | #");
        t.put(formPath("config", "comment", "database"), "Edite as configurações do banco de dados abaixo");
        t.put(formPath("config", "comment", "database", "type"), """
        Aqui você pode definir como armazenar os dados do plugin.
        OPÇÕES:
        - H2 (Preferível ao SQLite)
        - SQLite
        - MariaDB (Preferível ao MySQL)
        - MySQL
        PADRÃO: H2
        """);
        t.put(formPath("config", "comment", "pool-size"), "# AVISO: NÃO ALTERE NADA ABAIXO SE NÃO SOUBER O QUE ESTÁ FAZENDO");
        t.put(formPath("config", "comment", "general", "language"), """
        Aqui você pode definir o idioma do plugin. Todos os idiomas podem ser encontrados, editados e criados no diretório de idiomas.
        IDIOMAS DISPONÍVEIS: [de, en, es, fr, it, nl, pl, pt-br, ru, zhcn, <custom>]
        """);
        t.put(formPath("config", "comment", "general", "auto-update"), "Ativar ou desativar atualizações automáticas do plugin.");
        t.put(formPath("config", "comment", "general", "announce-update"), "Alternar se o plugin deve anunciar atualizações disponíveis no console e para jogadores com a permissão adequada.");
        t.put(formPath("config", "comment", "general", "metrics"), """
        Ativar ou desativar a coleta de métricas para ajudar a melhorar o plugin.
        Todos os dados coletados são anônimos e usados apenas para fins estatísticos.
        !AVISO: Requer reinicialização do servidor para ter efeito!
        """);
        t.put(formPath("config", "comment", "general", "debug-mode"), "Ativar ou desativar o modo de depuração para registros mais detalhados.");
        t.put(formPath("config", "comment", "teleport-options"), "Configurações relacionadas ao comportamento de teletransporte");
        t.put(formPath("config", "comment", "teleport-options", "enable-safe-teleport"), "Ativar ou desativar o teletransporte seguro para evitar que jogadores sejam teletransportados para locais perigosos.");
        t.put(formPath("config", "comment", "teleport-options", "dimensional-teleportation"), "Ativar ou desativar o teletransporte dimensional, permitindo que jogadores se teletransportem entre diferentes mundos ou dimensões.");
        t.put(formPath("config", "comment", "teleport-options", "play-sound"), "Reproduzir um efeito sonoro quando um jogador é teletransportado.");
        t.put(formPath("config", "comment", "teleport-options", "restricted-worlds", "enable"), "Ativar ou desativar a restrição de teletransporte para determinados mundos.");
        t.put(formPath("config", "comment", "teleport-options", "restricted-worlds", "mode"), """
        Defina o modo para mundos restritos.
        OPÇÕES:
        - blacklist: Jogadores não podem se teletransportar para os mundos listados abaixo.
        - whitelist: Jogadores só podem se teletransportar para os mundos listados abaixo.
        """);
        t.put(formPath("config", "comment", "teleport-options", "restricted-worlds", "worlds"), "Lista de mundos afetados pela configuração de mundos restritos.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "enable"), "Ativar ou desativar o período de aquecimento do teletransporte.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "time"), "Definir o tempo de aquecimento em segundos antes do teletransporte.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "cancel-on-move"), "Cancelar o teletransporte se o jogador se mover durante o período de aquecimento.");
        t.put(formPath("config", "comment", "teleport-options", "warmup", "show-on-actionbar"), "Mostrar contagem regressiva do aquecimento na barra de ação.");
        t.put(formPath("config", "comment", "limits", "enabled"), "Ativar ou desativar limites de home para jogadores.");
        t.put(formPath("config", "comment", "limits", "default"), "Número padrão de homes que um jogador pode definir.");
        t.put(formPath("config", "comment", "limits", "examples"), "Exemplos de limites baseados em grupos de jogadores.");
        t.put(formPath("config", "comment", "commands"), "!AVISO: Quase tudo abaixo requer reinicialização para aplicar.");
        t.put(formPath("config", "comment", "commands", "command-cost"), "command-cost requer Vault para funcionar.");
        t.put(formPath("config", "comment", "commands", "homes", "types"), """
        Defina como as homes serão exibidas ao jogador.
        OPÇÕES:
        - text: Exibe as homes em formato de lista simples.
        - menu: Abre um menu gráfico para selecionar homes.
        """);
        t.put(formPath("config", "comment", "permissions"), "Nós de permissão usados pelo plugin");
        t.put(formPath("config", "comment", "permissions", "bypass", "limit"), "Permissão para ignorar limites de home");
        t.put(formPath("config", "comment", "permissions", "bypass", "dimensional-teleportation"), "Permissão para ignorar restrições de teletransporte dimensional");
        t.put(formPath("config", "comment", "permissions", "bypass", "safe-teleportation"), "Permissão para ignorar verificações de teletransporte seguro");
        t.put(formPath("config", "comment", "permissions", "bypass", "restricted-worlds"), "Permissão para ignorar verificações de mundos restritos");
        t.put(formPath("config", "comment", "permissions", "bypass", "warmup"), "Permissão para ignorar o aquecimento do teletransporte");
        t.put(formPath("config", "comment", "permissions", "bypass", "command-costs"), "Permissão para ignorar custos de comandos");
        t.put(formPath("config", "comment", "permissions", "bypass", "command-cooldowns"), "Permissão para ignorar cooldowns de comandos");

        t.put("header", """
                ####################################################################################################
                # +----------------------------------------------------------------------------------------------+ #
                # |                                                                                              | #
                # |                                 zHomes — Arquivo de Idioma                                   | #
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
                "Aqui você pode gerenciar mensagens de hooks.");
        t.put(formPath("comments", "teleport-warmup"),
                "Mensagens relacionadas ao tempo de aquecimento do teletransporte.");
        t.put(formPath("comments", "commands"),
                "Mensagens relacionadas aos comandos.");
        t.put(formPath("comments", "commands", "no-permission"),
                "Aqui acima você encontrará mensagens que podem ser usadas em vários comandos.");
        t.put(formPath("comments", "commands", "main"),
                "Abaixo você encontrará mensagens específicas para os comandos.");

        // hooks
        t.put(formPath("hooks", "griefprevention", "cant-set-homes"),
                "%prefix% <red>Você não pode definir homes nesta área.");
        t.put(formPath("hooks", "worldguard", "cant-use-homes"),
                "%prefix% <red>Você não pode usar homes aqui.");
        t.put(formPath("hooks", "worldguard", "cant-set-homes"),
                "%prefix% <red>Você não pode definir uma home aqui.");
        t.put(formPath("hooks", "vault", "cant-afford-command"),
                "%prefix% <red>Você precisa de <gold>$%cost% <red>para executar este comando.");

        // teleport-warmup
        t.put(formPath("teleport-warmup", "warmup"),
                "%prefix% <green>Teletransportando em %time% segundos... Não se mova!");
        t.put(formPath("teleport-warmup", "warmup-actionbar"),
                "<green>Teletransportando em %time% segundos...");
        t.put(formPath("teleport-warmup", "cancelled"),
                "%prefix% <red>Você se moveu! Teletransporte cancelado.");
        t.put(formPath("teleport-warmup", "cancelled-actionbar"),
                "<red>Você se moveu! Teletransporte cancelado.");

        // commands - general
        t.put(formPath("commands", "no-permission"),
                "%prefix% <red>Você não tem permissão para executar este comando.");
        t.put(formPath("commands", "only-players"),
                "%prefix% <red>Apenas jogadores podem executar este comando.");
        t.put(formPath("commands", "in-cooldown"),
                "%prefix% <red>Você deve esperar %time% segundos antes de usar este comando novamente.");
        t.put(formPath("commands", "home-already-exist"),
                "%prefix% <red>Você já tem uma home com este nome.");
        t.put(formPath("commands", "home-doesnt-exist"),
                "%prefix% <red>Você não tem nenhuma home com este nome.");
        t.put(formPath("commands", "home-doesnt-exist-others"),
                "%prefix% <yellow>%player% <red>não tem nenhuma home com este nome.");
        t.put(formPath("commands", "cant-use-2dot"),
                "%prefix% <red>Você não pode usar <yellow>':' <red>neste comando.");
        t.put(formPath("commands", "cant-find-player"),
                "%prefix% <red>Este jogador não foi encontrado.");
        t.put(formPath("commands", "unable-to-find-safe-location"),
                "%prefix% <red>Não foi possível encontrar um local seguro para teletransportá-lo.");
        t.put(formPath("commands", "world-restricted-sethome"),
                "%prefix% <red>Você não pode definir homes neste mundo.");
        t.put(formPath("commands", "world-restricted-home"),
                "%prefix% <red>Você não pode se teletransportar para homes nesse mundo.");

        // commands.main.help
        t.put(formPath("commands", "main", "help", "help-perm"), """
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
        t.put(formPath("commands", "main", "help", "help-noperm"), """
                %prefix% <aqua>Usos de <yellow>/%command%<aqua>:
                <red>-> <yellow>/%command% <green>(help|?) <gray>Mostra exatamente esta mensagem de ajuda
                <red>-> <yellow>/%command% <green>(version|ver) <gray>Mostra a versão do plugin
                """);

        // commands.main.info
        t.put(formPath("commands", "main", "info", "output"), """
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
                %prefix% <dark_aqua>   MiniPlaceholders: <white>%use.miniplaceholders%
                %prefix% <dark_aqua>   GriefPrevention: <white>%use.griefprevention%
                %prefix% <dark_aqua>   WorldGuard: <white>%use.worldguard%
                %prefix% <dark_aqua>   Vault: <white>%use.vault%
                """);
        t.put(formPath("commands", "main", "info", "requpdate-yes"),
                "<red>Sim <gray>(Use <yellow>/%command% version <gray>para mais informações)");
        t.put(formPath("commands", "main", "info", "requpdate-no"),
                "<green>Não");

        // commands.main.version
        t.put(formPath("commands", "main", "version", "output"),
                "%prefix% <aqua>Versão atual: <green>%version%");
        t.put(formPath("commands", "main", "version", "update", "output"),
                "%prefix% <green>zHomes atualizado para a versão mais recente <yellow>(%update%)<green>, reinicie seu servidor para aplicar as mudanças.");
        t.put(formPath("commands", "main", "version", "update", "no-update"),
                "%prefix% <green>Você já está usando a versão mais recente do zHomes.");

        // commands.main.reload
        t.put(formPath("commands", "main", "reload", "usage"), """
                %prefix% <aqua>Usos de <yellow>/%command% <green>(reload|rl)<aqua>:
                <red>-> <yellow>/%command% <green>(reload|rl) <gold>[all, commands, config, languages]
                """);
        t.put(formPath("commands", "main", "reload", "output"),
                "%prefix% <green>Plugin recarregado em <aqua>%time%ms<green>.");
        t.put(formPath("commands", "main", "reload", "commands", "output"),
                "%prefix% <green>Todos os comandos do plugin recarregados em <aqua>%time%ms<green>.");
        t.put(formPath("commands", "main", "reload", "config", "output"),
                "%prefix% <green>Arquivo de configuração do plugin recarregado em <aqua>%time%ms<green>.");
        t.put(formPath("commands", "main", "reload", "languages", "output"),
                "%prefix% <green>Idiomas do plugin recarregados em <aqua>%time%ms<green>.");

        // commands.main.nearhomes
        t.put(formPath("commands", "main", "nearhomes", "usage"),
                "<red>-> <yellow>/%command% nearhomes <green>[<Raio>]");
        t.put(formPath("commands", "main", "nearhomes", "output"),
                "%prefix% <gray>Homes perto de você dentro de <yellow>%radius% <gray>blocos: <white>%homes%");
        t.put(formPath("commands", "main", "nearhomes", "output-not-found"),
                "%prefix% <red>Nenhuma home encontrada dentro de <yellow>%radius% <red>blocos.");
        t.put(formPath("commands", "main", "nearhomes", "home-string"),
                "<yellow><hover:show_text:'<green>Clique para teletransportar.'><click:run_command:'/%homecommand% %owner%:%home%'>%home%</click></hover> <gray><hover:show_text:'<green>Clique para filtrar a busca por jogador.'><click:run_command:'/%maincommand% nearhomes %radius% -user %owner%'>(%owner%)</click></hover>");
        t.put(formPath("commands", "main", "nearhomes", "filtered-player", "output"),
                "%prefix% <gray>Homes de <yellow>%player% <gray>perto de você dentro de <yellow>%radius% <gray>blocos: <white>%homes%");
        t.put(formPath("commands", "main", "nearhomes", "filtered-player", "home-string"),
                "<yellow><hover:show_text:'<green>Clique para teletransportar.'><click:run_command:'/%homecommand% %player%:%home%'>%home%</click></hover>");

        // commands.main.parse
        t.put(formPath("commands", "main", "parse", "usage"),
                "<red>-> <yellow>/%command% parse <gold>(Jogador) (Texto)");
        t.put(formPath("commands", "main", "parse", "output"),
                "%prefix% <gray>Texto analisado: <white>%parsed%");

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
                "%prefix% <green>Todos os dados convertidos!");
        t.put(formPath("commands", "main", "converter", "error"),
                "%prefix% <red>Algo deu errado ao converter os dados, verifique o console do seu servidor.");

        // commands.main.export
        t.put(formPath("commands", "main", "export", "output"),
                "%prefix% <green>Todas as homes exportadas para <yellow>%file%<green>!");
        t.put(formPath("commands", "main", "export", "error"),
                "%prefix% <red>Algo deu errado ao exportar os dados, verifique o console do seu servidor.");

        // commands.main.import
        t.put(formPath("commands", "main", "import", "usage"),
                "<red>-> <yellow>/%command% import <green>(<arquivo>)");
        t.put(formPath("commands", "main", "import", "output"),
                "%prefix% <green>Todas as homes importadas de <yellow>%file%<green>!");
        t.put(formPath("commands", "main", "import", "file-not-found"),
                "%prefix% <red>O arquivo <yellow>%file% <red>não foi encontrado.");
        t.put(formPath("commands", "main", "import", "error"),
                "%prefix% <red>Algo deu errado ao importar os dados, verifique o console do seu servidor.");

        // commands.sethome
        t.put(formPath("commands", "sethome", "output"),
                "%prefix% <green>Home <yellow>%home% <green>definida na sua posição.");
        t.put(formPath("commands", "sethome", "limit-reached"),
                "<red>Você não pode definir mais homes porque atingiu seu limite <yellow>(%limit% homes)<red>!");

        // commands.delhome
        t.put(formPath("commands", "delhome", "output"),
                "%prefix% <red>Home <yellow>%home% <red>deletada.");

        // commands.home
        t.put(formPath("commands", "home", "output"),
                "%prefix% <green>Teletransportado para <yellow>%home%<green>...");
        t.put(formPath("commands", "home", "cant-dimensional-teleport"),
                "<red>Seu teletransporte foi cancelado! Teletransporte dimensional está desativado.");

        // commands.home.rename
        t.put(formPath("commands", "home", "rename", "usage"),
                "<red>-> <yellow>/%command% <green>rename (Home) (NovoNome)");
        t.put(formPath("commands", "home", "rename", "output"),
                "%prefix% <green>Home <yellow>%home% <green>renomeada para <yellow>%newname%<green>.");
        t.put(formPath("commands", "home", "rename", "same-name"),
                "%prefix% <red>Você não pode renomear uma home para o mesmo nome.");

        // commands.homes
        t.put(formPath("commands", "homes", "output"),
                "%prefix% <gray>Suas homes (%amount%): <white>%homes%");
        t.put(formPath("commands", "homes", "home-string"),
                "<reset><hover:show_text:'<green>Clique para teletransportar.'><click:run_command:'/%homecommand% %home%'>%home%</click></hover>");
        t.put(formPath("commands", "homes", "invalid-page"),
                "%prefix% <red>Número de página inválido! Use um número maior que 0.");

        // commands.homes.others
        t.put(formPath("commands", "homes", "others", "output"),
                "%prefix% <gray>Homes de <yellow>%player% <gray>(%amount%): <white>%homes%");
        t.put(formPath("commands", "homes", "others", "home-string"),
                "<reset><hover:show_text:'<green>Clique para teletransportar.'><click:run_command:'/%homecommand% %player%:%home%'>%home%</click></hover>");

        return t;
    }

}