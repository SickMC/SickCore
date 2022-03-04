package me.anton.sickcore.api.handler.listeners.bukkit.events.player;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.InventoryBuilder;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

public class PlayerInventoryMoveItemEventHandler extends BukkitEventProvider<InventoryMoveItemEvent> {

    @EventHandler
    public void handleEvent(InventoryMoveItemEvent event) {
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        if (!(event.getInitiator().getViewers().get(0) instanceof Player))return;
        BukkitPlayer player = new BukkitPlayer( event.getInitiator().getViewers().get(0));
        if (inventoryBuilder(event, player))return;

        provider.iterator(run -> run.onItemInventoryMove(event, event.getItem(), player));
    }

    private boolean inventoryBuilder(InventoryMoveItemEvent event, BukkitPlayer player){
        if (!InventoryBuilder.getHandlers().containsKey(player.api().getUUID()))return false;
        InventoryBuilder builder = InventoryBuilder.getHandlers().get(player.api().getUUID());
        if (!builder.getInventory().equals(event.getDestination()))return false;
        if (builder.getMoveEvent() == null)return false;
        builder.onMove(event);
        event.setCancelled(true);
        return true;
    }
}
