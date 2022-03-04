package me.anton.sickcore.api.handler.listeners.bukkit.events.player;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class PlayerChangeBlockEventHandler extends BukkitEventProvider<EntityChangeBlockEvent> {


    @EventHandler
    public void handleEvent(EntityChangeBlockEvent event) {
        if (event.getEntityType() != EntityType.PLAYER)return;
        BukkitPlayer iBukkitPlayer = new BukkitPlayer(event.getEntity().getUniqueId());
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();

        provider.iterator(run -> run.onPlayerChangeBlock(iBukkitPlayer, event.getBlock(), event));
    }
}
