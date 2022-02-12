package me.anton.sickcore.api.utils.common;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.NotNull;

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

    public static NamedTextColor namedTextColorByChar(@NotNull String chars){
        return toNamedTextColor(org.bukkit.ChatColor.getByChar(chars.replace("§", "")));
    }

}
