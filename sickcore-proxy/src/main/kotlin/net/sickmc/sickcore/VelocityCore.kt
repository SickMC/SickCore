package net.sickmc.sickcore

import com.velocitypowered.api.event.connection.DisconnectEvent
import com.velocitypowered.api.event.connection.PostLoginEvent
import kotlinx.coroutines.launch
import net.sickmc.sickcore.commonPlayer.SickPlayers
import net.sickmc.sickcore.utils.listenVelocity
import net.sickmc.sickcore.utils.Environment
import net.sickmc.sickcore.utils.EventManager
import net.sickmc.sickcore.utils.mongo.databaseScope
import net.sickmc.sickcore.utils.mongo.players
import net.sickmc.sickcore.utils.mongo.replace
import net.sickmc.sickcore.utils.redis.kreds
import net.sickmc.sickcore.utils.test
import org.bson.Document

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
        if (!test) kreds.auth(System.getenv("REDIS_PASSWORD")) else kreds.auth(System.getProperty("REDIS_PASSWORD"))
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
                SickPlayers.instance.getEntity(it.player.uniqueId)

                kreds.set(
                    it.player.uniqueId.toString(), "{\n" +
                            "  \"joinedAt\": ${System.currentTimeMillis()},\n" +
                            "  \"ip\": \"${it.player.remoteAddress.hostName}\"\n" +
                            "}"
                )
            }
        }

        listenVelocity<DisconnectEvent> {
            databaseScope.launch {
                val playerData = kreds.get(it.player.uniqueId.toString())!!

                val doc = Document.parse(playerData)
                val player = SickPlayers.instance.reloadEntity(it.player.uniqueId)
                player.document["playtime"] =
                    player.document.getLong("playtime") + (System.currentTimeMillis() - doc.getLong("joinedAt"))
                players.replace("uuid", player.uniqueID.toString(), player.document)
                kreds.del(it.player.uniqueId.toString())
            }
        }
    }

}