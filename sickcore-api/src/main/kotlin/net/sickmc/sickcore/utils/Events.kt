package net.sickmc.sickcore.utils

class RankUpdateEvent(val rankGroup: String) : Event() {

    companion object{

        suspend fun registerCaller(){

        }

        fun run(e: RankUpdateEvent){

        }

    }

    override val name: String = "RankUpdateEvent"

}