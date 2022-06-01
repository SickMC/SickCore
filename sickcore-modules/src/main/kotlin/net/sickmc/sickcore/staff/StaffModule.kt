package net.sickmc.sickcore.staff

import net.sickmc.sickcore.Module
import net.sickmc.sickcore.environment
import net.sickmc.sickcore.utils.Environment

class StaffModule : Module(){

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
            else -> {}
        }
    }

    override suspend fun shutdown() {
    }
}