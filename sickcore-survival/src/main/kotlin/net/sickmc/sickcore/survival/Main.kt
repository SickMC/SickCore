package net.sickmc.sickcore.survival

import kotlinx.coroutines.launch
import net.minecraft.server.level.ServerPlayer
import net.sickmc.sickapi.playerCache
import net.sickmc.sickapi.rank.parent
import net.sickmc.sickapi.util.Colors
import net.sickmc.sickcore.api.fabric.extensions.displayName
import net.sickmc.sickcore.api.fabric.modScope
import net.sickmc.sickcore.api.fabric.server
import net.sickmc.sickcore.api.fabric.tablist.tablist
import net.silkmc.silk.core.annotations.InternalSilkApi
import net.silkmc.silk.core.event.Events
import net.silkmc.silk.core.event.Player
import net.silkmc.silk.core.text.broadcastText
import net.silkmc.silk.core.text.literalText

@OptIn(InternalSilkApi::class)
@Suppress("unused")
fun init() {
    SurvivalMixinEntrypoint.init()
    val tablist = tablist {
        generateName {
            val sickPlayer = playerCache.get(this.uuid) ?: return@generateName this.displayName to "0"
            sickPlayer.displayName to sickPlayer.currentRank.parent.
        }
    }
}

object SurvivalMixinEntrypoint {

    fun init() {
        joinListener
    }

    private val joinListener = Events.Player.postLogin.listen { event ->
        modScope.launch {
            var sickPlayer = playerCache.get(event.player.uuid)
            if (sickPlayer != null) server.broadcastText(sickPlayer.displayName.append(literalText(" joined the server!") {
                color = Colors.gray
            }))
            else {
                while (playerCache.cache.containsKey(event.player.uuid)) {
                    sickPlayer = playerCache.get(event.player.uuid)
                    server.broadcastText(sickPlayer!!.displayName.append(literalText(" joined the server!") {
                        color = Colors.gray
                    }))
                    return@launch
                }
            }
        }
    }

    fun onQuit(player: ServerPlayer) {
        modScope.launch {
            val sickPlayer = playerCache.get(player.uuid) ?: return@launch
            server.broadcastText(sickPlayer.displayName.append(literalText(" quit the server!") {
                color = Colors.gray
            }))
        }
    }
}