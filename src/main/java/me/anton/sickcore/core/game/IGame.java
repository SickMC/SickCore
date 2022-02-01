package me.anton.sickcore.core.game;

import eu.thesimplecloud.api.eventapi.IListener;
import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.handler.listeners.cloud.CloudListenerProvider;
import me.anton.sickcore.core.BukkitCore;
import me.anton.sickcore.core.Core;
import me.anton.sickcore.games.defaults.all.appereance.ITablistBuilder;

public abstract class IGame {

    public void load(){}
    public void unload(){}
    public void registerListener(BukkitHandler... handler){
        for (BukkitHandler bukkitHandler : handler) {
            Core.getInstance().bukkit().getProvider().register(bukkitHandler);
        }
    }
    public void registerCloudListener(IListener listener){
        CloudListenerProvider.register(listener);
    }
    public BukkitCore getCore(){
        return Core.getInstance().bukkit();
    }
    public String getName(){return null;}
    public DatabaseModel getModel(){
        return new DatabaseModel(getName());
    }
    public ITablistBuilder getTablistBuilder(){return null;}

}
