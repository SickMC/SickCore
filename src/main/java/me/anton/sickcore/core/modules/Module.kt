package me.anton.sickcore.core.modules

import me.anton.sickcore.core.Environment
import me.anton.sickcore.core.PaperCore

abstract class Module {

    abstract val name: String

    abstract fun start()
    abstract fun shutdown()

    val environment: Environment = if (PaperCore.instance == null) Environment.VELOCITY else Environment.PAPER

}