package me.anton.sickcore.core.game;

import me.anton.sickcore.core.Core;
import me.anton.sickcore.games.lobby.LobbyGame;
import me.anton.sickcore.games.survival.SurvivalGame;

import java.util.ArrayList;
import java.util.List;

public class GameHandler {

    private List<IGame> loadedGames = new ArrayList<>();

    public void loadGame(){
        if (Core.getInstance().bukkit().getPlugin().getServer().getName().startsWith("Lobby-")){loadedGames.add(new LobbyGame()); new LobbyGame().load();}
        if (Core.getInstance().bukkit().getPlugin().getServer().getName().startsWith("Survival-")){loadedGames.add(new SurvivalGame()); new SurvivalGame().load();}

    }

    public void unloadGame(){
        loadedGames.forEach(IGame::unload);
    }

}
