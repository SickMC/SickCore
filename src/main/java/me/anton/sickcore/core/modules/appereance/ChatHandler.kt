package me.anton.sickcore.core.modules.appereance

import io.papermc.paper.event.player.AsyncChatEvent
import me.anton.sickcore.core.listenVelocity
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor

class ChatHandler {

    fun handleChat(){
        listenVelocity<AsyncChatEvent> {
            it.message(it.player.displayName().color(TextColor.fromCSSHexString("#5b6667")).append(Component.text(" » ")).color(TextColor.fromCSSHexString("#00b8c2")).append(it.originalMessage()))
        }
    }

}