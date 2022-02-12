package me.anton.sickcore.modules.basic;

import co.aikar.commands.BungeeCommandManager;
import lombok.Getter;
import me.anton.sickcore.api.handler.listeners.bungee.BungeeListenerProvider;
import me.anton.sickcore.core.Core;
import me.anton.sickcore.core.module.proxiedModule.ProxiedIModule;
import me.anton.sickcore.modules.basic.buildserver.BuildServerCommand;
import me.anton.sickcore.modules.basic.lobby.LobbyCommand;
import me.anton.sickcore.modules.basic.lobby.LobbyCommandAd;
import me.anton.sickcore.modules.basic.playtime.PlaytimeCommand;

@Getter
public class BasicModule implements ProxiedIModule {

    private BungeeCommandManager manager;
    private BungeeListenerProvider provider;

    @Override
    public void load() {
        this.manager = Core.getInstance().bungee().getManager();
        this.provider = Core.getInstance().bungee().getProvider();

        register();
    }

    @Override
    public void unload() {

    }

    @Override
    public void register() {
        manager.registerCommand(new LobbyCommand());
        provider.register(new LobbyCommandAd());

        manager.registerCommand(new BuildServerCommand());

        manager.registerCommand(new PlaytimeCommand());
    }
}
