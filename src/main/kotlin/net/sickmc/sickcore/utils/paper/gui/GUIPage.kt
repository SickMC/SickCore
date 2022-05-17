package net.sickmc.sickcore.utils.paper.gui

import net.kyori.adventure.text.Component
import net.sickmc.sickcore.utils.paper.inventory.ItemBuilder
import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory

class GUIPage(
    val gui: GUI,
    var page: Int,
    height: Int,
    var items: ArrayList<GUIItem>,
    var name: Component? = null,
    var transition: PageChangeEffect = InstantEffect(),
    var closeEvent: (InventoryCloseEvent.() -> Unit)? = null,
    var dragAllowed: Boolean = false,
    var moveItemAllowed: Boolean = false,
    var dropAllowed: Boolean = false
) {

    val inventory = if (name == null) Bukkit.createInventory(null, height * 9) else Bukkit.createInventory(null, page, name!!)

    fun item(slot: Int, item: ItemBuilder, effect: ClickAction){
        items.add(GUIItem(slot, item, effect))
    }

    fun closeEvent(event: InventoryCloseEvent.() -> Unit){
        closeEvent = event
    }

    fun fill() {
        items.forEach {
            inventory.setItem(it.slot, it.item)
        }
    }

    fun open(previous: Inventory?) {
        transition.perform(previous, inventory)
    }

    fun finish(){
        gui.pages[page] = this
    }

}