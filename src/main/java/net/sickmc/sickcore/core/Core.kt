package net.sickmc.sickcore.core

import kotlinx.coroutines.launch
import net.sickmc.sickcore.utils.mongo.MongoCollection
import net.sickmc.sickcore.utils.mongo.databaseScope
import net.sickmc.sickcore.utils.redis.RedisConnection

val environment = if (PaperCore.instance == null) Environment.VELOCITY else Environment.PAPER

abstract class Core {

    val configCollection = MongoCollection("config")
    val redisConnection = RedisConnection()

    init {
        databaseScope.launch {
            redisConnection.loadClient()
        }
    }

}