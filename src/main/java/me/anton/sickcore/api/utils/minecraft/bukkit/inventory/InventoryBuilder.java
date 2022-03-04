package me.anton.sickcore.api.utils.minecraft.bukkit.inventory;

import lombok.Getter;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.utils.common.Logger;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.sound.DefaultSounds;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Consumer;

@Getter
public class InventoryBuilder {

    @Getter
    private static HashMap<UUID, InventoryBuilder> handlers = new HashMap<>();

    private final BukkitPlayer player;
    private Inventory inventory;
    private HashMap<Integer, ItemBuilder> itemSlots;
    public HashMap<ItemStack, Consumer<InventoryClickEvent>> clickableItems;
    private Consumer<InventoryCloseEvent> closeEvent;
    private Consumer<InventoryMoveItemEvent> moveEvent;
    private Consumer<InventoryOpenEvent> openEvent;
    private InventoryUsage usage;

    public InventoryBuilder (BukkitPlayer player, String name, int height){
        this.player = player;
        this.inventory = Bukkit.createInventory(null, 9*height, Component.text(name));
        this.clickableItems = new HashMap<>();
        this.itemSlots = new HashMap<>();
        this.closeEvent = null;
        this.moveEvent = null;
        this.openEvent = null;
        this.usage = InventoryUsage.UTILITY;
        handlers.put(player.api().getUUID(), this);
    }

    public InventoryBuilder setUsage(InventoryUsage usage){
        this.usage = usage;
        return this;
    }

    public InventoryBuilder setItem(ItemBuilder builder, int slot){
        if (builder.build() == null)return this;
        this.inventory.setItem(slot, builder.build());
        this.itemSlots.put(slot, builder);
        return this;
    }

    public InventoryBuilder setItem(ItemBuilder builder, int slot, Consumer<InventoryClickEvent> event){
        if (builder.build() == null)return this;
        this.inventory.setItem(slot, builder.build());
        this.itemSlots.put(slot, builder);
        this.clickableItems.put(builder.build(), event);
        return this;
    }

    public InventoryBuilder addItem(ItemBuilder builder){
        if (builder.build() == null)return this;
        this.inventory.addItem(builder.build());
        this.itemSlots.put(inventory.first(builder.build()), builder);
        return this;
    }

    public InventoryBuilder addItem(ItemBuilder builder, Consumer<InventoryClickEvent> event){
        if (builder.build() == null)return this;
        this.inventory.addItem(builder.build());
        this.itemSlots.put(inventory.first(builder.build()), builder);
        this.clickableItems.put(builder.build(), event);
        return this;
    }

    public void onClick(InventoryClickEvent event){
        if (!clickableItems.containsKey(event.getCurrentItem()))return;
        this.clickableItems.get(event.getCurrentItem()).accept(event);
    }

    public InventoryBuilder fillEmpty(){
        List<Integer> empties = new ArrayList<>();
        for (int i = 0; i<inventory.getSize(); i++){
            if (inventory.getItem(i) == null) empties.add(i);
        }
        empties.forEach(integer -> {
            setItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, player).setName(null), integer);
        });
        return this;
    }

    public InventoryBuilder fill(ItemBuilder itemStack, int start, int end){
        for(int i = start; i<=end;i++){
            setItem(itemStack, i, event -> event.setCancelled(true));
        }
        return this;
    }

    public InventoryBuilder fillPlaceholder(int start, int end){
        fill(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, player).setName(null), start, end);
        return this;
    }

    public InventoryBuilder fillPlaceholder(int... slot) {
        for (int i : slot) {
            setItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, player).setName(null), i,event -> {
                DefaultSounds.pling.play(player);
            });
        }
        return this;
    }

    public InventoryBuilder setOnOpen(Consumer<InventoryOpenEvent> event){
        this.openEvent = event;
        return this;
    }

    public InventoryBuilder setOnMove(Consumer<InventoryMoveItemEvent> event){
        this.moveEvent = event;
        return this;
    }

    public InventoryBuilder setOnClose(Consumer<InventoryCloseEvent> event){
        this.closeEvent = event;
        return this;
    }

    public boolean isOpened(){
        if (!player.getPlayer().isOnline())return false;
        if (player.getPlayer() == null)return false;
        return player.getPlayer().getOpenInventory().getTopInventory() == this.inventory;
    }

    public void onOpen(InventoryOpenEvent event){
        handlers.put(player.api().getUUID(), this);
        if (this.openEvent != null)this.openEvent.accept(event);
    }

    public void onMove(InventoryMoveItemEvent event){
        if (this.moveEvent == null)return;
        this.moveEvent.accept(event);
    }

    public void onClose(InventoryCloseEvent event){
        if (!isOpened())return;
        if (closeEvent != null)this.closeEvent.accept(event);
        handlers.remove(player.api().getUUID(), this);
    }

    public void open(){
        if (!player.getPlayer().isOnline())return;
        if (usage == InventoryUsage.UTILITY)moveEvent = event ->{
            getMoveEvent().accept(event);
            event.setCancelled(true);
        };

        player.getPlayer().openInventory(inventory);
    }

}
