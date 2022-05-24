package net.sickmc.sickcore.utils

abstract class Event {

    abstract val name: String

}

object EventManager{

    fun callEvent(event: Event){
        if (event is RankUpdateEvent)RankUpdateEvent.run(event)
    }

    suspend fun register(){
        RankUpdateEvent.registerCaller()
    }

}