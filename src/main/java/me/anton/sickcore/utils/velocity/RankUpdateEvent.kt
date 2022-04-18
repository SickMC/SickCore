package me.anton.sickcore.utils.velocity

import me.anton.sickcore.core.VelocityCore
import me.anton.sickcore.utils.redis.subscribeRedis

class RankUpdateEvent {

    fun getEventName(): String {
        return "RankUpdateEvent"
    }

}

class RankUpdateEventCaller{

    suspend fun handleRankUpdate(){
        subscribeRedis("rankupdate"){
            VelocityCore.instance?.base?.server?.eventManager?.fire(RankUpdateEvent())
        }
    }

}