package net.sickmc.sickcore.core.modules.staff

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.velocitypowered.api.command.BrigadierCommand
import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.proxy.Player
import kotlinx.coroutines.launch
import net.sickmc.sickcore.core.VelocityCore
import net.sickmc.sickcore.core.commonPlayer.SickPlayers
import net.sickmc.sickcore.utils.mongo.databaseScope
import net.sickmc.sickcore.utils.sendMessage
import net.kyori.adventure.text.minimessage.MiniMessage

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
                    veloPlayer.createConnectionRequest(VelocityCore.instance?.base?.server?.getServer("BuildServer-1")?.get())
                    val text = MiniMessage.miniMessage().deserialize("<gradient:#5B8906:#05561E>You were teleported to the buildserver!</gradient>")
                    sendMessage(player.uniqueID, text)
                }
                1
            }

        val command = BrigadierCommand(literalNode)
        val commandMeta = VelocityCore.instance?.base?.server?.commandManager?.metaBuilder("bs")?.build()
        VelocityCore.instance?.base?.server?.commandManager?.register(commandMeta, command)
    }

}