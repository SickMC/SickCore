package me.anton.sickcore.api.handler.listeners.bukkit.events.player;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.utils.common.Logger;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.InventoryBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlayerInteractInventoryEventHandler extends BukkitEventProvider<InventoryClickEvent> {


    @EventHandler
    public void handleEvent(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player))return;
        BukkitPlayer bukkitPlayer = new BukkitPlayer(event.getWhoClicked().getUniqueId());

        if (inventoryBuilder(event))return;
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        provider.iterator(run -> run.onInventoryClick(event, bukkitPlayer));
    }

    private boolean inventoryBuilder(InventoryClickEvent event){
        if (event.getCurrentItem() == null)return false;
        if (!InventoryBuilder.getHandlers().containsKey(event.getWhoClicked().getUniqueId()))return false;
        InventoryBuilder builder = InventoryBuilder.getHandlers().get(event.getWhoClicked().getUniqueId());
        if (!builder.getClickableItems().containsKey(event.getCurrentItem()))return false;
        builder.onClick(event);
        event.setCancelled(true);
        return true;
    }

}
