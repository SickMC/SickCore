package me.anton.sickcore.games.survival.appereance;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.games.survival.SurvivalGamePlayer;
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
            if (bukkitPlayer.api().hasAutoNick())
                all.bukkit().getPlayer().sendMessage((String) all.languageObject(bukkitPlayer.getNickDisplayName() + "§7joined the server!", bukkitPlayer.getNickDisplayName() + "§7hat den Server betreten!"));
            else
                all.bukkit().getPlayer().sendMessage((String) all.languageObject(bukkitPlayer.api().getDisplayName() + "§7joined the server!", bukkitPlayer.api().getDisplayName() + "§7hat den Server betreten!"));

        });
        SurvivalGamePlayer player = new SurvivalGamePlayer(bukkitPlayer.getPlayer().getUniqueId());
    }

    @Override
    public void onPlayerQuit(PlayerQuitEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        rawEvent.quitMessage(null);
        Bukkit.getOnlinePlayers().forEach(player -> {
            IAPIPlayer all = new APIPlayer(player.getUniqueId());
            if (!bukkitPlayer.isNicked())
                all.bukkit().getPlayer().sendMessage((String) all.languageObject(bukkitPlayer.api().getDisplayName() + "§7quit the server!", bukkitPlayer.api().getDisplayName() + "§7hat den Server verlassen!"));
            else
                all.bukkit().getPlayer().sendMessage((String) all.languageObject(bukkitPlayer.getNickDisplayName() + "§7quit the server!", bukkitPlayer.getNickDisplayName() + "§7hat den Server verlassen!"));

        });
    }

    @Override
    public void onPlayerAsyncChat(AsyncChatEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        rawEvent.getPlayer().getServer().getOnlinePlayers().forEach(player -> {
            if (!bukkitPlayer.isNicked())
                player.sendMessage(Component.text(bukkitPlayer.api().getDisplayName() + "§8» §7").append(rawEvent.message().color(TextColor.color(11184810))));
            else
                player.sendMessage(Component.text(bukkitPlayer.getNickDisplayName() + "§8» §7").append(rawEvent.message().color(TextColor.color(11184810))));

        });
        rawEvent.setCancelled(true);
    }

    @Override
    public void onPlayerDeath(Location deathLocation, List<ItemStack> drops, PlayerDeathEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        rawEvent.deathMessage(Component.text(rawEvent.deathMessage() + "§7!"));
    }
}
