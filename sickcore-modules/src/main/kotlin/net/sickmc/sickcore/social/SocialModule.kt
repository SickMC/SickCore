package net.sickmc.sickcore.social

import net.sickmc.sickcore.Module
import net.sickmc.sickcore.environment
import net.sickmc.sickcore.utils.Environment

class SocialModule : Module() {

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