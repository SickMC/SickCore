package net.sickmc.sickcore.utils.paper.gui

import net.axay.kspigot.runnables.task
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

abstract class PageChangeEffect{

    abstract fun perform(from: Inventory?, to: Inventory)

}

class VerticalSlideEffect: PageChangeEffect(){

    override fun perform(from: Inventory?, to: Inventory) {

    }

}

class VerticalSwipeEffect: PageChangeEffect(){

    override fun perform(from: Inventory?, to: Inventory) {

    }

}

class HorizontalSlideEffect: PageChangeEffect(){

    override fun perform(from: Inventory?, to: Inventory) {

    }

}

class HorizontalSwipeEffect: PageChangeEffect(){

    override fun perform(from: Inventory?, to: Inventory) {

    }

}

class InstantEffect: PageChangeEffect(){

    override fun perform(from: Inventory?, to: Inventory) {
        if (from?.holder == null || from.holder !is Player)return
        val player = from.holder as Player
        player.openInventory(to)
    }

}

class SwipeFromSidesEffect: PageChangeEffect(){

    override fun perform(from: Inventory?, to: Inventory) {
        if (from == null){
            val temporaryInv = Bukkit.createInventory(null, to.size)
            val rows = to.size / 9
            task(sync = true, period = 20, howOften = 5){

            }
        }
        if (from?.size != to.size)InstantEffect().perform(from, to)
        task(sync = true, period = 20, howOften = 5){

        }
    }

}