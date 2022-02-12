package me.anton.sickcore.game.games.lobby;

import lombok.Getter;
import me.anton.sickcore.game.games.lobby.appereance.LobbyChat;
import me.anton.sickcore.game.games.lobby.modi.ModiNPCHandler;
import me.anton.sickcore.game.games.lobby.utility.profile.ProfileItem;
import me.anton.sickcore.gameapi.AbstractGame;

@Getter
public class LobbyGame extends AbstractGame {

    @Getter
    private static LobbyGame instance;

    @Override
    public void onLoad() {
        instance = this;
        registerListener(new Events(), new ProfileItem(), new ModiNPCHandler(), new LobbyChat());
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
