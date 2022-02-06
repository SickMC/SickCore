package me.anton.sickcore.core.game;

import eu.thesimplecloud.api.CloudAPI;
import lombok.Getter;
import me.anton.sickcore.api.utils.common.system.Logger;
import me.anton.sickcore.core.BukkitCore;
import me.anton.sickcore.games.build.BuildGame;
import me.anton.sickcore.games.lobby.LobbyGame;
import me.anton.sickcore.games.survival.SurvivalGame;

import java.util.ArrayList;
import java.util.List;

public class GameHandler {

    private List<IGame> loadedGames = new ArrayList<>();

    public void loadGame(IGame game){
        loadedGames.add(game);
        game.load();
        Logger.info(game.getName() + " started!");
    }

    public void loadGames(){
        if (CloudAPI.getInstance().getThisSidesName().startsWith("Lobby-"))loadGame(new LobbyGame());
        if (CloudAPI.getInstance().getThisSidesName().startsWith("Survival-"))loadGame(new SurvivalGame());
        if (CloudAPI.getInstance().getThisSidesName().startsWith("Build-"))loadGame(new BuildGame());
    }

    public void unloadGame(){
        loadedGames.forEach(IGame::unload);
    }

}
