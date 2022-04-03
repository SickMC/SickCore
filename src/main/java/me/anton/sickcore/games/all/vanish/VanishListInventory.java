package me.anton.sickcore.games.all.vanish;

import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.PagedInventoryBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import me.anton.sickcore.oldcore.BukkitCore;
import org.bukkit.Material;

public class VanishListInventory {

    public static void openInventory(BukkitPlayer player){
        PagedInventoryBuilder builder = new PagedInventoryBuilder(player, "§6Nicklist");
        BukkitCore.getInstance().bukkit().getVanished().forEach(vanished -> {
            builder.addItem(new ItemBuilder(Material.PLAYER_HEAD, player).setSkull(vanished.getUniqueId()).setName("§6" + vanished.getName()).setLore((String) player.api().languageObject("§7Click to unvanish the player", "§7Klicke um diesen Spieler zu unvanishen")), event -> {
                BukkitPlayer bukkitPlayer = new BukkitPlayer(vanished);
                bukkitPlayer.unVanish();
                bukkitPlayer.getPlayer().sendMessage((String) bukkitPlayer.api().languageObject("§7You are now unvanished!", "§7Du bist nun wieder sichtbar!"));
                openInventory(player);
            });
        });
        builder.open();
    }

}
