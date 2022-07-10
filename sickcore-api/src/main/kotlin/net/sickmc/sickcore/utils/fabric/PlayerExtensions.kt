package net.sickmc.sickcore.utils.fabric

import net.minecraft.network.chat.*
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player
import net.sickmc.sickcore.commonPlayer.SickPlayer
import net.sickmc.sickcore.commonPlayer.SickPlayers
import java.util.*

fun ServerPlayer.sendMessage(message: MutableComponent) {
    this.sendChatMessage(
        PlayerChatMessage.unsigned(message),
        ChatSender(UUID.randomUUID(), Component.empty()),
        ChatType.SYSTEM
    )
}

val ServerPlayer.sickPlayer: SickPlayer?
    get() = SickPlayers.instance.getCachedEntity(this.uuid)

val Player.sickPlayer: SickPlayer?
    get() = SickPlayers.instance.getCachedEntity(this.uuid)