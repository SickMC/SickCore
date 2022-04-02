package me.anton.sickcore.api.player.bungeePlayer;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import lombok.Getter;
import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.apiPlayer.language.LanguageObject;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.core.ProxyCore;
import net.kyori.adventure.text.Component;

import java.util.UUID;

@Getter
public class BungeePlayer implements IAPIPlayer {

    APIPlayer player;
    Player bungeePlayer;

    public BungeePlayer(APIPlayer player){
        this.player = player;
        this.bungeePlayer = ProxyCore.getInstance().getPlugin().getPlayer(player.getUUID()).get();
    }

    public BungeePlayer(UUID uuid){
        this.player = new APIPlayer(uuid);
        this.bungeePlayer = ProxyCore.getInstance().getPlugin().getPlayer(player.getUUID()).get();
    }

    public BungeePlayer(Player player){
        this.player = new APIPlayer(player.getUniqueId());
        this.bungeePlayer = player;
    }

    public BungeePlayer(CommandSource sender){
        if (!(sender instanceof Player)) throw new NullPointerException();
        this.bungeePlayer = (Player) sender;
        this.player = new APIPlayer(bungeePlayer.getUniqueId());
    }

    
    public void sendMessage(LanguagePath path) {
        getBungeePlayer().sendMessage(Component.text(this.player.languageString(path).build()));
    }

    
    public void sendMessage(LanguageObject object) {
        getBungeePlayer().sendMessage(Component.text(object.build()));
    }

    
    public APIPlayer api() {
        return player;
    }

    
    public Player getPlayer() {
        return bungeePlayer;
    }

    @Override
    public UUID getUniqueID() {
        return player.getUniqueID();
    }
}

