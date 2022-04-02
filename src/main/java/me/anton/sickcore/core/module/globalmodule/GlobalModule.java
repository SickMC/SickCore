package me.anton.sickcore.core.module.globalmodule;

import com.velocitypowered.api.proxy.ProxyServer;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.utils.common.Logger;
import me.anton.sickcore.core.BukkitCore;
import me.anton.sickcore.core.ProxyCore;
import me.anton.sickcore.core.Core;
import me.anton.sickcore.core.Environment;

import java.lang.reflect.Proxy;

public abstract class GlobalModule {

    private String name = null;

    public abstract void load();

    public abstract void unload();

    public Environment getEnvironment(){
        return Core.getInstance().getEnvironment();
    }

    public void initializeConfig(String name){
        this.name = name;
    }

    public ModuleConfiguration getConfig(){
        if (name == null) {
            Logger.error("You have to set a name for this config", this.getClass());
            return null;
        }
        return new ModuleConfiguration(this, name);
    }

    public void registerVelocityEvents(Object... handlers){
        for (Object handler : handlers) {
            ProxyCore.getInstance().getPlugin().getEventManager().register(ProxyCore.getInstance().getPlugin(), handler);
        }
    }

    public void registerBukkitEvents(BukkitHandler... handlers){
        for (BukkitHandler handler : handlers) {
            BukkitCore.getInstance().getProvider().register(handler);
        }
    }

}
