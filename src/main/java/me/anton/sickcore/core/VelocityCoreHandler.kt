package me.anton.sickcore.core

import com.velocitypowered.api.event.connection.DisconnectEvent
import com.velocitypowered.api.event.connection.PostLoginEvent
import kotlinx.coroutines.launch
import me.anton.sickcore.core.player.SickPlayers
import me.anton.sickcore.utils.velocity.RankUpdateEventCaller
import org.bson.Document

class VelocityCoreHandler {

    val core = VelocityCore.instance!!

    init {
        handlePlayerList()
        handleSickPlayers()
    }

    private fun handlePlayerList(){
        listenVelocity<PostLoginEvent> {
            core.onlinePlayers.add(it.player)
        }

        listenVelocity<DisconnectEvent> {
            core.onlinePlayers.remove(it.player)
        }
    }

    private fun handleSickPlayers(){
        listenVelocity<PostLoginEvent> {
            Core.instance.databaseScope.launch {
                if (SickPlayers.collection.getDocument("uuid", it.player.uniqueId.toString()) == null)SickPlayers.createPlayer(it.player.uniqueId)
                else SickPlayers.reloadPlayer(it.player.uniqueId)

                Core.instance.redisConnection.client.set(it.player.uniqueId.toString(), "{\n" +
                        "  \"joinedAt\": ${System.currentTimeMillis()},\n" +
                        "\"ip\": ${it.player.remoteAddress.hostName}" +
                        "}")
            }
        }

        listenVelocity<DisconnectEvent> {
            Core.instance.databaseScope.launch {
                val playerData = Core.instance.redisConnection.client.get("${it.player.uniqueId}")!!

                val doc = Document.parse(playerData)
                val player = SickPlayers.getSickPlayer(it.player.uniqueId)!!
                player.document.document["playtime"] = player.document.document.getLong("playtime") + (System.currentTimeMillis() - doc.getLong("joinedAt"))
                player.document.save()
                Core.instance.redisConnection.client.del(it.player.uniqueId.toString())
            }
        }
    }

    suspend fun handleCustomEvents(){
        RankUpdateEventCaller().handleRankUpdate()
    }

}

inline fun <reified T> listenVelocity(crossinline callback: (T) -> Unit) {
    VelocityCore.instance!!.base.server.eventManager.register(VelocityCore.instance!!.base, T::class.java) { callback(it) }
}