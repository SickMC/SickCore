package net.sickmc.sickcore.utils.velocity

import net.sickmc.sickcore.core.VelocityCore
import net.sickmc.sickcore.utils.redis.subscribeRedis

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