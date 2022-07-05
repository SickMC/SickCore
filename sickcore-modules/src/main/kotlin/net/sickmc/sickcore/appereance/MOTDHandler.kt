package net.sickmc.sickcore.appereance

import com.velocitypowered.api.event.proxy.ProxyPingEvent
import net.kyori.adventure.text.minimessage.MiniMessage
import net.sickmc.sickcore.listenVelocity
import net.sickmc.sickcore.utils.mongo.configColl
import net.sickmc.sickcore.utils.mongo.retrieveOne

object MOTDHandler {

    suspend fun handleMOTD() {
        val document = configColl.retrieveOne("type", "motd")!!

        listenVelocity<ProxyPingEvent> {
            val newPing = it.ping.asBuilder()
            val firstLine = MiniMessage.miniMessage().deserialize(document.getString("firstLine") + "<newline>")
            val secondLine = MiniMessage.miniMessage().deserialize(document.getString("secondLine"))
            val description = firstLine.append(secondLine)
            newPing.description(description)
            newPing.maximumPlayers(document.getInteger("maxPlayers"))

            it.ping = newPing.build()
        }
    }

}