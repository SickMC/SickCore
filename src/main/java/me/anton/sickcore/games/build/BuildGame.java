package me.anton.sickcore.games.build;

import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.core.BukkitCore;
import me.anton.sickcore.core.game.IGame;
import me.anton.sickcore.games.build.appereance.BuildChat;
import me.anton.sickcore.games.build.tools.BuildTools;
import me.anton.sickcore.games.build.tools.invisitemframes.InvisFrameUpdate;
import me.anton.sickcore.games.defaults.all.appereance.TablistBuilder;

public class BuildGame extends IGame {

    TablistBuilder builder;

    @Override
    public void load() {
        builder = new TablistBuilder("Build", false);
        registerListener(new BuildChat(), new InvisFrameUpdate());

        BukkitCore.getInstance().getManager().registerCommand(new BuildTools(), true);
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
    public String getName() {
        return "Build";
    }

    @Override
    public DatabaseModel getModel() {
        return super.getModel();
    }
}
