package me.anton.sickcore.games.survival.appereance;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class SurvivalChat extends BukkitHandler {

    @Override
    public void onPlayerJoin(PlayerJoinEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        rawEvent.joinMessage(null);
        Bukkit.getOnlinePlayers().forEach(player -> {
            IAPIPlayer all = new APIPlayer(player.getUniqueId());
            all.bukkit().sendMessage(all.bukkit().getNickedPrefix() + "§7joined the server!", all.bukkit().getNickedPrefix() + "§7hat den Server betreten!");
        });
    }

    @Override
    public void onPlayerQuit(PlayerQuitEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        rawEvent.quitMessage(null);
        Bukkit.getOnlinePlayers().forEach(player -> {
            IAPIPlayer all = new APIPlayer(player.getUniqueId());
            all.bukkit().sendMessage(all.bukkit().getNickedPrefix() + "§7quit the server!", all.bukkit().getNickedPrefix() + "§7hat den Server verlassen!");
        });
    }

    @Override
    public void onPlayerAsyncChat(AsyncChatEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        rawEvent.getPlayer().getServer().getOnlinePlayers().forEach(player -> {
            player.sendMessage(Component.text(bukkitPlayer.getNickedPrefix() + "§7» §7").append(rawEvent.message().color(TextColor.color(11184810))));
        });
        rawEvent.setCancelled(true);
    }

    @Override
    public void onPlayerDeath(Location deathLocation, List<ItemStack> drops, PlayerDeathEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        rawEvent.deathMessage(Component.text(rawEvent.deathMessage() + "§7!"));
    }
}
