![zHomes Banner](https://cdn.modrinth.com/data/cached_images/390b76823fb5785189aa005591a5a7cbc4a0beb8.png)

<p align="center">
  <a href="https://modrinth.com/plugin/zhomes">
    <img src="https://raw.githubusercontent.com/vLuckyyy/badges/main/avaiable-on-modrinth.svg" alt="Modrinth" />
  </a>
  <a href="https://hangar.papermc.io/yLeoft/zHomes">
    <img src="https://raw.githubusercontent.com/vLuckyyy/badges/main/avaiable-on-hangar.svg" alt="Hangar" />
  </a>
  <a href="https://www.spigotmc.org/resources/zhomes.123141/">
    <img src="https://raw.githubusercontent.com/vLuckyyy/badges/main/available-on-spigotmc.svg" alt="SpigotMC" />
  </a>
  <a href="https://bstats.org/plugin/bukkit/zHomes/25021">
    <img src="https://raw.githubusercontent.com/vLuckyyy/badges/main/available-on-bstats.svg" alt="bStats" />
  </a>
</p>

<p align="center">
  <img src="https://img.shields.io/discord/934583519072620615" alt="Discord" />
  <img src="https://ci.codemc.io/job/yL3oft/job/zHomes/badge/icon" alt="Build Status" />
  <img src="https://img.shields.io/github/v/release/yL3oft/zHomes" alt="Latest Release" />
</p>

![About the Project](https://cdn.modrinth.com/data/cached_images/2939adae27590da621f6332a61d92a12bd474204.png)
zHomes is a modern, fast and fully-configurable homes plugin for Spigot/Paper/Purpur/PufferFish servers. It focuses on simplicity, speed and customization so server owners can give players a familiar — but powerful — teleportation experience.

Some features:
- Create, delete and list homes
- Admin tools for managing and converting homes
- PlaceholderAPI support for message placeholders
- Vault support for economy/command-costs
- GriefPrevention and WorldGuard integration for region checks
- Teleport warmup and safe-teleport checks
- Configurable commands, messages and permissions
- 100% configurable menus
- Uses SQL (H2/SQLite/MariaDB/MySQL) for storage (no local data/ directory)

Maintained by [yL3oft](https://github.com/yL3oft). This project is free and open-source.

---
![Quick Setup](https://cdn.modrinth.com/data/cached_images/a77fd7e63c0e306eb91827be8bc8ba55d6cb147c.png)

Requirements
- Java 17+ (check release notes for exact JDK requirement)
- Paper, Spigot or a compatible fork

Installation
1. Download the latest JAR from Releases / Modrinth / Hangar / Spigot.
2. Place the JAR in your server's `plugins/` folder.
3. Start the server once to generate config files (src/main/resources/config.yml defaults).
4. You're done!

Quick test
- As a player: `/sethome home1` then `/home home1`
- As an admin: `/zhomes reload`

---
![Commands](https://cdn.modrinth.com/data/cached_images/cb455c702cf3974b3c5394e22cc3e709f7dd0761.png)

Admin
- `/zhomes info` — Shows plugin info and stats.
- `/zhomes reload` — Reloads plugin configuration and messages.
- `/zhomes version <update>` — Checks the plugin version and updates if `<update>` is specified.
- `/zhomes nearhomes [radius] <-u <user> -h <home>>` — Check for homes near a location (your location by default). Admins can specify a user and home to check near that home.
- `/zhomes parse <player> <string>` - Parse a string with Plugin Placeholders & PlaceholderAPI placeholders for a specific player.
- `/zhomes export` — Export all homes to a .json.gz file in the plugin folder.
- `/zhomes import <file>` — Import homes from a .json.gz file exported by `/zhomes export`.
- `/zhomes converter <type>` — Inline converter tool to import homes from other plugins (example: `/zhomes converter ultimatehomes`). See detailed converter docs: https://docs.yleoft.me/zhomes/commands-and-permissions/zhomes-converter-less-than-type-greater-than

Player / Home commands
- `/sethome [name]` — Create a home (default name if omitted).
- `/delhome [name]` — Delete a home. Admins can use `/delhome <player:home>`.
- `/home [name]` — Teleport to a home. Admins can use `/home <player:home>`.
- `/homes [player]` — List homes. Admins can use `/homes <player>`.
- `/home rename [oldName] [newName]` — Rename a home

Notes
- Command costs and vault integration require Vault to be installed.
- Some commands have admin-only variants (see Permissions below).

Full command list and usage: https://docs.yleoft.me/zhomes/commands-and-permissions

---
![Permissions](https://cdn.modrinth.com/data/cached_images/40bf4619a4b6ccf117f9ddc579aa2697a6ea37ec.png)

Below are the permission nodes and their default intent as defined in the plugin configuration (zHomes/config.yml). Use your permissions plugin to grant or deny these.

Command permissions
- zhomes.command.main — Main command (True by default)
    - zhomes.command.main.help — Help subcommand (Only OP by default)
    - zhomes.command.main.version — Version (True by default)
        - zhomes.command.main.version.update — Version update (Only OP by default)
    - zhomes.command.main.reload — Reload (Only OP by default)
    - zhomes.command.main.converter — Converter (Only OP by default)
- zhomes.command.sethome — `/sethome` (True by default)
- zhomes.command.delhome — `/delhome` (True by default)
    - zhomes.command.delhome.others — `/delhome <player:home>` (Only OP by default)
- zhomes.command.homes — `/homes` (True by default)
    - zhomes.command.homes.others — `/homes <player>` (Only OP by default)
- zhomes.command.home — `/home` (True by default)
    - zhomes.command.home.others — `/home <player:home>` (Only OP by default)
    - zhomes.command.home.rename — `/home rename` (True by default)

Bypass / misc permissions (as defined in config.yml)
- zhomes.bypass.limit — Bypass home limits (Only OP by default)
- zhomes.bypass.dimensionalteleportation — Bypass dimension restriction (Only OP by default)
- zhomes.bypass.safeteleport — Bypass safe-teleport checking (False by default)
- zhomes.bypass.warmup — Bypass teleport warmup (Only OP by default)
- %command_permission%.bypass.command-cost — Bypass command-cost (Only OP by default). Command-specific bypass permission is generated from the command permission string (see config.yml)
- %command_permission%.bypass.command-cooldown — Bypass commands cooldown (Only OP by default). Command-specific bypass permission is generated from the command permission string (see config.yml)

If you want to see the authoritative defaults and change them, open src/main/resources/config.yml in the repo or check the live docs: https://docs.yleoft.me/zhomes/commands-and-permissions

---
![Migration](https://cdn.modrinth.com/data/cached_images/7afe5e3f8cd9ab1810a6995fd3642ff2a86f2e51.png)

zHomes contains an inline converter to import homes from well-known plugins. The converter runs in-chat (not a GUI). See full converter details here: https://docs.yleoft.me/zhomes/commands-and-permissions/zhomes-converter-less-than-type-greater-than

---
![Integrations](https://cdn.modrinth.com/data/cached_images/8364276160b1c02ff9531cda26955dce593eea92.png)

Supported integrations:
- PlaceholderAPI — message placeholders supported.
- Vault — for economy-based command-costs.
- GriefPrevention — region checks integration.
- WorldGuard — region and flag checks.
- MiniMessage — advanced message formatting.
- bStats — anonymous metrics (configurable).

Enable or disable integrations from config and ensure the third-party plugins are installed on the server when you expect integration behavior.

---
![Support & Community](https://cdn.modrinth.com/data/cached_images/9998e313cf239213862c4c6dbf0fe3bfdb63564e.png)

If you find zHomes helpful or need assistance:
- Discord: https://discord.gg/yCdhVDgn4K
- Issues: https://github.com/yL3oft/zHomes/issues
- Docs: https://docs.yleoft.me/zhomes

Support the project by starring the repo and joining the Discord!

---

<p align="center">
  <img src="https://bstats.org/signatures/bukkit/zHomes.svg" alt="bStats Graph" />
</p>