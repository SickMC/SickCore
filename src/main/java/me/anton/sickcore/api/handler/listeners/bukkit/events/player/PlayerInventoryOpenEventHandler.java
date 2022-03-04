package me.anton.sickcore.api.handler.listeners.bukkit.events.player;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.utils.common.Logger;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.InventoryBuilder;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class PlayerInventoryOpenEventHandler extends BukkitEventProvider<InventoryOpenEvent> {


    @EventHandler
    public void handleEvent(InventoryOpenEvent event) {
        BukkitPlayer bukkitPlayer = new BukkitPlayer(event.getPlayer().getUniqueId());

        if(inventoryBuilder(event)) return;
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        provider.iterator(run -> run.onInventoryOpen(event, bukkitPlayer));
    }

    private boolean inventoryBuilder(InventoryOpenEvent event){
        InventoryBuilder builder = InventoryBuilder.getHandlers().get(event.getPlayer().getUniqueId());
        if (!builder.getInventory().equals(event.getInventory()))return false;
        builder.onOpen(event);
        return true;
    }
}
