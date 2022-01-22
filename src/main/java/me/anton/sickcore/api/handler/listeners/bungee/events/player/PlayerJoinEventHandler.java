package me.anton.sickcore.api.handler.listeners.bungee.events.player;

import me.anton.sickcore.api.handler.listeners.bungee.BungeeEventProvider;
import me.anton.sickcore.api.handler.listeners.bungee.BungeeListenerProvider;
import me.anton.sickcore.core.BungeeCore;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.event.EventHandler;

public class PlayerJoinEventHandler extends BungeeEventProvider<LoginEvent> {

    @EventHandler
    public void handleEvent(LoginEvent event) {
        BungeeListenerProvider provider = BungeeCore.getInstance().getProvider();
        provider.iterator(run -> run.onPlayerJoin(event));
    }
}
