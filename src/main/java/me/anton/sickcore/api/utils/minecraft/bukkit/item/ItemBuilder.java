package me.anton.sickcore.api.utils.minecraft.bukkit.item;

import lombok.Getter;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.InventoryBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Getter
public class ItemBuilder {

    @Getter
    private static HashMap<UUID, HashMap<ItemStack, ItemBuilder>> playerHandler = new HashMap<>();

    private ItemStack itemStack;
    private ItemMeta itemMeta;
    private Consumer<PlayerInteractEvent> itemClickEvent;
    private final BukkitPlayer player;
    private InventoryBuilder builder;

    public ItemBuilder(Material material, @Nullable BukkitPlayer player){
        this.itemStack = new ItemStack(material, 1);
        this.itemMeta = itemStack.getItemMeta();
        this.player = player;
        this.itemClickEvent = null;
    }

    public ItemBuilder(Material material, int amount,@Nullable BukkitPlayer player){
        this.itemStack = new ItemStack(material, amount);
        this.itemMeta = itemStack.getItemMeta();
        this.player = player;
        this.itemClickEvent = null;
    }

    public ItemBuilder(ItemStack itemStack,@Nullable BukkitPlayer player){
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
        this.player = player;
        this.itemClickEvent = null;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level){
        itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder setEnchanted(boolean value){
        if (!value){
            itemMeta.getEnchants().forEach((enchantment, id) -> itemMeta.removeEnchant(enchantment));
            itemMeta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
            return this;
        }
        itemMeta.addEnchant(Enchantment.MENDING, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder setName(@Nullable String title){
        if (title == null)title = "§6";
        itemMeta.displayName(Component.text(title));
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

    public void onPlayerClick(PlayerInteractEvent event){
        if (itemClickEvent == null)return;
        this.itemClickEvent.accept(event);
    }

    public ItemBuilder setPlayerEvent(Consumer<PlayerInteractEvent> event){
        HashMap<ItemStack, ItemBuilder> add;
        if (playerHandler.containsKey(player.getUniqueID()))add = new HashMap<>(playerHandler.get(player.getUniqueID()));
        else add = new HashMap<>();
        add.put(this.build(), this);
        playerHandler.put(player.api().getUUID(), add);
        this.itemClickEvent = event;
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

    public ItemBuilder setSkull(UUID owner){
        if (!(itemStack.getType() == Material.PLAYER_HEAD)) return this;
        SkullMeta skullMeta = (SkullMeta) itemMeta;
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(owner));
        return this;
    }

    public ItemStack build(){
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }

}
