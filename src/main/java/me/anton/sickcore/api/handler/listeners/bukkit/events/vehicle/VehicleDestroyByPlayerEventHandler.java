package me.anton.sickcore.api.handler.listeners.bukkit.events.vehicle;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.vehicle.VehicleDestroyEvent;

public class VehicleDestroyByPlayerEventHandler extends BukkitEventProvider<VehicleDestroyEvent> {


    @EventHandler
    public void handleEvent(VehicleDestroyEvent event) {
        if (event.getAttacker().getType() != EntityType.PLAYER) return;
        IBukkitPlayer bukkitPlayer = new BukkitPlayer(event.getAttacker().getUniqueId());
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        provider.iterator(run -> run.onVehicleDestroyByPlayer(event, bukkitPlayer, event.getVehicle()));
    }
}
