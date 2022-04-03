package me.anton.sickcore.utils.redis

import io.github.crackthecodeabhi.kreds.connection.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import me.anton.sickcore.core.Core
import me.anton.sickcore.utils.FileUtils

suspend inline fun subscribeRedis(channelName: String, crossinline callback: (String) -> Unit){

    coroutineScope {
        val subscriptionHandler = object : AbstractKredsSubscriber(){
            override fun onException(ex: Throwable) {
                println("Exception while handling Subscription to redis: ${ex.stackTrace} ")
            }

            override fun onMessage(channel: String, message: String) {
                callback(message)
            }
        }
        val credentials = withContext(Dispatchers.IO) {
            FileUtils.getFileAsDocument("redis")
        }
        newSubscriberClient(Endpoint.from("${credentials.getString("address")}:${credentials.getInteger("port")}"), subscriptionHandler).use {
            it.subscribe(channelName)
        }
    }

}

suspend inline fun publish(channelName: String, message: String){

    Core.instance.redisConnection.client.publish(channelName, message)

}