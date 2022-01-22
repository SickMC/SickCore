package me.anton.sickcore.api.utils.minecraft.bukkit.item;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemBuilder(Material material){
        this.itemStack = new ItemStack(material, 1);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, int amount){
        this.itemStack = new ItemStack(material, amount);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(ItemStack itemStack){
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level){
        itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder setName(@Nullable String title){
        if (title == null)title = "§6";
        itemMeta.setDisplayName(title);
        return this;
    }

    public ItemBuilder addItemFlags(@NotNull ItemFlag... itemFlags){
        itemMeta.addItemFlags(itemFlags);
        return this;
    }

    public ItemBuilder setLore(@NotNull String... lore){
        List<Component> lores = new ArrayList();
        for (String loreline : lore)lores.add(Component.text(loreline));
        itemMeta.lore(lores);
        return this;
    }

    public ItemBuilder setLore(@NotNull List<String> lore){
        List<Component> lores = new ArrayList();
        lore.forEach(loreline -> lores.add(Component.text(loreline)));
        itemMeta.lore(lores);
        return this;
    }
    public ItemBuilder setAmount(int amount){
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setLeatherArmorColor(@NotNull Color color){
        LeatherArmorMeta meta = (LeatherArmorMeta) itemMeta;
        meta.setColor(color);
        return this;
    }

    public ItemBuilder setDurability(int damage){
        Damageable damageable = (Damageable) itemMeta;
        damageable.setDamage(damage);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean value){
        itemMeta.setUnbreakable(value);
        return this;
    }

    public ItemBuilder setSkullMeta(UUID owner){
        if (!(itemStack.getType() == Material.PLAYER_HEAD)) return this;
        SkullMeta skullMeta = (SkullMeta) itemMeta;
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(owner));
        return this;
    }

    public ItemStack build(){
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
