package net.sickmc.sickcore.games.survival

import com.mojang.brigadier.CommandDispatcher
import kotlinx.coroutines.launch
import net.axay.fabrik.core.item.itemStack
import net.axay.fabrik.core.text.broadcastText
import net.axay.fabrik.core.text.literalText
import net.axay.fabrik.core.text.sendText
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents.AllowDeath
import net.fabricmc.fabric.api.event.player.AttackEntityCallback
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Items
import net.sickmc.sickcore.commonPlayer.SickPlayers
import net.sickmc.sickcore.utils.Colors
import net.sickmc.sickcore.utils.mongo.databaseScope
import net.sickmc.sickcore.utils.fabric.getHead
import net.sickmc.sickcore.utils.fabric.mod_id
import net.sickmc.sickcore.utils.fabric.textures
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import kotlin.random.Random
import kotlin.random.nextInt

object CommonEvents {

    fun register() {
        join()
        mobDrops()
        quit()
        death()
    }

    private fun join() {
        ServerPlayConnectionEvents.JOIN.register { handler, _, server ->
            server.sendText(
                handler.player.displayName.copy().append(literalText(" joined the server!") { color = 0x8C969A })
            )

            var player: SurvivalPlayer? = null
            databaseScope.launch {
                player = SurvivalPlayers.instance.reloadEntity(handler.player.uuid)
            }

            if (!handler.player.tags.contains("$mod_id.firstjoin.$mod_id")) {
                handler.player.addTag("$mod_id.firstjoin.$mod_id")
                handler.player.inventory.add(itemStack(Items.BEEF, 64) {})
            }
        }
    }

    private fun quit() {
        ServerPlayConnectionEvents.DISCONNECT.register { handler, server ->
            server.sendText(
                handler.player.displayName.copy().append(literalText(" quit the server!") { color = 0x8C969A })
            )
        }
    }

    private fun mobDrops() {
        AttackEntityCallback.EVENT.register { player, level, hand, entity, result ->
            if (player !is ServerPlayer) InteractionResult.PASS
            if (entity.isAlive) InteractionResult.PASS
            if (entity.getHead().isOnlyCreative || entity.getHead().isNaturalAvailable) InteractionResult.PASS
            if (Random.nextInt(1..64) == 51) {
                databaseScope.launch {
                    val gamePlayer = SurvivalPlayers.instance.getCachedEntity(player.uuid)
                    gamePlayer?.addHead(entity)
                    SurvivalPlayers.instance.reloadEntity(player.uuid)
                }
                player.playSound(SoundEvents.NOTE_BLOCK_CHIME)
                (player as ServerPlayer).connection.send(ClientboundSetTitleTextPacket(literalText("TT") {
                    currentStyle.withObfuscated(true)
                    color = Colors.GOLD
                    text("Mob Head gathered") {
                        color = Colors.LIGHT_RED
                        bold = true
                    }
                    text("TT") {
                        currentStyle.withObfuscated(true)
                        color = Colors.GOLD
                    }
                }))
                player.connection.send(ClientboundSetSubtitleTextPacket(literalText(entity.getHead().attributes.name) {
                    color = entity.getHead().attributes.rarity.color
                }))
            }
            InteractionResult.PASS
        }
    }

    private fun death() {
        ServerPlayerEvents.ALLOW_DEATH.register { player, damageSource, damageAmount ->
            databaseScope.launch {
                val gamePlayer = SurvivalPlayers.instance.getCachedEntity(player.uuid)
                gamePlayer?.addDeath()
                SurvivalPlayers.instance.reloadEntity(player.uuid)
            }
            true
        }
    }

    fun chat(message: String, player: ServerPlayer, server: MinecraftServer) {
        val sickPlayer = SickPlayers.instance.getCachedEntity(player.uuid)!!
        val newMessage = sickPlayer.displayName.getName().append(literalText(message) { color = 0x8C969A })
    }

    fun command(
        source: CommandSourceStack,
        command: String,
        cir: CallbackInfoReturnable<Int>,
        dispatcher: CommandDispatcher<CommandSourceStack>
    ) {

    }

}