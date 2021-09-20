package net.taccy.manhunt.utils;

import org.bukkit.ChatColor;

public class Colorize {

    public static String color(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

}
