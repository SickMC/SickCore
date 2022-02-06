package me.anton.sickcore.api.handler.listeners.bukkit.events.world;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ItemSpawnEvent;

public class ItemSpawnEventHandler extends BukkitEventProvider<ItemSpawnEvent> {

    @EventHandler
    public void handleEvent(ItemSpawnEvent event) {
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        provider.iterator(run -> run.onItemSpawn(event, event.getEntity(), event.getEntity().getWorld()));
    }
}
