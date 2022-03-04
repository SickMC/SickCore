package me.anton.sickcore.api.handler.listeners.bukkit.events.player;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryDragEvent;

public class PlayerInventoryDragEventHandler extends BukkitEventProvider<InventoryDragEvent> {


    @EventHandler
    public void handleEvent(InventoryDragEvent event) {
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        if (!(event.getInventory().getViewers().get(0) instanceof Player))return;


        provider.iterator(run -> run.onPlayerInventoryDrag(event, event.getInventory(), event.getNewItems(), new BukkitPlayer((Player) event.getInventory().getViewers().get(0))));
    }
}
