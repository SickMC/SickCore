package net.sickmc.sickcore.games.survival

import kotlinx.coroutines.launch
import net.axay.fabrik.core.item.itemStack
import net.axay.fabrik.core.text.literalText
import net.axay.fabrik.core.text.sendText
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents
import net.fabricmc.fabric.api.event.player.AttackEntityCallback
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.network.chat.ChatType
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.EntityEvent
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.projectile.FireworkRocketEntity
import net.minecraft.world.item.Items
import net.sickmc.sickcore.commonPlayer.SickPlayer
import net.sickmc.sickcore.utils.Colors
import net.sickmc.sickcore.utils.fabric.*
import net.sickmc.sickcore.utils.mongo.databaseScope
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
            var sickPlayer: SickPlayer? = null
            databaseScope.launch {
                sickPlayer = SurvivalPlayers.instance.reloadEntity(handler.player.uuid).sickPlayer
            }

            if (!handler.player.tags.contains("$mod_id.firstjoin.$mod_id")) {
                handler.player.addTag("$mod_id.firstjoin.$mod_id")
                handler.player.inventory.add(itemStack(Items.BEEF, 64) {})
            }

            server.sendText(
                handler.player.name.copy().append(literalText(" joined the server!") { color = 0x8C969A })
            )
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
        AttackEntityCallback.EVENT.register { player, level, _, entity, _ ->
            if (player !is ServerPlayer) InteractionResult.PASS
            if (entity.isAlive) InteractionResult.PASS
            if (Random.nextInt(1 .. 1000000) == 51){
                val special = extraHeads.filter { it.rarity == MobHeadRarity.SPECIAL }.random()
                databaseScope.launch {
                    val gamePlayer = SurvivalPlayers.instance.getCachedEntity(player.uuid)
                    gamePlayer?.addHead(MobHead(true, false, createHead(special.texture), EntityKey(EntityType.ARROW), special))
                    SurvivalPlayers.instance.reloadEntity(player.uuid)
                }
                player.playSound(SoundEvents.ENDER_DRAGON_GROWL, 10.0F, 0F)
                (player as ServerPlayer).connection.send(ClientboundSetTitleTextPacket(literalText("Mob Head gathered") {
                    color = Colors.LIGHT_RED
                    bold = true
                }))
                player.connection.send(ClientboundSetSubtitleTextPacket(literalText(special.name) {
                    color = special.rarity.color
                }))
            }
            if (Random.nextInt(1 .. 1000000000) == 51){
                val mythic = extraHeads.filter { it.rarity == MobHeadRarity.MYTHIC }.random()
                databaseScope.launch {
                    val gamePlayer = SurvivalPlayers.instance.getCachedEntity(player.uuid)
                    gamePlayer?.addHead(MobHead(isOnlyCreative = true, isNaturalAvailable = false, createHead(mythic.texture), EntityKey(EntityType.ARROW), mythic))
                    SurvivalPlayers.instance.reloadEntity(player.uuid)
                }
                player.playSound(SoundEvents.ENDER_DRAGON_GROWL, 10.0F, 0F)
                (player as ServerPlayer).connection.send(ClientboundSetTitleTextPacket(literalText("Mob Head gathered") {
                    color = Colors.LIGHT_RED
                    bold = true
                }))
                player.connection.send(ClientboundSetSubtitleTextPacket(literalText(mythic.name) {
                    color = mythic.rarity.color
                }))
                level.server!!.playerList.broadcastSystemMessage(player.displayName.copy().append(literalText(" found a mythic head:") { color = Colors.LIGHT_RED
                    text(mythic.getDisplayName(), inheritStyle = false) {  }
                }), ChatType.CHAT)
                val firework = FireworkRocketEntity(level, itemStack(Items.FIREWORK_ROCKET){}, player)
                player.connection.send(ClientboundAddEntityPacket(firework))
                level.broadcastEntityEvent(firework, EntityEvent.FIREWORKS_EXPLODE)
            }
            val head = entity.getHead()
            if (head.isOnlyCreative || head.isNaturalAvailable) InteractionResult.PASS
            if (Random.nextInt(1..64) == 51) {
                databaseScope.launch {
                    val gamePlayer = SurvivalPlayers.instance.getCachedEntity(player.uuid)
                    gamePlayer?.addHead(head)
                    SurvivalPlayers.instance.reloadEntity(player.uuid)
                }
                player.playSound(SoundEvents.NOTE_BLOCK_CHIME, 10.0F, 0F)
                (player as ServerPlayer).connection.send(ClientboundSetTitleTextPacket(literalText("Mob Head gathered") {
                    color = Colors.LIGHT_RED
                    bold = true
                }))
                player.connection.send(ClientboundSetSubtitleTextPacket(literalText(head.attributes.name) {
                    color = head.attributes.rarity.color
                }))
            }
            InteractionResult.PASS
        }
    }

    private fun death() {
        ServerPlayerEvents.ALLOW_DEATH.register { player, _, _ ->
            databaseScope.launch {
                val gamePlayer = SurvivalPlayers.instance.getCachedEntity(player.uuid)
                gamePlayer?.addDeath()
                SurvivalPlayers.instance.reloadEntity(player.uuid)
            }
            true
        }
    }

}