package net.sickmc.sickcore.core.games.survival

import net.sickmc.sickcore.core.games.Game

class Survival : Game() {

    override val name: String = "Survival"
    override val startEnvironment: Array<String> = arrayOf("public_survival_latest")

    override suspend fun enable() {
        
    }

    override suspend fun disable() {

    }
}