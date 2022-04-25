package net.sickmc.sickcore.core

import net.sickmc.sickcore.core.modules.ModuleHandler
import net.axay.kspigot.main.KSpigot
import org.bukkit.entity.Player
class PaperCore(val launcher: KSpigot) : Core() {

    companion object{
        var instance: PaperCore? = null
    }

    init {
        instance = this
        environment = Environment.PAPER
    }

    val moduleHandler = ModuleHandler()
    var onlinePlayers = ArrayList<Player>()
    private val coreHandler = PaperCoreHandler()

    suspend fun start(){
        coreHandler.handleServerStartup()
        coreHandler.handleMessages()
        coreHandler.handleCustomEvents()
        moduleHandler.start()
    }

    suspend fun shutdown(){
        moduleHandler.shutdown()
        coreHandler.handleServerShutdown()
    }

}