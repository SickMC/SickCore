package me.anton.sickcore.api.handler.listeners.bukkit.events.player;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

public class PlayerInventoryMoveItemEventHandler extends BukkitEventProvider<InventoryMoveItemEvent> {

    @EventHandler
    public void handleEvent(InventoryMoveItemEvent event) {
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();

        provider.iterator(run -> run.onItemInventoryMove(event, event.getItem()));

    }
}
