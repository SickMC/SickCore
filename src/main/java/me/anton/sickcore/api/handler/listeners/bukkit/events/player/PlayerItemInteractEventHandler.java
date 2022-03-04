package me.anton.sickcore.api.handler.listeners.bukkit.events.player;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.utils.common.Logger;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerItemInteractEventHandler extends BukkitEventProvider<PlayerInteractEvent> {

    @EventHandler
    public void handleEvent(PlayerInteractEvent event) {
        if (event.getItem() == null)return;
        BukkitPlayer bukkitPlayer = new BukkitPlayer(event.getPlayer().getUniqueId());

        if (itemBuilder(bukkitPlayer, event))return;
        ItemStack item = event.getItem();
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        provider.iterator(run -> run.onPlayerItemInteract(item, event, bukkitPlayer));
    }

    private boolean itemBuilder(BukkitPlayer player, PlayerInteractEvent event){
        if (event.getItem() == null)return false;
        if (!ItemBuilder.getPlayerHandler().containsKey(player.api().getUUID()))return false;
        if (!ItemBuilder.getPlayerHandler().get(player.getUniqueID()).containsKey(event.getItem()))return false;
        ItemBuilder builder = ItemBuilder.getPlayerHandler().get(player.api().getUUID()).get(event.getItem());
        builder.onPlayerClick(event);
        event.setCancelled(true);
        return true;
    }

}
