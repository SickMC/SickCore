package me.anton.sickcore.api.handler.listeners.bukkit;

import me.anton.sickcore.api.utils.common.runnable.Runnabled;
import me.anton.sickcore.api.utils.common.Logger;

import java.util.ArrayList;
import java.util.Collection;

public class BukkitListenerProvider {

    protected final Collection<BukkitHandler> handlers = new ArrayList<>();

    public void register(BukkitHandler bukkitHandler){
        handlers.add(bukkitHandler);
    }

    public boolean isRegistered(BukkitHandler bukkitHandler){
        return handlers.contains(bukkitHandler);
    }

    public void iterator(Runnabled<BukkitHandler> task){
        for (BukkitHandler handler : handlers){
            try {
                task.run(handler);
            }catch (Exception e){
                Logger.error("Error while run bukkit handler " + handler.getClass().getName(), this.getClass());
                e.printStackTrace();
            }
        }
    }

    public void unregister(BukkitHandler handler){
        if (!handlers.contains(handler)) return;
        handlers.remove(handler);
    }

    public void clear(){
        handlers.clear();
    }

}
