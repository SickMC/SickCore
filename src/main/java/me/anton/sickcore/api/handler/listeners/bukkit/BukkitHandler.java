package me.anton.sickcore.api.handler.listeners.bukkit;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
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

    public void onPlayerJoin(PlayerJoinEvent rawEvent, IBukkitPlayer bukkitPlayer){}

    public void onPlayerLogin(PlayerLoginEvent rawEvent, IBukkitPlayer bukkitPlayer){}

    public void onPlayerQuit(PlayerQuitEvent rawEvent, IBukkitPlayer bukkitPlayer){}

    public void onPlayerInteract(PlayerInteractEvent rawEvent, IBukkitPlayer bukkitPlayer){}

    public void onPlayerItemInteract(ItemStack itemStack, PlayerInteractEvent rawEvent, IBukkitPlayer bukkitPlayer){}

    public void onPlayerDeath(Location deathLocation, List<ItemStack> drops, PlayerDeathEvent rawEvent, IBukkitPlayer bukkitPlayer) {}

    public void onPlayerDeathByPlayer(Location deathLocation, List<ItemStack> drops, PlayerDeathEvent rawEvent, IBukkitPlayer bukkitPlayer, IBukkitPlayer killer){}

    public void onBlockBreak(Block block, BlockBreakEvent rawEvent, IBukkitPlayer bukkitPlayer){}

    public void onBlockPlace(Block block, BlockPlaceEvent rawEvent, IBukkitPlayer bukkitPlayer){}

    public void onPlayerToggleSneak(PlayerToggleSneakEvent rawEvent, IBukkitPlayer bukkitPlayer) {}

    public void onPlayerMove(PlayerMoveEvent rawEvent, IBukkitPlayer bukkitPlayer){}

    public void onPlayerChangeWorld(PlayerChangedWorldEvent rawEvent, IBukkitPlayer bukkitPlayer){}

    public void onPlayerFish(PlayerFishEvent rawEvent, IBukkitPlayer bukkitPlayer){}

    public void onInventoryOpen(InventoryOpenEvent rawEvent, IBukkitPlayer bukkitPlayer){}

    public void onInventoryClick(InventoryClickEvent rawEvent, IBukkitPlayer bukkitPlayer){}

    public void onInventoryClose(InventoryCloseEvent rawEvent, IBukkitPlayer bukkitPlayer){}

    public void onPlayerDropItem(PlayerDropItemEvent rawEvent, IBukkitPlayer bukkitPlayer, ItemStack drop){}

    public void onPlayerPickUpItem(EntityPickupItemEvent rawEvent, IBukkitPlayer bukkitPlayer, ItemStack item){}

    public void onEntityPickupItem(EntityPickupItemEvent rawEvent, Entity entity, ItemStack item){}

    public void onFoodLevelChange(FoodLevelChangeEvent rawEvent, IBukkitPlayer bukkitPlayer){}

    public void onPlayerDamage(EntityDamageEvent rawEvent, IBukkitPlayer bukkitPlayer){}

    public void onPlayerDamageByEntity(Entity entity, EntityDamageByEntityEvent rawEvent, IBukkitPlayer bukkitPlayer){}

    public void onPlayerDamageByPlayer(IBukkitPlayer damager, EntityDamageByEntityEvent rawEvent, IBukkitPlayer bukkitPlayer){}

    public void onEntityDamage(Entity entity, EntityDamageEvent rawEvent){}

    public void onEntitySpawn(EntitySpawnEvent rawEvent, Entity entity){}

    public void onEntityDamageByEntity(Entity entity, EntityDamageByEntityEvent rawEvent, Entity damager){}

    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent rawEvent, IBukkitPlayer bukkitPlayer) {}

    public void onPlayerInteractAtPlayer(PlayerInteractAtEntityEvent rawEvent, IBukkitPlayer bukkitPlayer, IBukkitPlayer target){}

    public void onPlayerInteractEntity(Entity entity, PlayerInteractEntityEvent rawEvent, IBukkitPlayer bukkitPlayer) {}

    public void onVehicleDestroyByPlayer(VehicleDestroyEvent rawEvent, IBukkitPlayer bukkitPlayer, Vehicle vehicle){}

    public void onVehicleDestroyByEntity(VehicleDestroyEvent rawEvent, Entity entity, Vehicle vehicle){}

    public void onVehicleExitByEntity(VehicleExitEvent rawEvent, Entity entity){}

    public void onVehicleExitByPlayer(VehicleExitEvent rawEvent, IBukkitPlayer bukkitPlayer){}

    public void onVehicleDamageByPlayer(VehicleDamageEvent rawEvent, IBukkitPlayer bukkitPlayer, Vehicle vehicle){}

    public void onVehicleDamageByEntity(VehicleDamageEvent rawEvent, Entity bukkitPlayer, Vehicle vehicle){}

    public void onBlockMove(Block fromBlock, Block toBlock, BlockFromToEvent rawEvent){}

    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent rawEvent, IBukkitPlayer bukkitPlayer){}

    public void onEntityInteract(Entity entity, EntityInteractEvent rawEvent){}

    public void onPlayerAsyncChat(AsyncChatEvent rawEvent, IBukkitPlayer bukkitPlayer){}

    public void onEntityRegainHealth(Entity entity, EntityRegainHealthEvent rawEvent){}

    public void onPlayerRegainHealth(EntityRegainHealthEvent rawEvent, IBukkitPlayer bukkitPlayer){}

    public void onEntityDeathByPlayer(Entity entity, EntityDeathEvent rawEvent, IBukkitPlayer killer){}

    public void onEntityDeath(Entity entity, EntityDeathEvent rawEvent){}

    public void onWeatherChange(World world, WeatherChangeEvent rawEvent){}

    public void onEntityExplode(Entity entity, EntityExplodeEvent rawEvent){}

    public void onPlayerItemHeldSlot(int previousSlot, int newSlot, PlayerItemHeldEvent rawEvent, IBukkitPlayer bukkitPlayer){}

    public void onCreatureSpawn(Entity entity, CreatureSpawnEvent.SpawnReason spawnReason, CreatureSpawnEvent rawEvent){}

    public void onPlayerInventoryDrag(InventoryDragEvent rawEvent, Inventory inventory, Map<Integer, ItemStack> newItems){}

    public void onPlayerCommandPreprocess(String message, PlayerCommandPreprocessEvent rawEvent, IBukkitPlayer bukkitPlayer){}

    public void onPlayerCommandSend(Collection<String> commands, PlayerCommandSendEvent rawEvent, IBukkitPlayer bukkitPlayer){}

    public void onPlayerVelocity(Vector vector, PlayerVelocityEvent rawEvent, IBukkitPlayer bukkitPlayer){}

    public void onCraftItem(CraftItemEvent rawEvent, IBukkitPlayer bukkitPlayer, ItemStack item){}

    public void onPlayerNickFinish(NickFinishEvent rawEvent, IBukkitPlayer bukkitPlayer, String nickname){}

    public void onChunkLoad(Chunk chunk, ChunkLoadEvent rawEvent){}

    public void onEntityChangeBlock(Entity entity, Block block, EntityChangeBlockEvent rawEvent){}

    public void onPlayerChangeBlock(IBukkitPlayer entity, Block block, EntityChangeBlockEvent rawEvent){}

    public void onHandSwap(IBukkitPlayer player, PlayerSwapHandItemsEvent rawEvent){}

    public void onPlayerElytra(IBukkitPlayer player, EntityToggleGlideEvent rawEvent){}

    public void onEntityGlide(Entity entity, EntityToggleGlideEvent rawEvent){}

    public void onHeadDBLoad(DatabaseLoadEvent rawEvent){}

    public void onPlayerAdvancementDone(PlayerAdvancementDoneEvent rawEvent, Advancement advancement, IBukkitPlayer player){}

    public void onItemInventoryMove(InventoryMoveItemEvent rawEvent, ItemStack itemStack){}

    public void onPlayerLevelChangeEvent(PlayerLevelChangeEvent rawEvent, int oldLevel, int newLevel, IBukkitPlayer player){}

    public void onPlayerUnknownCommand(UnknownCommandEvent rawEvent, String command){}

    public void onItemSpawn(ItemSpawnEvent rawEvent, Item item, World world){}

    public void onHangingBreak(HangingBreakEvent rawEvent, Hanging hanging){}

    public void onHangingPlace(HangingPlaceEvent rawEvent, Hanging hanging, IBukkitPlayer handler){}

}
