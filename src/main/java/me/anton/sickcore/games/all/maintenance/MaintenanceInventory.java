package me.anton.sickcore.games.all.maintenance;

import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.InventoryBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.sound.DefaultSounds;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

public class MaintenanceInventory {

    public void openInv(BukkitPlayer player){
        InventoryBuilder builder = new InventoryBuilder(player, "§6Maintenance", 3);

        if (MaintenanceModule.getInstance().isActive()){
            if (MaintenanceModule.getInstance().isSecure()){
                builder.setItem(new ItemBuilder(Material.RED_CONCRETE, player).setName("§6Secure Mode").addEnchantment(Enchantment.MENDING, 1).setLore("§7Click to disable securemode").addItemFlags(ItemFlag.HIDE_ENCHANTS), 15, event -> {
                    MaintenanceModule.getInstance().disable();
                    openInv(player);
                    DefaultSounds.levelUP.play(player);
                });
                builder.setItem(new ItemBuilder(Material.GREEN_CONCRETE, player).setName("§6Maintenance").addEnchantment(Enchantment.MENDING, 1).setLore("§7Click to disable maintenance!").addItemFlags(ItemFlag.HIDE_ENCHANTS), 11, event -> {
                    MaintenanceModule.getInstance().disable();
                    openInv(player);
                    DefaultSounds.levelUP.play(player);
                });
            }else {
                builder.setItem(new ItemBuilder(Material.GREEN_CONCRETE, player).setName("§6Maintenance").addEnchantment(Enchantment.MENDING, 1).setLore("§7Click to disable maintenance!").addItemFlags(ItemFlag.HIDE_ENCHANTS), 11, event -> {
                    MaintenanceModule.getInstance().disable();
                    openInv(player);
                    DefaultSounds.levelUP.play(player);
                });
                builder.setItem(new ItemBuilder(Material.RED_CONCRETE, player).setName("§6Secure Mode").setLore("§7Click to enable securemode"), 15, event -> {
                    MaintenanceModule.getInstance().disable();
                    openInv(player);
                    DefaultSounds.levelUP.play(player);
                });
            }
        }else {
            builder.setItem(new ItemBuilder(Material.GREEN_CONCRETE, player).setName("§6Maintenance").setLore("§7Click to enable maintenance!"), 11, event -> {
                MaintenanceModule.getInstance().enable(false);
                openInv(player);
                DefaultSounds.levelUP.play(player);
            });
            builder.setItem(new ItemBuilder(Material.RED_CONCRETE, player).setName("§6Secure Mode").setLore("§7Click to enable securemode"), 15, event -> {
                MaintenanceModule.getInstance().enable(true);
                openInv(player);
                DefaultSounds.levelUP.play(player);
            });
        }

        builder.fillEmpty();
        builder.open();
    }

}
