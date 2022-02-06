package me.anton.sickcore.api.handler.listeners.bukkit.events.world;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.hanging.HangingBreakEvent;

public class HangingBreakEventHandler extends BukkitEventProvider<HangingBreakEvent> {
    @EventHandler
    public void handleEvent(HangingBreakEvent event) {
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        provider.iterator(run -> run.onHangingBreak(event, event.getEntity()));
    }
}
