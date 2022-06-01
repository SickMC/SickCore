package net.sickmc.sickcore.appereance

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.velocitypowered.api.command.BrigadierCommand
import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.event.player.PlayerChatEvent
import com.velocitypowered.api.proxy.Player
import kotlinx.coroutines.launch
import net.sickmc.sickcore.listenVelocity
import net.sickmc.sickcore.proxyServer
import net.sickmc.sickcore.utils.mm
import net.sickmc.sickcore.utils.mongo.databaseScope
import net.sickmc.sickcore.utils.sendMessage

class LobbyCommand {

    fun register(){
        val literalNode = LiteralArgumentBuilder
            .literal<CommandSource>("l")
            .executes(){
                databaseScope.launch {
                    val veloPlayer = it.source as Player
                    if (veloPlayer.currentServer.get().serverInfo.name.startsWith("Lobby")){
                        val text = mm.deserialize("<gradient:#890000:#7E0000>You are already connected to the lobby!</gradient>")
                        sendMessage(veloPlayer.uniqueId, text)
                        return@launch
                    }
                    veloPlayer.createConnectionRequest(proxyServer?.getServer("Lobby-1")?.get())
                    val text = mm.deserialize("<gradient:#5B8906:#05561E>You were teleported to the lobby!</gradient>")
                    sendMessage(veloPlayer.uniqueId, text)
                }
                1
            }

        val command = BrigadierCommand(literalNode)
        val commandMeta = proxyServer?.commandManager?.metaBuilder("l")?.aliases("lobby", "leave", "hub")?.build()
        proxyServer?.commandManager?.register(commandMeta, command)
    }

    fun registerAddition(){
        val aliases = arrayOf("leave","lobby", "l", "hub").toList()
        listenVelocity<PlayerChatEvent> {
            databaseScope.launch {
                if (aliases.contains(it.message.split("7")[1])) {
                    val veloPlayer = it.player
                    if (veloPlayer.currentServer.get().serverInfo.name.startsWith("Lobby")) {
                        val text = mm.deserialize("<gradient:#890000:#7E0000>You are already connected to the lobby!</gradient>")
                        sendMessage(veloPlayer.uniqueId, text)
                        return@launch
                    }
                    veloPlayer.createConnectionRequest(proxyServer?.getServer("Lobby-1")?.get())
                    val text = mm.deserialize("<gradient:#5B8906:#05561E>You were teleported to the lobby!</gradient>")
                    sendMessage(veloPlayer.uniqueId, text)
                }
            }
        }
    }

}