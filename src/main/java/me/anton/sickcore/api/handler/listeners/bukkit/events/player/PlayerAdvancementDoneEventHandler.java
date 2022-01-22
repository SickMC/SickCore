package me.anton.sickcore.api.handler.listeners.bukkit.events.player;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class PlayerAdvancementDoneEventHandler extends BukkitEventProvider<PlayerAdvancementDoneEvent> {


    @EventHandler
    public void handleEvent(PlayerAdvancementDoneEvent event) {
        IBukkitPlayer bukkitPlayer = new BukkitPlayer(event.getPlayer());
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();

        provider.iterator(run -> run.onPlayerAdvancementDone(event, event.getAdvancement(), bukkitPlayer));
    }
}
