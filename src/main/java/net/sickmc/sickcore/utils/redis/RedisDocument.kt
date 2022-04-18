package net.sickmc.sickcore.utils.redis

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import net.sickmc.sickcore.core.*

class RedisDocument(val key: String) {

    lateinit var jsonObject: JsonObject

    suspend fun load(){
        jsonObject = JsonParser.parseString(if (environment == Environment.PAPER) PaperCore.instance!!.redisConnection.client.get(key) else VelocityCore.instance!!.redisConnection.client.get(key)).asJsonObject
    }

    suspend fun save(){
        if (environment == Environment.PAPER) PaperCore.instance!!.redisConnection.client.set(key, jsonObject.toString())
        else VelocityCore.instance!!.redisConnection.client.set(key, jsonObject.toString())
    }

}