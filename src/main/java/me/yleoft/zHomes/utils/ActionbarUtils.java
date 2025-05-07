package me.yleoft.zHomes.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.lang.reflect.Constructor;

public class ActionbarUtils {
    private static String version;
    private static Method spigotSendMessage;
    private static Object chatMessageType_ACTION_BAR;
    private static final boolean legacy = Bukkit.getVersion().contains("1.8") || Bukkit.getBukkitVersion().startsWith("1.8");

    public static void send(Player player, String message) {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        String[] parts = packageName.split("\\.");
        version = parts.length > 3 ? parts[3] : "";

        if (!legacy) {
            try {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
                return;
            } catch (Exception ignored) {}
        }

        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object connection = handle.getClass().getField("playerConnection").get(handle);

            Class<?> chatSerializer = Class.forName("net.minecraft.server." + version + ".IChatBaseComponent$ChatSerializer");
            Method a = chatSerializer.getMethod("a", String.class);
            Object icbc = a.invoke(null, "{\"text\":\"" + message + "\"}");

            Class<?> packetPlayOutChat = Class.forName("net.minecraft.server." + version + ".PacketPlayOutChat");
            Constructor<?> constructor = packetPlayOutChat.getConstructor(
                    Class.forName("net.minecraft.server." + version + ".IChatBaseComponent"), byte.class
            );

            Object packet = constructor.newInstance(icbc, (byte) 2);
            connection.getClass().getMethod("sendPacket", Class.forName("net.minecraft.server." + version + ".Packet")).invoke(connection, packet);
        } catch (Exception ignored) {
        }
    }
}
