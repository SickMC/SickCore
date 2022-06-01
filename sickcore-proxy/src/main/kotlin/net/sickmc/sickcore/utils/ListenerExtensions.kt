package net.sickmc.sickcore.utils

import net.sickmc.sickcore.VelocityCore
import net.sickmc.sickcore.server

inline fun <reified T> listenVelocity(crossinline callback: (T) -> Unit) {
    server.eventManager.register(VelocityCore.instance.base, T::class.java) {
        callback(it)
    }
}