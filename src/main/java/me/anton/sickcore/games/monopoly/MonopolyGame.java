package me.anton.sickcore.games.monopoly;

import lombok.Getter;
import lombok.Setter;
import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.gameapi.AbstractGame;
import me.anton.sickcore.games.monopoly.gamestate.GameState;
import me.anton.sickcore.games.monopoly.gamestate.GameStateHandler;

@Getter
public class MonopolyGame extends AbstractGame {

    @Getter
    private static MonopolyGame instance;
    @Setter
    private GameState current;
    private GameStateHandler handler;

    @Override
    public void onLoad() {
        instance = this;
        this.handler = new GameStateHandler();

    }

    @Override
    public void onUnload() {
        instance = null;
    }

    @Override
    public boolean nick() {
        return true;
    }

    @Override
    public String getName() {
        return "Monopoly";
    }

    @Override
    public DatabaseModel getModel() {
        return super.getModel();
    }

    @Override
    public DatabaseModel getPlayerModel() {
        return super.getPlayerModel();
    }
}
