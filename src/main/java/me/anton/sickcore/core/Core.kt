package me.anton.sickcore.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import me.anton.sickcore.utils.mongo.MongoCollection
import me.anton.sickcore.utils.mongo.MongoConnection
import me.anton.sickcore.utils.redis.RedisConnection

val environment = if (PaperCore.instance == null) Environment.VELOCITY else Environment.PAPER

abstract class Core {

    val ioScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    val configCollection = MongoCollection("config")
    val redisConnection = RedisConnection()

    init {
        ioScope.launch {
            redisConnection.loadClient()
        }
    }

}