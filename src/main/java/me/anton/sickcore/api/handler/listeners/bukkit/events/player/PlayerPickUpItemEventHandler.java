package me.anton.sickcore.api.handler.listeners.bukkit.events.player;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class PlayerPickUpItemEventHandler extends BukkitEventProvider<EntityPickupItemEvent> {


    @EventHandler
    public void handleEvent(EntityPickupItemEvent event) {
        if (event.getEntity().getType() != EntityType.PLAYER) return;
        BukkitPlayer bukkitPlayer = new BukkitPlayer(event.getEntity().getUniqueId());
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        provider.iterator(run -> run.onPlayerPickUpItem(event, bukkitPlayer, event.getItem().getItemStack()));
    }
}
