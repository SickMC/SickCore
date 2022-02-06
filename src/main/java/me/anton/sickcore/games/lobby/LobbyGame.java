package me.anton.sickcore.games.lobby;

import eu.thesimplecloud.api.eventapi.IListener;
import lombok.Getter;
import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.core.BukkitCore;
import me.anton.sickcore.games.defaults.all.appereance.TablistBuilder;
import me.anton.sickcore.core.game.IGame;
import me.anton.sickcore.games.lobby.appereance.LobbyChat;
import me.anton.sickcore.games.lobby.modi.ModiNPCHandler;
import me.anton.sickcore.games.lobby.utility.profile.ProfileItem;

@Getter
public class LobbyGame extends IGame {

    @Getter
    private static LobbyGame instance;
    @Getter
    private TablistBuilder builder;

    @Override
    public void load() {
        instance = this;
        builder = new TablistBuilder("Lobby", false);
        registerListener(new Events(), new ProfileItem(), new ModiNPCHandler(), new LobbyChat());
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

}
