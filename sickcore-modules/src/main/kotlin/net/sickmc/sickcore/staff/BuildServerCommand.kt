package net.sickmc.sickcore.staff

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.velocitypowered.api.command.BrigadierCommand
import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.proxy.Player
import kotlinx.coroutines.launch
import net.sickmc.sickcore.core.commonPlayer.SickPlayers
import net.sickmc.sickcore.utils.mongo.databaseScope
import net.sickmc.sickcore.utils.sendMessage
import net.kyori.adventure.text.minimessage.MiniMessage
import net.sickmc.sickcore.proxyServer

class BuildServerCommand {

    fun register(){
        val literalNode = LiteralArgumentBuilder
            .literal<CommandSource>("bs")
            .executes(){
                databaseScope.launch {
                    val veloPlayer = it.source as Player
                    val player = SickPlayers.instance.getEntity(veloPlayer.uniqueId)
                    if (!player.isStaff()){
                        val text = MiniMessage.miniMessage().deserialize("<gradient:#890000:#7E0000>This is a staff command!</gradient>")
                        sendMessage(player.uniqueID, text)
                        return@launch
                    }
                    veloPlayer.createConnectionRequest(proxyServer?.getServer("BuildServer-1")?.get())
                    val text = MiniMessage.miniMessage().deserialize("<gradient:#5B8906:#05561E>You were teleported to the buildserver!</gradient>")
                    sendMessage(player.uniqueID, text)
                }
                1
            }

        val command = BrigadierCommand(literalNode)
        val commandMeta = proxyServer?.commandManager?.metaBuilder("bs")?.build()
        proxyServer?.commandManager?.register(commandMeta, command)
    }

}