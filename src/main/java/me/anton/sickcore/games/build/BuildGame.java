package me.anton.sickcore.games.build;

import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.core.BukkitCore;
import me.anton.sickcore.core.game.IGame;
import me.anton.sickcore.games.defaults.all.appereance.ITablistBuilder;
import me.anton.sickcore.games.defaults.all.appereance.TablistBuilder;

public class BuildGame extends IGame {
    @Override
    public void load() {

        getTablistBuilder();
    }

    @Override
    public void unload() {

    }
    @Override
    public void registerListener(BukkitHandler... handler) {
        super.registerListener(handler);
    }

    @Override
    public BukkitCore getCore() {
        return super.getCore();
    }

    @Override
    public ITablistBuilder getTablistBuilder() {
        return new TablistBuilder("Build", false);
    }

    @Override
    public String getName() {
        return "Build";
    }

    @Override
    public DatabaseModel getModel() {
        return super.getModel();
    }
}
