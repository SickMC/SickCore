package me.anton.sickcore.api.handler.listeners.bukkit.events.player;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.InventoryBuilder;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlayerInteractInventoryEventHandler extends BukkitEventProvider<InventoryClickEvent> {


    @EventHandler
    public void handleEvent(InventoryClickEvent event) {
        IBukkitPlayer bukkitPlayer = new BukkitPlayer(event.getWhoClicked().getUniqueId());

        if(inventoryHandler(bukkitPlayer, event)) return;
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        provider.iterator(run -> run.onInventoryClick(event, bukkitPlayer));
    }

    private boolean inventoryHandler(IBukkitPlayer apiPlayer, InventoryClickEvent event){
        if(InventoryBuilder.getHandlers().containsKey(apiPlayer.api().getUUID())){
            InventoryBuilder inventoryHandler = InventoryBuilder.getHandlers().get(apiPlayer.api().getUUID());
            if(inventoryHandler.getInventory() == null) return false;
            if(!inventoryHandler.getInventory().equals(event.getClickedInventory())) return false;
            event.setCancelled(true);
            inventoryHandler.onClick(event);
            return true;
        }
        return false;
    }
}
