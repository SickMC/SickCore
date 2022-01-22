package me.anton.sickcore.api.handler.listeners.bukkit.events.other;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.core.BukkitCore;
import me.arcaniax.hdb.api.DatabaseLoadEvent;
import org.bukkit.event.EventHandler;

public class HeadDataBaseLoadEventHandler extends BukkitEventProvider<DatabaseLoadEvent> {

    @EventHandler
    public void handleEvent(DatabaseLoadEvent event) {
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        provider.iterator(run -> run.onHeadDBLoad(event));
    }
}
