package me.anton.sickcore.games.defaults.all.vanish;

import dev.dbassett.skullcreator.SkullCreator;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.PagedInventoryBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class VanishListInventory {

    public static void openInventory(IBukkitPlayer player){
        PagedInventoryBuilder builder = new PagedInventoryBuilder(player.api(), "§6Nicklist");
        BukkitCore.getInstance().bukkit().getVanished().forEach(vanished -> {
            ItemStack itemStack = SkullCreator.itemFromUuid(vanished.getUniqueId());
            builder.addItem(new ItemBuilder(itemStack).setName("§6" + vanished.getName()).setLore((String) player.api().languageObject("§7Click to unvanish the player", "§7Klicke um diesen Spieler zu unvanishen")).build(), event -> {
                IBukkitPlayer bukkitPlayer = new BukkitPlayer(vanished);
                bukkitPlayer.unVanish();
                bukkitPlayer.getPlayer().sendMessage((String) bukkitPlayer.api().languageObject("§7You are now unvanished!", "§7Du bist nun wieder sichtbar!"));
                openInventory(player);
            });
        });
        builder.resort();
        builder.open(1);
    }

}
