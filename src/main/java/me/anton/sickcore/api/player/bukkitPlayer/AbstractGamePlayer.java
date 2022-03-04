package me.anton.sickcore.api.player.bukkitPlayer;

import lombok.Getter;
import me.anton.sickcore.api.player.apiPlayer.APIPlayer;

import java.util.UUID;

@Getter
public class AbstractGamePlayer {

    private final UUID uniqueID;
    private final BukkitPlayer player;
    private final APIPlayer apiPlayer;

    public AbstractGamePlayer(UUID uuid){
        this.uniqueID = uuid;
        this.player = new BukkitPlayer(uuid);
        this.apiPlayer = new APIPlayer(uuid);
    }

}
