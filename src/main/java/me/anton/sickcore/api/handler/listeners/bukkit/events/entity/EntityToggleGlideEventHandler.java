package me.anton.sickcore.api.handler.listeners.bukkit.events.entity;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityToggleGlideEvent;

public class EntityToggleGlideEventHandler extends BukkitEventProvider<EntityToggleGlideEvent> {


    @EventHandler
    public void handleEvent(EntityToggleGlideEvent event) {
        if (event.getEntityType() == EntityType.PLAYER)return;
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        provider.iterator(run -> run.onEntityGlide(event.getEntity(), event));
    }
}
