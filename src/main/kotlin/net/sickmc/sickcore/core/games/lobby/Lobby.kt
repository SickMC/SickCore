package net.sickmc.sickcore.core.games.lobby

import net.sickmc.sickcore.core.games.Game

class Lobby : Game() {

    override val name: String = "Lobby"

    override val startEnvironment: Array<String> = arrayOf("lobby_public")

    override suspend fun enable() {

    }

    override suspend fun disable() {

    }
}