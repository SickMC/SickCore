package net.sickmc.sickcore

import com.velocitypowered.api.proxy.ProxyServer
import net.minecraft.server.MinecraftServer
import net.sickmc.sickcore.appereance.AppereanceModule
import net.sickmc.sickcore.rank.RankModule
import net.sickmc.sickcore.social.SocialModule
import net.sickmc.sickcore.staff.StaffModule
import net.sickmc.sickcore.utils.Environment
import net.sickmc.sickcore.utils.test
import net.sickmc.sickcore.utils.redis.kreds

var environment: Environment = Environment.STANDALONE
var minecraftServer: MinecraftServer? = null
var proxyServer: ProxyServer? = null
var proxyPlugin: Any? = null
class ModuleHandler(env: Environment) {

    init {
        environment = env
    }

    val modules = listOf(RankModule(), AppereanceModule(), StaffModule(), SocialModule())

    suspend fun start(){
        if (!test) kreds.auth(System.getenv("REDIS_PASSWORD")) else kreds.auth(System.getProperty("REDIS_PASSWORD"))
        modules.forEach {
            it.start()
        }
    }

    suspend fun shutdown(){
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