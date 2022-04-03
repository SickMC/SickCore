package me.anton.sickcore.utils.redis

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import me.anton.sickcore.core.Core

class RedisDocument(val key: String) {

    lateinit var jsonObject: JsonObject

    suspend fun load(){
        jsonObject = JsonParser.parseString(Core.instance.redisConnection.client.get(key)).asJsonObject
    }

    suspend fun save(){
        Core.instance.redisConnection.client.set(key, jsonObject.toString());
    }

}