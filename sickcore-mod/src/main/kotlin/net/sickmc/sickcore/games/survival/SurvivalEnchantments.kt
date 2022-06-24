package net.sickmc.sickcore.games.survival

import net.minecraft.network.chat.Component
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.item.enchantment.EnchantmentCategory
import net.silkmc.silk.core.text.literalText

class Telekinesis(category: EnchantmentCategory) : Enchantment(Rarity.RARE, category, arrayOf(EquipmentSlot.MAINHAND)) {

    override fun getMinLevel(): Int {
        return 1
    }

    override fun getMaxLevel(): Int {
        return 1
    }

    override fun getMinCost(level: Int): Int {
        return 30
    }

    override fun getMaxCost(level: Int): Int {
        return 30
    }

    override fun getFullname(level: Int): Component {
        return literalText("Telekinesis"){
            color = 0x876212
        }
    }

    override fun isTradeable(): Boolean {
        return true
    }

    override fun isDiscoverable(): Boolean {
        return true
    }
}