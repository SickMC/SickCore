package me.anton.sickcore.api.player.bungeePlayer;

import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public interface IBungeePlayer {

    public void sendMessage(String en, String de);

    public void sendMessage(BaseComponent en, BaseComponent de);

    public IAPIPlayer api();

    public ProxiedPlayer bungeeAPI();
}
