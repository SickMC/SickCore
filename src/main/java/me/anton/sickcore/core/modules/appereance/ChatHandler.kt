package me.anton.sickcore.core.modules.appereance

import io.papermc.paper.event.player.AsyncChatEvent
import me.anton.sickcore.core.listenVelocity
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.event.listen
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.event.command.UnknownCommandEvent

class ChatHandler {

    fun handleChat(){
        listenVelocity<AsyncChatEvent> {
            it.message(it.player.displayName().color(TextColor.fromCSSHexString("#5b6667")).append(Component.text(" » ")).color(TextColor.fromCSSHexString("#00b8c2")).append(it.originalMessage()))
        }

        listen<UnknownCommandEvent> {
            it.message(Component.text("The command /${it.commandLine} cannot be found!").color(KColors.DARKRED))
        }
    }

}