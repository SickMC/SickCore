package me.anton.sickcore.games.defaults.all.vanish;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.api.service.defaults.bukkitDefault.enums.TablistFormat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class VanishAction extends BukkitHandler {

    JavaPlugin plugin;

    public VanishAction(Player player, JavaPlugin plugin){
        if (VanishListInventory.vanishlist.contains(player))return;
        Bukkit.getOnlinePlayers().forEach(handlers ->{
            IBukkitPlayer bukkitPlayer = new BukkitPlayer(handlers);
            if (bukkitPlayer.api().isAdmin())return;
            handlers.hidePlayer(plugin, player);
            VanishListInventory.vanishlist.add(player);
        });
    }

    public VanishAction(JavaPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public void onPlayerJoin(PlayerJoinEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        for (Player player : VanishListInventory.vanishlist) {
            new VanishAction(player, plugin);
        }
    }


    @Override
    public void onPlayerQuit(PlayerQuitEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        if (VanishListInventory.vanishlist.contains(bukkitPlayer.getPlayer()))VanishListInventory.vanishlist.remove(bukkitPlayer.getPlayer());
    }

    public static void unVanish(JavaPlugin plugin, Player player, TablistFormat format){
        Bukkit.getOnlinePlayers().forEach(handlers ->{
            handlers.showPlayer(plugin, player);
            if (format == TablistFormat.LOBBY)player.setGlowing(true);
            VanishListInventory.vanishlist.remove(player);
        });
    }

}
