package me.anton.sickcore.api.handler.listeners.bukkit.events.player;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.InventoryBuilder;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class PlayerInventoryCloseEventHandler extends BukkitEventProvider<InventoryCloseEvent> {


    @EventHandler
    public void handleEvent(InventoryCloseEvent event) {
        BukkitPlayer bukkitPlayer = new BukkitPlayer(event.getPlayer().getUniqueId());

        if(inventoryBuilder(bukkitPlayer, event)) return;
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        provider.iterator(run -> run.onInventoryClose(event, bukkitPlayer));
    }

    private boolean inventoryBuilder(BukkitPlayer apiPlayer, InventoryCloseEvent event){
        if (!InventoryBuilder.getHandlers().containsKey(apiPlayer.api().getUUID()))return false;
        InventoryBuilder builder = InventoryBuilder.getHandlers().get(apiPlayer.api().getUUID());
        if (builder.getInventory() == null)return false;
        if (!builder.getInventory().equals(event.getInventory()))return false;
        builder.onClose(event);
        return true;
    }
}
