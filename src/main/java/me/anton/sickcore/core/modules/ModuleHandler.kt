package me.anton.sickcore.core.modules

import me.anton.sickcore.core.modules.appereance.AppereanceModule
import me.anton.sickcore.core.modules.rank.RankModule

class ModuleHandler {

    val modules = listOf(RankModule(), AppereanceModule())

    suspend fun start(){
        modules.forEach {
            it.start()
        }
    }

    suspend fun shutdown(){
        modules.forEach {
            it.shutdown()
        }
    }

}