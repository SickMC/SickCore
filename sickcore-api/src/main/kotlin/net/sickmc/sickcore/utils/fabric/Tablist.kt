package net.sickmc.sickcore.utils.fabric

import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket
import net.minecraft.network.protocol.game.ClientboundTabListPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player
import net.minecraft.world.scores.Team

class Tablist(private val server: MinecraftServer, private val builder: TablistBuilder){

    val players = hashMapOf<ServerPlayer, PlayerTablistBuilder>()

    inline fun modify(player: ServerPlayer, crossinline modifier: PlayerTablistBuilder.() -> Unit) {
        if (players[player] == null)players[player] = PlayerTablistBuilder(player).apply(modifier)
        else players[player]!!.apply(modifier)
        reload(player)
    }

    fun reload(player: ServerPlayer) {
        if (players[player] == null)players[player] = builder.generate.invoke(player)
        val playerBuilder = players[player]!!
        val sickPlayer = player.sickPlayer!!

        player.connection.send(ClientboundTabListPacket(playerBuilder.header, playerBuilder.footer))

        player.server.scoreboard.playerTeams.forEach { it.scoreboard.removePlayerTeam(it) }

        val team = player.server.scoreboard.addPlayerTeam("${sickPlayer.rank.getParent().priority}-${sickPlayer.name}")
        team.color = ChatFormatting.getById(playerBuilder.color)
        team.playerPrefix = playerBuilder.prefix
        team.playerSuffix = playerBuilder.suffix
        team.nameTagVisibility = playerBuilder.nameTagVisibility
        team.collisionRule = playerBuilder.collisionRule
        team.deathMessageVisibility = playerBuilder.deathMessageVisibility

        player.connection.send(ClientboundSetPlayerTeamPacket.createPlayerPacket(team, sickPlayer.name, ClientboundSetPlayerTeamPacket.Action.ADD))
    }

    fun reloadAll(){
        server.playerList.players.forEach(this::reload)
    }

}

class PlayerTablistBuilder(
    val player: ServerPlayer
) {

    var header: MutableComponent = Component.empty()
    var footer: MutableComponent = Component.empty()
    var prefix: MutableComponent = Component.empty()
    var suffix: MutableComponent = Component.empty()
    var color: Int = 0
    var nameTagVisibility: Team.Visibility = Team.Visibility.ALWAYS
    var collisionRule: Team.CollisionRule = Team.CollisionRule.ALWAYS
    var deathMessageVisibility: Team.Visibility = Team.Visibility.ALWAYS

}

class TablistBuilder(val generate: Player.() -> PlayerTablistBuilder)