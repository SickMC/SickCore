package net.sickmc.sickcore.utils.paper.gui

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

data class GUIItem(val slot: Int, val item: ItemStack, val action: ClickAction?) {}

abstract class ClickAction(item: GUIItem){

    abstract fun perform(e: InventoryClickEvent)

}

class ItemSwitcher(item: GUIItem, val toItem: GUIItem): ClickAction(item){

    override fun perform(e: InventoryClickEvent) {
        e.inventory.setItem(e.slot, toItem.item)
    }

}

class CommonClick(item: GUIItem, val event: InventoryClickEvent.() -> Unit): ClickAction(item){

    override fun perform(e: InventoryClickEvent) {
        event.invoke(e)
    }

}

class GUIPageSwitcher(item: GUIItem, val toInv: GUIPage): ClickAction(item){

    override fun perform(e: InventoryClickEvent) {
        toInv.open(e.inventory)
    }

}