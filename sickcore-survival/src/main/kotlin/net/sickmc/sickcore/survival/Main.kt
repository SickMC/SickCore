package net.sickmc.sickcore.survival

import kotlinx.coroutines.*
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.sickmc.sickapi.playerCache
import net.sickmc.sickapi.rank.parent
import net.sickmc.sickapi.util.Colors
import net.sickmc.sickcore.api.fabric.chat.ChatPresets
import net.sickmc.sickcore.api.fabric.extensions.displayName
import net.sickmc.sickcore.api.fabric.extensions.sickPlayer
import net.sickmc.sickcore.api.fabric.modScope
import net.sickmc.sickcore.api.fabric.moderation.ChatModeration
import net.sickmc.sickcore.api.fabric.tablist.Tablist
import net.sickmc.sickcore.api.fabric.tablist.tablist
import net.sickmc.sickcore.survival.commands.deathsCommand
import net.sickmc.sickcore.survival.commands.sharePos
import net.silkmc.silk.core.event.Events
import net.silkmc.silk.core.event.Player
import net.silkmc.silk.core.text.literalText
import net.silkmc.silk.game.sideboard.SideboardLine

lateinit var tablist: Tablist

@Suppress("unused")
fun init() {
    tablist = tablist {
        generateName {
            val sickPlayer = playerCache.get(this.uuid) ?: return@generateName this.displayName to "100"
            (sickPlayer.displayName ?: this.displayName) to "${sickPlayer.currentRank.parent.priority}"
        }

        header(
            listOf(
                SideboardLine.Static(literalText("SickMC") { color = Colors.lightBlue }), SideboardLine.Static(
                    Component.empty()
                )
            )
        )

        footer(
            listOf(
                SideboardLine.Static(
                    Component.empty()
                ), SideboardLine.Static(literalText("play.sickmc.net") { color = Colors.lightBlue })
            )
        )
    }
    ChatPresets.onlyNames
    SurvivalEntrypoint.init()
    ChatModeration.init()
    sharePos
    deathsCommand
}

object SurvivalEntrypoint {
    fun init() {
        modScope.launch {
            join()
        }
    }

    private suspend fun join(): Nothing = Events.Player.postLogin.collect { event ->
        event.player.customName = event.player.sickPlayer()?.displayName
    }

    fun tick() {
        CombatLock.updateTimer()
    }

    fun quit(player: ServerPlayer) {
        CombatLock.quit(player)
    }
}