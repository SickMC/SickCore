package me.anton.sickcore.game.defaults.all.maintenance;

import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.InventoryBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.InventoryUsage;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.sound.DefaultSounds;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

public class MaintenanceInventory {

    public void openInv(IBukkitPlayer player){
        InventoryBuilder builder = new InventoryBuilder(player.api(), "§6Maintenance", 3*9, InventoryUsage.UTILITY);

        if (MaintenanceModule.getInstance().isActive()){
            if (MaintenanceModule.getInstance().isSecure()){
                builder.setItem(new ItemBuilder(Material.RED_CONCRETE).setName("§6Secure Mode").addEnchantment(Enchantment.MENDING, 1).setLore("§7Click to disable securemode").addItemFlags(ItemFlag.HIDE_ENCHANTS).build(), 15, event -> {
                    MaintenanceModule.getInstance().disable();
                    openInv(player);
                    DefaultSounds.levelUP.play(player);
                });
                builder.setItem(new ItemBuilder(Material.GREEN_CONCRETE).setName("§6Maintenance").addEnchantment(Enchantment.MENDING, 1).setLore("§7Click to disable maintenance!").addItemFlags(ItemFlag.HIDE_ENCHANTS).build(), 11, event -> {
                    MaintenanceModule.getInstance().disable();
                    openInv(player);
                    DefaultSounds.levelUP.play(player);
                });
            }else {
                builder.setItem(new ItemBuilder(Material.GREEN_CONCRETE).setName("§6Maintenance").addEnchantment(Enchantment.MENDING, 1).setLore("§7Click to disable maintenance!").addItemFlags(ItemFlag.HIDE_ENCHANTS).build(), 11, event -> {
                    MaintenanceModule.getInstance().disable();
                    openInv(player);
                    DefaultSounds.levelUP.play(player);
                });
                builder.setItem(new ItemBuilder(Material.RED_CONCRETE).setName("§6Secure Mode").setLore("§7Click to enable securemode").build(), 15, event -> {
                    MaintenanceModule.getInstance().disable();
                    openInv(player);
                    DefaultSounds.levelUP.play(player);
                });
            }
        }else {
            builder.setItem(new ItemBuilder(Material.GREEN_CONCRETE).setName("§6Maintenance").setLore("§7Click to enable maintenance!").build(), 11, event -> {
                MaintenanceModule.getInstance().enable(false);
                openInv(player);
                DefaultSounds.levelUP.play(player);
            });
            builder.setItem(new ItemBuilder(Material.RED_CONCRETE).setName("§6Secure Mode").setLore("§7Click to enable securemode").build(), 15, event -> {
                MaintenanceModule.getInstance().enable(true);
                openInv(player);
                DefaultSounds.levelUP.play(player);
            });
        }

        builder.fillEmpty();
        builder.open();
    }

}
