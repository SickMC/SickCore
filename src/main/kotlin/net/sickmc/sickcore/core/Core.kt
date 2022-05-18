package net.sickmc.sickcore.core

import kotlinx.coroutines.launch
import net.sickmc.sickcore.utils.mongo.databaseScope
import net.sickmc.sickcore.utils.redis.RedisConnection

var environment: Environment = Environment.STANDALONE

abstract class Core {
    init {
        databaseScope.launch {
            RedisConnection.loadClient()
        }
    }

}