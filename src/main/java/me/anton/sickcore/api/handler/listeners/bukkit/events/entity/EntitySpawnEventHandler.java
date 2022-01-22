package me.anton.sickcore.api.handler.listeners.bukkit.events.entity;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntitySpawnEventHandler extends BukkitEventProvider<EntitySpawnEvent> {


    @EventHandler
    public void handleEvent(EntitySpawnEvent event) {
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        provider.iterator(run -> run.onEntitySpawn(event, event.getEntity()));
    }
}
