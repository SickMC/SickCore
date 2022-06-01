package net.sickmc.sickcore.utils.fabric

import net.minecraft.network.chat.ChatSender
import net.minecraft.network.chat.ChatType
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.PlayerChatMessage
import net.minecraft.server.level.ServerPlayer
import java.util.UUID

fun ServerPlayer.sendMessage(message: Component){
    this.sendChatMessage(PlayerChatMessage.unsigned(message), ChatSender(UUID.randomUUID(), Component.empty()), ChatType.CHAT)
}