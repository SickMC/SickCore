package net.sickmc.sickcore.appereance

import io.ktor.websocket.*
import net.sickmc.sickcore.minecraftServer
import net.sickmc.sickcore.utils.Colors
import net.sickmc.sickcore.utils.fabric.sendMessage
import net.sickmc.sickcore.utils.toUUID
import net.sickmc.sickcore.utils.websockets.listen
import net.sickmc.sickcore.utils.websockets.listenChannel
import net.silkmc.silk.core.text.literalText

object DiscordRewards {

    //Pattern reward/discord/uuid/message
    suspend fun listener() {
        listen {
            for (frame in incoming) {
                when (frame) {
                    is Frame.Text -> {
                        val components = frame.readText().split("/")
                        if (components[0] != "discord") continue
                        val uuid = components[1].toUUID()
                        val message = components[2]
                        minecraftServer?.playerList?.getPlayer(uuid)
                            ?.sendMessage(literalText(message) { color = Colors.WHITE })
                    }
                    else -> {}
                }
            }
        }
    }

}