package net.sickmc.sickcore.api.fabric.commands

import net.minecraft.commands.arguments.EntityArgument
import net.sickmc.sickapi.SickPlayer
import net.sickmc.sickapi.util.Colors
import net.sickmc.sickapi.util.players
import net.silkmc.silk.commands.command
import net.silkmc.silk.core.text.literalText
import org.litote.kmongo.eq
import kotlin.time.Duration.Companion.milliseconds

val playtimeCommand = command("playtime") {
    argument("player", EntityArgument.player()) { targetPl ->
        runsAsync {
            val player = source.playerOrException
            val target = players.findOne(SickPlayer::uuidString eq targetPl().findSinglePlayer(source).stringUUID)
            if (target == null) {
                player.sendSystemMessage(literalText("This player cannot be found!") { color = Colors.darkRed }, true)
                return@runsAsync
            }
            player.sendSystemMessage(literalText(target.playtime.milliseconds.toString()) { color = 0xF99147 })
        }
    }

    runsAsync {
        val player = source.playerOrException
        player.sendSystemMessage(literalText(players.findOne(SickPlayer::uuidString eq player.stringUUID)?.playtime?.milliseconds.toString()) {
            color = 0xF99147
        })
    }
}