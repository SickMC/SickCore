package me.anton.sickcore.api.player.bukkitPlayer;

import lombok.Getter;
import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;

import java.util.UUID;

@Getter
public class AbstractGamePlayer {

    private final UUID uniqueID;
    private final IBukkitPlayer player;
    private final IAPIPlayer apiPlayer;

    public AbstractGamePlayer(UUID uuid){
        this.uniqueID = uuid;
        this.player = new BukkitPlayer(uuid);
        this.apiPlayer = new APIPlayer(uuid);
    }

}
