package net.sickmc.sickcore.core

import kotlinx.coroutines.launch
import net.sickmc.sickcore.utils.mongo.MongoCollection
import net.sickmc.sickcore.utils.mongo.databaseScope
import net.sickmc.sickcore.utils.redis.RedisConnection
import org.litote.kmongo.set

val redisConnection = RedisConnection()
var environment: Environment = Environment.STANDALONE

abstract class Core {

    val configCollection = MongoCollection("config")

    init {
        databaseScope.launch {
            redisConnection.loadClient()
        }
    }

}