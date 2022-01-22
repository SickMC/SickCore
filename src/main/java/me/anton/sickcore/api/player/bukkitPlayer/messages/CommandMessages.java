package me.anton.sickcore.api.player.bukkitPlayer.messages;

import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CommandMessages {

    private final IBukkitPlayer player;
    private final Player bukkitPlayer;

    public CommandMessages(IBukkitPlayer player){
        this.player = player;
        this.bukkitPlayer = Bukkit.getPlayer(player.api().getUUID());
    }

    public CommandMessages(UUID uuid){
        this.player = new APIPlayer(uuid).bukkit();
        this.bukkitPlayer = Bukkit.getPlayer(uuid);
    }

    public void noPermission(){
        player.sendMessage("§4You don't have the permission to perform this command!", "§4Du hast nicht die Berechtigung diesen Command auszuführen!");
    }

    public void noTeam(){
        player.sendMessage("§4This is a staff command!", "§4Das ist ein Staff Command!");
    }

}
