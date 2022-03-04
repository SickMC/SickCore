package me.anton.sickcore.games.survival.spawn;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Teleport extends BukkitHandler {

    Location spawnlocation = Elytra.spawnlocation;

    @Override
    public void onPlayerInteract(PlayerInteractEvent rawEvent, BukkitPlayer bukkitPlayer) {
        if (rawEvent.getClickedBlock() == null)return;
        if (!rawEvent.getClickedBlock().getLocation().getChunk().equals(spawnlocation.getChunk()))return;
        if (rawEvent.getAction() != Action.PHYSICAL)return;
        if (!(rawEvent.getClickedBlock().getType() == Material.POLISHED_BLACKSTONE_PRESSURE_PLATE))return;
        Vector vector = bukkitPlayer.getPlayer().getLocation().getDirection().setX(0).setZ(0).setY(10);
        bukkitPlayer.getPlayer().setVelocity(vector);
        bukkitPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 35, 15));
        bukkitPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 35, 15));
        Bukkit.getScheduler().runTaskLater(Core.getInstance().bukkit().getPlugin(), () -> {
            bukkitPlayer.getPlayer().teleport(spawnlocation);
            bukkitPlayer.getPlayer().playSound(spawnlocation, Sound.ITEM_ARMOR_EQUIP_IRON, 1, 1);
        },15L);
    }

    @Override
    public void onPlayerJoin(PlayerJoinEvent rawEvent, BukkitPlayer bukkitPlayer) {
        if (rawEvent.getPlayer().hasPlayedBefore())return;
        rawEvent.getPlayer().teleport(spawnlocation);
    }

}
