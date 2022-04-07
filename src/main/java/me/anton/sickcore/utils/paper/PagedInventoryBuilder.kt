package me.anton.sickcore.utils.paper

import me.anton.sickcore.core.player.SickPlayer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent

class PagedInventoryBuilder(val player: SickPlayer, val name: Component) {

    val items = HashMap<ItemBuilder, Int>()
    val interactions = HashMap<ItemBuilder, (InventoryClickEvent) -> Unit>()
    var pages = 1

    fun addItem(item: ItemBuilder, priority: Int, interaction: (InventoryClickEvent) -> Unit){
        items[item] = priority
        interactions[item] = interaction
    }

    fun open(page: Int){
        val sorted = items.entries.sortedByDescending { it.value }.map { it.key }
        val pagedItems = ArrayList<ArrayList<ItemBuilder>>()

        pages = (sorted.size / 25) + 1

        var count = 1
        val itemsToAdd = ArrayList<ItemBuilder>()
        sorted.forEach {
            itemsToAdd.add(it)
            count++
            if (count == 25){
                pagedItems.add(itemsToAdd)
                itemsToAdd.clear()
                count = 1
            }
        }

        val builder = InventoryBuilder(player, 6, name)

        builder.setPlaceholder(2, 3, 4, 5, 6, 10, 16, 18, 26, 27, 35, 37, 43, 47, 48, 49, 50, 51)
        val purple = intArrayOf(0, 1, 9, 44, 52, 53)
        val pink = intArrayOf(7, 8, 17, 36, 45, 46)
        for (i in purple) builder.setItem(i, ItemBuilder(player, Material.PURPLE_STAINED_GLASS_PANE).setName(null)){event -> event.isCancelled = true}
        for (i in pink) builder.setItem(i, ItemBuilder(player, Material.MAGENTA_STAINED_GLASS_PANE).setName(null)){ event -> event.isCancelled = true}

        builder.setItem(49, ItemBuilder(player, Material.PAPER).setName(MiniMessage.miniMessage().deserialize("<gradient:#102FFB:#8555FD>Page $page / $pages </gradient>"))){event -> event.isCancelled = true}
        if (page == pages)builder.setPlaceholder(50)
        else builder.setItem(50, ItemBuilder(player, Material.PLAYER_HEAD).setSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDJiMGMwN2ZhMGU4OTIzN2Q2NzllMTMxMTZiNWFhNzVhZWJiMzRlOWM5NjhjNmJhZGIyNTFlMTI3YmRkNWIxIn19fQ==")
            .setName(MiniMessage.miniMessage().deserialize("<gradient:#102FFB:#8555FD>Next Page</gradient>"))
            .addLore(MiniMessage.miniMessage().deserialize("<gradient:#E84CFB:#A53990>Click to open the next page</gradient>"))){open(page + 1)}

        if (page == 1)builder.setPlaceholder(48)
        else builder.setItem(50, ItemBuilder(player, Material.PLAYER_HEAD).setSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDU5YmUxNTU3MjAxYzdmZjFhMGIzNjk2ZDE5ZWFiNDEwNDg4MGQ2YTljZGI0ZDVmYTIxYjZkYWE5ZGIyZDEifX19")
            .setName(MiniMessage.miniMessage().deserialize("<gradient:#102FFB:#8555FD>Previous Page</gradient>"))
            .addLore(MiniMessage.miniMessage().deserialize("<gradient:#E84CFB:#A53990>Click to open the previous page</gradient>"))){open(page - 1)}

        pagedItems[page - 1].forEach {
            builder.addItem(it, interactions[it]!!)
        }

        builder.open()
    }

}