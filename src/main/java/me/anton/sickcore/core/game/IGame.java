package me.anton.sickcore.core.game;

import eu.thesimplecloud.api.eventapi.IListener;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.handler.listeners.cloud.CloudListenerProvider;
import me.anton.sickcore.core.BukkitCore;
import me.anton.sickcore.core.Core;

public interface IGame {

    public void load();
    public void unload();
    public void registerListener();
    public default void registerListener(BukkitHandler... handler){
        for (BukkitHandler bukkitHandler : handler) {
            Core.getInstance().bukkit().getProvider().register(bukkitHandler);
        }
    }
    public default void registerCloudListener(IListener listener){
        CloudListenerProvider.register(listener);
    }
    public default BukkitCore getCore(){
        return Core.getInstance().bukkit();
    }
    public String getName();

}
