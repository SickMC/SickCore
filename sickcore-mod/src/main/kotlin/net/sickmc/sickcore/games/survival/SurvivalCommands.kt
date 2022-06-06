package net.sickmc.sickcore.games.survival

import com.mojang.brigadier.exceptions.CommandSyntaxException
import net.axay.fabrik.commands.command
import net.axay.fabrik.core.text.broadcastText
import net.axay.fabrik.core.text.literalText
import net.minecraft.server.level.ServerPlayer
import net.sickmc.sickcore.utils.Colors
import net.sickmc.sickcore.utils.fabric.sendMessage
import net.sickmc.sickcore.utils.mongo.players

object SurvivalCommands {
    fun register() {
        sharePos
        heads
    }

    val sharePos = command("sharepos") {
        argument<String>("targetName") { name ->
            runs {
                if (name() == "") {
                    if (!this.source.isPlayer) {
                        this.source.sendFailure(literalText("This is a player only command!") {
                            color = Colors.DARK_RED
                        })
                        return@runs
                    }

                    val player = this.source.player!!
                    this.source.server.broadcastText("${player.displayName}'s location: ") {
                        color = Colors.LIGHT_GRAY
                        text(player.position().toString()) {
                            color = Colors.GOLD
                        }
                    }
                    return@runs
                }
                if (!this.source.isPlayer) {
                    this.source.sendFailure(literalText("This is a player only command!") {
                        color = Colors.DARK_RED
                    })
                    return@runs
                }
                val player = this.source.player!!
                val target = this.source.server.playerList.getPlayerByName(name()) ?: this.source.sendFailure(
                    literalText("The player cannot be found!") { color = Colors.DARK_RED })
                val location = player.position()
                val message = literalText("${player.displayName}'s location: ") {
                    color = Colors.LIGHT_GRAY
                    text(location.toString()) {
                        color = Colors.GOLD
                    }
                }
                (target as ServerPlayer).sendMessage(message)
                player.sendMessage(message)
            }
        }
    }

    val heads = command("heads") {
        runs {
            if (!this.source.isPlayer) {
                this.source.sendFailure(literalText("This is a player only command!") {
                    color = Colors.DARK_RED
                })
            }
            val player = this.source.player
            val survivalPlayer = SurvivalPlayers.instance.getCachedEntity(player!!.uuid)!!
            openHeadGUI(survivalPlayer)
        }
    }
}

