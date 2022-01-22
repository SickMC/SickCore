package me.anton.sickcore.api.handler.listeners.bukkit.events.vehicle;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.vehicle.VehicleExitEvent;

public class VehicleExitByEntityEventHandler extends BukkitEventProvider<VehicleExitEvent> {

    @EventHandler
    public void handleEvent(VehicleExitEvent event) {
        if (event.getExited().getType() == EntityType.PLAYER)return;
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        provider.iterator(run -> run.onVehicleExitByEntity(event, event.getExited()));
    }
}
