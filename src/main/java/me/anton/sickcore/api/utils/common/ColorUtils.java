package me.anton.sickcore.api.utils.common;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.md_5.bungee.api.ChatColor;

import java.awt.*;

public class ColorUtils {

    public static TextColor toTextColor(Color color){
        return TextColor.color(color.getRGB());
    }

    public static ChatColor toBungeeColor(Color color){
        return ChatColor.of(color);
    }

    public static NamedTextColor toNamedTextColor(org.bukkit.ChatColor color){
        TextColor color1 = TextColor.color(color.asBungee().getColor().getRed(), color.asBungee().getColor().getGreen(), color.asBungee().getColor().getBlue());
        return NamedTextColor.nearestTo(color1);
    }

    public static NamedTextColor toNamedTextColor(String color){
        org.bukkit.ChatColor color1 = toChatColor(color);
        return toNamedTextColor(color1);
    }

    public static org.bukkit.ChatColor toChatColor(String color){
        return org.bukkit.ChatColor.valueOf(color);
    }

    public static ChatColor toBungeeColor(String color){
        return ChatColor.of(color);
    }

}
