package me.anton.sickcore.api.player.bungeePlayer;

import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.apiPlayer.enums.Language;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

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
    public void sendMessage(String en, String de) {
        if (player.getLanguage().equals(Language.ENGLISCH))
            bungeePlayer.sendMessage(new TextComponent(en));
        else if (player.getLanguage().equals(Language.DEUTSCH))
            bungeePlayer.sendMessage(new TextComponent(de));
    }

    @Override
    public void sendMessage(BaseComponent en, BaseComponent de) {
        if (player.getLanguage().equals(Language.ENGLISCH))
            bungeePlayer.sendMessage(en);
        else if (player.getLanguage().equals(Language.DEUTSCH))
            bungeePlayer.sendMessage(de);
    }
    @Override
    public IAPIPlayer api() {
        return player;
    }

    @Override
    public ProxiedPlayer bungeeAPI() {
        return bungeePlayer;
    }
}

