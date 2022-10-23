package net.sickmc.sickcore.api.fabric.tablist

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import net.minecraft.network.chat.Component
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket
import net.minecraft.network.protocol.game.ClientboundTabListPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.silkmc.silk.core.Silk
import net.silkmc.silk.core.annotations.DelicateSilkApi
import net.silkmc.silk.core.annotations.InternalSilkApi
import net.silkmc.silk.core.packet.sendPacket
import net.silkmc.silk.core.task.initWithServerAsync
import net.silkmc.silk.core.task.silkCoroutineScope
import net.silkmc.silk.core.text.literal
import net.silkmc.silk.game.sideboard.SideboardLine
import java.util.*


/**
 * A tablist configuration which will be displayed to all players.
 * You can reload the [playerNames] using the [updateNames]/[updateName] function.
 *
 * **Note:** You probably want to build this class using the tablist builder API. See [tablist]!
 */

class Tablist(
    private val nameGenerator: (suspend ServerPlayer.() -> Pair<Component, String>)?,
    private val headers: List<SideboardLine>,
    private val footers: List<SideboardLine>
) {

    companion object {
        @JvmStatic
        var currentTablist: Tablist? = null
    }

    val currentHeaders: MutableList<Component> = mutableListOf()
    val currentFooters: MutableList<Component> = mutableListOf()

    @OptIn(InternalSilkApi::class, DelicateSilkApi::class)
    val headerFooterDeferred = initWithServerAsync {
        silkCoroutineScope.launch {
            currentFooters.preFill(footers)
            currentHeaders.preFill(headers)
        }

        Silk.currentServer?.sendTablistUpdate(currentHeaders, currentFooters)

        headers.forEachIndexed { index, scoreboardLine ->
            silkCoroutineScope.launch {
                scoreboardLine.textFlow.collect {
                    if (currentHeaders.getOrNull(index) == null) currentHeaders += it
                    else currentHeaders[index] = it
                    Silk.currentServer?.sendTablistUpdate(currentHeaders, currentFooters)
                }
            }
        }
        footers.forEachIndexed { index, scoreboardLine ->
            silkCoroutineScope.launch {
                scoreboardLine.textFlow.collect {
                    if (currentFooters.getOrNull(index) == null) currentFooters += it
                    else currentFooters[index] = it
                    Silk.currentServer?.sendTablistUpdate(currentHeaders, currentFooters)
                }
            }
        }
    }

    val playerNames = HashMap<UUID, Pair<Component, String>>()

    /**
     * Regenerates all player names and priorities
     */
    @OptIn(DelicateSilkApi::class)
    fun updateNames() {
        if (nameGenerator == null) return
        val server = Silk.currentServer
        if (server?.isRunning == false) return

        silkCoroutineScope.launch {
            server?.playerList?.players?.forEach {
                val nameGen = nameGenerator.invoke(it)
                playerNames[it.uuid] = nameGen
            }

            playerNames.forEach { (uuid, pair) ->
                val player = server?.playerList?.getPlayer(uuid) ?: return@forEach
                val team = player.scoreboard.getPlayerTeam(pair.second) ?: player.scoreboard.addPlayerTeam(
                    pair.second
                )
                player.scoreboard.addPlayerToTeam(player.scoreboardName, team)
            }

            server?.playerList?.players?.sendPacket(
                ClientboundPlayerInfoPacket(
                    ClientboundPlayerInfoPacket.Action.UPDATE_DISPLAY_NAME, server.playerList.players
                )
            )
        }
    }

    /**
     * Regenerate the name and priority of the [player]
     *
     * @param player the player whose attributes should be updated
     */
    @OptIn(DelicateSilkApi::class)
    fun updateName(player: ServerPlayer) {
        if (nameGenerator == null) return
        if (Silk.currentServer?.isRunning == false) return
        silkCoroutineScope.launch {
            val nameGen = nameGenerator.invoke(player)
            playerNames[player.uuid] = nameGen
            val team = player.scoreboard.getPlayerTeam(nameGen.second) ?: player.scoreboard.addPlayerTeam(
                nameGen.second
            )
            player.scoreboard.addPlayerToTeam(player.scoreboardName, team)

            player.server.playerList.players.sendPacket(
                ClientboundPlayerInfoPacket(
                    ClientboundPlayerInfoPacket.Action.UPDATE_DISPLAY_NAME, player
                )
            )
        }
    }

    /**
     * Generates the [player]'s name and adds it to the [playerNames] list
     *
     * @param player the player whose name should be added
     */
    @OptIn(DelicateSilkApi::class)
    fun addPlayer(player: ServerPlayer) {
        if (nameGenerator == null) return
        silkCoroutineScope.launch {
            val nameGen = nameGenerator.invoke(player)
            playerNames[player.uuid] = nameGen
            player.server.sendTablistUpdate(currentHeaders, currentFooters)
            val team = player.scoreboard.getPlayerTeam(nameGen.second) ?: player.scoreboard.addPlayerTeam(
                nameGen.second
            )
            player.scoreboard.addPlayerToTeam(player.scoreboardName, team)
            player.server.playerList.players.sendPacket(
                ClientboundPlayerInfoPacket(
                    ClientboundPlayerInfoPacket.Action.UPDATE_DISPLAY_NAME, player
                )
            )
        }
    }

    /**
     * Removes the [player]'s name from the [playerNames] list
     *
     * @param player the player whose name should be removed
     */
    fun removePlayer(player: ServerPlayer) {
        playerNames.remove(player.uuid)
    }

    init {
        currentTablist = this
    }
}

private fun List<Component>.merge(): Component {
    return Component.empty().also {
        this.forEachIndexed { i, component ->
            it.append(component)
            if (this.size - 1 > i) it.append("\n".literal)
        }
    }
}

private fun MinecraftServer?.sendTablistUpdate(headers: List<Component>, footers: List<Component>) {
    this?.playerList?.players?.forEach {
        it.connection.send(
            ClientboundTabListPacket(
                headers.merge(), footers.merge()
            )
        )
    }
}

private suspend fun MutableList<Component>.preFill(source: List<SideboardLine>) {
    if (this.size >= source.size) return
    repeat(source.size - this.size) { this += Component.empty() }
    source.filterIsInstance<SideboardLine.Updatable>().forEachIndexed { index, scoreboardLine ->
        this[index] = scoreboardLine.textFlow.last()
    }
    source.filterIsInstance<SideboardLine.Static>().forEachIndexed { index, scoreboardLine ->
        this[index] = scoreboardLine.textFlow.last()
    }
}