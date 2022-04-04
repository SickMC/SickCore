package me.anton.sickcore.core.modules.social

import me.anton.sickcore.core.Environment

class SocialModule : me.anton.sickcore.core.modules.Module() {

    override val name: String
        get() = "Social"


    override suspend fun start() {
        when(environment){
            Environment.VELOCITY -> {
                MsgCommand().register()
            }
        }
    }

    override suspend fun shutdown() {

    }
}