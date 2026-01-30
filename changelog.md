# ALERT: This is a major release. Please read the changelog carefully before updating. Specially if you are updating from version 2.x.x.

## Dependency Changes
- Updated [zAPI to 2.0.0](https://github.com/yL3oft/zAPI/releases/tag/2.0.0)

## General Changes
- The whole plugin has been migrated to use zAPI 2.0.0 as a base. This includes changes to how commands, events, and configurations are handled.
- Refactored codebase for better readability and maintainability.
- Removed support for Minecraft versions below 1.18.2.
- Migrated from old coloring system (ChatColor) to more modern text components using MiniMessage for better text formatting and color handling.
- New subcommand added to /zhomes: `/zhomes parse <player> <string>` - Parses a string for a specific player's placeholders with the plugin placeholder system.
- A lot of new features to menus and GUI handling, including better pagination and item management:
- Improved database connections and query handling for better performance.
- Rework on dependency management & automatic download system.
- Rework on how config.yml & languages are handled (This also includes migration to zAPI's new configuration system).
- A lot of new options in config.yml and in languages (I really don't want to list them all here, go check yourself!)
- Improved hook detection and management for better compatibility with other plugins.
- Updated license year to 2026.

## What will happen if I update from 2.x.x?
- The plugin is going to migrate colors from ChatColor to MiniMessage format. If you have custom colors in your config or language files, they will be converted automatically, but it's recommended to review them after the update.
- The configuration file (config.yml) and language files have been restructured. It's recommended to back them up before updating, as some settings may have changed or been removed.
- The plugin no longer supports Minecraft versions below 1.18.2. Ensure your server is running a compatible version
- The menu system for homes has been 100% reworked, and because of that the file of the menu is renamed to "homes-menu.yml", that also means that your old menu file will be ignored. Make sure to back it up if you want to reuse some settings.


**Full Changelog**: https://github.com/yL3oft/zHomes/compare/2.2.2...3.0.0