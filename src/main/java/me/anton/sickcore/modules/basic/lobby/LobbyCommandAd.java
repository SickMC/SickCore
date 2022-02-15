package me.anton.sickcore.modules.basic.lobby;

import me.anton.sickcore.api.handler.listeners.bungee.BungeeHandler;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.player.bungeePlayer.IBungeePlayer;
import net.md_5.bungee.api.event.ChatEvent;

public class LobbyCommandAd extends BungeeHandler {

    String[] messages = {"7l", "7hub", "7leave", "7lobby"};

    @Override
    public void onChat(ChatEvent rawEvent, String message, IBungeePlayer player) {
        for (String s : messages){
            if (!s.equalsIgnoreCase(message))continue;
            if (player.api().cloud().cloudAPI().getConnectedServer().isLobby()){
                player.sendMessage(LanguagePath.PROXY_STAFF_COMMAND_LOBBY_ALREADY);
                return;
            }
            player.api().cloud().cloudAPI().sendToLobby();
            player.sendMessage(LanguagePath.PROXY_STAFF_COMMAND_LOBBY_SUCCESS);
            rawEvent.setCancelled(true);
        }
    }
}
