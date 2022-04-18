package me.anton.sickcore.core.modules.staff

import me.anton.sickcore.core.Environment
import me.anton.sickcore.core.environment

class StaffModule : me.anton.sickcore.core.modules.Module(){

    companion object{
        lateinit var instance: StaffModule
    }

    override val name: String
        get() = "Staff"

    val staff = Staff()

    override suspend fun start() {
        instance = this
        when(environment){
            Environment.VELOCITY ->{
                BuildServerCommand().register()
            }
        }
    }

    override suspend fun shutdown() {
    }
}