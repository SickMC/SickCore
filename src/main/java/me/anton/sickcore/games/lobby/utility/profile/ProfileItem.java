package me.anton.sickcore.games.lobby.utility.profile;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ProfileItem extends BukkitHandler {

    @Override
    public void onPlayerItemInteract(ItemStack itemStack, PlayerInteractEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        if (rawEvent.getPlayer().getInventory().getItemInMainHand().getType() == Material.PLAYER_HEAD && rawEvent.getAction().isRightClick())ProfileInventory.openProfileInventory(bukkitPlayer);
    }

}
