package net.sickmc.sickcore.core.modules.social

import net.sickmc.sickcore.core.Environment
import net.sickmc.sickcore.core.environment

class SocialModule : net.sickmc.sickcore.core.modules.Module() {

    override val name: String
        get() = "Social"


    override suspend fun start() {
        when(environment){
            Environment.VELOCITY -> {
                MsgCommand().register()
            }
            else -> {}
        }
    }

    override suspend fun shutdown() {

    }
}