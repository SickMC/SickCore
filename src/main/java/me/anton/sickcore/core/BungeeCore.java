package me.anton.sickcore.core;

import co.aikar.commands.BungeeCommandManager;
import lombok.Getter;
import me.anton.sickcore.api.handler.listeners.bungee.BungeeEventProvider;
import me.anton.sickcore.api.handler.listeners.bungee.BungeeListenerProvider;
import me.anton.sickcore.api.handler.listeners.bungee.events.player.*;
import me.anton.sickcore.api.handler.listeners.bungee.events.service.ServerPingEventHandler;
import me.anton.sickcore.core.module.ModuleHandler;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

@Getter
public class BungeeCore extends Core{

    @Getter
    private static BungeeCore instance;
    private final Plugin plugin;
    private final BungeeCommandManager manager;
    private final BungeeListenerProvider provider;
    private final ModuleHandler moduleHandler;

    public BungeeCore(Plugin plugin){
        instance = this;
        this.plugin = plugin;
        this.manager = new BungeeCommandManager(plugin);
        this.provider = new BungeeListenerProvider();
        moduleHandler = new ModuleHandler();
        moduleHandler.loadModules();
    }

    public void onLoad(){
        register();
    }

    public void onUnLoad(){
        moduleHandler.unLoadModules();
    }

    private void register(){
        List<BungeeEventProvider> bungeeList = Arrays.asList(
                //Player
                new PlayerPostJoinEventHandler(),
                new PlayerJoinEventHandler(),
                new PlayerConnectedServerEventHandler(),
                new PlayerConnectServerEventHandler(),
                new PlayerSwitchServerEventHandler(),
                new PlayerDisconnectEventHandler(),
                new PlayerChatEventHandler(),

                //Service
                new ServerPingEventHandler()

        );
        bungeeList.forEach(handler -> plugin.getProxy().getPluginManager().registerListener(plugin, handler));
    }

}
