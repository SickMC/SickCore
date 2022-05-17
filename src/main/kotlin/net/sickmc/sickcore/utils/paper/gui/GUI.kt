package net.sickmc.sickcore.utils.paper.gui

import net.axay.kspigot.event.listen
import net.kyori.adventure.text.Component
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryMoveItemEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class GUI(val height: Int, val title: Component?, val pages: HashMap<Int, GUIPage>, private val defaultPage: Int) {

    companion object{

        val guis = arrayListOf<GUI>()

        fun registerHandlers(){
            listen<InventoryCloseEvent> {
                guis.forEach { gui ->
                    gui.pages.values.filter { page -> it.inventory == page.inventory }.forEach{ page ->
                        page.closeEvent?.invoke(it)
                    }
                }
            }
            listen<InventoryClickEvent>{
                guis.forEach { gui ->
                    gui.pages.values.filter { page -> it.inventory == page.inventory }.forEach { page ->
                        page.items.filter { item -> item.slot == it.slot } .forEach { item ->
                            if (item.action != null)item.action.perform(it)
                            if (!page.dropAllowed)it.isCancelled = true
                        }
                    }
                }
            }
            listen<InventoryOpenEvent> {
                guis.forEach { gui ->
                    gui.pages.values.filter { page -> it.inventory == page.inventory }.forEach { page ->
                        page.transition.perform(null, page.inventory)
                    }
                }
            }
            listen<InventoryMoveItemEvent> {
                guis.forEach { gui ->
                    gui.pages.values.filter { page -> it.initiator == page.inventory }.forEach { page ->
                        if (!page.moveItemAllowed)it.isCancelled = true
                    }
                }
            }
            listen<InventoryDragEvent> {
                guis.forEach { gui ->
                    gui.pages.values.filter { page -> it.inventory == page.inventory }.forEach { page ->
                        if (!page.dragAllowed)it.isCancelled = true
                    }
                }
            }
        }

    }

    fun page(page: Int): GUIPage{
        return GUIPage(page = page, name = title, height = height, gui = this, items = arrayListOf())
    }

    fun open(page: Int = defaultPage){
        pages[page]?.open(null)
    }

}