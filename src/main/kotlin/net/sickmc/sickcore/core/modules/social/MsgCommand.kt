package net.sickmc.sickcore.core.modules.social

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.tree.LiteralCommandNode
import com.velocitypowered.api.command.BrigadierCommand
import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.proxy.Player
import kotlinx.coroutines.launch
import net.sickmc.sickcore.core.VelocityCore
import net.sickmc.sickcore.core.commonPlayer.SickPlayers
import net.sickmc.sickcore.utils.PlayerUtils
import net.sickmc.sickcore.utils.mongo.databaseScope
import net.sickmc.sickcore.utils.sendMessage
import net.axay.kspigot.chat.KColors
import net.kyori.adventure.text.Component
import net.sickmc.sickcore.core.redisConnection
import net.sickmc.sickcore.utils.paper.mm

class MsgCommand  {

    fun register(){
        val literalNode: LiteralCommandNode<CommandSource> = LiteralArgumentBuilder
            .literal<CommandSource>("msg")
            .then(RequiredArgumentBuilder.argument<CommandSource, String>("target", StringArgumentType.string())
                .then(RequiredArgumentBuilder.argument<CommandSource, String>("msg", StringArgumentType.greedyString())
                    .executes() {
                        databaseScope.launch {
                            val sender = it.source as Player
                            val target = StringArgumentType.getString(it, "target")
                            val targetUUID = PlayerUtils.fetchUUID(target)

                            val noPlayer = Component.text("This commonPlayer is not online!").color(KColors.DARKRED)
                            if (redisConnection.client.get(it.toString()) == null) {
                                sendMessage(sender.uniqueId, noPlayer)
                                return@launch
                            }
                            val message = StringArgumentType.getString(it, "msg")
                            val senderText = SickPlayers.getCachedSickPlayer(sender.uniqueId).displayName.append(mm.deserialize("<#5b6667>» ")).
                                append(SickPlayers.getCachedSickPlayer(targetUUID).displayName).append(mm.deserialize("<#858585>: $message"))
                            sendMessage(targetUUID, senderText)
                        }
                        1
                    }))
            .build()

        val command = BrigadierCommand(literalNode)
        val commandMeta = VelocityCore.instance?.base?.server?.commandManager?.metaBuilder("msg")?.aliases("message", "w", "tell")?.build()
        VelocityCore.instance?.base?.server?.commandManager?.register(commandMeta, command)
    }

}