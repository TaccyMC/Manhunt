package net.taccy.manhunt.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class MessageUtil {

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void sendActionBar(Player target, String message) {
        target.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR,
                TextComponent.fromLegacyText(color(message)));
    }

    public static void log(Level level, String message) {
        Bukkit.getLogger().log(level, "[Manhunt] " + message);
    }

}
