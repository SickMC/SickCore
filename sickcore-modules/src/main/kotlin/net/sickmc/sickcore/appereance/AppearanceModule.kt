package net.sickmc.sickcore.appereance

import net.sickmc.sickcore.Module
import net.sickmc.sickcore.environment
import net.sickmc.sickcore.utils.Environment

class AppearanceModule : Module() {

    companion object {
        var instance: AppearanceModule? = null
    }

    override val name: String
        get() = "Appereance"

    override suspend fun start() {
        instance = this
        when (environment) {
            Environment.VELOCITY -> {
                MOTDHandler.handleMOTD()
                val lobbyCommands = LobbyCommand()
                lobbyCommands.register()
                lobbyCommands.registerAddition()
                PlaytimeCommand().register()
                Verify.register()
            }
            Environment.FABRIC -> {
                DiscordRewards.listener()
            }
            else -> {}
        }
    }

    override suspend fun shutdown() {

    }

}