package me.anton.sickcore.games.build.tools.invisitemframes;

import me.anton.sickcore.core.BukkitCore;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class InvisItemFrame {

    private static NamespacedKey invisibleKey = new NamespacedKey(BukkitCore.getInstance().getPlugin(), "invisible");

    public static ItemStack generateInvisibleItemFrame(){
        ItemStack item = new ItemStack(Material.ITEM_FRAME, 1);
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.DURABILITY, 1 ,true);
        meta.setDisplayName(ChatColor.WHITE + "Invisible Item Frame");
        meta.getPersistentDataContainer().set(invisibleKey, PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(meta);
        return item;
    }

}
