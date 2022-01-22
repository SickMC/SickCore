package me.anton.sickcore.api.handler.listeners.bukkit.events.player;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.InventoryBuilder;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class PlayerInventoryOpenEventHandler extends BukkitEventProvider<InventoryOpenEvent> {


    @EventHandler
    public void handleEvent(InventoryOpenEvent event) {
        IBukkitPlayer bukkitPlayer = new BukkitPlayer(event.getPlayer().getUniqueId());

        if(inventoryHandler(bukkitPlayer, event)) return;
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        provider.iterator(run -> run.onInventoryOpen(event, bukkitPlayer));
    }

    private boolean inventoryHandler(IBukkitPlayer apiPlayer, InventoryOpenEvent event){
        if(event.getInventory().getType() == InventoryType.MERCHANT) return false;
        if(InventoryBuilder.getHandlers().containsKey(apiPlayer.api().getUUID())){
            InventoryBuilder inventoryHandler = InventoryBuilder.getHandlers().get(apiPlayer.api().getUUID());
            if(inventoryHandler.getInventory() == null) return false;
            if(!inventoryHandler.getInventory().equals(event.getInventory())) return false;
            inventoryHandler.onOpen(event);
            return true;
        }
        Inventory inventory = event.getInventory();

        return false;
    }
}
