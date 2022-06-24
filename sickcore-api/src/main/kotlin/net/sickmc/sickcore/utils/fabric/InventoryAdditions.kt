package net.sickmc.sickcore.utils.fabric

import net.minecraft.world.Container
import net.minecraft.world.item.ItemStack

fun Container.canAddItem(stack: ItemStack): Boolean {
    if (this.isEmpty) return true
    for (i in 0..this.containerSize) {
        if (this.getItem(i).isEmpty) return true
        if (this.getItem(i).isStackable && this.getItem(i).item == stack.item && this.getItem(i).count + stack.count < this.getItem(
                i
            ).maxStackSize
        ) return true
    }
    return false
}