package me.anton.sickcore.api.utils.minecraft.bukkit.inventory;


import lombok.Getter;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.games.all.HeadDBAPI;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Getter
public class PagedInventoryBuilder {

    private final BukkitPlayer player;
    private final String name;
    private List<PagedInventoryItem> items;
    private List<List<PagedInventoryItem>> pagedItems;
    private int pages;

    public PagedInventoryBuilder(BukkitPlayer player, String name){
        this.player = player;
        this.name = name;
        this.items = new ArrayList<>();
        this.pagedItems = new ArrayList<>();
    }

    public void addItem(ItemBuilder item){
        items.add(new PagedInventoryItem(item, 1, event -> event.setCancelled(true)));
    }

    public void addItem(ItemBuilder item, Consumer<InventoryClickEvent> event){
        items.add(new PagedInventoryItem(item, 1, event));
    }

    public void addItem(ItemBuilder item, int importance){
        items.add(new PagedInventoryItem(item, importance, event -> event.setCancelled(true)));
    }

    public void addItem(ItemBuilder item, int importance, Consumer<InventoryClickEvent> event){
        items.add(new PagedInventoryItem(item, importance, event));
    }

    public void sort(){
        pagedItems.clear();
        List<PagedInventoryItem> sorted = items.stream().sorted(Comparator.comparing(PagedInventoryItem::getImportance)).collect(Collectors.toList());

        pages = (sorted.size() / 25) + 1;

        if (pages == 1){
            List<PagedInventoryItem> itemsToAdd = new ArrayList<>(sorted);
            pagedItems.add(itemsToAdd);

            return;
        }
        
        for (int i = 0; i < pages; i++) {
            List<PagedInventoryItem> pagedItemsToadd = new ArrayList<>();

            for (int slot = 0; slot <= 24; slot++) {
                if (slot == 24)break;
                if (slot >= sorted.size())break;
                pagedItemsToadd.add(sorted.get(slot));
                sorted.remove(slot);
            }

            pagedItems.add(pagedItemsToadd);
        }
    }

    public void open(int i){
        sort();

        HeadDatabaseAPI heads = HeadDBAPI.getApi();

        if (i == 1){
            InventoryBuilder builder = new InventoryBuilder(player, name, 6);
            builder.fillPlaceholder(2,3,4,5,6,10,16,18, 26, 27, 35,37,43,47,48,49,50,51);
            int[] purple = {0, 1, 9, 44, 52,53};
            int[] pink = {7,8,17, 36,45,46};
            for (int prasd : purple) builder.setItem(new ItemBuilder(Material.PURPLE_STAINED_GLASS_PANE, player).setName(null), prasd);
            for (int prasd : pink) builder.setItem(new ItemBuilder(Material.MAGENTA_STAINED_GLASS_PANE, player).setName(null), prasd);

            for (PagedInventoryItem item : pagedItems.get(0)) {
                builder.addItem(item.getBuilder(), item.getClickEvent());
            }

            if (pages != 1){
                ItemBuilder nextPage = new ItemBuilder(heads.getItemHead("9979"), player)
                        .setName((String) player.api().languageObject("§6Next page", "§6Nächste Seite"))
                        .setLore((String) player.api().languageObject("§7Click to open the next page!", "§7Klicke um die nächste Seite zu öffnen!"));

                ItemBuilder pageItem = new ItemBuilder(Material.PAPER, player).setName((String) player.api().languageObject("§7Page: §6" + i + "§7/§6" + pages, "§7Seite: §6" + i + "§7/§6" + pages));

                builder.setItem(nextPage, 50,event -> open(i + 1));
                builder.setItem(pageItem, 49, event -> event.setCancelled(true));
            }

            builder.setOnMove(event -> event.setCancelled(true));
            builder.open();
            return;
        }

        ItemBuilder nextPage = new ItemBuilder(heads.getItemHead("9979"), player)
                .setName((String) player.api().languageObject("§6Next page", "§6Nächste Seite"))
                .setLore((String) player.api().languageObject("§7Click to open the next page!", "§7Klicke um die nächste Seite zu öffnen!"));
        ItemBuilder previousPage = new ItemBuilder(heads.getItemHead("9982"), player)
                .setName((String) player.api().languageObject("§6Previous page", "§6Vorherige Seite"))
                .setLore((String) player.api().languageObject("§7Click to open the previous page!", "§7Klicke um die vorherige Seite zu öffnen!"));
        ItemBuilder pageItem = new ItemBuilder(Material.PAPER, player).setName((String) player.api().languageObject("§7Page: §6" + i + "§7/§6" + pages, "§7Seite: §6" + i + "§7/§6" + pages));

        InventoryBuilder builder = new InventoryBuilder(player, name, 6);
        builder.fillPlaceholder(2,3,4,5,6,10,16,18, 26, 27, 35,37,43,47,48,49,50,51);
        int[] purple = {0, 1, 9, 44, 52,53};
        int[] pink = {7,8,17, 36,45,46};
        for (int prasd : purple) builder.setItem(new ItemBuilder(Material.PURPLE_STAINED_GLASS_PANE, player).setName(null), prasd);
        for (int prasd : pink) builder.setItem(new ItemBuilder(Material.MAGENTA_STAINED_GLASS_PANE, player).setName(null), prasd);

        if (i == pages)builder.fillPlaceholder(50);
        else builder.setItem(nextPage, 50,event -> open(i + 1));
        builder.setItem(previousPage, 48,event -> open(i - 1));
        builder.setItem(pageItem, 49, event -> event.setCancelled(true));

        for (PagedInventoryItem item : pagedItems.get(i - 1)) {
            builder.addItem(item.getBuilder(), item.getClickEvent());
        }

        builder.setOnMove(event -> event.setCancelled(true));
        builder.open();
    }

    public void open(){
        open(1);
    }
}