package net.sickmc.sickcore.utils.redis

import io.github.crackthecodeabhi.kreds.connection.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.sickmc.sickcore.utils.FileUtils

lateinit var kreds: KredsClient
object RedisConnection {

    suspend fun loadClient(){
        val credentials = withContext(Dispatchers.IO) {
            FileUtils.getFileAsDocument("redis")
        }
        newClient(Endpoint.from("${credentials.getString("address")}:${credentials.getInteger("port")}")).use { kredsclient ->
            kreds = kredsclient
        }
    }

}