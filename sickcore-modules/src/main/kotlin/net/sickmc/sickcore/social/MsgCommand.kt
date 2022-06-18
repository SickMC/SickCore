package net.sickmc.sickcore.social

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.tree.LiteralCommandNode
import com.velocitypowered.api.command.BrigadierCommand
import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.proxy.Player
import kotlinx.coroutines.launch
import net.sickmc.sickcore.commonPlayer.SickPlayers
import net.sickmc.sickcore.utils.PlayerUtils
import net.sickmc.sickcore.utils.mongo.databaseScope
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.sickmc.sickcore.proxyServer
import net.sickmc.sickcore.utils.mm

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

                            val noPlayer = Component.text("This player is not online!").color(TextColor.color(0x9A100D))
                            val message = StringArgumentType.getString(it, "msg")
                            val senderText = SickPlayers.instance.getCachedEntity(sender.uniqueId)!!.displayName.getKyoriName().append(mm.deserialize("<#5b6667>» ")).append(SickPlayers.instance.getCachedEntity(targetUUID)!!.displayName.getKyoriName()).append(mm.deserialize("<#858585>: $message"))
                        }
                        1
                    }))
            .build()

        val command = BrigadierCommand(literalNode)
        val commandMeta = proxyServer?.commandManager?.metaBuilder("msg")?.aliases("message", "w", "tell")?.build()
        proxyServer?.commandManager?.register(commandMeta, command)
    }

}