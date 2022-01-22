package me.anton.sickcore.api.handler.listeners.bungee.events.player;

import me.anton.sickcore.api.handler.listeners.bungee.BungeeEventProvider;
import me.anton.sickcore.api.handler.listeners.bungee.BungeeListenerProvider;
import me.anton.sickcore.api.player.bungeePlayer.BungeePlayer;
import me.anton.sickcore.core.BungeeCore;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.event.EventHandler;

public class PlayerPostJoinEventHandler extends BungeeEventProvider<PostLoginEvent> {


    @EventHandler
    public void handleEvent(PostLoginEvent event) {
        BungeeListenerProvider provider = BungeeCore.getInstance().getProvider();
        provider.iterator(run -> run.onPostJoin(event, new BungeePlayer(event.getPlayer())));
    }
}
