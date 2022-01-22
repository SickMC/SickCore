package me.anton.sickcore.api.handler.listeners.bukkit.events.vehicle;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.vehicle.VehicleDestroyEvent;

public class VehicleDestroyByEntityEventHandler  extends BukkitEventProvider<VehicleDestroyEvent> {


    @EventHandler
    public void handleEvent(VehicleDestroyEvent event) {
        if (event.getAttacker().getType() == EntityType.PLAYER) return;
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        provider.iterator(run -> run.onVehicleDestroyByEntity(event, event.getAttacker(), event.getVehicle()));
    }
}
