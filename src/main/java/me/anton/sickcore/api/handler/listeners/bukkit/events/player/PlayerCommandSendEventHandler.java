package me.anton.sickcore.api.handler.listeners.bukkit.events.player;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandSendEvent;

public class PlayerCommandSendEventHandler extends BukkitEventProvider<PlayerCommandSendEvent> {


    @EventHandler
    public void handleEvent(PlayerCommandSendEvent event) {
        BukkitPlayer bukkitPlayer = new BukkitPlayer(event.getPlayer().getUniqueId());
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();

        provider.iterator(run -> run.onPlayerCommandSend(event.getCommands(), event, bukkitPlayer));
    }
}
