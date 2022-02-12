package me.anton.sickcore.game.defaults.all;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.arcaniax.hdb.api.DatabaseLoadEvent;
import me.arcaniax.hdb.api.HeadDatabaseAPI;

public class HeadDBAPI extends BukkitHandler {

    private static HeadDatabaseAPI api;

    @Override
    public void onHeadDBLoad(DatabaseLoadEvent rawEvent) {
        api = new HeadDatabaseAPI();
    }

    public static HeadDatabaseAPI getApi() {
        return api;
    }
}
