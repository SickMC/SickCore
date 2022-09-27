package net.sickmc.sickcore.api.fabric.extensions

import net.sickmc.sickapi.SickPlayer
import net.sickmc.sickapi.rank.parent
import net.sickmc.sickcore.api.fabric.server
import net.silkmc.silk.core.text.literalText

val SickPlayer.displayName
    get() = literalText(
        server.playerList.getPlayer(this.uuid)?.name?.string ?: error("requesting displayname of null player")
    ) { color = currentRank.parent.color.fallbackColor }