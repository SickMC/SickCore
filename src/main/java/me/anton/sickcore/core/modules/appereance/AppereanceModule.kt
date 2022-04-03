package me.anton.sickcore.core.modules.appereance

import me.anton.sickcore.core.Environment

class AppereanceModule : me.anton.sickcore.core.modules.Module() {

    companion object{
        var instance: AppereanceModule? = null
    }

    override val name: String
        get() = "Appereance"

    override fun start() {
        instance = this
        when(environment){
            Environment.VELOCITY -> MOTDHandler()
        }
    }

    override fun shutdown() {

    }

}