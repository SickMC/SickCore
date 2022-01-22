package me.anton.sickcore.api.handler.listeners.bukkit.events.player;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class PlayerFoodLevelChangeEventHandler extends BukkitEventProvider<FoodLevelChangeEvent> {


    @EventHandler
    public void handleEvent(FoodLevelChangeEvent event) {
        IBukkitPlayer bukkitPlayer = new BukkitPlayer(event.getEntity().getUniqueId());
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();

        provider.iterator(run -> run.onFoodLevelChange(event, bukkitPlayer));
    }
}
