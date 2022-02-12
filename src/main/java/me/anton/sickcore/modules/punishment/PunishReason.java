package me.anton.sickcore.modules.punishment;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PunishReason {

    HACKING("Hacking/Cheating"), TEAMING("Teaming"), BUGUSING("Bugusing"), APPEREANCE("Appereance"), PERMA("Permanent");

    public String name;

}
