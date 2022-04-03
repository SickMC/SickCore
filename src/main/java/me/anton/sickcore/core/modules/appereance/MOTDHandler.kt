package me.anton.sickcore.core.modules.appereance

import com.velocitypowered.api.event.proxy.ProxyPingEvent
import com.velocitypowered.api.proxy.server.ServerPing
import kotlinx.coroutines.launch
import me.anton.sickcore.core.Core
import me.anton.sickcore.core.listenVelocity
import me.anton.sickcore.utils.mongo.MongoDocument
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer

class MOTDHandler {

    suspend fun handleMOTD(){
        val document = Core.instance.configCollection.getDocument("type", "motd")!!

        listenVelocity<ProxyPingEvent> {
            val ping = it.ping.asBuilder()
            val firstLine = MiniMessage.miniMessage().deserialize(document.document.getString("firstLine") + "<newline>")
            val secondLine = MiniMessage.miniMessage().deserialize(document.document.getString("secondLine"))
            val description = firstLine.append(secondLine)
            ping.description(description)
            ping.maximumPlayers(document.document.getInteger("maxPlayers"))
            ping.version(ServerPing.Version(18, document.document.getString("maxPlayers")))

            it.ping = ping.build()
        }
    }

}