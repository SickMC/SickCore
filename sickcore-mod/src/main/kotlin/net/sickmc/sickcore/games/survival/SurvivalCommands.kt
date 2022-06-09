package net.sickmc.sickcore.games.survival

import net.axay.fabrik.commands.command
import net.axay.fabrik.core.text.broadcastText
import net.axay.fabrik.core.text.literalText
import net.minecraft.network.chat.ChatType
import net.minecraft.network.chat.Component
import net.sickmc.sickcore.utils.Colors
import net.sickmc.sickcore.utils.fabric.sickPlayer
import net.sickmc.sickcore.utils.fabric.toPrettyString

object SurvivalCommands {
    fun register() {
        sharePos
        heads
    }

    val sharePos = command("sharepos") {
        runs {
            val player = source.playerOrException

            player.sickPlayer?.displayName?.getName()?.copy()
                ?.append(literalText("'s location: ") {
                    color = Colors.LIGHT_GRAY
                    text(player.position().toPrettyString()) {
                        color = Colors.GOLD
                    }
                } ?: Component.empty())?.let { this.source.server.playerList.broadcastSystemMessage(it, ChatType.CHAT) }
        }
    }

}

val heads = command("heads") {
    runs {
        if (!this.source.isPlayer) {
            this.source.sendSuccess(literalText("This is a player only command!") {
                color = Colors.DARK_RED
            }, false)
            return@runs
        }
        val player = this.source.player
        val survivalPlayer = SurvivalPlayers.instance.getCachedEntity(player!!.uuid)!!
        openHeadGUI(survivalPlayer)
    }
}

