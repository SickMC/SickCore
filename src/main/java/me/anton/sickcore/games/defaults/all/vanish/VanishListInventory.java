package me.anton.sickcore.games.defaults.all.vanish;

import dev.dbassett.skullcreator.SkullCreator;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.PagedInventoryBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class VanishListInventory {


    public static List<Player> vanishlist = new ArrayList<>();

    public static void openInventory(IBukkitPlayer player, JavaPlugin plugin){
        PagedInventoryBuilder builder = new PagedInventoryBuilder(player.api(), "§6Nicklist");
        vanishlist.forEach(vanished -> {
            ItemStack itemStack = SkullCreator.itemFromUuid(vanished.getUniqueId());
            builder.addItem(new ItemBuilder(itemStack).setName("§6" + vanished.getName()).setLore(player.api().languageString("§7Click to unvanish the player", "§7Klicke um diesen Spieler zu unvanishen")).build(), event -> {
                new BukkitPlayer(vanished).unVanish(plugin);
                new BukkitPlayer(vanished).sendMessage("§7You are now unvanished!", "§7Du bist nun wieder sichtbar!");
                openInventory(player, plugin);
            });
        });
        builder.resort();
        builder.open(1);
    }

}
