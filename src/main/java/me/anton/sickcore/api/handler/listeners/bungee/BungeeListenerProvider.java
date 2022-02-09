package me.anton.sickcore.api.handler.listeners.bungee;

import lombok.Getter;
import me.anton.sickcore.api.utils.common.runnable.Runnabled;
import me.anton.sickcore.api.utils.common.Logger;

import java.util.ArrayList;
import java.util.Collection;

public class BungeeListenerProvider {

    @Getter
    protected final Collection<BungeeHandler> handlers = new ArrayList<>();

    public void register(BungeeHandler handler) {
        handlers.add(handler);
    }

    public void iterator(Runnabled<BungeeHandler> handlerTask) {
        for (BungeeHandler handler : handlers) {
            try {
                handlerTask.run(handler);
            }catch (Exception e){
                Logger.error("Error while run proxied handler " + handler.getClass().getName());
                e.printStackTrace();
            }
        }
    }

    public void unregister(BungeeHandler handler){
        if(!handlers.contains(handler)) return;
        handlers.remove(handler);
    }

    public void clear() {
        handlers.clear();
    }

}
