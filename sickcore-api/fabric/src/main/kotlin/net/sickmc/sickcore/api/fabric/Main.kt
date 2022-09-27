package net.sickmc.sickcore.api.fabric

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import net.minecraft.server.MinecraftServer
import net.sickmc.sickapi.SickPlayer
import net.sickmc.sickapi.playerCache
import net.sickmc.sickapi.rank.loadRanks
import net.sickmc.sickapi.util.databaseScope
import net.sickmc.sickapi.util.initMongo
import net.sickmc.sickapi.util.players
import net.silkmc.silk.core.event.Events
import net.silkmc.silk.core.event.Player
import net.silkmc.silk.core.event.Server
import org.litote.kmongo.eq
import java.util.*

lateinit var modScope: CoroutineScope
lateinit var server: MinecraftServer

@Suppress("unused")
fun init() {
    modScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    databaseScope.launch {
        initMongo()
        loadRanks()
    }
    FabricEntrypoint.init()
}

object FabricEntrypoint {
    private val playerPlayTimes = hashMapOf<UUID, Long>()
    private val playTimeMutex = Mutex()

    fun init() {
        serverStartListener
        playerListener
    }

    private val serverStartListener = Events.Server.preStart.listen { event -> server = event.server }

    private val playerListener = Events.Player.preLogin.listen { event ->
        databaseScope.launch {
            playerCache.set(
                event.player.uuid,
                players.find(SickPlayer::uuid eq event.player.uuid).first() ?: playerCache.create(event.player.uuid)
            )
            playTimeMutex.withLock {
                playerPlayTimes[event.player.uuid] = System.currentTimeMillis()
            }
        }
    }

    fun quit(uuid: UUID) {
        databaseScope.launch {
            var playTime = 0L
            playTimeMutex.withLock {
                playTime = System.currentTimeMillis() - (playerPlayTimes[uuid]
                    ?: error("cannot load playerTime from playerPlayTimes in FabricEntrypoint"))
            }
            playerCache.get(uuid)?.playtime?.plus(playTime)
        }
    }
}