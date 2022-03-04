package me.anton.sickcore.games.survival.survival;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class StartingEquip extends BukkitHandler {

    @Override
    public void onPlayerJoin(PlayerJoinEvent rawEvent, BukkitPlayer bukkitPlayer) {
        if (!bukkitPlayer.getPlayer().hasPlayedBefore()){
            bukkitPlayer.getPlayer().getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 32));
        }
    }
}
