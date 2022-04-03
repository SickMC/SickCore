package me.anton.sickcore.core

import com.velocitypowered.api.event.connection.DisconnectEvent
import com.velocitypowered.api.event.connection.PostLoginEvent
import kotlinx.coroutines.launch
import me.anton.sickcore.core.player.SickPlayer
import me.anton.sickcore.core.player.SickPlayers

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
            }
        }
    }

}

inline fun <reified T> listenVelocity(crossinline callback: (T) -> Unit) {
    VelocityCore.instance!!.base.server.eventManager.register(VelocityCore.instance!!.base, T::class.java) { callback(it) }
}