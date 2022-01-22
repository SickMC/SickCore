package me.anton.sickcore.api.handler.listeners.bukkit.events.entity;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityEventHandler extends BukkitEventProvider<EntityDamageByEntityEvent> {


    @EventHandler
    public void handleEvent(EntityDamageByEntityEvent event) {
        if (event.getDamager().getType() == EntityType.PLAYER)return;
        if (event.getEntity().getType() == EntityType.PLAYER)return;

        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        provider.iterator(run -> run.onEntityDamageByEntity(event.getEntity(), event, event.getDamager()));
    }
}
