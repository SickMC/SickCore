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

    private var join: suspend ServerPlayer.() -> Component? = { null }
    private var quit: suspend ServerPlayer.() -> Component? = { null }
    private var chat: suspend ServerPlayer.(message: ChatMessageContent) -> Component? = { null }
    private var advancement: suspend ServerPlayer.(advancement: Advancement) -> Component? = { null }
    private var kill: suspend ServerPlayer.() -> Component? = { null }
    private var death: suspend ServerPlayer.(tracker: CombatTracker) -> Component? = { null }

    private var deathName: ServerPlayer.(CombatTracker) -> Component = { Component.empty() }

    fun join(block: suspend ServerPlayer.() -> Component?) {
        join = block
    }

    fun quit(block: suspend ServerPlayer.() -> Component?) {
        quit = block
    }

    fun chat(block: suspend ServerPlayer.(message: ChatMessageContent) -> Component?) {
        chat = block
    }

    fun advancement(block: suspend ServerPlayer.(advancement: Advancement) -> Component?) {
        advancement = block
    }

    fun kill(block: suspend ServerPlayer.() -> Component?) {
        kill = block
    }

    fun death(block: suspend ServerPlayer.(tracker: CombatTracker) -> Component?) {
        death = block
    }

    fun deathName(block: ServerPlayer.(CombatTracker) -> Component) {
        deathName = block
    }

    fun build(): ChatManager = ChatManager(join, quit, chat, advancement, death, deathName)
}