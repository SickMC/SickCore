package net.sickmc.sickcore

abstract class Module {

    abstract val name: String

    abstract suspend fun start()
    abstract suspend fun shutdown()

}