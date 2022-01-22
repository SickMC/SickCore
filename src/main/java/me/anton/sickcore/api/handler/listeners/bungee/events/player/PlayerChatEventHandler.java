package me.anton.sickcore.api.handler.listeners.bungee.events.player;

import me.anton.sickcore.api.handler.listeners.bungee.BungeeEventProvider;
import me.anton.sickcore.api.handler.listeners.bungee.BungeeListenerProvider;
import me.anton.sickcore.api.player.bungeePlayer.BungeePlayer;
import me.anton.sickcore.api.player.bungeePlayer.IBungeePlayer;
import me.anton.sickcore.core.Core;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.event.EventHandler;

public class PlayerChatEventHandler extends BungeeEventProvider<ChatEvent> {
    @EventHandler
    public void handleEvent(ChatEvent event) {
        if (!(event.getSender() instanceof ProxiedPlayer))return;
        ProxiedPlayer proxiedPlayer = (ProxiedPlayer)event.getSender();
        IBungeePlayer apiPlayer = new BungeePlayer(proxiedPlayer);

        BungeeListenerProvider provider = Core.getInstance().bungee().getProvider();
        provider.iterator(run -> run.onChat(event, event.getMessage(), apiPlayer));
    }
}
