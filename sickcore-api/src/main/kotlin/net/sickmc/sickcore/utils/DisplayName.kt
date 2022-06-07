package net.sickmc.sickcore.utils

import net.axay.fabrik.core.text.literalText
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import net.minecraft.network.chat.MutableComponent
import net.sickmc.sickcore.commonRanks.RankGroup

data class DisplayName(val parent: RankGroup, val rawName: String, val color: Int){

    fun getKyoriName(): Component{
        return Component.text(parent.prefix.uppercase()).decorate(TextDecoration.BOLD)
            .color(TextColor.color(parent.color)).append(Component.text(" $rawName"))
            .color(TextColor.color(parent.color))
    }

    fun getName(): MutableComponent{
        return literalText(parent.prefix.uppercase()) {
            bold = true
            color = parent.color
            text(" $rawName") {
                color = parent.color
                bold = false
            }
        }
    }

}
