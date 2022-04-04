package me.anton.sickcore.core.modules.social

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.tree.LiteralCommandNode
import com.velocitypowered.api.command.BrigadierCommand
import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.proxy.Player
import kotlinx.coroutines.launch
import me.anton.sickcore.core.Core
import me.anton.sickcore.core.player.SickPlayers
import me.anton.sickcore.utils.PlayerUtils
import me.anton.sickcore.utils.sendMessage
import net.axay.kspigot.chat.KColors
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage

class MsgCommand  {

    fun register(){
        val literalNode: LiteralCommandNode<CommandSource> = LiteralArgumentBuilder
            .literal<CommandSource>("msg")
            .then(RequiredArgumentBuilder.argument<CommandSource, String>("target", StringArgumentType.string())
                .then(RequiredArgumentBuilder.argument<CommandSource, String>("msg", StringArgumentType.greedyString())
                    .executes() {
                        Core.instance.databaseScope.launch {
                            val sender = it.source as Player
                            val target = StringArgumentType.getString(it, "target")
                            val targetUUID = PlayerUtils.fetchUUID(target)

                            val noPlayer = Component.text("This player is not online!").color(KColors.DARKRED)
                            if (Core.instance.redisConnection.client.get(it.toString()) == null) {
                                sendMessage(sender.uniqueId, noPlayer)
                                return@launch
                            }
                            val message = StringArgumentType.getString(it, "msg")
                            val senderText = SickPlayers.getSickPlayer(sender.uniqueId)?.getDisplayname()?.append(MiniMessage.miniMessage().deserialize("<#5b6667>» "))?.
                                append(SickPlayers.getSickPlayer(targetUUID)!!.getDisplayname())?.append(MiniMessage.miniMessage().deserialize("<#858585>: $message"))!!
                            sendMessage(targetUUID, senderText)
                        }
                        1
                    }))
            .build()

        val command = BrigadierCommand(literalNode)
    }

}