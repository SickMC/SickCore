package net.sickmc.sickcore.core.modules.rank

import net.sickmc.sickcore.core.Environment
import net.sickmc.sickcore.core.environment
import net.sickmc.sickcore.core.modules.Module
import net.sickmc.sickcore.utils.mongo.MongoCollection

class RankModule: Module() {

    companion object{
        lateinit var instance: RankModule
    }

    val rankCollection = MongoCollection("ranks")
    val groupCollection = MongoCollection("rankGroups")

    override val name: String
        get() = "Rank"

    override suspend fun start() {
        instance = this
        Ranks.ranks
        RankGroups.groups
        when(environment){
            Environment.PAPER -> PermissibleListener()
            Environment.VELOCITY -> VelocityPermissionProvider()
            else -> {}
        }
    }

    override suspend fun shutdown() {

    }


}