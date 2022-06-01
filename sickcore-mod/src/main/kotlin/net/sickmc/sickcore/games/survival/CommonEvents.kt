package net.sickmc.sickcore.games.survival

import com.mojang.brigadier.CommandDispatcher
import kotlinx.coroutines.launch
import net.axay.fabrik.core.Fabrik
import net.axay.fabrik.core.item.itemStack
import net.axay.fabrik.core.text.broadcastText
import net.axay.fabrik.core.text.literalText
import net.axay.fabrik.core.text.sendText
import net.fabricmc.fabric.api.event.player.AttackEntityCallback
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.*
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.network.TextFilter
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Items
import net.sickmc.sickcore.commonPlayer.SickPlayers
import net.sickmc.sickcore.utils.mongo.databaseScope
import net.sickmc.sickcore.utils.fabric.getHead
import net.sickmc.sickcore.utils.mod_id
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import java.util.*
import kotlin.random.Random
import kotlin.random.nextInt

object CommonEvents {

    fun register() {
        join()
        mobDrops()
        quit()
    }

    private fun join() {
        ServerPlayConnectionEvents.JOIN.register { handler, _, server ->
            server.broadcastText(handler.player.displayName.contents.toString()) {
                text(" joined the server") {
                    color = 0x8C969A
                }
            }

            var player: SurvivalPlayer? = null
            databaseScope.launch {
                player = SurvivalPlayers.instance.reloadEntity(handler.player.uuid)
            }
            handler.player.customName = player!!.sickPlayer.displayName.full

            if (!handler.player.tags.contains("$mod_id.firstjoin.$mod_id")) {
                handler.player.addTag("$mod_id.firstjoin.$mod_id")
                handler.player.inventory.add(itemStack(Items.BEEF, 64) {})
            }
        }
    }

    private fun quit() {
        ServerPlayConnectionEvents.DISCONNECT.register { handler, server ->
            server.broadcastText(handler.player.displayName.contents.toString()) {
                text(" quit the server") {
                    color = 0x8C969A
                }
            }
        }
    }

    private fun mobDrops() {
        AttackEntityCallback.EVENT.register { player, level, hand, entity, result ->
            if (entity.getHead().isOnlyCreative || entity.getHead().isNaturalAvailable) InteractionResult.PASS
            if (Random.nextInt(1..64) == 0) {
                databaseScope.launch {
                    val gamePlayer = SurvivalPlayers.instance.getCachedEntity(player.uuid)
                    gamePlayer?.addHead(entity)
                    SurvivalPlayers.instance.reloadEntity(player.uuid)
                }
            }
            InteractionResult.PASS
        }
    }

    fun chat(message: String, player: ServerPlayer, server: MinecraftServer) {
        val sickPlayer = SickPlayers.instance.getCachedEntity(player.uuid)!!
        val newMessage = sickPlayer.displayName.full.append(literalText(message) { color = 0x8C969A })
    }

    fun command(
        source: CommandSourceStack,
        command: String,
        cir: CallbackInfoReturnable<Int>,
        dispatcher: CommandDispatcher<CommandSourceStack>
    ) {

    }

}