package me.anton.sickcore.core

import com.velocitypowered.api.proxy.Player
import me.anton.sickcore.core.modules.ModuleHandler

class VelocityCore(val base: VelocityBootstrap) : Core() {

    companion object{
        var instance: VelocityCore? = null
    }

    init {
        instance = this
    }

    val moduleHandler = ModuleHandler()
    var onlinePlayers = ArrayList<Player>()

    suspend fun start(){
        VelocityCoreHandler()
        moduleHandler.start()
    }

    suspend fun shutdown(){
        moduleHandler.shutdown()
    }

}