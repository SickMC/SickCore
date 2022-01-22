package me.anton.sickcore.games.lobby;

import eu.thesimplecloud.api.eventapi.IListener;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.games.lobby.appereance.LobbyChat;
import me.anton.sickcore.core.game.IGame;
import me.anton.sickcore.games.lobby.appereance.LobbyTablist;
import me.anton.sickcore.games.lobby.appereance.LobbyTablistCloudProvider;

public class LobbyGame implements IGame {

    @Override
    public void load() {
        register();
    }

    @Override
    public void unload() {

    }

    @Override
    public void register() {
        getProvider().register(new LobbyChat());
        getProvider().register(new LobbyTablist());

        registerCloudListener(new LobbyTablistCloudProvider());
    }

    @Override
    public BukkitListenerProvider getProvider() {
        return IGame.super.getProvider();
    }

    @Override
    public void registerCloudListener(IListener listener) {
        IGame.super.registerCloudListener(listener);
    }

    @Override
    public String getName() {
        return "Lobby";
    }
}
