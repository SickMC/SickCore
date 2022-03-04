package me.anton.sickcore.api.handler.listeners.bukkit.events.player;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class PlayerInteractAtPlayerEventHandler extends BukkitEventProvider<PlayerInteractAtEntityEvent> {


    @EventHandler
    public void handleEvent(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked().getType() != EntityType.PLAYER) return;
        BukkitPlayer bukkitPlayer = new BukkitPlayer(event.getPlayer().getUniqueId());
        BukkitPlayer rightClicked = new BukkitPlayer(event.getRightClicked().getUniqueId());
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();

        provider.iterator(run -> run.onPlayerInteractAtPlayer(event, bukkitPlayer, rightClicked));
    }
}
