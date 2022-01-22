package me.anton.sickcore.api.handler.listeners.bukkit.events.player;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.EventHandler;
import xyz.haoshoku.nick.events.NickFinishEvent;

public class PlayerNickUpdateEventHandler extends BukkitEventProvider<NickFinishEvent> {

    @EventHandler
    public void handleEvent(NickFinishEvent event) {
        IBukkitPlayer bukkitPlayer = new BukkitPlayer(event.getPlayer().getUniqueId());
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();

        provider.iterator(run -> run.onPlayerNickFinish(event, bukkitPlayer, event.getName()));
    }
}
