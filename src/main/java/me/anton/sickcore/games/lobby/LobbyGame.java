package me.anton.sickcore.games.lobby;

import eu.thesimplecloud.api.eventapi.IListener;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.games.lobby.appereance.LobbyChat;
import me.anton.sickcore.core.game.IGame;
import me.anton.sickcore.games.lobby.appereance.LobbyTablist;
import me.anton.sickcore.games.lobby.appereance.LobbyTablistCloudProvider;
import me.anton.sickcore.games.lobby.modi.ModiNPCHandler;
import me.anton.sickcore.games.lobby.utility.profile.ProfileItem;

public class LobbyGame implements IGame {

    @Override
    public void load() {
        registerListener();
    }

    @Override
    public void unload() {

    }

    @Override
    public void registerListener() {
        registerListener(new LobbyChat(), new LobbyTablist(), new Events(), new ProfileItem(), new ModiNPCHandler());
        registerCloudListener(new LobbyTablistCloudProvider());
    }


    @Override
    public void registerListener(BukkitHandler... handler) {
        IGame.super.registerListener(handler);
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
