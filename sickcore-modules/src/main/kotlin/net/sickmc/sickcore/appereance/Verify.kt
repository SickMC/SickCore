@file:OptIn(ExperimentalStdlibApi::class)

package net.sickmc.sickcore.appereance

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.velocitypowered.api.command.BrigadierCommand
import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.proxy.Player
import io.ktor.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.TextColor
import net.sickmc.sickcore.commonPlayer.SickPlayers
import net.sickmc.sickcore.proxyServer
import net.sickmc.sickcore.utils.Colors
import net.sickmc.sickcore.utils.mm
import net.sickmc.sickcore.utils.mongo.databaseScope
import net.sickmc.sickcore.utils.toUUID
import net.sickmc.sickcore.utils.websockets.listenChannel
import net.sickmc.sickcore.utils.websockets.sendChannelMessage
import java.util.*
import kotlin.jvm.optionals.getOrNull

object Verify {

    private val codeGeneratorScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private val verifyCache = hashMapOf<UUID, Int>()

    suspend fun register() {
        registerVerifyCommand()
        registerVerifyListener()
    }

    //Verify Pattern {status}/{uuid}/{id/code}

    private suspend fun registerVerifyListener() {
        listenChannel("verify") {
            send("jo")
            println("init")
            for (frame in incoming) {
                if (frame !is Frame.Text) continue
                val components = frame.readText().split("/")
                if (components[0] != "success") continue
                val uuid = components[1].toUUID()
                val discordTag = components[2]
                val player = proxyServer!!.getPlayer(uuid).getOrNull() ?: continue
                player.sendMessage(
                    Component.text("Your account is now linked with ").color(TextColor.color(Colors.LIGHT_GREEN))
                        .append(Component.text(discordTag).color(TextColor.color(Colors.GOLD)))
                )
            }
        }
    }

    private fun registerVerifyCommand() {
        val node = LiteralArgumentBuilder.literal<CommandSource>("verify").executes() {
            if (it.source !is Player) return@executes 1
            val veloPlayer = it.source as Player
            if (veloPlayer.isAlreadyVerified()) {
                veloPlayer.sendMessage(
                    mm.deserialize("You are already verified!").color(TextColor.color(Colors.DARK_RED))
                )
                return@executes 1
            }
            if (verifyCache.containsKey(veloPlayer.uniqueId)) {
                veloPlayer.sendMessage(
                    mm.deserialize("<gradient:#FFFD0B:#80A720>Your verification process is already running. Your verification code is </gradient><gradient:#FF6F13:#FFC034>${verifyCache[veloPlayer.uniqueId]}</gradient><#80A720>!")
                        .clickEvent(
                            ClickEvent.copyToClipboard(verifyCache[veloPlayer.uniqueId].toString())
                        )
                )
                databaseScope.launch {
                    sendChannelMessage("verify", Frame.Text("request/${veloPlayer.uniqueId}/${verifyCache[veloPlayer.uniqueId]!!}"))
                    println("Should be sent!")
                }
                return@executes 1
            }
            var code = (100000..999999).random()
            codeGeneratorScope.launch {
                while (verifyCache.containsValue(code)) code = (100000..999999).random()
            }
            verifyCache[veloPlayer.uniqueId] = code
            databaseScope.launch {
                sendChannelMessage("verify", Frame.Text("request/${veloPlayer.uniqueId}/$code"))
                println("Should be sent!")
            }
            veloPlayer.sendMessage(
                mm.deserialize("<gradient:#FFFD0B:#80A720>Your verification code is </gradient><gradient:#FF6F13:#FFC034>$code</gradient><#80A720>!")
                    .clickEvent(
                        ClickEvent.copyToClipboard(code.toString())
                    )
            )
            1
        }

        val command = BrigadierCommand(node)
        val commandMeta = proxyServer?.commandManager?.metaBuilder("verify")?.build()
        proxyServer?.commandManager?.register(commandMeta, command)
    }

    private fun Player.isAlreadyVerified(): Boolean {
        val sickPlayer = SickPlayers.instance.getCachedEntity(this.uniqueId)!!
        return sickPlayer.discordID != "0"
    }

}