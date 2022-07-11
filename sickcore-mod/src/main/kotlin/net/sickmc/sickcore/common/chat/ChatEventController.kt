package net.sickmc.sickcore.common.chat

import kotlinx.coroutines.launch
import net.minecraft.network.chat.ChatType
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer
import net.sickmc.sickcore.server
import net.sickmc.sickcore.utils.Colors
import net.sickmc.sickcore.utils.fabric.sickPlayer
import net.sickmc.sickcore.utils.mongo.databaseScope
import net.silkmc.silk.core.text.literalText
import java.util.Optional

class ChatController {

    var quitMessage: Optional<QuitEvent.() -> MutableComponent>? = null
    var defaultJoinMessage: Optional<JoinEvent.() -> MutableComponent>? = null
    var joinMessage: JoinMessagePresets = JoinMessagePresets.NONE
    var deathEvent: Optional<DeathEvent.() -> MutableComponent>? = null
    var getAttackerNameEvent: (GetAttackerNameEvent.() -> MutableComponent)? = null
    var chatEvent: Optional<ChatEvent.() -> MutableComponent>? = null
    var advancementEvent: Optional<AdvancementEvent.() -> MutableComponent>? = null
    var deathNameEvent: (DeathNameEvent.() -> MutableComponent)? = null

}

inline fun chatController(crossinline controller: ChatController.() -> Unit): ChatController {
    return ChatController().apply(controller)
}

enum class JoinMessagePresets {
    NO_MESSAGE,
    NONE,
    DEFAULT_WITH_DISPLAYNAME
}

object JoinMessageController {

    fun handle(preset: JoinMessagePresets, player: ServerPlayer) {
        when (preset) {
            JoinMessagePresets.NO_MESSAGE -> {}
            JoinMessagePresets.DEFAULT_WITH_DISPLAYNAME -> {
                databaseScope.launch {
                    val sickPlayer = player.sickPlayer!!
                    server.playerList.broadcastSystemMessage(
                        sickPlayer.displayName.getName()
                            .append(literalText("joined the server!") { color = Colors.WHITE }), ChatType.SYSTEM
                    )
                }
            }
            else -> {}
        }
    }

}