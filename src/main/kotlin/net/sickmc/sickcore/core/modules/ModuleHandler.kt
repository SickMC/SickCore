package net.sickmc.sickcore.core.modules

import net.sickmc.sickcore.core.modules.appereance.AppereanceModule
import net.sickmc.sickcore.core.modules.rank.RankModule

object ModuleHandler {

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