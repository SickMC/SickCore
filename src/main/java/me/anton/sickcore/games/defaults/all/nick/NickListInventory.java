package me.anton.sickcore.games.defaults.all.nick;

import dev.dbassett.skullcreator.SkullCreator;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.PagedInventoryBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class NickListInventory {

    public static HashMap<Player, String> nicklist = new HashMap<>();

    public static void openInventory(IBukkitPlayer player){

        PagedInventoryBuilder builder = new PagedInventoryBuilder(player.api(), "§6Nicklist");
        player.getPlayer().getServer().getOnlinePlayers().forEach(onlinePlayer ->{
            IBukkitPlayer online = new BukkitPlayer(onlinePlayer);
            if (!online.isNicked())return;
            nicklist.put(onlinePlayer, online.getName());
            ItemStack itemStack = SkullCreator.itemFromUuid(onlinePlayer.getUniqueId());
            builder.addItem(new ItemBuilder(itemStack).setName("§6" + onlinePlayer.getName()).setLore("§7Nickname: §7" + online.getName(), (String) player.api().languageObject("§7Click to unnick the player", "§7Klicke um diesen Spieler zu unnicken")).build(), event -> {
                online.unnick();
                openInventory(player);
            });
        });
        builder.resort();
        builder.open(1);
    }

}
