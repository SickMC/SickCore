package net.sickmc.sickcore.core.games.survival

import com.velocitypowered.api.event.player.PlayerChatEvent
import kotlinx.coroutines.launch
import net.axay.fabrik.core.Fabrik
import net.axay.fabrik.core.item.itemStack
import net.axay.fabrik.core.text.literalText
import net.fabricmc.fabric.api.event.player.AttackEntityCallback
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ServerboundChatPacket
import net.minecraft.server.network.ServerGamePacketListenerImpl
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Items
import net.sickmc.sickcore.utils.mongo.databaseScope
import net.sickmc.sickcore.utils.fabric.getHead
import net.sickmc.sickcore.utils.mod_id
import java.util.*
import kotlin.random.Random
import kotlin.random.nextInt

object CommonEvents {

    fun register(){
        join()
        mobDrops()
        quit()
    }

    fun join(){
        ServerPlayConnectionEvents.JOIN.register { handler, _, server->
            server.playerList.players.forEach {
                it.sendMessage(literalText(handler.player.displayName.contents) {
                    text(" joined the server") {
                        color = 0x8C969A
                    }
                }, UUID.randomUUID())
            }

            databaseScope.launch {
                SurvivalPlayers.instance.reloadEntity(handler.player.uuid)
            }
            if (!handler.player.tags.contains("$mod_id.firstjoin.$mod_id")){
                handler.player.addTag("$mod_id.firstjoin.$mod_id")
                handler.player.inventory.add(itemStack(Items.BEEF, 64){})
            }
        }
    }

    fun quit(){
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

    fun mobDrops(){
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

    fun chat(){
        
    }

}