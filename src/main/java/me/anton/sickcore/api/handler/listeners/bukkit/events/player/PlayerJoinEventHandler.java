package me.anton.sickcore.api.handler.listeners.bukkit.events.player;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.core.BukkitCore;
import me.anton.sickcore.core.Core;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinEventHandler extends BukkitEventProvider<PlayerJoinEvent> {


    @EventHandler
    public void handleEvent(PlayerJoinEvent event) {
        Core.getInstance().bukkit().getOnlineBukkitPlayers().add(new BukkitPlayer(event.getPlayer()));
        Core.getInstance().bukkit().getOnlineAPIPlayers().add(new APIPlayer(event.getPlayer().getUniqueId()));
        IBukkitPlayer bukkitPlayer = new BukkitPlayer(event.getPlayer().getUniqueId());
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        provider.iterator(run -> run.onPlayerJoin(event, bukkitPlayer));
    }
}
