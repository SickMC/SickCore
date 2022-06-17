package net.sickmc.sickcore

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.proxy.ProxyServer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.sickmc.sickcore.utils.redis.kreds
import org.litote.kmongo.serialization.SerializationClassMappingTypeService
import org.slf4j.Logger

val bootstrapScope = CoroutineScope(Dispatchers.Default)
lateinit var server: ProxyServer
@Plugin(
    id = "sickcore",
    name = "SickCore",
    version = "1.1",
    description = "Velocity Core of SickNetwork",
    url = "discord.sickmc.net",
    authors = ["btwonion"]
)
class VelocityBootstrap @Inject constructor(val pserver: ProxyServer, val logger: Logger) {

    companion object{
        lateinit var instance: VelocityBootstrap
    }

    private var core: VelocityCore? = null

    @Subscribe
    fun onProxyInitialize(event: ProxyInitializeEvent) {
        instance = this
        server = pserver
        System.setProperty(
            "org.litote.mongo.mapping.service",
            SerializationClassMappingTypeService::class.qualifiedName!!
        )
        core = VelocityCore(this)
        bootstrapScope.launch {
            kreds
            core!!.start()
        }
    }

    @Subscribe
    fun onProxyShutdown(event: ProxyShutdownEvent) {
        bootstrapScope.launch {
            core!!.shutdown()
        }
    }


}