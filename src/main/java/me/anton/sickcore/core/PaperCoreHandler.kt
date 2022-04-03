package me.anton.sickcore.core

import kotlinx.coroutines.launch
import me.anton.sickcore.core.player.SickPlayers
import net.axay.kspigot.event.listen
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerLoginEvent
import org.bukkit.event.player.PlayerQuitEvent

class PaperCoreHandler {

    val core = PaperCore.instance!!

    init {
        handlePlayerList()
        handleSickPlayers()
    }

    private fun handlePlayerList(){
        listen<PlayerJoinEvent> {
            core.onlinePlayers.add(it.player)
        }

        listen<PlayerQuitEvent> {
            core.onlinePlayers.remove(it.player)
        }
    }

    private fun handleSickPlayers(){
        listen<PlayerLoginEvent>() {
            Core.instance.databaseScope.launch {
                if (SickPlayers.collection.getDocument("uuid", it.player.uniqueId.toString()) == null) SickPlayers.createPlayer(it.player.uniqueId)
                else SickPlayers.reloadPlayer(it.player.uniqueId)
            }
        }
    }

}