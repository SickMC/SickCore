package me.anton.sickcore.api.handler.listeners.bukkit.events.player;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.EventHandler;

public class PlayerAsyncChatEventHandler extends BukkitEventProvider<AsyncChatEvent> {

    @EventHandler
    public void handleEvent(AsyncChatEvent event) {
        IBukkitPlayer bukkitPlayer = new BukkitPlayer(event.getPlayer().getUniqueId());
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();

        provider.iterator(run -> run.onPlayerAsyncChat(event, bukkitPlayer));
    }
}
