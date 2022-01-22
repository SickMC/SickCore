package me.anton.sickcore.api.handler.listeners.bukkit.events.world;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockFromToEvent;

public class BlockMoveEventHandler extends BukkitEventProvider<BlockFromToEvent> {

    @EventHandler
    public void handleEvent(BlockFromToEvent event) {
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        provider.iterator(run -> run.onBlockMove(event.getBlock(), event.getToBlock(), event));
    }
}
