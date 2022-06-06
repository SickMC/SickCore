package net.sickmc.sickcore.utils.redis

import io.github.crackthecodeabhi.kreds.connection.*
import kotlinx.coroutines.coroutineScope

suspend inline fun subscribeRedis(channelName: String, crossinline callback: (String) -> Unit) {

    coroutineScope {
        val subscriptionHandler = object : AbstractKredsSubscriber() {
            override fun onException(ex: Throwable) {
                println("Exception while handling Subscription to redis: ${ex.stackTrace} ")
            }

            override fun onMessage(channel: String, message: String) {
                callback(message)
            }
        }
        /*newSubscriberClient(
            Endpoint.from("${System.getenv("REDIS_ADDRESS")}:${System.getenv("REDIS_PORT")}"),
            subscriptionHandler
        ).use {
            it.subscribe(channelName)
        }
         */
        newSubscriberClient(
            Endpoint.from("${System.getProperty("REDIS_ADDRESS")}:${System.getProperty("REDIS_PORT")}"),
            subscriptionHandler
        ).use {
            it.subscribe(channelName)
        }
    }

}

suspend inline fun publish(channelName: String, message: String) {

    kreds.publish(channelName, message)

}