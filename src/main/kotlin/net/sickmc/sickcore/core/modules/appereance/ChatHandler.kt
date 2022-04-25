package net.sickmc.sickcore.core.modules.appereance

import io.papermc.paper.event.player.AsyncChatEvent
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.event.listen
import net.axay.kspigot.extensions.broadcast
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.event.command.UnknownCommandEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class ChatHandler {

    fun handleChat(){
        listen<PlayerJoinEvent> {
            it.joinMessage(null)
        }

        listen<PlayerQuitEvent> {
            it.quitMessage(null)
        }

        listen<AsyncChatEvent> {
            it.isCancelled = true
            broadcast(it.player.displayName().append(Component.text(" » ")).color(TextColor.fromCSSHexString("#00b8c2")).append(it.originalMessage()))
        }

        listen<UnknownCommandEvent> {
            it.message(Component.text("The command /${it.commandLine} cannot be found!").color(KColors.DARKRED))
        }
    }

}