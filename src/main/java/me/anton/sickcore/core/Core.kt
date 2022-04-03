package me.anton.sickcore.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import me.anton.sickcore.utils.mongo.MongoCollection
import me.anton.sickcore.utils.mongo.MongoConnection
import me.anton.sickcore.utils.redis.RedisConnection

abstract class Core {

    companion object{
        lateinit var instance: Core
    }

    val connection = MongoConnection()
    val configCollection = MongoCollection("config")
    val databaseScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    val ioScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    val redisConnection = RedisConnection()

    init {
        instance = this
        ioScope.launch {
            redisConnection.loadClient()
        }
    }

}