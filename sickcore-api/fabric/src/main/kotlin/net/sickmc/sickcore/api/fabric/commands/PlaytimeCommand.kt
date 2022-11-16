package net.sickmc.sickcore.api.fabric.commands

import kotlinx.coroutines.sync.withLock
import net.minecraft.commands.arguments.GameProfileArgument
import net.sickmc.sickapi.SickPlayer
import net.sickmc.sickapi.util.Colors
import net.sickmc.sickapi.util.players
import net.sickmc.sickcore.api.fabric.FabricEntrypoint
import net.sickmc.sickcore.api.fabric.extensions.sickPlayer
import net.silkmc.silk.commands.command
import net.silkmc.silk.core.text.literalText
import org.litote.kmongo.eq
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

val playtimeCommand = command("playtime") {
    argument("player", GameProfileArgument.gameProfile()) { targetPl ->
        runsAsync {
            val player = source.playerOrException
            val target = players.findOne(SickPlayer::uuidString eq targetPl().getNames(source).first().id.toString())
            if (target == null) {
                player.sendSystemMessage(literalText("This player cannot be found!") { color = Colors.darkRed }, true)
                return@runsAsync
            }
            val playtime = target.playtime.milliseconds + FabricEntrypoint.playTimeMutex.withLock {
                FabricEntrypoint.playerPlayTimes[target.uuid]?.milliseconds ?: Duration.ZERO
            }
            player.sendSystemMessage(literalText(playtime.toString()) { color = 0xF99147 })
        }
    }

    runsAsync {
        val player = source.playerOrException
        player.sendSystemMessage(literalText(player.sickPlayer()?.playtime?.milliseconds.toString()) {
            color = 0xF99147
        })
    }
}