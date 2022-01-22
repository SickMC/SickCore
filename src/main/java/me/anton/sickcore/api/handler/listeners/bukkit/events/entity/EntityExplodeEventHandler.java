package me.anton.sickcore.api.handler.listeners.bukkit.events.entity;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplodeEventHandler extends BukkitEventProvider<EntityExplodeEvent> {

    @EventHandler
    public void handleEvent(EntityExplodeEvent event) {
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        provider.iterator(run -> run.onEntityExplode(event.getEntity(), event));
    }
}
