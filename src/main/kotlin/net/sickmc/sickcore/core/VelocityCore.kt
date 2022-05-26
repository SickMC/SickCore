package net.sickmc.sickcore.core

import com.velocitypowered.api.event.connection.DisconnectEvent
import com.velocitypowered.api.event.connection.LoginEvent
import kotlinx.coroutines.launch
import net.sickmc.sickcore.core.commonPlayer.SickPlayers
import net.sickmc.sickcore.core.modules.ModuleHandler
import net.sickmc.sickcore.utils.EventManager
import net.sickmc.sickcore.utils.mongo.databaseScope
import net.sickmc.sickcore.utils.mongo.players
import net.sickmc.sickcore.utils.mongo.replace
import net.sickmc.sickcore.utils.mongo.retrieveOne
import net.sickmc.sickcore.utils.redis.kreds
import org.bson.Document

class VelocityCore(val base: VelocityBootstrap) {

    companion object{
        var instance: VelocityCore? = null
    }

    init {
        instance = this
        environment = Environment.VELOCITY
    }

    val moduleHandler = ModuleHandler
    val coreHandler = VelocityCoreHandler
    suspend fun start(){
        coreHandler.initiateStartUp()
        moduleHandler.start()
        EventManager.register()
    }

    suspend fun shutdown(){
        moduleHandler.shutdown()
        coreHandler.initiateShutdown()
    }

}

object VelocityCoreHandler {

    val core = VelocityCore.instance!!

    suspend fun initiateStartUp(){
        SickPlayers()
        handleSickPlayers()
    }

    suspend fun initiateShutdown(){

    }
    private fun handleSickPlayers(){
        listenVelocity<LoginEvent> {
            databaseScope.launch {
                if (players.retrieveOne("uuid", it.player.uniqueId.toString()) == null) SickPlayers.instance.createEntity(it.player.uniqueId)
                else SickPlayers.instance.reloadEntity(it.player.uniqueId)

                kreds.set(it.player.uniqueId.toString(),"{\n" +
                        "  \"joinedAt\": ${System.currentTimeMillis()},\n" +
                        "  \"ip\": \"${it.player.remoteAddress.hostName}\"\n" +
                        "}")
            }
        }

        listenVelocity<DisconnectEvent> {
            databaseScope.launch {
                val playerData = kreds.get("${it.player.uniqueId}")!!

                val doc = Document.parse(playerData)
                val player = SickPlayers.instance.reloadEntity(it.player.uniqueId)
                player.document["playtime"] = player.document.getLong("playtime") + (System.currentTimeMillis() - doc.getLong("joinedAt"))
                players.replace("uuid", player.uniqueID.toString(), player.document)
                kreds.del(it.player.uniqueId.toString())
            }
        }
    }

}

inline fun <reified T> listenVelocity(crossinline callback: (T) -> Unit) {
    VelocityCore.instance!!.base.server.eventManager.register(VelocityCore.instance!!.base, T::class.java) { callback(it) }
}