package me.anton.sickcore.core

import kotlinx.coroutines.launch
import me.anton.sickcore.core.player.SickPlayers
import me.anton.sickcore.utils.paper.InventoryBuilders
import me.anton.sickcore.utils.paper.ItemBuilders
import me.anton.sickcore.utils.paper.RankUpdateEventCaller
import me.anton.sickcore.utils.redis.subscribeRedis
import me.arcaniax.hdb.api.DatabaseLoadEvent
import me.arcaniax.hdb.api.HeadDatabaseAPI
import net.axay.kspigot.event.listen
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerLoginEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.UUID

class PaperCoreHandler {

    val core = PaperCore.instance!!

    init {
        handlePlayerList()
        handleSickPlayers()
        handleGUIs()
        handleHeadDBAPI()
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
                SickPlayers.reloadPlayer(it.player.uniqueId)
            }
        }
    }

    suspend fun handleCustomEvents(){
        RankUpdateEventCaller().handleRankUpdate()
    }

    suspend fun handleMessages(){
        subscribeRedis("message"){
            val uuid = UUID.fromString(it.split('/')[0])
            val component = MiniMessage.miniMessage().deserialize(it.split('/')[1])
            if (Bukkit.getPlayer(uuid) == null)return@subscribeRedis
            Bukkit.getPlayer(uuid)?.sendMessage(component)
        }
    }

    fun handleGUIs(){
        ItemBuilders().registerBuilders()
        InventoryBuilders().registerInventoryHandlers()
    }

    fun handleHeadDBAPI(){
        listen<DatabaseLoadEvent> {
            PaperCore.instance?.headDBAPI = HeadDatabaseAPI()
        }
    }

}