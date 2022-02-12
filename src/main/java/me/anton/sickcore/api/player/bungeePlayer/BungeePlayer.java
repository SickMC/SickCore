package me.anton.sickcore.api.player.bungeePlayer;

import lombok.Getter;
import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.apiPlayer.language.LanguageObject;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

@Getter
public class BungeePlayer implements IBungeePlayer {

    IAPIPlayer player;
    ProxiedPlayer bungeePlayer;

    public BungeePlayer(IAPIPlayer player){
        this.player = player;
        this.bungeePlayer = ProxyServer.getInstance().getPlayer(player.getUUID());
    }

    public BungeePlayer(UUID uuid){
        this.player = new APIPlayer(uuid);
        this.bungeePlayer = ProxyServer.getInstance().getPlayer(uuid);
    }

    public BungeePlayer(ProxiedPlayer player){
        this.player = new APIPlayer(player.getUniqueId());
        this.bungeePlayer = player;
    }

    public BungeePlayer(CommandSender sender){
        if (!(sender instanceof ProxiedPlayer)) throw new NullPointerException();
        this.bungeePlayer = (ProxiedPlayer) sender;
        this.player = new APIPlayer(bungeePlayer.getUniqueId());
    }

    @Override
    public void sendMessage(LanguagePath path) {
        getBungeePlayer().sendMessage(new TextComponent(this.player.languageString(path).build()));
    }

    @Override
    public void sendMessage(LanguageObject object) {
        getBungeePlayer().sendMessage(new TextComponent(object.build()));
    }

    @Override
    public IAPIPlayer api() {
        return player;
    }

    @Override
    public ProxiedPlayer getPlayer() {
        return bungeePlayer;
    }
}

