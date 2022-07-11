package net.sickmc.sickcore.games.lobby

import net.sickmc.sickcore.common.chat.ChatController
import net.sickmc.sickcore.common.chat.ChatPresets
import net.sickmc.sickcore.common.tablist.defaultTablist
import net.sickmc.sickcore.games.Game
import net.sickmc.sickcore.common.tablist.Tablist

class Lobby : Game() {

    override val name: String = "Lobby"

    override val startEnvironment: Array<String> = arrayOf("lobby")

    override fun preEnable() {

    }

    override suspend fun enable() {

    }

    override suspend fun disable() {

    }

    override val tablist: Tablist = defaultTablist

    override val chat: ChatController = ChatPresets.nothing

}