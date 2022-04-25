package net.sickmc.sickcore.core.modules

abstract class Module {

    abstract val name: String

    abstract suspend fun start()
    abstract suspend fun shutdown()

}