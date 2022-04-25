package net.sickmc.sickcore.utils.paper

import net.sickmc.sickcore.utils.redis.subscribeRedis
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class RankUpdateEvent : Event() {

    override fun getEventName(): String {
        return "RankUpdateEvent"
    }

    override fun getHandlers(): HandlerList {
        return HandlerList()
    }

    companion object{
        @JvmStatic
        fun getHandlerList():HandlerList{
            return HandlerList()
        }
    }

}

class RankUpdateEventCaller{

    suspend fun handleRankUpdate(){
        subscribeRedis("rankupdate"){
            val updateEvent = RankUpdateEvent()
            Bukkit.getPluginManager().callEvent(updateEvent)
        }
    }

}