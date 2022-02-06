package me.anton.sickcore.api.handler.listeners.bukkit.events.world;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.hanging.HangingPlaceEvent;

public class HangingPlaceEventHandler extends BukkitEventProvider<HangingPlaceEvent> {
    @EventHandler
    public void handleEvent(HangingPlaceEvent event) {
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        provider.iterator(run -> run.onHangingPlace(event, event.getEntity(), new BukkitPlayer(event.getPlayer().getUniqueId())));
    }
}
