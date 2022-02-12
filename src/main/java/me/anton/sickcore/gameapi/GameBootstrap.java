package me.anton.sickcore.gameapi;

import eu.thesimplecloud.api.CloudAPI;
import me.anton.sickcore.api.utils.common.Logger;
import me.anton.sickcore.games.build.BuildGame;
import me.anton.sickcore.games.lobby.LobbyGame;
import me.anton.sickcore.games.survival.SurvivalGame;

import java.util.Arrays;
import java.util.List;

public class GameBootstrap {

    public AbstractGame current = null;
    private List<AbstractGame> games = Arrays.asList(new LobbyGame(),
            new BuildGame(),
            new SurvivalGame());

    public void loadGame(){
        for (AbstractGame game : games) {
            if (!CloudAPI.getInstance().getThisSidesName().startsWith(game.getName()))continue;
            this.current = game;
            game.load();
            Logger.info("Game: " + game.getName() + " initialized!");
        }
    }

    public void unloadGame(){
        current.unload();
        Logger.info("Game: " + current.getName() + " unloaded!");
        this.current = null;
    }

}
