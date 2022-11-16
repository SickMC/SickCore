package net.sickmc.sickcore.api.fabric

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.sickmc.sickapi.SickPlayer
import net.sickmc.sickapi.playerCache
import net.sickmc.sickapi.rank.Rank
import net.sickmc.sickapi.rank.RankGroup
import net.sickmc.sickapi.rank.loadRanks
import net.sickmc.sickapi.util.*
import net.sickmc.sickcore.api.fabric.chat.ChatManager
import net.sickmc.sickcore.api.fabric.commands.enderChestSeeCommand
import net.sickmc.sickcore.api.fabric.commands.invSeeCommand
import net.sickmc.sickcore.api.fabric.commands.playtimeCommand
import net.sickmc.sickcore.api.fabric.tablist.Tablist.Companion.currentTablist
import net.silkmc.silk.core.event.Events
import net.silkmc.silk.core.event.Player
import net.silkmc.silk.core.event.Server
import org.litote.kmongo.eq
import java.util.*

var modScope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
lateinit var server: MinecraftServer

fun main() {
    val defaultUUID = UUID.randomUUID()
    val defaultRankID = UUID.randomUUID()
    println(
        Json.encodeToString(
            RankGroup(
                defaultUUID, defaultUUID.toString(), "Warden", StaticColor(0x097d9e), mutableListOf(
                    Rank(defaultRankID, defaultRankID.toString(), "Warden", mutableListOf())
                ), mutableListOf(), "Warden", priority = 1
            )
        )
    )
}

@Suppress("unused")
fun init() {
    databaseScope.launch {
        initMongo()
        loadRanks()
    }
    FabricEntrypoint.init()
    playtimeCommand
    invSeeCommand
    enderChestSeeCommand
}

object FabricEntrypoint {
    val playerPlayTimes = hashMapOf<UUID, Long>()
    val playTimeMutex = Mutex()

    fun init() {
        serverStartListener
        playerListener
    }

    private val serverStartListener = Events.Server.preStart.listen { event -> server = event.server }

    private val playerListener = Events.Player.preLogin.listen { event ->
        databaseScope.launch {
            playerCache.set(
                event.player.uuid,
                players.find(SickPlayer::uuidString eq event.player.uuid.toString()).first()
                    ?: playerCache.create(event.player.uuid)
            )
            ChatManager.current?.handleJoin(event.player, event.player.server.playerList)
            currentTablist?.addPlayer(event.player)
            if (currentTablist != null) currentTablist!!.addPlayer(event.player)
            playTimeMutex.withLock {
                playerPlayTimes[event.player.uuid] = System.currentTimeMillis()
            }
        }
    }

    fun quit(player: ServerPlayer) {
        databaseScope.launch {
            currentTablist?.removePlayer(player)
            var playTime = 0L
            playTimeMutex.withLock {
                playTime = System.currentTimeMillis() - (playerPlayTimes[player.uuid]
                    ?: error("cannot load playerTime from playerPlayTimes in FabricEntrypoint"))
            }
            players.replaceOne(SickPlayer::uuidString eq player.stringUUID, playerCache.get(player.uuid)!!.also {
                it.playtime = it.playtime + playTime
            })
            playerCache.mutex.withLock {
                playerCache.cache.remove(player.uuid)
            }
        }
    }
}