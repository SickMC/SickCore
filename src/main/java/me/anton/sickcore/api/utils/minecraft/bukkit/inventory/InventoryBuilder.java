package me.anton.sickcore.api.utils.minecraft.bukkit.inventory;

import lombok.Getter;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Consumer;

public class InventoryBuilder implements Listener {
    @Getter
    private static HashMap<UUID, InventoryBuilder> handlers = new HashMap<>();
    private final IBukkitPlayer apiPlayer;
    @Getter
    private Inventory inventory;
    private Map<ItemStack, Consumer<InventoryClickEvent>> items;
    private Map<Integer, ItemStack> itemSlots;
    private Consumer<InventoryCloseEvent> onClose;
    private Consumer<InventoryMoveItemEvent> onMoveItem;
    private InventoryUsage usage;
    private boolean mayClick;
    private List<Integer> allowedClickSlots;
    private String name;
    private Consumer<InventoryOpenEvent> onOpen;

    public InventoryBuilder(IAPIPlayer apiPlayer, String title, int size, InventoryUsage usage){
        this.name = title;
        this.apiPlayer = apiPlayer.bukkit();
        this.allowedClickSlots = new ArrayList<>();
        this.usage = usage;
        inventory = Bukkit.createInventory(null, size, Component.text(title));
        items = new HashMap<>();
        itemSlots = new HashMap<>();
        mayClick = usage != InventoryUsage.PLAYER;
        handlers.put(apiPlayer.getUUID(), this);
    }

    public InventoryBuilder(IAPIPlayer apiPlayer, String title, InventoryType type){
        this.name = title;
        this.apiPlayer = apiPlayer.bukkit();
        this.allowedClickSlots = new ArrayList<>();
        inventory = Bukkit.createInventory(null, type, Component.text(title));
        items = new HashMap<>();
        itemSlots = new HashMap<>();
        mayClick = usage != InventoryUsage.PLAYER;
        handlers.put(apiPlayer.getUUID(), this);
    }

    public void fillEmpty(){
        List<Integer> empties = new ArrayList<>();
        for (int i = 0; i<inventory.getSize(); i++){
            if (inventory.getItem(i) == null) empties.add(i);
        }
        empties.forEach(integer -> {
            setItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(null).build(), integer);
        });
    }

    public void fill(ItemStack itemStack, int start, int end){
        for(int i = start; i<=end;i++){
            setItem(itemStack, i, event -> event.setCancelled(true));
        }
    }

    public void fillPlaceholder(int start, int end){
        fill(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(null).build(), start, end);
    }

    public void fillPlaceholder(int... slot) {
        for (int i : slot) {
            setItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(null).build(), i, event -> event.setCancelled(true));
        }
    }

    public void onClose(InventoryCloseEvent event){
        if(onClose == null) return;
        onClose.accept(event);
        handlers.remove(apiPlayer.api().getUUID(), this);
    }

    public void onMove(InventoryMoveItemEvent event){
        if(onMoveItem == null) return;
        onMoveItem.accept(event);
    }

    public void onClick(InventoryClickEvent event){
        try {
            if (mayClick && !allowedClickSlots.contains(event.getSlot())) event.setCancelled(true);
            if (!items.containsKey(event.getCurrentItem())) return;
            items.get(event.getCurrentItem()).accept(event);
            if (usage == InventoryUsage.UTILITY) event.getWhoClicked().playSound(Sound.sound(Key.key("minecraft:block.note_block.pling"), Sound.Source.BLOCK, 2, 1));
        }catch (NullPointerException e){
            if (mayClick && !allowedClickSlots.contains(event.getSlot())) event.setCancelled(true);
            if (!itemSlots.containsKey(event.getSlot())) return;
            items.get(itemSlots.get(event.getSlot())).accept(event);
            if (usage == InventoryUsage.UTILITY) event.getWhoClicked().playSound(Sound.sound(Key.key("minecraft:block.note_block.pling"), Sound.Source.BLOCK, 2, 1));
        }
    }

    public boolean isOpen(IBukkitPlayer apiPlayer){
        if(!apiPlayer.getPlayer().isOnline()) return false;
        if(apiPlayer.getPlayer() == null) return false;
        if(apiPlayer.getPlayer().getOpenInventory().getTopInventory() == null) return false;
        return apiPlayer.getPlayer().getOpenInventory().getTopInventory() == this.inventory;
    }

    public void onOpen(InventoryOpenEvent event){
        handlers.put(apiPlayer.api().getUUID(), this);
    }

    public void setItem(ItemStack itemStack, int slot, Consumer<InventoryClickEvent> onClick){
        if(itemStack == null) return;
        inventory.setItem(slot, itemStack);
        items.put(itemStack, onClick);
        itemSlots.put(slot, itemStack);
    }

    public void setItem(ItemStack itemStack, int slot){
        if(itemStack == null) return;
        inventory.setItem(slot, itemStack);
        itemSlots.put(slot, itemStack);
    }


    public void addItem(ItemStack itemStack, Consumer<InventoryClickEvent> onClick){
        if(itemStack == null) return;
        inventory.addItem(itemStack);
        items.put(itemStack, onClick);
    }


    public void open() {
        if(this.onOpen != null) this.onOpen.accept(null);
        if (!apiPlayer.getPlayer().isOnline()) return;
        apiPlayer.getPlayer().openInventory(this.inventory);
    }
}
