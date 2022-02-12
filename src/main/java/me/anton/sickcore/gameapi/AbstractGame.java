package me.anton.sickcore.gameapi;

import eu.thesimplecloud.api.eventapi.IListener;
import lombok.Getter;
import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.handler.listeners.cloud.CloudListenerProvider;
import me.anton.sickcore.api.utils.common.Logger;
import me.anton.sickcore.core.BukkitCore;
import me.anton.sickcore.core.Core;
import me.anton.sickcore.game.defaults.all.nick.NickProvider;
import me.anton.sickcore.gameapi.tablist.Tablist;
import me.anton.sickcore.gameapi.tablist.TablistProvider;
import org.jetbrains.annotations.NotNull;

@Getter
public abstract class AbstractGame {

    public void load(){
        register();
        onLoad();
    }
    public abstract void onLoad();

    public abstract void onUnload();

    public void unload(){

        onUnload();
    }

    public abstract boolean nick();

    public void registerListener(@NotNull BukkitHandler... handler){
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
    public abstract String getName();
    public DatabaseModel getModel(){
        return new DatabaseModel(getName());
    }
    public Tablist getTablist(){
        return new Tablist(this);
    }

    private void register(){
        //Tablist
        getTablist().reload();
        registerListener(new TablistProvider());
        registerCloudListener(new TablistProvider());

        //Nick
        registerListener(new NickProvider());
    }

}
