package me.anton.sickcore.api.handler.listeners.bukkit.events.entity;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityInteractEvent;

public class EntityInteractEventHandler extends BukkitEventProvider<EntityInteractEvent> {


    @EventHandler
    public void handleEvent(EntityInteractEvent event) {
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        provider.iterator(run -> run.onEntityInteract(event.getEntity(), event));
    }
}
