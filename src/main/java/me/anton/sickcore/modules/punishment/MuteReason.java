package me.anton.sickcore.modules.punishment;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MuteReason {

    HARDCHAT("Hard offense"), LIGHTCHAT("Light offense");

    private final String name;

}
