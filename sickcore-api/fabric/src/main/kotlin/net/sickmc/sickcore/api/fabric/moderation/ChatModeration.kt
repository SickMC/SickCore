package net.sickmc.sickcore.api.fabric.moderation

import net.minecraft.commands.arguments.EntityArgument
import net.silkmc.silk.commands.PermissionLevel
import net.silkmc.silk.commands.command
import net.silkmc.silk.core.text.literalText
import java.util.*

object ChatModeration {

    fun init() {
        chatCommand
    }

    val mutedPlayers = arrayListOf<UUID>()
    private val chatCommand = command("chat") {
        requiresPermissionLevel(PermissionLevel.BAN_RIGHTS)
        literal("mute") {
            argument("target", EntityArgument.player()) { target ->
                runs {
                    val player = target().findSinglePlayer(source)
                    mutedPlayers += player.uuid
                    source.playerOrException.sendSystemMessage(literalText("${player.name} has been muted!"))
                    player.sendSystemMessage(literalText("You have been muted for the chat!") { color = 0xFF9C46 })
                }
            }
        }

        literal("unmute") {
            argument("target", EntityArgument.player()) { target ->
                runs {
                    val player = target().findSinglePlayer(source)
                    mutedPlayers -= player.uuid
                    source.playerOrException.sendSystemMessage(literalText("${player.name} has been unmuted!"))
                    player.sendSystemMessage(literalText("You have been unmuted from the chat!") { color = 0xFF9C46 })
                }
            }
        }
    }
}