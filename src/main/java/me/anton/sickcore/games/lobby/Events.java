package me.anton.sickcore.games.lobby;

import lombok.Getter;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.apiPlayer.enums.Rank;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.games.lobby.utility.LobbyItems;
import me.anton.sickcore.games.all.maintenance.MaintenanceModule;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Events extends BukkitHandler {

    @Getter
    private final static Location spawn = new Location(Bukkit.getWorld("Lobby-1"), 0.5, 65.5, 0.5);

    @Override
    public void onPlayerLogin(PlayerLoginEvent rawEvent, BukkitPlayer bukkitPlayer) {
        if (MaintenanceModule.getInstance().isSecure()){
            if (!rawEvent.getPlayer().getUniqueId().equals(UUID.fromString("84c7eef5-ae2c-4ebb-a006-c3ee07643d79")))
                if (MaintenanceModule.getInstance().isActive())rawEvent.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("Maintenance is enabled!"));
        }else {
            if (!bukkitPlayer.api().isTeam())
                if (MaintenanceModule.getInstance().isActive())rawEvent.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("Maintenance is enabled!"));
        }
    }

    @Override
    public void onPlayerJoin(PlayerJoinEvent rawEvent, BukkitPlayer bukkitPlayer) {
        Player player = bukkitPlayer.getPlayer();

        player.teleport(getSpawn());
        player.setGameMode(GameMode.SURVIVAL);
        player.setSaturation(20);
        player.setHealth(20);
        LobbyItems.addLobbyItems(bukkitPlayer);
        List<Rank> glowingRanks = Arrays.asList(Rank.ADMIN, Rank.BUILDER, Rank.CONTENT, Rank.MODERATOR);
        if (bukkitPlayer.api().isTeam() && !bukkitPlayer.isNicked())player.setGlowing(true);
    }

    @Override
    public void onPlayerDamageByPlayer(BukkitPlayer damager, EntityDamageByEntityEvent rawEvent, BukkitPlayer bukkitPlayer) {
        rawEvent.setCancelled(true);
    }

    @Override
    public void onFoodLevelChange(FoodLevelChangeEvent rawEvent, BukkitPlayer bukkitPlayer) {
        rawEvent.setCancelled(true);
    }

    @Override
    public void onWeatherChange(World world, WeatherChangeEvent rawEvent) {
        rawEvent.setCancelled(true);
    }

    @Override
    public void onBlockBreak(Block block, BlockBreakEvent rawEvent, BukkitPlayer bukkitPlayer) {
        rawEvent.setCancelled(true);
    }

    @Override
    public void onBlockPlace(Block block, BlockPlaceEvent rawEvent, BukkitPlayer bukkitPlayer) {
        rawEvent.setCancelled(true);
    }

    @Override
    public void onEntityDamage(Entity entity, EntityDamageEvent rawEvent) {
        rawEvent.setCancelled(true);
    }

    @Override
    public void onEntitySpawn(EntitySpawnEvent rawEvent, Entity entity) {
        rawEvent.setCancelled(true);
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent rawEvent, BukkitPlayer bukkitPlayer) {
        rawEvent.setCancelled(true);
    }

    @Override
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent rawEvent, BukkitPlayer bukkitPlayer) {
        rawEvent.setCancelled(true);
    }

    @Override
    public void onPlayerInteractAtPlayer(PlayerInteractAtEntityEvent rawEvent, BukkitPlayer bukkitPlayer, BukkitPlayer target) {
        rawEvent.setCancelled(true);
    }

    @Override
    public void onPlayerInteractEntity(Entity entity, PlayerInteractEntityEvent rawEvent, BukkitPlayer bukkitPlayer) {
        rawEvent.setCancelled(true);
    }

    @Override
    public void onPlayerMove(PlayerMoveEvent rawEvent, BukkitPlayer bukkitPlayer) {
        if (rawEvent.getPlayer().getLocation().getY() <= 55)rawEvent.getPlayer().teleport(getSpawn());
    }

    @Override
    public void onHandSwap(BukkitPlayer player, PlayerSwapHandItemsEvent rawEvent) {
        rawEvent.setCancelled(true);
    }

    @Override
    public void onPlayerDropItem(PlayerDropItemEvent rawEvent, BukkitPlayer bukkitPlayer, ItemStack drop) {
        rawEvent.setCancelled(true);
    }

    @Override
    public void onPlayerItemInteract(ItemStack itemStack, PlayerInteractEvent rawEvent, BukkitPlayer bukkitPlayer) {
        rawEvent.setCancelled(true);
    }

    @Override
    public void onItemInventoryMove(InventoryMoveItemEvent rawEvent, ItemStack itemStack, BukkitPlayer player) {
        rawEvent.setCancelled(true);
    }

    @Override
    public void onEntityInteract(Entity entity, EntityInteractEvent rawEvent) {
        rawEvent.setCancelled(true);
    }

    @Override
    public void onInventoryClick(InventoryClickEvent rawEvent, BukkitPlayer bukkitPlayer) {
        rawEvent.setCancelled(true);
    }
}
