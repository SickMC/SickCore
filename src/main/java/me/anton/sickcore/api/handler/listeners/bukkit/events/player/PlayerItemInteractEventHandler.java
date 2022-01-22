package me.anton.sickcore.api.handler.listeners.bukkit.events.player;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerItemInteractEventHandler extends BukkitEventProvider<PlayerInteractEvent> {


    @EventHandler
    public void handleEvent(PlayerInteractEvent event) {
        IBukkitPlayer bukkitPlayer = new BukkitPlayer(event.getPlayer().getUniqueId());

        ItemStack item = event.getItem();
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        provider.iterator(run -> run.onPlayerItemInteract(item, event, bukkitPlayer));
    }
}
