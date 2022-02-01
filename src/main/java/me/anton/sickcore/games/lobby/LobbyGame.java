package me.anton.sickcore.games.lobby;

import eu.thesimplecloud.api.eventapi.IListener;
import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.core.BukkitCore;
import me.anton.sickcore.games.defaults.all.appereance.ITablistBuilder;
import me.anton.sickcore.games.defaults.all.appereance.TablistBuilder;
import me.anton.sickcore.core.game.IGame;

public class LobbyGame extends IGame {

    @Override
    public void load() {
        getTablistBuilder();
    }

    @Override
    public void unload() {

    }

    @Override
    public String getName() {
        return "Lobby";
    }

    @Override
    public void registerListener(BukkitHandler... handler) {
        super.registerListener(handler);
    }

    @Override
    public void registerCloudListener(IListener listener) {
        super.registerCloudListener(listener);
    }

    @Override
    public BukkitCore getCore() {
        return super.getCore();
    }

    @Override
    public DatabaseModel getModel() {
        return super.getModel();
    }

    @Override
    public ITablistBuilder getTablistBuilder() {
        return new TablistBuilder("Lobby", false);
    }
}
