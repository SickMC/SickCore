package me.anton.sickcore.api.utils.minecraft.bukkit.inventory;

import lombok.Getter;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

@Getter
public class PagedInventoryItem {

    public ItemBuilder builder;
    public int importance;
    public Consumer<InventoryClickEvent> clickEvent;

    public PagedInventoryItem(ItemBuilder builder, int importance, Consumer<InventoryClickEvent> eventConsumer){
        this.builder = builder;
        this.importance = importance;
        this.clickEvent = eventConsumer;
    }

}
