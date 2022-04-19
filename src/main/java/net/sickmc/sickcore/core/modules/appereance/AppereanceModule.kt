package net.sickmc.sickcore.core.modules.appereance

import net.sickmc.sickcore.core.Environment
import net.sickmc.sickcore.core.environment

class AppereanceModule : net.sickmc.sickcore.core.modules.Module() {

    companion object{
        var instance: AppereanceModule? = null
    }

    override val name: String
        get() = "Appereance"

    override suspend fun start() {
        instance = this
        when(environment){
            Environment.VELOCITY ->{
                MOTDHandler().handleMOTD()
                val lobbyCommands = LobbyCommand()
                lobbyCommands.register()
                lobbyCommands.registerAddition()
                PlaytimeCommand().register()
            }
            Environment.PAPER -> {
                TablistProvider().handleTablist()
                CommandSuggestionHandler().handlePaperCommands()
                NameTagHandler().handleNameTags()
                ChatHandler().handleChat()
            }
            else -> {}
        }
    }

    override suspend fun shutdown() {

    }

}