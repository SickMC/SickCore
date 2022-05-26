package net.sickmc.sickcore.core.modules.appereance

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
import net.sickmc.sickcore.utils.mm
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.ExperimentalTime

class PlaytimeCommand {

    @OptIn(ExperimentalTime::class)
    fun register(){
        val literalNode = LiteralArgumentBuilder
            .literal<CommandSource>("playtime")
            .executes(){
                databaseScope.launch {
                    val veloPlayer = it.source as Player
                    val sickPlayer = SickPlayers.instance.getEntity(veloPlayer.uniqueId)
                    val text = mm.deserialize("<gradient:#FFFD0B:#80A720>Your playtime is </gradient><gradient:#FF6F13#FFC034>${sickPlayer.playtime.milliseconds}</gradient><#80A720>!")
                    sendMessage(veloPlayer.uniqueId, text)
                }
                1
            }

        val command = BrigadierCommand(literalNode)
        val commandMeta = VelocityCore.instance?.base?.server?.commandManager?.metaBuilder("playtime")?.build()
        VelocityCore.instance?.base?.server?.commandManager?.register(commandMeta, command)
    }

}