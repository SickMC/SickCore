package net.sickmc.sickcore.games.survival

import kotlinx.coroutines.launch
import net.axay.fabrik.core.item.itemStack
import net.axay.fabrik.core.item.setCustomName
import net.axay.fabrik.core.item.setSkullTexture
import net.axay.fabrik.core.text.broadcastText
import net.axay.fabrik.core.text.literalText
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents
import net.fabricmc.fabric.api.event.player.AttackEntityCallback
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.minecraft.network.chat.ChatType
import net.minecraft.network.chat.Style
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.EntityEvent
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.projectile.FireworkRocketEntity
import net.minecraft.world.item.Items
import net.sickmc.sickcore.commonPlayer.SickPlayer
import net.sickmc.sickcore.commonPlayer.SickPlayers
import net.sickmc.sickcore.utils.Colors
import net.sickmc.sickcore.utils.fabric.*
import net.sickmc.sickcore.utils.mongo.databaseScope
import kotlin.random.Random
import kotlin.random.nextInt

object CommonEvents {

    fun register() {
        join()
        mobDrops()
        death()
        quit()
    }


    private fun join() {
        ServerPlayConnectionEvents.JOIN.register { handler, _, server ->

            databaseScope.launch {
                val sickPlayer = SurvivalPlayers.instance.reloadEntity(handler.player.uuid).sickPlayer

                server.playerList.broadcastSystemMessage(
                    sickPlayer.displayName.getName().copy().append(literalText(" joined the server!") {
                        color = Colors.LIGHT_GRAY
                        bold = false
                    }), ChatType.CHAT
                )
            }

            if (!handler.player.tags.contains("$mod_id.firstjoin.$mod_id")) {
                handler.player.addTag("$mod_id.firstjoin.$mod_id")
                handler.player.inventory.add(itemStack(Items.COOKED_BEEF, 64) {})
            }
        }
    }

    private fun quit() {
        ServerPlayConnectionEvents.DISCONNECT.register { handler, server ->
            val sickPlayer = handler.player.sickPlayer
            server.playerList.broadcastSystemMessage(
                sickPlayer!!.displayName.getName().copy()
                    .append(literalText(" quit the server!") {
                        color = Colors.LIGHT_GRAY
                        bold = false
                    }), ChatType.CHAT
            )
        }
    }

    private fun mobDrops() {
        AttackEntityCallback.EVENT.register { player, level, _, entity, _ ->
            if (!entity.isAlive && player is ServerPlayer) {
                if (Random.nextInt(1..1000000) == 51) {
                    val special = extraHeads.filter { it.rarity == MobHeadRarity.SPECIAL }.random()
                    addPlayerHead(
                        player, MobHead(
                            isOnlyCreative = true,
                            isNaturalAvailable = false,
                            itemStack(Items.PLAYER_HEAD) {
                                setSkullTexture(special.texture)
                            },
                            EntityKey(EntityType.ARROW),
                            special
                        ), SoundEvents.ENDER_DRAGON_GROWL
                    )
                }
                if (Random.nextInt(1..1000000000) == 51) {
                    val mythic = extraHeads.filter { it.rarity == MobHeadRarity.MYTHIC }.random()
                    addPlayerHead(
                        player, MobHead(
                            isOnlyCreative = true,
                            isNaturalAvailable = false,
                            itemStack(Items.PLAYER_HEAD) {
                                setSkullTexture(mythic.texture)
                            },
                            EntityKey(EntityType.ARROW),
                            mythic
                        ), SoundEvents.ENDER_DRAGON_GROWL
                    )
                    level.server!!.playerList.broadcastSystemMessage(
                        player.displayName.copy().append(literalText(" found a mythic head:") {
                            color = Colors.LIGHT_RED
                            text(mythic.getDisplayName(), inheritStyle = false) { }
                        }), ChatType.CHAT
                    )
                    val firework = FireworkRocketEntity(level, itemStack(Items.FIREWORK_ROCKET) {}, player)
                    player.connection.send(ClientboundAddEntityPacket(firework))
                    level.broadcastEntityEvent(firework, EntityEvent.FIREWORKS_EXPLODE)
                    player.addItem(itemStack(Items.PLAYER_HEAD) {
                        setSkullTexture(mythic.texture)
                    }.setCustomName(mythic.name) {
                        color = mythic.rarity.color
                    })
                }
                if (Random.nextInt(1..64) == 51) {
                    val head = entity.getHead()
                    if (!head.isOnlyCreative && !head.isNaturalAvailable) addPlayerHead(player, head)
                }
            }
            InteractionResult.PASS
        }
    }

    private fun addPlayerHead(player: ServerPlayer, head: MobHead, sound: SoundEvent = SoundEvents.NOTE_BLOCK_CHIME) {
        databaseScope.launch {
            val gamePlayer = SurvivalPlayers.instance.getCachedEntity(player.uuid)
            gamePlayer?.addHead(head)
            SurvivalPlayers.instance.reloadEntity(player.uuid)
        }
        player.playSound(sound, 10.0F, 0F)
        player.connection.send(ClientboundSetTitleTextPacket(literalText("Mob Head gathered") {
            color = Colors.LIGHT_RED
            bold = true
        }))
        player.connection.send(ClientboundSetSubtitleTextPacket(literalText(head.attributes.name) {
            color = head.attributes.rarity.color
        }))
        player.addItem(head.item.setHoverName(head.attributes.getDisplayName()))
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