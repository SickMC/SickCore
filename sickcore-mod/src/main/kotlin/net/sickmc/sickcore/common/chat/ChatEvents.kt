package net.sickmc.sickcore.common.chat

import net.minecraft.advancements.Advancement
import net.minecraft.server.PlayerAdvancements
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.damagesource.CombatTracker
import net.minecraft.world.level.Level

data class QuitEvent(val player: ServerPlayer, val level: Level)
data class JoinEvent(val player: ServerPlayer, val level: Level)
data class ChatEvent(val player: ServerPlayer, val message: String, val level: Level)
data class DeathEvent(
    val player: ServerPlayer, val level: Level, val tracker: CombatTracker
)
data class DeathNameEvent(val player: ServerPlayer, val level: Level)

data class AdvancementEvent(
    val player: ServerPlayer,
    val level: Level,
    val advancement: Advancement,
    val playerAdvancements: PlayerAdvancements
)
data class GetAttackerNameEvent(val player: ServerPlayer, val level: Level)