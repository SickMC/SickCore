package net.sickmc.sickcore.survival

import kotlinx.datetime.Clock
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.sickmc.sickapi.util.Colors
import net.sickmc.sickapi.util.Gradient
import net.sickmc.sickcore.api.fabric.extensions.colorOf
import net.sickmc.sickcore.api.fabric.server
import net.silkmc.silk.core.text.literalText
import java.util.*
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

object CombatLock {
    private val lockedPlayers = hashMapOf<UUID, Pair<Int, Long>>()
    val blockedPlayers = hashMapOf<UUID, Long>()
    private val colors = buildList {
        val gradient = Gradient(0xFF4927, 0xFF8D36)
        val textChars = 10
        for (i in 0..textChars) add(gradient.colorOf(i.toDouble() / textChars))
        this.addAll(this.reversed())
        repeat(textChars * 2) { add(gradient.colorOf(0.0)) }
    }
    private var nextSecond = Clock.System.now().toEpochMilliseconds() + 1.seconds.inWholeMilliseconds

    fun updateTimer() {
        if (Clock.System.now().toEpochMilliseconds() >= nextSecond) {
            nextSecond = Clock.System.now().toEpochMilliseconds() + 1.seconds.inWholeMilliseconds
            lockedPlayers.forEach { (uuid, cooldown) ->
                val newCooldown = cooldown.second - 1.seconds.inWholeMilliseconds
                if (newCooldown <= 0L) lockedPlayers.remove(uuid) else lockedPlayers[uuid] =
                    cooldown.copy(second = newCooldown)
            }
        }

        lockedPlayers.forEach { (uuid, cooldown) ->
            val player = server.playerList.getPlayer(uuid)
            if (cooldown.first >= colors.size) lockedPlayers[uuid] = cooldown.copy(first = 0)
            val chars = "${cooldown.second.milliseconds} left".split("")
            val component = literalText("")

            chars.forEachIndexed { index, char ->
                var num = index + cooldown.first
                if (num >= colors.size * 2) num = 0
                else if (num >= colors.size) num -= colors.size
                component.append(
                    literalText(char) {
                        bold = true
                        color = colors[num]
                    },
                )
            }

            lockedPlayers[uuid] = cooldown.copy(first = cooldown.first + 1)

            player?.sendSystemMessage(component, true)
        }
    }

    fun playerHit(first: ServerPlayer, second: ServerPlayer) {
        lockedPlayers[first.uuid] = 0 to 30.seconds.inWholeMilliseconds
        lockedPlayers[second.uuid] = 0 to 30.seconds.inWholeMilliseconds
    }

    fun quit(player: ServerPlayer) {
        if (!lockedPlayers.containsKey(player.uuid)) return
        lockedPlayers.remove(player.uuid)
        blockedPlayers[player.uuid] = Clock.System.now().toEpochMilliseconds() + 5.minutes.inWholeMilliseconds
    }

    fun playerJoin(uuid: UUID): Component = literalText {
        text("You left to early in a fight!") { color = Colors.lightBlue }
        emptyLine()
        text(
            "You can join again in ${
                (blockedPlayers[uuid]!!.milliseconds.inWholeMilliseconds - Clock.System.now()
                    .toEpochMilliseconds()).milliseconds
            }"
        ) { color = Colors.red }
    }
}