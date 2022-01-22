package me.anton.sickcore.games.survival.spawn;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Protection extends BukkitHandler {

    Location spawnlocation = Elytra.spawnlocation;

    @Override
    public void onBlockBreak(Block block, BlockBreakEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        if (rawEvent.getPlayer().getGameMode().equals(GameMode.CREATIVE))return;
        if (!rawEvent.getBlock().getLocation().getChunk().equals(spawnlocation.getChunk()))return;
        rawEvent.setCancelled(true);
    }

    @Override
    public void onBlockPlace(Block block, BlockPlaceEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        if (rawEvent.getPlayer().getGameMode().equals(GameMode.CREATIVE))return;
        if (!rawEvent.getBlock().getLocation().getChunk().equals(spawnlocation.getChunk()))return;
        rawEvent.setCancelled(true);
    }

    @Override
    public void onEntitySpawn(EntitySpawnEvent rawEvent, Entity entity) {
        if (!rawEvent.getLocation().getChunk().equals(spawnlocation.getChunk()))return;
        rawEvent.setCancelled(true);
    }

    @Override
    public void onPlayerDropItem(PlayerDropItemEvent rawEvent, IBukkitPlayer bukkitPlayer, ItemStack drop) {
        if (!rawEvent.getPlayer().getChunk().equals(spawnlocation.getChunk()))return;
        rawEvent.setCancelled(true);
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        if (!rawEvent.getPlayer().getChunk().equals(spawnlocation.getChunk()))return;
        rawEvent.setCancelled(true);
    }

}
