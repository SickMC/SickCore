package me.anton.sickcore.api.handler.listeners.bungee.events.service;

import me.anton.sickcore.api.handler.listeners.bungee.BungeeEventProvider;
import me.anton.sickcore.api.handler.listeners.bungee.BungeeListenerProvider;
import me.anton.sickcore.core.BungeeCore;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.event.EventHandler;

public class ServerPingEventHandler extends BungeeEventProvider<ProxyPingEvent> {


    @EventHandler
    public void handleEvent(ProxyPingEvent event) {
        BungeeListenerProvider provider = BungeeCore.getInstance().getProvider();
        provider.iterator(run -> run.onPing(event, event.getConnection(), event.getResponse()));
    }
}
