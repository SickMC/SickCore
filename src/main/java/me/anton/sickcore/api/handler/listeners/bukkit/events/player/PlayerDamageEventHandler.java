package me.anton.sickcore.api.handler.listeners.bukkit.events.player;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageEventHandler extends BukkitEventProvider<EntityDamageEvent> {


    @EventHandler
    public void handleEvent(EntityDamageEvent event) {
        if (event.getEntity().getType() != EntityType.PLAYER) return;
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();

        IBukkitPlayer bukkitPlayer = new BukkitPlayer(event.getEntity().getUniqueId());

        provider.iterator(run -> run.onPlayerDamage(event, bukkitPlayer));
    }
}
