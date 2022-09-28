package net.sickmc.sickcore.survival

import kotlinx.coroutines.launch
import net.minecraft.network.chat.*
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.players.PlayerList
import net.sickmc.sickapi.playerCache
import net.sickmc.sickapi.rank.parent
import net.sickmc.sickapi.util.Colors
import net.sickmc.sickcore.api.fabric.extensions.displayName
import net.sickmc.sickcore.api.fabric.modScope
import net.sickmc.sickcore.api.fabric.server
import net.sickmc.sickcore.api.fabric.tablist.Tablist
import net.sickmc.sickcore.api.fabric.tablist.tablist
import net.silkmc.silk.core.annotations.InternalSilkApi
import net.silkmc.silk.core.event.Events
import net.silkmc.silk.core.event.Player
import net.silkmc.silk.core.text.broadcastText
import net.silkmc.silk.core.text.literalText
import net.silkmc.silk.game.sideboard.SideboardLine

@OptIn(InternalSilkApi::class)
lateinit var tablist: Tablist

@OptIn(InternalSilkApi::class)
@Suppress("unused")
fun init() {
    SurvivalMixinEntrypoint.init()
    tablist = tablist {
        generateName {
            val sickPlayer = playerCache.get(this.uuid) ?: return@generateName this.displayName to "0"
            sickPlayer.displayName to "${sickPlayer.currentRank.parent.priority}"
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
}

object SurvivalMixinEntrypoint {

    fun init() {
        joinListener
    }

    private val joinListener = Events.Player.postLogin.listen { event ->
        modScope.launch {
            var sickPlayer = playerCache.get(event.player.uuid)
            if (sickPlayer != null) server.broadcastText(sickPlayer.displayName.append(literalText(" joined the server!") {
                color = Colors.gray
            }))
            else {
                while (playerCache.cache.containsKey(event.player.uuid)) {
                    sickPlayer = playerCache.get(event.player.uuid)
                    server.broadcastText(sickPlayer!!.displayName.append(literalText(" joined the server!") {
                        color = Colors.gray
                    }))
                    return@launch
                }
            }
        }
    }

    fun onQuit(player: ServerPlayer) {
        modScope.launch {
            val sickPlayer = playerCache.get(player.uuid) ?: return@launch
            server.broadcastText(sickPlayer.displayName.append(literalText(" quit the server!") {
                color = Colors.gray
            }))
        }
    }

    fun onChat(player: ServerPlayer, message: PlayerChatMessage, list: PlayerList) {
        if (playerCache.cache.containsKey(player.uuid)) {
            modScope.launch {
                val sickPlayer = playerCache.get(player.uuid) ?: return@launch
                list.broadcastChatMessage(
                    PlayerChatMessage.unsigned(
                        MessageSigner.system(), ChatMessageContent(
                            "${sickPlayer.displayName.string}  ${message.serverContent().string}",
                            sickPlayer.displayName.append(literalText("  ${message.serverContent().string}") {
                                color = Colors.gray
                            })
                        )
                    ), player, ChatType.bind(ChatType.CHAT, player)
                )
            }
            return
        }
        list.broadcastChatMessage(message, player, ChatType.bind(ChatType.CHAT, player))
    }

    fun onAdvancement(player: ServerPlayer, message: PlayerChatMessage, list: PlayerList) {
        if (playerCache.cache.containsKey(player.uuid)) {
            modScope.launch {
                val sickPlayer = playerCache.get(player.uuid) ?: return@launch
                list.broadcastChatMessage(
                    PlayerChatMessage.unsigned(
                        MessageSigner.system(), ChatMessageContent(
                            "${sickPlayer.displayName.string}  ${message.serverContent().string}",
                            sickPlayer.displayName.append(literalText("  ${message.serverContent().string}") {
                                color = Colors.gray
                            })
                        )
                    ), player, ChatType.bind(ChatType.CHAT, player)
                )
            }
            return
        }
        list.broadcastChatMessage(message, player, ChatType.bind(ChatType.CHAT, player))
    }
}