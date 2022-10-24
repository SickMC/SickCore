package net.sickmc.sickcore.api.fabric.extensions

import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer
import net.sickmc.sickapi.SickPlayer
import net.sickmc.sickapi.playerCache
import net.sickmc.sickapi.rank.parent
import net.sickmc.sickcore.api.fabric.server
import net.silkmc.silk.core.text.literalText

val SickPlayer.displayName: MutableComponent?
    get() {
        val player = server.playerList.getPlayer(this.uuid) ?: return null
        return literalText(
            player.name.string
        ) { color = currentRank.parent.color.fallbackColor }
    }

suspend fun ServerPlayer.sickPlayer(): SickPlayer? = playerCache.get(this.uuid)