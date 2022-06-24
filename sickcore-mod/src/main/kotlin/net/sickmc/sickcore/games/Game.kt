package net.sickmc.sickcore.games

import net.sickmc.sickcore.games.survival.Survival
import net.sickmc.sickcore.utils.fabric.Tablist

abstract class Game{

    companion object{
        private val games: ArrayList<Game> = arrayListOf(Survival())

        lateinit var current: Game

        fun preEnable(){
            games.forEach {
                if (!it.startEnvironment.contains(System.getenv("GAME_START_ENVIRONMENT")))return@forEach
                it.preEnable()
                println("Commands of ${it.name} registered!")
                current = it
            }
        }
        suspend fun enable(){
            current.enable()
        }
    }

    abstract val name: String
    abstract val startEnvironment: Array<String>

    abstract fun preEnable()
    abstract suspend fun enable()
    abstract suspend fun disable()

    abstract val tablist: Tablist

}