package me.anton.sickcore.games.all.nick;

import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.PagedInventoryBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class NickListInventory {

    public static HashMap<Player, String> nicklist = new HashMap<>();

    public static void openInventory(BukkitPlayer player){

        PagedInventoryBuilder builder = new PagedInventoryBuilder(player, "§6Nicklist");
        player.getPlayer().getServer().getOnlinePlayers().forEach(onlinePlayer ->{
            BukkitPlayer online = new BukkitPlayer(onlinePlayer);
            if (!online.isNicked())return;
            nicklist.put(onlinePlayer, online.getName());
            builder.addItem(new ItemBuilder(Material.PLAYER_HEAD, player).setSkull(onlinePlayer.getUniqueId()).setName("§6" + onlinePlayer.getName()).setLore("§7Nickname: §7" + online.getName(), (String) player.api().languageObject("§7Click to unnick the player", "§7Klicke um diesen Spieler zu unnicken")), event -> {
                online.unnick();
                openInventory(player);
            });
        });
        builder.open();
    }

}
