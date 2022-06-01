package net.sickmc.sickcore.rank

import net.sickmc.sickcore.Module
import net.sickmc.sickcore.commonRanks.RankGroups
import net.sickmc.sickcore.commonRanks.Ranks
import net.sickmc.sickcore.environment
import net.sickmc.sickcore.utils.Environment

class RankModule: Module() {

    companion object{
        lateinit var instance: RankModule
    }

    override val name: String
        get() = "Rank"

    override suspend fun start() {
        instance = this
        RankGroups.load()
        Ranks.load()
        when(environment){
            Environment.FABRIC -> {}
            Environment.VELOCITY -> VelocityPermissionProvider()
            else -> {}
        }
    }

    override suspend fun shutdown() {

    }


}