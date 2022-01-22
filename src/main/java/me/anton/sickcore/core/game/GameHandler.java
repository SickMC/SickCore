package me.anton.sickcore.core.game;

import eu.thesimplecloud.api.CloudAPI;
import me.anton.sickcore.api.utils.common.system.Logger;
import me.anton.sickcore.games.lobby.LobbyGame;
import me.anton.sickcore.games.survival.SurvivalGame;

import java.util.ArrayList;
import java.util.List;

public class GameHandler {

    private List<IGame> loadedGames = new ArrayList<>();

    public void loadGame(IGame game){
        Logger.info(game.getName() + " started!");
        loadedGames.add(game);
        game.load();
    }

    public void loadGames(){
        if (CloudAPI.getInstance().getThisSidesName().startsWith("Lobby-"))loadGame(new LobbyGame());
        if (CloudAPI.getInstance().getThisSidesName().startsWith("Survival-"))loadGame(new SurvivalGame());
    }

    public void unloadGame(){
        loadedGames.forEach(IGame::unload);
    }

}
