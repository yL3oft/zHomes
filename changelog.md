## Dependency Changes
- Updated [zAPI to 2.0.1](https://github.com/yL3oft/zAPI/releases/tag/2.0.1)
- Updated to Paper API 1.21.11

## General Changes
- Plugin changed to paper plugin, it will no longer work on spigot or bukkit servers, if you are using spigot or bukkit, please update to paper for better performance and stability.
- Fix folia compatibility for long distance traveling.
- Removed support for Minecraft 1.18 and below. Minimum supported version is now Minecraft 1.19.
- Added [MiniPlaceholders](https://modrinth.com/plugin/miniplaceholders) support, more info in the [wiki](https://docs.yleoft.me/zhomes/)
- New parameter for command /homes: `type` - This parameter allows you to specify the type of homes output to text or menu, usage: `/homes -type <text|menu>`, if not specified, it will default to the one in config.yml.
- `/zhomes parse` can now be parsed with MiniPlaceholders, allowing you to use placeholders from both MiniPlaceholders and PlaceholderAPI at the same time, without any issues. Also you can now parse without specifing the player.
- `config.yml` comments are now translated with the language file (If it's a custom language file, it will fallback to english)
- Changed the way languages are loaded.
- Updated the database to have 1 extra column: `NAME`, this column is used to store the name of the player, it helps with the offline player getter, allowing you to get the name of the player even if they are offline, and also helps with the performance of the plugin, as it reduces the number of queries to the database when getting the name of the player.
- Auto update is no longer supported, due to the plugin now being a paper plugin.

## Language Changes
- Added new 2 options for `/homes`:
- - `home-string` - The string that will be used to display the home in the list of homes, it supports placeholders, you can use `%home%` to display the name of the home and also supports MiniPlaceholders placeholders.
- - `others.home-string` - The string that will be used to display the home in the list of homes when the player is viewing other player's homes, it supports placeholders, you can use `%home%` to display the name of the home, and `%player%` to display the owner of the home and also supports MiniPlaceholders placeholders.
- Since the plugin now accepts MiniMessage, `/zhomes nearhomes` & `/homes` default home-strings now have hover & click effects to teleport to the home. And `/zhomes nearhomes` has a click effect on the player name to filter the search to the player's homes.


**Full Changelog**: https://github.com/yL3oft/zHomes/compare/3.0.0...3.0.1