package net.sickmc.sickcore

import com.velocitypowered.api.proxy.ProxyServer
import net.minecraft.server.MinecraftServer
import net.sickmc.sickcore.appereance.AppereanceModule
import net.sickmc.sickcore.rank.RankModule
import net.sickmc.sickcore.social.SocialModule
import net.sickmc.sickcore.staff.StaffModule
import net.sickmc.sickcore.utils.Environment

var environment: Environment = Environment.STANDALONE
var minecraftServer: MinecraftServer? = null
var proxyServer: ProxyServer? = null
class ModuleHandler(env: Environment, val server: ProxyServer? = null, val mcServer: MinecraftServer? = null) {

    init {
        environment = env
        proxyServer = server
        minecraftServer = this.mcServer
    }

    val modules = listOf(RankModule(), AppereanceModule(), StaffModule(), SocialModule())

    suspend fun start(){
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