package me.anton.sickcore.api.handler.listeners.bungee.events.player;

import me.anton.sickcore.api.handler.listeners.bungee.BungeeEventProvider;
import me.anton.sickcore.api.handler.listeners.bungee.BungeeListenerProvider;
import me.anton.sickcore.api.player.bungeePlayer.BungeePlayer;
import me.anton.sickcore.core.BungeeCore;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.event.EventHandler;

public class PlayerSwitchServerEventHandler extends BungeeEventProvider<ServerSwitchEvent> {


    @EventHandler
    public void handleEvent(ServerSwitchEvent event) {
        BungeeListenerProvider provider = BungeeCore.getInstance().getProvider();
        provider.iterator(run -> run.onServerSwitch(event, new BungeePlayer(event.getPlayer())));
    }
}
