package me.anton.sickcore.api.player.apiPlayer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.anton.sickcore.api.utils.common.ColorUtils;

@Getter
@AllArgsConstructor
public enum Rank {

    ADMIN(001, "Admin", "DARK_RED"),
    MODERATOR(002, "Mod", "DARK_GREEN"),
    DEV(003, "Dev", "DARK_AQUA"),
    BUILDER(004, "Builder", "DARK_AQUA"),
    CONTENT(005, "Content", "DARK_AQUA"),
    MVP(006, "MVP", "AQUA"),
    VIP(007, "VIP", "LIGHT_PURPLE"),
    PLAYER(010, "Player", "GRAY");

    private final double priority;
    private final String name;
    private final String color;

}
