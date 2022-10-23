package net.sickmc.sickcore.api.fabric.chat

import net.minecraft.advancements.Advancement
import net.minecraft.network.chat.ChatMessageContent
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.damagesource.CombatTracker

inline fun chat(builder: ChatBuilder.() -> Unit): ChatManager {
    return ChatBuilder().also(builder).build()
}

class ChatBuilder {

    private var join: suspend (player: ServerPlayer) -> Component? = { null }
    private var quit: suspend (player: ServerPlayer) -> Component? = { null }
    private var chat: suspend (player: ServerPlayer, message: ChatMessageContent) -> Component? = { _, _ -> null }
    private var advancement: suspend (player: ServerPlayer, advancement: Advancement) -> Component? = { _, _ -> null }
    private var death: suspend (player: ServerPlayer, tracker: CombatTracker) -> Component? = { _, _ -> null }
    private var deathName: (player: ServerPlayer, tracker: CombatTracker) -> Component? = { _, _ -> null }

    fun join(block: suspend (player: ServerPlayer) -> Component?) {
        join = block
    }

    fun quit(block: suspend (player: ServerPlayer) -> Component?) {
        quit = block
    }

    fun chat(block: suspend (player: ServerPlayer, message: ChatMessageContent) -> Component?) {
        chat = block
    }

    fun advancement(block: suspend (player: ServerPlayer, advancement: Advancement) -> Component?) {
        advancement = block
    }

    fun death(block: suspend (player: ServerPlayer, tracker: CombatTracker) -> Component?) {
        death = block
    }

    fun deathName(block: (player: ServerPlayer, tracker: CombatTracker) -> Component) {
        deathName = block
    }

    fun build(): ChatManager = ChatManager(join, quit, chat, advancement, death, deathName)
}