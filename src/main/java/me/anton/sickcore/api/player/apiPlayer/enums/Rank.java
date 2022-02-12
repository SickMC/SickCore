package me.anton.sickcore.api.player.apiPlayer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Rank {

    ADMIN(001, "Admin", "§4"),
    MODERATOR(002, "Mod", "§2"),
    DEV(003, "Dev", "§3"),
    BUILDER(004, "Builder", "§3"),
    CONTENT(005, "Content", "§3"),
    MVP(006, "MVP", "§b"),
    VIP(007, "VIP", "§d"),
    PLAYER(010, "Player", "§7");

    private final double priority;
    private final String name;
    private final String color;

}
