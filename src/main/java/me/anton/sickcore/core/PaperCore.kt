package me.anton.sickcore.core

import me.anton.sickcore.core.modules.ModuleHandler
import net.axay.kspigot.main.KSpigot
import org.bukkit.entity.Player

class PaperCore(val launcher: KSpigot) : Core() {

    companion object{
        var instance: PaperCore? = null
    }

    init {
        instance = this
    }

    val moduleHandler = ModuleHandler()
    var onlinePlayers = ArrayList<Player>()

    suspend fun start(){
        val coreHandler = PaperCoreHandler()
        coreHandler.handleMessages()
        coreHandler.handleCustomEvents()
        moduleHandler.start()
    }

    suspend fun shutdown(){
        moduleHandler.shutdown()
    }

}