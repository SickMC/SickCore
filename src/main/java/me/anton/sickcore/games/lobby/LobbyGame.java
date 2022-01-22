package me.anton.sickcore.games.lobby;

import eu.thesimplecloud.api.eventapi.IListener;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.games.lobby.appereance.LobbyChat;
import me.anton.sickcore.core.game.IGame;
import me.anton.sickcore.games.lobby.appereance.LobbyTablist;
import me.anton.sickcore.games.lobby.appereance.LobbyTablistCloudProvider;
import me.anton.sickcore.games.lobby.modi.ModiNPCHandler;
import me.anton.sickcore.games.lobby.utility.profile.ProfileItem;

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
        getProvider().register(new Events());
        getProvider().register(new ProfileItem());
        getProvider().register(new ModiNPCHandler());
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
