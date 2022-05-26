package net.sickmc.sickcore.core.games.survival

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.velocitypowered.api.event.player.PlayerChatEvent
import kotlinx.coroutines.launch
import net.axay.fabrik.core.Fabrik
import net.axay.fabrik.core.item.itemStack
import net.axay.fabrik.core.text.literalText
import net.fabricmc.fabric.api.event.player.AttackEntityCallback
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.ChatType
import net.minecraft.network.chat.Style
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ServerGamePacketListener
import net.minecraft.network.protocol.game.ServerboundChatPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.network.ServerGamePacketListenerImpl
import net.minecraft.server.network.TextFilter
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Items
import net.sickmc.sickcore.core.commonPlayer.SickPlayer
import net.sickmc.sickcore.core.commonPlayer.SickPlayers
import net.sickmc.sickcore.utils.mongo.databaseScope
import net.sickmc.sickcore.utils.fabric.getHead
import net.sickmc.sickcore.utils.mod_id
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import java.util.*
import kotlin.random.Random
import kotlin.random.nextInt

object CommonEvents {

    fun register(){
        join()
        mobDrops()
        quit()
    }

    private fun join(){
        ServerPlayConnectionEvents.JOIN.register { handler, _, server->
            server.playerList.players.forEach {
                it.sendMessage(literalText(handler.player.displayName.contents) {
                    text(" joined the server") {
                        color = 0x8C969A
                    }
                }, UUID.randomUUID())
            }

            var player: SurvivalPlayer? = null
            databaseScope.launch {
                player = SurvivalPlayers.instance.reloadEntity(handler.player.uuid)
            }
            handler.player.customName = player!!.sickPlayer.displayName.full

            if (!handler.player.tags.contains("$mod_id.firstjoin.$mod_id")){
                handler.player.addTag("$mod_id.firstjoin.$mod_id")
                handler.player.inventory.add(itemStack(Items.BEEF, 64){})
            }
        }
    }

    private fun quit(){
        ServerPlayConnectionEvents.DISCONNECT.register {handler, server ->
            server.playerList.players.forEach {
                it.sendMessage(literalText(handler.player.displayName.contents){
                    text("quit the server") {
                        color = 0x8C969A
                    }
                }, UUID.randomUUID())
            }
        }
    }

    private fun mobDrops(){
        AttackEntityCallback.EVENT.register { player, level, hand, entity, result ->
            if (entity.getHead().isOnlyCreative || entity.getHead().isNaturalAvailable)InteractionResult.PASS
            if (Random.nextInt(1 .. 64) == 0){
                databaseScope.launch {
                    val gamePlayer = SurvivalPlayers.instance.getCachedEntity(player.uuid)
                    gamePlayer?.addHead(entity)
                    SurvivalPlayers.instance.reloadEntity(player.uuid)
                }
            }
            InteractionResult.PASS
        }
    }

    fun chat(message: TextFilter.FilteredText, player: ServerPlayer, server: MinecraftServer){
        val sickPlayer = SickPlayers.instance.getCachedEntity(player.uuid)!!
        val newMessage = sickPlayer.displayName.full.append(literalText(message.filtered) { color = 0x8C969A })
        server.playerList.players.forEach{
            it.sendMessage(newMessage, ChatType.CHAT, UUID.randomUUID())
        }
    }

    fun command(source: CommandSourceStack, command: String, cir: CallbackInfoReturnable<Int>, dispatcher: CommandDispatcher<CommandSourceStack>){

    }

}