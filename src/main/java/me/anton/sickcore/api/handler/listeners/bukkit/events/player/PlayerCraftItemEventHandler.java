package me.anton.sickcore.api.handler.listeners.bukkit.events.player;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;

public class PlayerCraftItemEventHandler extends BukkitEventProvider<CraftItemEvent> {


    @EventHandler
    public void handleEvent(CraftItemEvent event) {
        BukkitPlayer bukkitPlayer = new BukkitPlayer(event.getWhoClicked().getUniqueId());
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();

        provider.iterator(run -> run.onCraftItem(event, bukkitPlayer, event.getCurrentItem()));
    }
}
