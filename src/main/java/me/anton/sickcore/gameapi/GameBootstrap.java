package me.anton.sickcore.gameapi;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import me.anton.sickcore.games.build.BuildGame;
import me.anton.sickcore.games.lobby.LobbyGame;
import me.anton.sickcore.games.monopoly.MonopolyGame;
import me.anton.sickcore.games.survival.SurvivalGame;

import java.util.Arrays;
import java.util.List;

public class GameBootstrap {

    public AbstractGame current = null;
    private List<AbstractGame> games = Arrays.asList(new LobbyGame(),
            new BuildGame(),
            new SurvivalGame(),
            new MonopolyGame());

    public void loadGame(){
        for (AbstractGame game : games) {
            if (!CloudNetDriver.getInstance().getComponentName().startsWith(game.getName()))continue;
            this.current = game;
            game.load();
            Logger.info("Game: " + game.getName() + " initialized!", this.getClass());
        }
    }

    public void unloadGame(){
        current.unload();
        Logger.info("Game: " + current.getName() + " unloaded!", this.getClass());
        this.current = null;
    }

}
