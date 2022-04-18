package net.sickmc.sickcore.core

import kotlinx.coroutines.launch
import net.sickmc.sickcore.core.player.SickPlayers
import net.sickmc.sickcore.utils.mongo.databaseScope
import net.sickmc.sickcore.utils.paper.InventoryBuilders
import net.sickmc.sickcore.utils.paper.ItemBuilders
import net.sickmc.sickcore.utils.paper.RankUpdateEventCaller
import net.sickmc.sickcore.utils.redis.subscribeRedis
import net.axay.kspigot.event.listen
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
            databaseScope.launch {
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

    private fun handleGUIs(){
        ItemBuilders().registerBuilders()
        InventoryBuilders().registerInventoryHandlers()
    }

}