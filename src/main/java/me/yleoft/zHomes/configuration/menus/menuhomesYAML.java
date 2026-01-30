package me.yleoft.zHomes.configuration.menus;

import me.yleoft.zAPI.configuration.YAMLBuilder;
import me.yleoft.zAPI.inventory.InventoryBuilder;
import me.yleoft.zHomes.zHomes;

import java.io.File;
import java.util.List;

import static me.yleoft.zAPI.inventory.InventoryBuilder.*;
import static me.yleoft.zAPI.item.ItemBuilder.*;
import static me.yleoft.zAPI.item.ItemBuilder.KEY_DISPLAY_CONDITION;
import static me.yleoft.zAPI.item.ItemBuilder.KEY_PLACEHOLDERS;

public class menuhomesYAML extends YAMLBuilder {

    public menuhomesYAML() {
        super(new File(zHomes.getInstance().getDataFolder(), "menus/homes-menu.yml"));
        migrateLegacyColors(true);
        buildMenu();
    }

    private void buildMenu() {
        header("""
                ####################################################################################################
                # +----------------------------------------------------------------------------------------------+ #
                # |                                                                                              | #
                # |                                 zHomes — Large Homes Menu                                    | #
                # |                                                                                              | #
                # |   • Wiki:        https://docs.yleoft.me/zhomes                                               | #
                # |   • Discord:     https://discord.gg/yCdhVDgn4K                                               | #
                # |   • GitHub:      https://github.com/yL3oft/zHomes                                            | #
                # |                                                                                              | #
                # +----------------------------------------------------------------------------------------------+ #
                ####################################################################################################
                
                Wiki for menus is still being created... If you have any doubts, please ask in the Discord server.
                """);

        addDefault(formPath(KEY_INVENTORY, InventoryBuilder.KEY_TITLE), "<red>> Homes");
        addDefault(formPath(KEY_INVENTORY, "title-other"), "<red>> <yellow>%target%<red>'s Homes");
        comment("This menu is modular, so if you want to just shrink the menu size, just change the number of rows. Make sure it's more than 1 row though!");
        addDefault(formPath(KEY_INVENTORY, InventoryBuilder.KEY_ROWS), 6);
        addDefault(formPath(KEY_INVENTORY, InventoryBuilder.KEY_PLACEHOLDERS, "slots"), "{math: 9*%rows%}");
        addDefault(formPath(KEY_INVENTORY, InventoryBuilder.KEY_PLACEHOLDERS, "home-slots"), "{math: 9*(%rows%-1)}");

        setValue(formPath(KEY_ITEMS, "filler", KEY_MATERIAL), "GRAY_STAINED_GLASS_PANE");
        setValue(formPath(KEY_ITEMS, "filler", KEY_SLOT), "0-%slots%");
        setValue(formPath(KEY_ITEMS, "filler", KEY_NAME), "");

        setValue(formPath(KEY_ITEMS, "close", KEY_MATERIAL), "BARRIER");
        setValue(formPath(KEY_ITEMS, "close", KEY_SLOT), "{math: %slots%-5}");
        setValue(formPath(KEY_ITEMS, "close", KEY_NAME), "<red>Close");
        setValue(formPath(KEY_ITEMS, "close", KEY_COMMANDS), "[INV] close");
        setValue(formPath(KEY_ITEMS, "close", KEY_ENCHANTMENTS), "FIRE_ASPECT;1");
        setValue(formPath(KEY_ITEMS, "close", KEY_ITEM_FLAGS), "HIDE_ENCHANTS");

        setValue(formPath(KEY_ITEMS, "homes", KEY_MATERIAL), "WHITE_BED", "LIGHT_GRAY_BED", "GRAY_BED", "BLACK_BED", "BROWN_BED", "RED_BED", "ORANGE_BED", "YELLOW_BED", "LIME_BED", "GREEN_BED", "CYAN_BED", "LIGHT_BLUE_BED", "BLUE_BED", "PURPLE_BED", "MAGENTA_BED", "PINK_BED");
        setValue(formPath(KEY_ITEMS, "homes", KEY_SLOT), "0-{math: %home-slots%-1}");
        setValue(formPath(KEY_ITEMS, "homes", KEY_NAME), "<green>Home: <yellow>%currenthome%");
        setValue(formPath(KEY_ITEMS, "homes", KEY_LORE), List.of("<aqua>Click to teleport!"));
        setValue(formPath(KEY_ITEMS, "homes", KEY_COMMANDS), "home %currenthome%", "[INV] close");
        setValue(formPath(KEY_ITEMS, "homes", KEY_DISPLAY_CONDITION), "%currenthome%!=");
        setValue(formPath(KEY_ITEMS, "homes", KEY_PLACEHOLDERS, "currenthome"), "%zhomes_%target%_home_{math: %currentitem%+(%page%-1)*%home-slots%}%");

        setValue(formPath(KEY_ITEMS, "previous-page", KEY_MATERIAL), "ARROW");
        setValue(formPath(KEY_ITEMS, "previous-page", KEY_SLOT), "{math: %slots%-7}");
        setValue(formPath(KEY_ITEMS, "previous-page", KEY_NAME), "<red>Previous Page");
        setValue(formPath(KEY_ITEMS, "previous-page", KEY_COMMANDS), "homes {math: %page%-1}");
        setValue(formPath(KEY_ITEMS, "previous-page", KEY_ENCHANTMENTS), "FIRE_ASPECT;1");
        setValue(formPath(KEY_ITEMS, "previous-page", KEY_ITEM_FLAGS), "HIDE_ENCHANTS");
        setValue(formPath(KEY_ITEMS, "previous-page", KEY_DISPLAY_CONDITION), "%page%>1");

        setValue(formPath(KEY_ITEMS, "next-page", KEY_MATERIAL), "ARROW");
        setValue(formPath(KEY_ITEMS, "next-page", KEY_SLOT), "{math: %slots%-3}");
        setValue(formPath(KEY_ITEMS, "next-page", KEY_NAME), "<green>Next Page");
        setValue(formPath(KEY_ITEMS, "next-page", KEY_COMMANDS), "homes {math: %page%+1}");
        setValue(formPath(KEY_ITEMS, "next-page", KEY_ENCHANTMENTS), "FIRE_ASPECT;1");
        setValue(formPath(KEY_ITEMS, "next-page", KEY_ITEM_FLAGS), "HIDE_ENCHANTS");
        setValue(formPath(KEY_ITEMS, "next-page", KEY_DISPLAY_CONDITION), "%zhomes_%target%_set%-(%page%-1)*%home-slots%>%home-slots%");

        build();
    }

}