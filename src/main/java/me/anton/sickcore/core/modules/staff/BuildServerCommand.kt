package me.anton.sickcore.core.modules.staff

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.velocitypowered.api.command.BrigadierCommand
import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.proxy.Player
import kotlinx.coroutines.launch
import me.anton.sickcore.core.Core
import me.anton.sickcore.core.VelocityCore
import me.anton.sickcore.core.player.SickPlayers
import me.anton.sickcore.utils.sendMessage
import net.kyori.adventure.text.minimessage.MiniMessage

class BuildServerCommand {

    fun register(){
        val literalNode = LiteralArgumentBuilder
            .literal<CommandSource>("bs")
            .executes(){
                Core.instance.databaseScope.launch {
                    val veloPlayer = it.source as Player
                    val player = SickPlayers.getSickPlayer(veloPlayer.uniqueId)!!
                    if (!player.isStaff()){
                        val text = MiniMessage.miniMessage().deserialize("<gradient:#890000:#7E0000>This is a staff command!</gradient>")
                        sendMessage(player.uuid, text)
                        return@launch
                    }
                    veloPlayer.createConnectionRequest(VelocityCore.instance?.base?.server?.getServer("BuildServer-1")?.get())
                    val text = MiniMessage.miniMessage().deserialize("<gradient:#5B8906:#05561E>You were teleported to the buildserver!</gradient>")
                    sendMessage(player.uuid, text)
                }
                1
            }

        val command = BrigadierCommand(literalNode)
        val commandMeta = VelocityCore.instance?.base?.server?.commandManager?.metaBuilder("bs")?.build()
        VelocityCore.instance?.base?.server?.commandManager?.register(commandMeta, command)
    }

}