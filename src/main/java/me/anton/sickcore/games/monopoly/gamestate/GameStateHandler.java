package me.anton.sickcore.games.monopoly.gamestate;

import me.anton.sickcore.games.monopoly.MonopolyGame;

public class GameStateHandler {

    public void setGameState(GameState gameState){
        switch (gameState){
            case LOBBY -> {
                MonopolyGame.getInstance().setCurrent(GameState.LOBBY);
                initializeLobby();
            }
            case GAME -> {
                MonopolyGame.getInstance().setCurrent(GameState.GAME);
            }
            case END -> {
                MonopolyGame.getInstance().setCurrent(GameState.END);
            }
        }
    }

    private void initializeLobby(){
        MonopolyGame.getInstance().registerListeners(new LobbyHandler());
    }
}
