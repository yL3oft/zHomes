![About the Project](https://cdn.modrinth.com/data/cached_images/2939adae27590da621f6332a61d92a12bd474204.png)
**zHomes** is a plugin for Spigot/Paper/Purpur/Bukkit servers to make player transportation easier and 100% customizable.
It's is 100% free, and only maintained by [yLeoft](https://github.com/yL3oft), has support for PlaceholderAPI, customizable commands, permissions, messages and more.

This plugin can also convert homes you had from previous plugins (The famous ones) See more in: [/zhomes converter](https://github.com/yL3oft/zHomes/wiki/Commands-&-Permissions#zhomes-converter)

THANK YOU for using zHomes, if you want to support the project, or have any questions, consider joining our [Discord](https://discord.gg/yCdhVDgn4K) :)

![Commands](https://cdn.modrinth.com/data/cached_images/cb455c702cf3974b3c5394e22cc3e709f7dd0761.png)

### Admin Commands
- `/zhomes reload`
- `/zhomes converter`

### Home Commands
- `/sethome (Home)`
- `/delhome (Home)` - **Admins can use /delhome (Player:home)**
- `/home (Home)` - **Admins can use /home (Player:home)**
- `/homes` - **Admins can use /homes (Player)**

![Configuration](https://cdn.modrinth.com/data/cached_images/28ec0907a3e472a42fda6f68758355518a82d3f6.png)

Below you will see every configuration file the plugin has, if you need any help using it you can either visit the [wiki](https://github.com/yL3oft/zHomes/wiki) or enter our [discord](https://discord.gg/yCdhVDgn4K)
<details>
<summary>config.yml</summary>

```
prefix: "&8[&4zHomes&8] "

# Edit your mysql database config here
database:
  # Here you can define how to store the plugin data.
  # OPTIONS:
  # - H2 (Preferred over SQLite)
  # - SQLite
  # - MariaDB (Preferred over MySQL)
  # - MySQL
  # DEFAULT: SQLite
  type: SQLite
  host: localhost
  port: 3306
  database: db
  username: root
  password: pass
  # -------------------- #
  # WARNING: DO NOT CHANGE ANYTHING BELOW IF YOU DON'T KNOW WHAT YOU'RE DOING
  pool-size: 10
  table-prefix: zhomes

general:
  # Here you can define the language of the plugin, all languages can be found, edited and created on languages' directory.
  # DEFAULT OPTIONS: [en, pt-br, <custom>] --- DEFAULT: en
  language: "en"
  # Define it the plugin should be updated automatically
  # OPTIONS: [true, false] --- DEFAULT: true
  auto-update: true
  # Here you can define if metrics should be on or off.
  # !WARNING: This option does not work with reload.
  # OPTIONS: [true, false] --- DEFAULT: true
  metrics: true

# In this section you can change the teleport options for the players
teleport-options:
  # This option defines if the players can teleport to other dimensions with homes.
  # OPTIONS: [true, false] --- DEFAULT: true
  dimensional-teleportation: true # There is also a permission to bypass: zhomes.bypass.dimensionalteleportation

limits:
  # Limit example:
  # '15':
  #   - group.vip
  #   - group.mvp
  # This options defines if homes should have a limit or not.
  # OPTIONS: [true, false] --- DEFAULT: false
  enabled: false

  # The default limit of homes a player can have
  # OPTIONS: [>=0] --- DEFAULT: 10
  default: 10

commands:
  main:
    command: zhomes
    description: "The main command for the plugin"
    permission: zhomes.command.main
    aliases:
      - zh
    # Sub command - Help
    help:
      permission: "zhomes.command.main.help" # Only OP by default
    # Sub command - Version
    version:
      permission: "zhomes.command.main.version" # True by default
    # Sub command - Reload
    reload:
      permission: "zhomes.command.main.reload" # Only OP by default
    # Sub command - Reload
    converter:
      permission: "zhomes.command.main.converter" # Only OP by default
  sethome:
    command: sethome
    description: "Sethome"
    permission: "zhomes.command.sethome" # True by default
    aliases:
      - seth
  delhome:
    command: delhome
    description: "Delhome"
    permission: "zhomes.command.delhome" # True by default
    aliases:
      - deletehome
      - delh
    others:
      permission: "zhomes.command.delhome.others" # Only OP by default
  homes:
    command: homes
    description: "Homes"
    permission: "zhomes.command.homes" # True by default
    aliases:
      - myhomes
    others:
      permission: "zhomes.command.homes.others" # Only OP by default
  home:
    command: home
    description: "Home"
    permission: "zhomes.command.home" # True by default
    aliases: []
    others:
      permission: "zhomes.command.home.others" # Only OP by default
```

</details>
<details>
<summary>Language Example (en.yml)</summary>

```
commands:
  # Up here you will find messages that can be used in multiple commands
  home-already-exist: "&cYou already have a home with this name."
  home-doesnt-exist: "&cYou don't have any home with this name."
  home-doesnt-exist-others: "&e%player% &cdon't have any home with this name."
  no-permission: "&cYou don't have permission to execute this command."
  cooldown: "&cYou need to wait %time% seconds to execute this command again."
  cant-use-2dot: "&cYou can't use &e':' &cin this command."
  cant-find-player: "&cThis player was not found."

  # Below you will find messages specific for the commands
  main:
    help:
      help-perm: |-
        %prefix%&bUsages of &e/%command%&b:
        &c-> &e/%command% &a(help|?)
        &c-> &e/%command% &a(reload|rl) &6[all, commands, config, languages]
        &c-> &e/%command% &a(version|ver)
        &c-> &e/%command% &aconverter (converter-type) &7Convert data from one place to another
      help-noperm: |-
        %prefix%&bUsages of &e/%command%&b:
        &c-> &e/%command% &a(help|?)
        &c-> &e/%command% &a(version|ver)
    version:
      output: "%prefix%&bCurrent version: &a%version%"
    reload:
      usage: |-
        %prefix%&bUsages of &e/%command% &a(reload|rl)&b:
        &c-> &e/%command% &a(reload|rl) &6[all, commands, config, languages]
      output: "%prefix%&aReloaded plugin in &b%time%ms&a."
      commands:
        output: "%prefix%&aReloaded all plugin commands in &b%time%ms&a."
      config:
        output: "%prefix%&aReloaded plugin config file in &b%time%ms&a."
      languages:
        output: |-
          %prefix%&aReloaded plugin languages in &b%time%ms&a.
          &7Note: This will not update the language from config.yml, use '/%command% reload config' to do that.
    converter:
      usage: |-
        %prefix%&bUsages of &e/%command% &aconverter&b:
        &c-> &e/%command% &aconverter sqlitetomysql
        &c-> &e/%command% &aconverter mysqltosqlite
        &c-> &e/%command% &aconverter essentials
        &c-> &e/%command% &aconverter sethome
        &c-> &e/%command% &aconverter ultimatehomes
      output: "%prefix%&aAll data converted!"
      error: "%prefix%&cSomething went wrong converting the data, please check your server console."
  sethome:
    usage: "&c-> &e/%command% &a(Home)"
    output: "%prefix%&aHome &e%home% &aset to your position."
    limit-reached: "&cYou can't set more homes because you reached your limit &e(%limit% homes)&c!"
  delhome:
    usage: "&c-> &e/%command% &a(Home)"
    output: "%prefix%&cHome &e%home% &cdeleted."
  home:
    usage: "&c-> &e/%command% &a(Home)"
    output: "%prefix%&aTeleporting to &e%home%&a..."
    cant-dimensional-teleport: "&cYour teleport was cancelled! Dimensional teleportation is disabled."
  homes:
    output: "%prefix%&7Your homes: &f%homes%"
    others:
      output: "%prefix%&e%player%'s &7homes: &f%homes%"
```

</details>



![bStats Graph](https://bstats.org/signatures/bukkit/zHomes.svg)
