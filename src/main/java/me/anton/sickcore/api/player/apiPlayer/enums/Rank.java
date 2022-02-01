package me.anton.sickcore.api.player.apiPlayer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;

@Getter
@AllArgsConstructor
public enum Rank {

    ADMIN(001, "Admin", ChatColor.DARK_RED),
    MODERATOR(002, "Mod", ChatColor.DARK_GREEN),
    DEV(003, "Dev", ChatColor.DARK_AQUA),
    BUILDER(004, "Builder", ChatColor.DARK_AQUA),
    CONTENT(005, "Content", ChatColor.DARK_AQUA),
    MVP(006, "MVP", ChatColor.AQUA),
    VIP(007, "VIP", ChatColor.LIGHT_PURPLE),
    PLAYER(010, "Player", ChatColor.GRAY);

    private final double priority;
    private final String rawPrefix;
    private final ChatColor color;

    public String getPrefix(){
        return getColor() + getRawPrefix() + "§8 × §r";
    }


}
