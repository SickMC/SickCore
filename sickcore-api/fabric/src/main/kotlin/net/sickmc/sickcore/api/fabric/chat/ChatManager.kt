package net.sickmc.sickcore.api.fabric.chat

import kotlinx.coroutines.launch
import net.minecraft.ChatFormatting
import net.minecraft.advancements.Advancement
import net.minecraft.network.chat.ChatMessageContent
import net.minecraft.network.chat.ChatType
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.PlayerChatMessage
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.players.PlayerList
import net.minecraft.world.damagesource.CombatTracker
import net.sickmc.sickcore.api.fabric.modScope

class ChatManager(
    var join: suspend ServerPlayer.() -> Component?,
    var quit: suspend ServerPlayer.() -> Component?,
    var chat: suspend ServerPlayer.(message: ChatMessageContent) -> Component?,
    var advancement: suspend ServerPlayer.(advancement: Advancement) -> Component?,
    var death: suspend ServerPlayer.(tracker: CombatTracker) -> Component?,
    var deathName: ServerPlayer.(tracker: CombatTracker) -> Component?
) {
    companion object {
        var current: ChatManager? = null
    }

    suspend fun handleJoin(player: ServerPlayer, list: PlayerList) {
        val result = join(player)
        if (result == Component.empty()) return
        if (result == null) {
            list.broadcastSystemMessage(
                Component.translatable(
                    "multiplayer.player.joined", player.displayName
                ).withStyle(
                    ChatFormatting.YELLOW
                ), false
            )
            return
        }
        list.broadcastSystemMessage(result, false)
    }

    fun handleQuit(player: ServerPlayer, list: PlayerList) {
        modScope.launch {
            val result = quit(player)
            if (result == Component.empty()) return@launch
            if (result == null) {
                list.broadcastSystemMessage(
                    Component.translatable("multiplayer.player.left", player.displayName)
                        .withStyle(ChatFormatting.YELLOW), false
                )
                return@launch
            }
            list.broadcastSystemMessage(result, false)
        }
    }

    fun handleChat(player: ServerPlayer, message: ChatMessageContent, list: PlayerList) {
        modScope.launch {
            val result = chat.invoke(player, message)
            if (result == Component.empty()) return@launch
            if (result == null) {
                list.broadcastChatMessage(
                    PlayerChatMessage.system(message), player, ChatType.bind(ChatType.CHAT, player)
                )
                return@launch
            }
            list.broadcastSystemMessage(result, false)
        }
    }

    fun handleAdvancement(player: ServerPlayer, advancement: Advancement, list: PlayerList) {
        modScope.launch {
            val result = this@ChatManager.advancement.invoke(player, advancement)
            if (result == Component.empty()) return@launch
            if (result == null) {
                list.broadcastSystemMessage(
                    Component.translatable(
                        "chat.type.advancement." + advancement.display?.frame?.getName(),
                        player.displayName,
                        advancement.chatComponent
                    ), false
                );
                return@launch
            }
            list.broadcastSystemMessage(result, false)
        }
    }

    fun handleDeath(player: ServerPlayer, tracker: CombatTracker, list: PlayerList) {
        modScope.launch {
            val result = this@ChatManager.death.invoke(player, tracker)
            if (result == Component.empty()) return@launch
            if (result == null) {
                list.broadcastSystemMessage(
                    tracker.deathMessage, false
                );
                return@launch
            }
            list.broadcastSystemMessage(result, false)
        }
    }

    fun changeDeathName(player: ServerPlayer, tracker: CombatTracker): Component? =
        this.deathName.invoke(player, tracker)

    init {
        current = this
    }
}