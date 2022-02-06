package me.anton.sickcore.api.handler.listeners.bukkit.events.player;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.command.UnknownCommandEvent;

public class PlayerUnknownCommandEvent extends BukkitEventProvider<UnknownCommandEvent> {
    @EventHandler
    public void handleEvent(UnknownCommandEvent event) {
        if (!(event.getSender() instanceof Player))return;
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        provider.iterator(run -> run.onPlayerUnknownCommand(event, event.getCommandLine()));
    }
}
