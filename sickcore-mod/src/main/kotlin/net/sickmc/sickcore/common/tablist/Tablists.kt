package net.sickmc.sickcore.common

import net.sickmc.sickcore.utils.Colors
import net.sickmc.sickcore.utils.fabric.Tablist
import net.sickmc.sickcore.utils.fabric.playerTabListBuilder
import net.sickmc.sickcore.utils.fabric.sickPlayer
import net.silkmc.silk.core.text.literalText

val defaultTablist = Tablist() {
    playerTabListBuilder(this) {
        val sickPlayer = player.sickPlayer!!
        prefix = literalText(sickPlayer.rank.getParent().prefix)
        color = sickPlayer.rank.getParent().color
        header = literalText("headdderrrr") { color = Colors.BLACK }
        footer = literalText("fooooterrr") { color = Colors.WHITE }
    }
}