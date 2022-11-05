package net.sickmc.sickcore.survival.commands

import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.server.level.ServerPlayer
import net.sickmc.sickcore.api.fabric.extensions.displayName
import net.sickmc.sickcore.api.fabric.extensions.sickPlayer
import net.silkmc.silk.commands.command
import net.silkmc.silk.core.text.broadcastText
import net.silkmc.silk.core.text.literalText
import kotlin.math.roundToLong

val sharePos = command("sharepos") {
    alias("sp")
    argument("target", EntityArgument.player()) { target ->
        runsAsync {
            val player = source.playerOrException
            val message = locationMessage(player)
            target().findSinglePlayer(source).sendSystemMessage(message)
            player.sendSystemMessage(message)
        }
    }
    runsAsync {
        source.server.broadcastText(locationMessage(source.playerOrException))
    }
}

private suspend fun locationMessage(player: ServerPlayer) =
    player.sickPlayer()?.displayName?.append(literalText("'s location: ") {
        color = 0xFF9C46
        text("${player.x.roundToLong()}, ${player.y.roundToLong()}, ${player.z.roundToLong()}  (${player.level.dimension().location().path})") {
            color = 0xFFD162
        }
    }) ?: literalText("no location wtf")