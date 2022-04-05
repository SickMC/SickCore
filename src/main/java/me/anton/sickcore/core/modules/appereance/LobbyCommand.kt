package me.anton.sickcore.core.modules.appereance

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.velocitypowered.api.command.BrigadierCommand
import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.event.player.PlayerChatEvent
import com.velocitypowered.api.proxy.Player
import kotlinx.coroutines.launch
import me.anton.sickcore.core.Core
import me.anton.sickcore.core.VelocityCore
import me.anton.sickcore.core.listenVelocity
import me.anton.sickcore.utils.sendMessage
import net.kyori.adventure.text.minimessage.MiniMessage

class LobbyCommand {

    fun register(){
        val literalNode = LiteralArgumentBuilder
            .literal<CommandSource>("l")
            .executes(){
                Core.instance.databaseScope.launch {
                    val veloPlayer = it.source as Player
                    if (veloPlayer.currentServer.get().serverInfo.name.startsWith("Lobby")){
                        val text = MiniMessage.miniMessage().deserialize("<gradient:#890000:#7E0000>You are already connected to the lobby!</gradient>")
                        sendMessage(veloPlayer.uniqueId, text)
                        return@launch
                    }
                    veloPlayer.createConnectionRequest(VelocityCore.instance?.base?.server?.getServer("Lobby-1")?.get())
                    val text = MiniMessage.miniMessage().deserialize("<gradient:#5B8906:#05561E>You were teleported to the lobby!</gradient>")
                    sendMessage(veloPlayer.uniqueId, text)
                }
                1
            }

        val command = BrigadierCommand(literalNode)
        val commandMeta = VelocityCore.instance?.base?.server?.commandManager?.metaBuilder("l")?.aliases("lobby", "leave", "hub")?.build()
        VelocityCore.instance?.base?.server?.commandManager?.register(commandMeta, command)
    }

    suspend fun registerAddition(){
        val aliases = arrayOf("leave","lobby", "l", "hub").toList()
        listenVelocity<PlayerChatEvent> {
            Core.instance.databaseScope.launch {
                if (aliases.contains(it.message.split("7")[1])) {
                    val veloPlayer = it.player
                    if (veloPlayer.currentServer.get().serverInfo.name.startsWith("Lobby")) {
                        val text = MiniMessage.miniMessage()
                            .deserialize("<gradient:#890000:#7E0000>You are already connected to the lobby!</gradient>")
                        sendMessage(veloPlayer.uniqueId, text)
                        return@launch
                    }
                    veloPlayer.createConnectionRequest(VelocityCore.instance?.base?.server?.getServer("Lobby-1")?.get())
                    val text = MiniMessage.miniMessage()
                        .deserialize("<gradient:#5B8906:#05561E>You were teleported to the lobby!</gradient>")
                    sendMessage(veloPlayer.uniqueId, text)
                }
            }
        }
    }

}