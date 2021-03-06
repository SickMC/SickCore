package net.sickmc.sickcore

import com.velocitypowered.api.proxy.ProxyServer
import net.minecraft.server.MinecraftServer
import net.sickmc.sickcore.appereance.AppearanceModule
import net.sickmc.sickcore.rank.RankModule
import net.sickmc.sickcore.social.SocialModule
import net.sickmc.sickcore.staff.StaffModule
import net.sickmc.sickcore.utils.Environment

var environment: Environment = Environment.STANDALONE
var minecraftServer: MinecraftServer? = null
var proxyServer: ProxyServer? = null
var proxyPlugin: Any? = null

class ModuleHandler(env: Environment) {
    init {
        environment = env
    }

    val modules = listOf(RankModule(), AppearanceModule(), StaffModule(), SocialModule())

    suspend fun start() {
        modules.forEach {
            it.start()
        }
    }

    suspend fun shutdown() {
        modules.forEach {
            it.shutdown()
        }
    }

}

inline fun <reified T> listenVelocity(crossinline callback: (T) -> Unit) {
    proxyServer?.eventManager?.register(proxyPlugin, T::class.java) {
        callback(it)
    }
}