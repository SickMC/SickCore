package me.anton.sickcore.api.handler.listeners.bukkit.events.player;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLevelChangeEvent;

public class PlayerLevelChangeEventHandler extends BukkitEventProvider<PlayerLevelChangeEvent> {



    @EventHandler
    public void handleEvent(PlayerLevelChangeEvent event) {
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        provider.iterator(run -> run.onPlayerLevelChangeEvent(event, event.getOldLevel(), event.getNewLevel(), new BukkitPlayer(event.getPlayer())));
    }
}
