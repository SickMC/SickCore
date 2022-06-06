package net.sickmc.sickcore.utils

import net.axay.fabrik.core.text.literalText
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import net.minecraft.network.chat.MutableComponent
import net.sickmc.sickcore.commonRanks.RankGroup

data class DisplayName(val parent: RankGroup, val name: String, val color: Int){

    fun getKyoriName(): Component{
        return Component.text(parent.name.uppercase()).decorate(TextDecoration.BOLD)
            .color(TextColor.color(parent.color)).append(Component.text(" $name"))
            .color(TextColor.color(parent.color))
    }

    fun getName(): MutableComponent{
        return literalText(parent.name.uppercase()) {
            bold = true
            color = parent.color
            text(" $name") {
                color = parent.color
                bold = false
            }
        }
    }

}
