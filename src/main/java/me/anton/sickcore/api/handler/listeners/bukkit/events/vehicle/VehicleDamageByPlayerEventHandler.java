package me.anton.sickcore.api.handler.listeners.bukkit.events.vehicle;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.vehicle.VehicleDamageEvent;

public class VehicleDamageByPlayerEventHandler extends BukkitEventProvider<VehicleDamageEvent> {

    @EventHandler
    public void handleEvent(VehicleDamageEvent event) {
        IBukkitPlayer bukkitPlayer = new BukkitPlayer(event.getAttacker().getUniqueId());
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        provider.iterator(run -> run.onVehicleDamageByPlayer(event, bukkitPlayer, event.getVehicle()));
    }
}
