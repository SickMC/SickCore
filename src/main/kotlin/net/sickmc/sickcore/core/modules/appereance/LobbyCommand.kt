package net.sickmc.sickcore.core.modules.appereance

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.velocitypowered.api.command.BrigadierCommand
import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.event.player.PlayerChatEvent
import com.velocitypowered.api.proxy.Player
import kotlinx.coroutines.launch
import net.sickmc.sickcore.core.Core
import net.sickmc.sickcore.core.VelocityCore
import net.sickmc.sickcore.core.listenVelocity
import net.sickmc.sickcore.utils.mongo.databaseScope
import net.sickmc.sickcore.utils.sendMessage
import net.kyori.adventure.text.minimessage.MiniMessage

class LobbyCommand {

    fun register(){
        val literalNode = LiteralArgumentBuilder
            .literal<CommandSource>("l")
            .executes(){
                databaseScope.launch {
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
            databaseScope.launch {
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