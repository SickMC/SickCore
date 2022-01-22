package me.anton.sickcore.api.player.apiPlayer.enums;

import org.bukkit.ChatColor;

public class RankBridge {

    public static ChatColor getColor(Rank rank){
        if (rank.name().equalsIgnoreCase("admin"))return ChatColor.DARK_RED;
        if (rank.name().equalsIgnoreCase("dev"))return ChatColor.BLUE;
        if (rank.name().equalsIgnoreCase("moderator"))return ChatColor.DARK_GREEN;
        if (rank.name().equalsIgnoreCase("builder"))return ChatColor.BLUE;
        if (rank.name().equalsIgnoreCase("content"))return ChatColor.BLUE;
        if (rank.name().equalsIgnoreCase("mvp"))return ChatColor.AQUA;
        if (rank.name().equalsIgnoreCase("vip"))return ChatColor.LIGHT_PURPLE;
        return ChatColor.GRAY;
    }

}
