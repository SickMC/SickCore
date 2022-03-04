package me.anton.sickcore.api.handler.listeners.bukkit.events.player;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PlayerDeathEventHandler extends BukkitEventProvider<PlayerDeathEvent> {

    @EventHandler
    public void handleEvent(final PlayerDeathEvent event) {
        if (event.getPlayer().getKiller() == null)return;
        BukkitPlayer bukkitPlayer = new BukkitPlayer(event.getPlayer().getUniqueId());
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        Location location = event.getEntity().getLocation();
        List<ItemStack> drops = event.getDrops();
        if (event.getPlayer().getKiller() == null) provider.iterator(run -> run.onPlayerDeath(location, drops, event, bukkitPlayer));

        BukkitPlayer killer = new BukkitPlayer(event.getPlayer().getKiller().getUniqueId());

        provider.iterator(run -> run.onPlayerDeathByPlayer(location, drops, event, bukkitPlayer, killer));
    }
}
