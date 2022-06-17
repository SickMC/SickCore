package net.sickmc.sickcore.utils

import net.sickmc.sickcore.utils.redis.subscribeRedis

class RankUpdateEvent(val rankGroup: String) : Event() {

    companion object{

        suspend fun registerCaller(){
            /*subscribeRedis("rankupdate"){
                 EventManager.callEvent(RankUpdateEvent(it.split("-")[1]))
            }

             */
        }

        fun run(e: RankUpdateEvent){

        }

    }

    override val name: String = "RankUpdateEvent"

}