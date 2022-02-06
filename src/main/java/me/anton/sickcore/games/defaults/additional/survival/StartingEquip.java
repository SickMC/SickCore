package me.anton.sickcore.games.defaults.additional.survival;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class StartingEquip extends BukkitHandler {

    @Override
    public void onPlayerJoin(PlayerJoinEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        if (!bukkitPlayer.getPlayer().hasPlayedBefore()){
            bukkitPlayer.getPlayer().getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 32));
        }
    }
}
