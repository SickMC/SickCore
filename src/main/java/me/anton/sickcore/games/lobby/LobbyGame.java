package me.anton.sickcore.games.lobby;

import lombok.Getter;
import me.anton.sickcore.games.lobby.appereance.LobbyChat;
import me.anton.sickcore.games.lobby.modi.ModiNPCHandler;
import me.anton.sickcore.gameapi.AbstractGame;

@Getter
public class LobbyGame extends AbstractGame {

    @Getter
    private static LobbyGame instance;

    @Override
    public void onLoad() {
        instance = this;
        registerListeners(new Events(), new ModiNPCHandler(), new LobbyChat());
    }

    @Override
    public void onUnload() {

    }

    @Override
    public boolean nick() {
        return false;
    }

    @Override
    public String getName() {
        return "Lobby";
    }

}
