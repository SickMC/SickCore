package net.sickmc.sickcore.common.chat

import net.sickmc.sickcore.utils.Colors
import net.sickmc.sickcore.utils.fabric.sickPlayer
import net.silkmc.silk.core.text.literalText
import java.util.*

object ChatPresets {
    val styledNames = chatController {
        getAttackerNameEvent = {
            val sickPlayer = this.player.sickPlayer
            sickPlayer?.displayName?.getName()!!
        }
        deathNameEvent = {
            val sickPlayer = this.player.sickPlayer
            sickPlayer?.displayName?.getName()!!
        }
        advancementEvent = Optional.of {
            val sickPlayer = this.player.sickPlayer
            sickPlayer?.displayName?.getName()!!
        }
        joinMessage = JoinMessagePresets.DEFAULT_WITH_DISPLAYNAME
        quitMessage = Optional.of {
            val sickPlayer = this.player.sickPlayer
            sickPlayer?.displayName?.getName()!!.append(literalText("quit the server") { color = Colors.WHITE })
        }
        chatEvent = Optional.of {
            val sickPlayer = this.player.sickPlayer
            sickPlayer?.displayName?.getName()!!.append(literalText(" ") {
                color = Colors.WHITE
                text(message) { color = Colors.LIGHT_GRAY }
            })
        }
    }

    val nothing = chatController {
        deathEvent = Optional.empty()
        advancementEvent = Optional.empty()
        joinMessage = JoinMessagePresets.NO_MESSAGE
        quitMessage = Optional.empty()
        chatEvent = Optional.empty()
    }
}