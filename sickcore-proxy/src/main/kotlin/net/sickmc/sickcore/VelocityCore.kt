package net.sickmc.sickcore

import com.velocitypowered.api.event.connection.PostLoginEvent
import kotlinx.coroutines.launch
import net.sickmc.sickcore.commonPlayer.SickPlayers
import net.sickmc.sickcore.utils.listenVelocity
import net.sickmc.sickcore.utils.Environment
import net.sickmc.sickcore.utils.mongo.databaseScope

class VelocityCore(val base: VelocityBootstrap) {

    companion object {
        lateinit var instance: VelocityCore
    }

    init {
        instance = this
        proxyPlugin = base
        proxyServer = base.pserver
    }

    val moduleHandler = ModuleHandler(Environment.VELOCITY)
    val coreHandler = VelocityCoreHandler
    suspend fun start() {
        coreHandler.initiateStartUp()
        moduleHandler.start()
    }

    suspend fun shutdown() {
        moduleHandler.shutdown()
        coreHandler.initiateShutdown()
    }

}

object VelocityCoreHandler {

    val core = VelocityCore.instance

    suspend fun initiateStartUp() {
        SickPlayers()
        handleSickPlayers()
    }

    suspend fun initiateShutdown() {

    }

    private fun handleSickPlayers() {
        listenVelocity<PostLoginEvent> {
            databaseScope.launch {
                if (SickPlayers.instance.getEntity(it.player.uniqueId) == null)SickPlayers.instance.createEntity(it.player.uniqueId)
            }
        }
    }

}