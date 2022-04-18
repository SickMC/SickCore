package net.sickmc.sickcore.core

import com.velocitypowered.api.proxy.Player
import net.sickmc.sickcore.core.modules.ModuleHandler

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
        print("csdasd")
        VelocityCoreHandler().handleCustomEvents()
        moduleHandler.start()
    }

    suspend fun shutdown(){
        moduleHandler.shutdown()
    }

}