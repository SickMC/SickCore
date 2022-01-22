package me.anton.sickcore.modules.basic.lobby;

import me.anton.sickcore.api.handler.listeners.bungee.BungeeHandler;
import me.anton.sickcore.api.player.bungeePlayer.IBungeePlayer;
import net.md_5.bungee.api.event.ChatEvent;

public class LobbyCommandAd extends BungeeHandler {

    @Override
    public void onChat(ChatEvent rawEvent, String message, IBungeePlayer player) {
        if (!message.equalsIgnoreCase("7l") || !message.equalsIgnoreCase("7lobby") || !message.equalsIgnoreCase("7hub") || !message.equalsIgnoreCase("7leave"))return;
        rawEvent.setCancelled(true);

        if (player.api().cloud().cloudAPI().getConnectedServer().isLobby())player.sendMessage("§4You are already on the lobby!", "§4Du bist bereits in der Lobby!");
        player.api().cloud().cloudAPI().sendToLobby();
        player.sendMessage("§7You were send to the lobby!", "§7Du wurdest zur Lobby teleportiert!");
    }
}
