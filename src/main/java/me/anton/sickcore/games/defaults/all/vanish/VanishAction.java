package me.anton.sickcore.games.defaults.all.vanish;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.core.BukkitCore;
import me.anton.sickcore.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class VanishAction{

    JavaPlugin plugin;

    public VanishAction(Player player){
        if (BukkitCore.getInstance().bukkit().getVanished().contains(player))return;
        Core.getInstance().bukkit().getOnlineBukkitPlayers().forEach(handler -> {
            if (handler.api().isAdmin())return;
            handler.getPlayer().hidePlayer(plugin, player);
            BukkitCore.getInstance().bukkit().getVanished().add(player);
        });
    }



    public static void unVanish(Player player){
        Bukkit.getOnlinePlayers().forEach(handlers ->{
            handlers.showPlayer(Core.getInstance().bukkit().getPlugin(), player);
            if (Core.getInstance().bukkit().getPlugin().getServer().getName().startsWith("Lobby-"))player.setGlowing(true);
            BukkitCore.getInstance().bukkit().getVanished().remove(player);
        });
    }

}
