package me.anton.sickcore.api.handler.listeners.bungee.events.player;

import me.anton.sickcore.api.handler.listeners.bungee.BungeeEventProvider;
import me.anton.sickcore.api.handler.listeners.bungee.BungeeListenerProvider;
import me.anton.sickcore.api.player.bungeePlayer.BungeePlayer;
import me.anton.sickcore.core.BungeeCore;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.event.EventHandler;

public class PlayerDisconnectEventHandler extends BungeeEventProvider<PlayerDisconnectEvent> {

    @EventHandler
    public void handleEvent(PlayerDisconnectEvent event) {
        BungeeListenerProvider provider = BungeeCore.getInstance().getProvider();
        provider.iterator(run -> run.onDisconnect(event, new BungeePlayer(event.getPlayer())));
    }
}
