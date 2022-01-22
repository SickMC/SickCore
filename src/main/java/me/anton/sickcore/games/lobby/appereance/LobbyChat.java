package me.anton.sickcore.games.lobby.appereance;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.advancement.Advancement;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class LobbyChat extends BukkitHandler {

    @Override
    public void onPlayerJoin(PlayerJoinEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        bukkitPlayer.unnick();
        rawEvent.joinMessage(null);
    }

    @Override
    public void onPlayerQuit(PlayerQuitEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        rawEvent.quitMessage(null);
    }

    @Override
    public void onPlayerAsyncChat(AsyncChatEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        rawEvent.getPlayer().getServer().getOnlinePlayers().forEach(player -> {
            player.sendMessage(Component.text(bukkitPlayer.api().getDisplayName() + "§7» §7").append(rawEvent.message().color(TextColor.color(11184810))));
        });
        rawEvent.setCancelled(true);
    }
    @Override
    public void onPlayerDeath(Location deathLocation, List<ItemStack> drops, PlayerDeathEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        rawEvent.deathMessage(null);
    }

    @Override
    public void onPlayerAdvancementDone(PlayerAdvancementDoneEvent rawEvent, Advancement advancement, IBukkitPlayer player) {
        rawEvent.message(null);
    }
}
