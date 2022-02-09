package me.anton.sickcore.api.utils.minecraft.bukkit.inventory;


import lombok.Getter;
import lombok.Setter;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.games.defaults.all.HeadDBAPI;
import me.anton.sickcore.api.utils.common.Logger;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.sound.DefaultSounds;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.function.Consumer;

@Getter
public class PagedInventoryBuilder {

    private final IBukkitPlayer apiPlayer;
    private final String name;
    private final HashMap<Integer, InventoryBuilder> handlers;
    private final HashMap<Integer, LinkedHashMap<ItemStack, Consumer<InventoryClickEvent>>> pagedItems;
    private final HashMap<Integer, LinkedHashMap<ItemStack, Consumer<InventoryClickEvent>>> itemStacks;
    private Consumer<InventoryBuilder> defaultItemsConsumer;
    private Consumer<InventoryBuilder> pagedInventoryConsumer;
    private int highestOrder;
    @Setter
    private boolean fillLastPage = false;
    private ItemStack filler;

    public PagedInventoryBuilder(IAPIPlayer apiPlayer, String name) {
        this.name = name;
        this.apiPlayer = apiPlayer.bukkit();
        this.handlers = new HashMap<>();
        this.pagedItems = new HashMap<>();
        this.itemStacks = new HashMap<>();
        this.highestOrder = 1;
        this.filler = new ItemBuilder(Material.BARRIER).setName("§7").build();
    }

    public int getPages() {
        return this.pagedItems.size();
    }

    public void addItem(ItemStack itemStack, int order, Consumer<InventoryClickEvent> consumer) {
        if (order > this.highestOrder) this.highestOrder = order;
        if (itemStack == null) return;
        Material material;
        try {
            material = itemStack.getType();
        } catch (Exception e) {
            Logger.error("Error while add item: " + itemStack.getType());
            e.printStackTrace();
            return;
        }
        if (material == Material.AIR) return;
        if (material == Material.CAVE_AIR) return;
        if (material == Material.VOID_AIR) return;
        LinkedHashMap<ItemStack, Consumer<InventoryClickEvent>> map = this.itemStacks.getOrDefault(order, new LinkedHashMap<>());
        map.put(itemStack, consumer);
        itemStacks.put(order, map);
    }

    public void addItem(ItemStack itemStack, Consumer<InventoryClickEvent> consumer) {
        addItem(itemStack, -1, consumer);
    }

    public void resort() {
        pagedItems.clear();
        int currentPageCount = 1;
        LinkedHashMap<ItemStack, Consumer<InventoryClickEvent>> currentPage = new LinkedHashMap<>();

        for (int i = 0; i <= highestOrder; i++) {
            if (!itemStacks.containsKey(i)) continue;
            for (ItemStack itemStack : itemStacks.get(i).keySet()) {
                if (currentPage.size() == (22)) {
                    pagedItems.put(currentPageCount, new LinkedHashMap<>(currentPage));
                    currentPageCount++;
                    currentPage.clear();
                    continue;
                }
                currentPage.put(itemStack, itemStacks.get(i).get(itemStack));
            }
        }
        if (this.itemStacks.containsKey(-1)) {
            for (ItemStack itemStack : itemStacks.get(-1).keySet()) {
                if (currentPage.size() == 22) {
                    pagedItems.put(currentPageCount, new LinkedHashMap<>(currentPage));
                    currentPageCount++;
                    currentPage.clear();
                    continue;
                }
                currentPage.put(itemStack, itemStacks.get(-1).get(itemStack));
            }
        }

        if (currentPageCount != 0 && ((currentPageCount < 22))) {
            pagedItems.put(currentPageCount, new LinkedHashMap<>(currentPage));
            currentPage.clear();
            return;
        }

        if (!pagedItems.containsKey(1)) {
            LinkedHashMap<ItemStack, Consumer<InventoryClickEvent>> map = new LinkedHashMap<>();
            map.put(new ItemBuilder(Material.BARRIER).setName((String) apiPlayer.api().languageObject("§4Nothing found", "§4Es wurde nichts gefunden!")).build(), event -> event.setCancelled(true));
            pagedItems.put(1, map);
            return;
        }

        if (fillLastPage) {
            int fills = (22) - getItemStacks().get(getPages()).size();
            for (int i = 0; i < fills; i++) {
                getItemStacks().get(getPages()).put(filler, event -> event.setCancelled(true));
            }
        }
    }

    public boolean hasPage(int page) {
        return this.pagedItems.containsKey(page);
    }

    public void pagedInventoryItems(Consumer<InventoryBuilder> consumer) {
        this.pagedInventoryConsumer = consumer;
    }

    public void open(int page) {
        InventoryBuilder inventoryHandler = handlers.getOrDefault(page, new InventoryBuilder(apiPlayer.api(), name, 6 * 9, InventoryUsage.UTILITY));

        ItemStack headback = HeadDBAPI.getApi().getItemHead("7788");
        ItemStack headforwa = HeadDBAPI.getApi().getItemHead("7787");

        inventoryHandler.fillPlaceholder(0,8,45,53,1,7,9,17,36,44,46,52,2,3,5,6,47,51);

        inventoryHandler.setItem(new ItemBuilder(Material.PAPER).setName((String) apiPlayer.api().languageObject("§7Page §6" + page + "§7/§6" + pagedItems.size(),"§7Seite §6" + page + "§7/§6" + pagedItems.size())).build(), 49, event -> event.setCancelled(true));

        inventoryHandler.setItem(new ItemBuilder(headback).setName((String) apiPlayer.api().languageObject("§7Backward","§7Zurück")).build(), 48, event -> {
            if (!hasPage(page - 1)) {
                DefaultSounds.anvil.play(apiPlayer);
                return;
            }
            open(page - 1);
        });
        inventoryHandler.setItem(new ItemBuilder(headforwa).setName((String) apiPlayer.api().languageObject("§7Forward","§7Vor")).build(), 50, event -> {
            if (!hasPage(page + 1)) {
                DefaultSounds.anvil.play(apiPlayer);
                return;
            }
            open(page + 1);
        });

        int slot = 10;
        for (ItemStack itemStack : this.pagedItems.get(page).keySet()) {
            if (slot == 16) slot = 19;
            if (slot == 25) slot = 28;
            if (slot == 34) slot = 37;
            if (slot == 43) slot = 46;
            Consumer<InventoryClickEvent> consumer = this.pagedItems.get(page).get(itemStack);
            inventoryHandler.setItem(itemStack, slot, consumer);
            slot++;
        }


        inventoryHandler.open();
    }
}