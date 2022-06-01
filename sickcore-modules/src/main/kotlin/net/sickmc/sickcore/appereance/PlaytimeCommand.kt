package net.sickmc.sickcore.appereance

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.velocitypowered.api.command.BrigadierCommand
import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.proxy.Player
import kotlinx.coroutines.launch
import net.sickmc.sickcore.core.commonPlayer.SickPlayers
import net.sickmc.sickcore.proxyServer
import net.sickmc.sickcore.utils.mongo.databaseScope
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
                    veloPlayer.sendMessage(text)
                }
                1
            }

        val command = BrigadierCommand(literalNode)
        val commandMeta = proxyServer?.commandManager?.metaBuilder("playtime")?.build()
        proxyServer?.commandManager?.register(commandMeta, command)
    }

}