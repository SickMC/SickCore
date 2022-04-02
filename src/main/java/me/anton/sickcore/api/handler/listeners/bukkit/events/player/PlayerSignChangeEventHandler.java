package me.anton.sickcore.api.handler.listeners.bukkit.events.player;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.block.SignChangeEvent;

public class PlayerSignChangeEventHandler extends BukkitEventProvider<SignChangeEvent> {

    @Override
    public void handleEvent(SignChangeEvent event) {
        BukkitPlayer player = new BukkitPlayer(event.getPlayer());

        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        provider.iterator(run -> run.onPlayerSignChangeEvent(event, event.getBlock(), event.lines(), player));
    }
}
