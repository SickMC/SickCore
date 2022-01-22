package me.anton.sickcore.api.handler.listeners.bukkit.events.entity;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureSpawnEventHandler extends BukkitEventProvider<CreatureSpawnEvent> {


    @EventHandler
    public void handleEvent(CreatureSpawnEvent event) {
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        provider.iterator(run -> run.onCreatureSpawn(event.getEntity(), event.getSpawnReason(), event));
    }
}
