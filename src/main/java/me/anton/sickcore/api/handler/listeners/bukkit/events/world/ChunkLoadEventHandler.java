package me.anton.sickcore.api.handler.listeners.bukkit.events.world;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.ChunkLoadEvent;

public class ChunkLoadEventHandler extends BukkitEventProvider<ChunkLoadEvent> {

    @EventHandler
    public void handleEvent(ChunkLoadEvent event) {
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        provider.iterator(run -> run.onChunkLoad(event.getChunk(), event));
    }
}
