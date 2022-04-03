package me.anton.sickcore.core.modules.appereance

import me.anton.sickcore.core.Environment

class AppereanceModule : me.anton.sickcore.core.modules.Module() {

    companion object{
        var instance: AppereanceModule? = null
    }

    override val name: String
        get() = "Appereance"

    override suspend fun start() {
        instance = this
        when(environment){
            Environment.VELOCITY -> MOTDHandler().handleMOTD()
            Environment.PAPER -> {
                TablistProvider().handleTablist()
                CommandSuggestionHandler().handlePaperCommands()
                NameTagHandler().handleNameTags()
                ChatHandler().handleChat()
            }
        }
    }

    override suspend fun shutdown() {

    }

}