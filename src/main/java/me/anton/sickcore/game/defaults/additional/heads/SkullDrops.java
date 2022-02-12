package me.anton.sickcore.game.defaults.additional.heads;


import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class SkullDrops extends BukkitHandler {

    @Override
    public void onPlayerDeathByPlayer(Location deathLocation, List<ItemStack> drops, PlayerDeathEvent rawEvent, IBukkitPlayer bukkitPlayer, IBukkitPlayer killer) {
        ItemStack itemStack = new ItemBuilder(Material.PLAYER_HEAD)
                .setSkullMeta(killer.getPlayer().getUniqueId()).setName("§b" + bukkitPlayer.api().getName() + "'s Head")
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build();

        bukkitPlayer.getPlayer().getWorld().dropItem(bukkitPlayer.getPlayer().getLocation(), itemStack);
    }
}
