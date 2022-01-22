package me.anton.sickcore.games.survival.spawn;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Elytra extends BukkitHandler {
    public static Location spawnlocation = new Location(Bukkit.getWorld("world"), -263.5, 306.5, 344.5);

    private static List<UUID> glidingPlayers = new ArrayList<>();
    private HashMap<UUID, Integer> timesBoosted = new HashMap<UUID, Integer>();

    @Override
    public void onPlayerElytra(IBukkitPlayer player, EntityToggleGlideEvent rawEvent) {
        if (!glidingPlayers.contains(player.getPlayer().getUniqueId()))return;
        rawEvent.setCancelled(true);
    }

    @Override
    public void onPlayerToggleSneak(PlayerToggleSneakEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        if (!glidingPlayers.contains(rawEvent.getPlayer().getUniqueId()))return;
        if (!timesBoosted.containsKey(rawEvent.getPlayer().getUniqueId())) {
            rawEvent.getPlayer().boostElytra(new ItemStack(Material.FIREWORK_ROCKET));
            timesBoosted.put(rawEvent.getPlayer().getUniqueId(), 1);
        }
        timesBoosted.put(rawEvent.getPlayer().getUniqueId(), timesBoosted.get(rawEvent .getPlayer().getUniqueId()) + 1);
        if (timesBoosted.get(rawEvent.getPlayer().getUniqueId()) <= 5)rawEvent.getPlayer().boostElytra(new ItemStack(Material.FIREWORK_ROCKET));
        bukkitPlayer.sendMessage("§4You used all of your boosts!", "§4Du hast alle deine Boosts benutzt!");
    }

    @Override
    public void onPlayerMove(PlayerMoveEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        Location plocation = rawEvent.getPlayer().getLocation().clone().subtract(0, 1,0);
        Block block = plocation.getBlock();
        if (!rawEvent.getPlayer().getLocation().getChunk().equals(spawnlocation.getChunk()) && rawEvent.getPlayer().getLocation().getY() >= 290)return;
        if (!block.isEmpty() || !block.isSolid())return;
        if (block.isEmpty()){
            if (rawEvent.getPlayer().isGliding())return;
            rawEvent.getPlayer().setGliding(true);
            glidingPlayers.add(rawEvent.getPlayer().getUniqueId());
        }
        if (block.isSolid()){
            glidingPlayers.remove(rawEvent.getPlayer().getUniqueId());
            rawEvent.getPlayer().setGliding(true);
            if (!timesBoosted.containsKey(rawEvent.getPlayer().getUniqueId()))return;
            timesBoosted.put(rawEvent.getPlayer().getUniqueId(), 0);
        }

    }

}
