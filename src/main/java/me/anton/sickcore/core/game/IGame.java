package me.anton.sickcore.core.game;

import eu.thesimplecloud.api.eventapi.IListener;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.api.handler.listeners.cloud.CloudListenerProvider;
import me.anton.sickcore.core.BukkitCore;
import me.anton.sickcore.core.Core;

public interface IGame {

    public void load();
    public void unload();
    public void register();
    public default BukkitListenerProvider getProvider(){
        return Core.getInstance().bukkit().getProvider();
    }
    public default void registerCloudListener(IListener listener){
        CloudListenerProvider.register(listener);
    }
    public default BukkitCore getCore(){
        return Core.getInstance().bukkit();
    }
    public String getName();

}
