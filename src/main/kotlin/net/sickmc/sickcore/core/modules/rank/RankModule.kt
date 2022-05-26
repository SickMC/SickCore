package net.sickmc.sickcore.core.modules.rank

import net.sickmc.sickcore.core.Environment
import net.sickmc.sickcore.core.environment
import net.sickmc.sickcore.core.modules.Module

class RankModule: Module() {

    companion object{
        lateinit var instance: RankModule
    }

    override val name: String
        get() = "Rank"

    override suspend fun start() {
        instance = this
        Ranks.ranks
        RankGroups.groups
        when(environment){
            Environment.FABRIC -> {}
            Environment.VELOCITY -> VelocityPermissionProvider()
            else -> {}
        }
    }

    override suspend fun shutdown() {

    }


}