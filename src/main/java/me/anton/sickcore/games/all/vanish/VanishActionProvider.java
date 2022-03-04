package me.anton.sickcore.games.all.vanish;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class VanishActionProvider extends BukkitHandler {

    @Override
    public void onPlayerJoin(PlayerJoinEvent rawEvent, BukkitPlayer bukkitPlayer) {
        for (Player player : BukkitCore.getInstance().bukkit().getVanished()) {
            new VanishAction(player);
        }
    }

    @Override
    public void onPlayerQuit(PlayerQuitEvent rawEvent, BukkitPlayer bukkitPlayer) {
        if (BukkitCore.getInstance().bukkit().getVanished().contains(bukkitPlayer.getPlayer()))BukkitCore.getInstance().bukkit().getVanished().remove(bukkitPlayer.getPlayer());
    }

}
