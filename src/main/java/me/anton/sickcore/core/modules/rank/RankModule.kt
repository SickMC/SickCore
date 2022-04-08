package me.anton.sickcore.core.modules.rank

import me.anton.sickcore.core.Environment
import me.anton.sickcore.core.environment
import me.anton.sickcore.core.modules.Module
import me.anton.sickcore.utils.mongo.MongoCollection

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
        when(environment){
            Environment.PAPER -> PermissibleListener()
            Environment.VELOCITY -> VelocityPermissionProvider()
        }
    }

    override suspend fun shutdown() {

    }


}