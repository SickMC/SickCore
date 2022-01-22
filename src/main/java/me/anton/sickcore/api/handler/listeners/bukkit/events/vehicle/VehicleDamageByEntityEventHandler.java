package me.anton.sickcore.api.handler.listeners.bukkit.events.vehicle;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.vehicle.VehicleDamageEvent;

public class VehicleDamageByEntityEventHandler extends BukkitEventProvider<VehicleDamageEvent> {

    @EventHandler
    public void handleEvent(VehicleDamageEvent event) {
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        provider.iterator(run -> run.onVehicleDamageByEntity(event, event.getAttacker(), event.getVehicle()));
    }
}
