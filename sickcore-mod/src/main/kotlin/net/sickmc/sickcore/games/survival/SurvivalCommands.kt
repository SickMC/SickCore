package net.sickmc.sickcore.games.survival

import com.mojang.brigadier.context.CommandContext
import net.axay.fabrik.commands.LiteralCommandBuilder
import net.axay.fabrik.commands.command
import net.axay.fabrik.core.text.broadcastText
import net.axay.fabrik.core.text.literalText
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.arguments.GameProfileArgument
import net.minecraft.network.chat.ChatType
import net.minecraft.network.chat.Component
import net.sickmc.sickcore.utils.Colors
import net.sickmc.sickcore.utils.fabric.sendMessage
import net.sickmc.sickcore.utils.fabric.sickPlayer
import net.sickmc.sickcore.utils.fabric.toPrettyLocationStringWithWorld
import net.sickmc.sickcore.utils.fabric.toPrettyString

object SurvivalCommands {
    fun register() {
        val sharePosAliases = arrayListOf("sp", "sharepos")
        sharePosAliases.forEach {
            command(it){
                sharePos()
            }
        }
        heads
    }

    private fun LiteralCommandBuilder<CommandSourceStack>.sharePos(){
        argument("targetName"){ targetName ->
            runs {
                sharePos(targetName())
            }
        }
        runs { sharePos() }
    }

    private fun CommandContext<CommandSourceStack>.sharePos(targetName: String? = null){
        val player = source.playerOrException
        val message = player.sickPlayer?.displayName?.getName()?.copy()
            ?.append(literalText("'s location: ") {
                color = Colors.LIGHT_GRAY
                bold = false
                text(player.toPrettyLocationStringWithWorld()) {
                    color = Colors.GOLD
                    bold = false
                }

            })
        if (targetName == null){
            source.server.playerList.broadcastSystemMessage(message!!, ChatType.CHAT)
            return
        }
        val target = source.server.playerList.getPlayerByName(targetName)
        if (target == null){
            this.source.sendSuccess(literalText("This player cannot be found!") {
                color = Colors.DARK_RED
            }, false)
            return
        }

        target.sendMessage(message!!)
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

