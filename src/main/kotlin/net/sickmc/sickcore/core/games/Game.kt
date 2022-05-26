package net.sickmc.sickcore.core.games

import net.sickmc.sickcore.core.games.lobby.Lobby
import net.sickmc.sickcore.core.games.survival.Survival

abstract class Game{

    companion object{
        private val games: ArrayList<Game> = arrayListOf(Survival(), Lobby())

        lateinit var current: Game

        suspend fun enable(){
            games.forEach {
                if (!it.startEnvironment.contains(System.getenv("GAME_START_ENVIRONMENT")))return@forEach
                it.enable()
                current = it
            }
        }
    }

    abstract val name: String
    abstract val startEnvironment: Array<String>
    abstract suspend fun enable()
    abstract suspend fun disable()

}