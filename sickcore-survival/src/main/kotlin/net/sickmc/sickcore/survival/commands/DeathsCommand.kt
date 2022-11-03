package net.sickmc.sickcore.survival.commands

import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.stats.Stats
import net.silkmc.silk.commands.command
import net.silkmc.silk.core.text.literalText

val deathsCommand = command("deaths") {
    argument("target", EntityArgument.player()) { target ->
        runs {
            val player = source.playerOrException

            player.sendSystemMessage(literalText(
                "${
                    target().findSinglePlayer(source).stats.getValue(
                        Stats.CUSTOM.get(
                            Stats.DEATHS
                        )
                    )
                } deaths"
            ) {
                color = 0xFF9C46
            })
        }
    }

    runs {
        val player = source.playerOrException

        player.sendSystemMessage(literalText(
            "${
                player.stats.getValue(
                    Stats.CUSTOM.get(
                        Stats.DEATHS
                    )
                )
            } deaths"
        ) {
            color = 0xFF9C46
        })
    }
}