package me.anton.sickcore.api.handler.listeners.bukkit;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.arcaniax.hdb.api.DatabaseLoadEvent;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.Item;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.command.UnknownCommandEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import xyz.haoshoku.nick.events.NickFinishEvent;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class BukkitHandler {

    public void onPlayerJoin(PlayerJoinEvent rawEvent, BukkitPlayer bukkitPlayer){}

    public void onPlayerLogin(PlayerLoginEvent rawEvent, BukkitPlayer bukkitPlayer){}

    public void onPlayerQuit(PlayerQuitEvent rawEvent, BukkitPlayer bukkitPlayer){}

    public void onPlayerInteract(PlayerInteractEvent rawEvent, BukkitPlayer bukkitPlayer){}

    public void onPlayerItemInteract(ItemStack itemStack, PlayerInteractEvent rawEvent, BukkitPlayer bukkitPlayer){}

    public void onPlayerDeath(Location deathLocation, List<ItemStack> drops, PlayerDeathEvent rawEvent, BukkitPlayer bukkitPlayer) {}

    public void onPlayerDeathByPlayer(Location deathLocation, List<ItemStack> drops, PlayerDeathEvent rawEvent, BukkitPlayer bukkitPlayer, BukkitPlayer killer){}

    public void onBlockBreak(Block block, BlockBreakEvent rawEvent, BukkitPlayer bukkitPlayer){}

    public void onBlockPlace(Block block, BlockPlaceEvent rawEvent, BukkitPlayer bukkitPlayer){}

    public void onPlayerToggleSneak(PlayerToggleSneakEvent rawEvent, BukkitPlayer bukkitPlayer) {}

    public void onPlayerMove(PlayerMoveEvent rawEvent, BukkitPlayer bukkitPlayer){}

    public void onPlayerChangeWorld(PlayerChangedWorldEvent rawEvent, BukkitPlayer bukkitPlayer){}

    public void onPlayerFish(PlayerFishEvent rawEvent, BukkitPlayer bukkitPlayer){}

    public void onInventoryOpen(InventoryOpenEvent rawEvent, BukkitPlayer bukkitPlayer){}

    public void onInventoryClick(InventoryClickEvent rawEvent, BukkitPlayer bukkitPlayer){}

    public void onInventoryClose(InventoryCloseEvent rawEvent, BukkitPlayer bukkitPlayer){}

    public void onPlayerDropItem(PlayerDropItemEvent rawEvent, BukkitPlayer bukkitPlayer, ItemStack drop){}

    public void onPlayerPickUpItem(EntityPickupItemEvent rawEvent, BukkitPlayer bukkitPlayer, ItemStack item){}

    public void onEntityPickupItem(EntityPickupItemEvent rawEvent, Entity entity, ItemStack item){}

    public void onFoodLevelChange(FoodLevelChangeEvent rawEvent, BukkitPlayer bukkitPlayer){}

    public void onPlayerDamage(EntityDamageEvent rawEvent, BukkitPlayer bukkitPlayer){}

    public void onPlayerDamageByEntity(Entity entity, EntityDamageByEntityEvent rawEvent, BukkitPlayer bukkitPlayer){}

    public void onPlayerDamageByPlayer(BukkitPlayer damager, EntityDamageByEntityEvent rawEvent, BukkitPlayer bukkitPlayer){}

    public void onEntityDamage(Entity entity, EntityDamageEvent rawEvent){}

    public void onEntitySpawn(EntitySpawnEvent rawEvent, Entity entity){}

    public void onEntityDamageByEntity(Entity entity, EntityDamageByEntityEvent rawEvent, Entity damager){}

    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent rawEvent, BukkitPlayer bukkitPlayer) {}

    public void onPlayerInteractAtPlayer(PlayerInteractAtEntityEvent rawEvent, BukkitPlayer bukkitPlayer, BukkitPlayer target){}

    public void onPlayerInteractEntity(Entity entity, PlayerInteractEntityEvent rawEvent, BukkitPlayer bukkitPlayer) {}

    public void onVehicleDestroyByPlayer(VehicleDestroyEvent rawEvent, BukkitPlayer bukkitPlayer, Vehicle vehicle){}

    public void onVehicleDestroyByEntity(VehicleDestroyEvent rawEvent, Entity entity, Vehicle vehicle){}

    public void onVehicleExitByEntity(VehicleExitEvent rawEvent, Entity entity){}

    public void onVehicleExitByPlayer(VehicleExitEvent rawEvent, BukkitPlayer bukkitPlayer){}

    public void onVehicleDamageByPlayer(VehicleDamageEvent rawEvent, BukkitPlayer bukkitPlayer, Vehicle vehicle){}

    public void onVehicleDamageByEntity(VehicleDamageEvent rawEvent, Entity bukkitPlayer, Vehicle vehicle){}

    public void onBlockMove(Block fromBlock, Block toBlock, BlockFromToEvent rawEvent){}

    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent rawEvent, BukkitPlayer bukkitPlayer){}

    public void onEntityInteract(Entity entity, EntityInteractEvent rawEvent){}

    public void onPlayerAsyncChat(AsyncChatEvent rawEvent, BukkitPlayer bukkitPlayer){}

    public void onEntityRegainHealth(Entity entity, EntityRegainHealthEvent rawEvent){}

    public void onPlayerRegainHealth(EntityRegainHealthEvent rawEvent, BukkitPlayer bukkitPlayer){}

    public void onEntityDeathByPlayer(Entity entity, EntityDeathEvent rawEvent, BukkitPlayer killer){}

    public void onEntityDeath(Entity entity, EntityDeathEvent rawEvent){}

    public void onWeatherChange(World world, WeatherChangeEvent rawEvent){}

    public void onEntityExplode(Entity entity, EntityExplodeEvent rawEvent){}

    public void onPlayerItemHeldSlot(int previousSlot, int newSlot, PlayerItemHeldEvent rawEvent, BukkitPlayer bukkitPlayer){}

    public void onCreatureSpawn(Entity entity, CreatureSpawnEvent.SpawnReason spawnReason, CreatureSpawnEvent rawEvent){}

    public void onPlayerInventoryDrag(InventoryDragEvent rawEvent, Inventory inventory, Map<Integer, ItemStack> newItems, BukkitPlayer player){}

    public void onPlayerCommandPreprocess(String message, PlayerCommandPreprocessEvent rawEvent, BukkitPlayer bukkitPlayer){}

    public void onPlayerCommandSend(Collection<String> commands, PlayerCommandSendEvent rawEvent, BukkitPlayer bukkitPlayer){}

    public void onPlayerVelocity(Vector vector, PlayerVelocityEvent rawEvent, BukkitPlayer bukkitPlayer){}

    public void onCraftItem(CraftItemEvent rawEvent, BukkitPlayer bukkitPlayer, ItemStack item){}

    public void onPlayerNickFinish(NickFinishEvent rawEvent, BukkitPlayer bukkitPlayer, String nickname){}

    public void onChunkLoad(Chunk chunk, ChunkLoadEvent rawEvent){}

    public void onEntityChangeBlock(Entity entity, Block block, EntityChangeBlockEvent rawEvent){}

    public void onPlayerChangeBlock(BukkitPlayer entity, Block block, EntityChangeBlockEvent rawEvent){}

    public void onHandSwap(BukkitPlayer player, PlayerSwapHandItemsEvent rawEvent){}

    public void onPlayerElytra(BukkitPlayer player, EntityToggleGlideEvent rawEvent){}

    public void onEntityGlide(Entity entity, EntityToggleGlideEvent rawEvent){}

    public void onHeadDBLoad(DatabaseLoadEvent rawEvent){}

    public void onPlayerAdvancementDone(PlayerAdvancementDoneEvent rawEvent, Advancement advancement, BukkitPlayer player){}

    public void onItemInventoryMove(InventoryMoveItemEvent rawEvent, ItemStack itemStack, BukkitPlayer player){}

    public void onPlayerLevelChangeEvent(PlayerLevelChangeEvent rawEvent, int oldLevel, int newLevel, BukkitPlayer player){}

    public void onPlayerUnknownCommand(UnknownCommandEvent rawEvent, String command, BukkitPlayer player){}

    public void onItemSpawn(ItemSpawnEvent rawEvent, Item item, World world){}

    public void onHangingBreak(HangingBreakEvent rawEvent, Hanging hanging){}

    public void onHangingPlace(HangingPlaceEvent rawEvent, Hanging hanging, BukkitPlayer handler){}

}
