package net.sickmc.sickcore.games

import net.sickmc.sickcore.common.chat.ChatController
import net.sickmc.sickcore.games.lobby.Lobby
import net.sickmc.sickcore.games.survival.Survival
import net.sickmc.sickcore.utils.fabric.Tablist

abstract class Game {

    companion object {
        private val games: ArrayList<Game> = arrayListOf(Survival(), Lobby())

        lateinit var current: Game

        fun preEnable() {
            games.forEach {
                if (!it.startEnvironment.contains(System.getenv("GAME_START_ENVIRONMENT"))) return@forEach
                it.preEnable()
                println("${it.name} is pre-enabled!")
                current = it
            }
        }

        suspend fun enable() {
            current.enable()
        }
    }

    abstract val name: String
    abstract val startEnvironment: Array<String>

    abstract fun preEnable()
    abstract suspend fun enable()
    abstract suspend fun disable()

    abstract val tablist: Tablist

    abstract val chat: ChatController

}