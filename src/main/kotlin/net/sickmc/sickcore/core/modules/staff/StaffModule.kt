package net.sickmc.sickcore.core.modules.staff

import net.sickmc.sickcore.core.Environment
import net.sickmc.sickcore.core.environment

class StaffModule : net.sickmc.sickcore.core.modules.Module(){

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