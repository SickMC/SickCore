package me.anton.sickcore.game.defaults.all.vanish;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class VanishActionProvider extends BukkitHandler {

    @Override
    public void onPlayerJoin(PlayerJoinEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        for (Player player : BukkitCore.getInstance().bukkit().getVanished()) {
            new VanishAction(player);
        }
    }

    @Override
    public void onPlayerQuit(PlayerQuitEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        if (BukkitCore.getInstance().bukkit().getVanished().contains(bukkitPlayer.getPlayer()))BukkitCore.getInstance().bukkit().getVanished().remove(bukkitPlayer.getPlayer());
    }

}
