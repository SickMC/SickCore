package me.anton.sickcore.games.defaults.all.vanish;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class VanishAction extends BukkitHandler {

    JavaPlugin plugin;

    public VanishAction(Player player){
        if (VanishListInventory.vanishlist.contains(player))return;
        Core.getInstance().bukkit().getOnlineBukkitPlayers().forEach(handler -> {
            if (handler.api().isAdmin())return;
            handler.getPlayer().hidePlayer(plugin, player);
            VanishListInventory.vanishlist.add(player);
        });
    }

    @Override
    public void onPlayerJoin(PlayerJoinEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        for (Player player : VanishListInventory.vanishlist) {
            new VanishAction(player);
        }
    }


    @Override
    public void onPlayerQuit(PlayerQuitEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        if (VanishListInventory.vanishlist.contains(bukkitPlayer.getPlayer()))VanishListInventory.vanishlist.remove(bukkitPlayer.getPlayer());
    }

    public static void unVanish(Player player){
        Bukkit.getOnlinePlayers().forEach(handlers ->{
            handlers.showPlayer(Core.getInstance().bukkit().getPlugin(), player);
            if (Core.getInstance().bukkit().getPlugin().getServer().getName().startsWith("Lobby-"))player.setGlowing(true);
            VanishListInventory.vanishlist.remove(player);
        });
    }

}
