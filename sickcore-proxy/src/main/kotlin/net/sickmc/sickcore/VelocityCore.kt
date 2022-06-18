package net.sickmc.sickcore

import com.velocitypowered.api.event.connection.PostLoginEvent
import kotlinx.coroutines.launch
import net.sickmc.sickcore.commonPlayer.SickPlayers
import net.sickmc.sickcore.utils.listenVelocity
import net.sickmc.sickcore.utils.Environment
import net.sickmc.sickcore.utils.EventManager
import net.sickmc.sickcore.utils.mongo.databaseScope
import net.sickmc.sickcore.utils.test

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
        EventManager.register()
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
                SickPlayers.instance.reloadEntity(it.player.uniqueId)
            }
        }
    }

}