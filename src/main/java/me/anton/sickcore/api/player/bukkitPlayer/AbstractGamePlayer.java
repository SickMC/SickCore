package me.anton.sickcore.api.player.bukkitPlayer;

import lombok.Getter;

import java.util.UUID;

@Getter
public class AbstractGamePlayer {

    private final UUID uniqueID;
    private final IBukkitPlayer player;

    public AbstractGamePlayer(UUID uuid){
        this.uniqueID = uuid;
        this.player = new BukkitPlayer(uuid);
    }

}
