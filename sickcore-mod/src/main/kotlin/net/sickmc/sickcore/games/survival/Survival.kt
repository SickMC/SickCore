package net.sickmc.sickcore.games.survival

import net.sickmc.sickcore.games.Game

class Survival : Game() {

    override val name: String = "Survival"
    override val startEnvironment: Array<String> = arrayOf("public_survival_latest")

    override suspend fun enable() {
        CommonEvents.register()
        SurvivalCommands.register()
    }

    override suspend fun disable() {

    }
}