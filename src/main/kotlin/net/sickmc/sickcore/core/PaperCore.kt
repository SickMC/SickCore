package net.sickmc.sickcore.core

import kotlinx.coroutines.launch
import net.axay.kspigot.event.listen
import net.sickmc.sickcore.core.modules.ModuleHandler
import net.axay.kspigot.main.KSpigot
import net.kyori.adventure.text.minimessage.MiniMessage
import net.sickmc.sickcore.core.games.Game
import net.sickmc.sickcore.core.commonPlayer.SickPlayers
import net.sickmc.sickcore.utils.mongo.databaseScope
import net.sickmc.sickcore.utils.paper.RankUpdateEventCaller
import net.sickmc.sickcore.utils.paper.gui.GUI
import net.sickmc.sickcore.utils.redis.subscribeRedis
import org.bukkit.Bukkit
import org.bukkit.event.player.PlayerLoginEvent
import java.util.*

class PaperCore(val launcher: KSpigot) : Core() {

    companion object{
        var instance: PaperCore? = null
    }

    fun init() {
        instance = this
        environment = Environment.PAPER
    }

    val moduleHandler = ModuleHandler
    val coreHandler = PaperCoreHandler

    suspend fun start(){
        Game.enable()
        coreHandler.enable()
        moduleHandler.start()
    }

    suspend fun shutdown(){
        Game.current.disable()
        moduleHandler.shutdown()
        coreHandler.initiateShutdown()
    }

}

object PaperCoreHandler{

    val core = PaperCore.instance!!
    suspend fun enable(){
        initiateStartUp()
        handleCustomEvents()
        enableMessageReceiver()
        handleSickPlayers()
        GUI.registerHandlers()
    }

    fun initiateShutdown(){
        Bukkit.getScoreboardManager().mainScoreboard.objectives.forEach{it.unregister()}
    }

    fun initiateStartUp() {
        Bukkit.getScoreboardManager().mainScoreboard.objectives.forEach { it.unregister() }
    }

    private fun handleSickPlayers(){
        listen<PlayerLoginEvent>() {
            databaseScope.launch {
                SickPlayers.reloadPlayer(it.player.uniqueId)
                it.player.scoreboard.objectives.forEach { it.unregister() }
            }
        }
    }

    suspend fun handleCustomEvents(){
        RankUpdateEventCaller().handleRankUpdate()
    }

    suspend fun enableMessageReceiver(){
        subscribeRedis("message"){
            val uuid = UUID.fromString(it.split('/')[0])
            val component = MiniMessage.miniMessage().deserialize(it.split('/')[1])
            if (Bukkit.getPlayer(uuid) == null)return@subscribeRedis
            Bukkit.getPlayer(uuid)?.sendMessage(component)
        }
    }

}

