package me.anton.sickcore.games.all.vanish;

import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.oldcore.BukkitCore;
import me.anton.sickcore.oldcore.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class VanishAction{

    public VanishAction(Player player){
        if (BukkitCore.getInstance().bukkit().getVanished().contains(player))return;
        Bukkit.getOnlinePlayers().forEach(handler -> {
            BukkitPlayer bukkitPlayer = new BukkitPlayer(handler);
            if (bukkitPlayer.api().isAdmin())return;
            handler.hidePlayer(Core.getInstance().bukkit().getPlugin(), player);
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
