package me.anton.sickcore.gameapi;

import co.aikar.commands.BaseCommand;
import de.dytanic.cloudnet.driver.CloudNetDriver;
import lombok.Getter;
import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.core.BukkitCore;
import me.anton.sickcore.core.Core;
import me.anton.sickcore.games.all.nick.NickProvider;
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

    public void registerListeners(@NotNull BukkitHandler... handler){
        for (BukkitHandler bukkitHandler : handler) {
            Core.getInstance().bukkit().getProvider().register(bukkitHandler);
        }
    }
    public void registerCloudListeners(Object... listener){
        CloudNetDriver.getInstance().getEventManager().registerListeners(listener);
    }

    public void registerCommands(BaseCommand... commands){
        for (BaseCommand command : commands) {
            getCore().getManager().registerCommand(command, true);
        }
    }
    public BukkitCore getCore(){
        return Core.getInstance().bukkit();
    }
    public abstract String getName();
    public DatabaseModel getPlayerModel() {return new DatabaseModel(getName() + "_players");}
    public Tablist getTablist(){
        return new Tablist(this);
    }

    public abstract boolean isStaff();

    private void register(){
        //Tablist
        getTablist().reload();
        registerListeners(new TablistProvider());
        registerCloudListeners(new TablistProvider());

        //Nick
        registerListeners(new NickProvider());
    }

    public GameConfiguration getConfig(){
        return new GameConfiguration(this, getName());
    }

}
