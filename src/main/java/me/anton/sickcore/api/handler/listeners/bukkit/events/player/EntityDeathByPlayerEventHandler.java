package me.anton.sickcore.api.handler.listeners.bukkit.events.player;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathByPlayerEventHandler extends BukkitEventProvider<EntityDeathEvent> {


    @EventHandler
    public void handleEvent(EntityDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;
        BukkitPlayer bukkitPlayer = new BukkitPlayer(event.getEntity().getKiller().getUniqueId());
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();

        provider.iterator(run -> run.onEntityDeathByPlayer(event.getEntity(), event, bukkitPlayer));
    }
}
