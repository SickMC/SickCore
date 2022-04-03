package me.anton.sickcore.core

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.proxy.ProxyServer
import org.slf4j.Logger

@Plugin(
    id = "SickCore_Velocity",
    name = "SickCore",
    version = "1.1",
    description = "Velocity Core of SickNetwork",
    url = "discord.sickmc.net",
    authors = ["btwonion"]
)

class VelocityBootstrap @Inject constructor(val server: ProxyServer,val logger: Logger) {

    private var core: VelocityCore? = null

    @Subscribe
    suspend fun onProxyInitialize(event: ProxyInitializeEvent){
        core = VelocityCore(this)
        core!!.start()
    }

    @Subscribe
    suspend fun onProxyShutdown(event: ProxyShutdownEvent){
        core!!.shutdown()
    }


}