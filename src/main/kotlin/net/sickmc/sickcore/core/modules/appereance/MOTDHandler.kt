package net.sickmc.sickcore.core.modules.appereance

import com.velocitypowered.api.event.proxy.ProxyPingEvent
import com.velocitypowered.api.proxy.server.ServerPing.SamplePlayer
import net.sickmc.sickcore.core.listenVelocity
import net.kyori.adventure.text.minimessage.MiniMessage
import net.sickmc.sickcore.utils.mongo.configColl
import net.sickmc.sickcore.utils.mongo.retrieveOne
import java.util.*

object MOTDHandler {

    suspend fun handleMOTD(){
        val document = configColl.retrieveOne("type", "motd")!!

        listenVelocity<ProxyPingEvent> {
            val ping = it.ping.asBuilder()
            val firstLine = MiniMessage.miniMessage().deserialize(document.getString("firstLine") + "<newline>")
            val secondLine = MiniMessage.miniMessage().deserialize(document.getString("secondLine"))
            val description = firstLine.append(secondLine)
            ping.description(description)
            ping.maximumPlayers(document.getInteger("maxPlayers"))
            val first = SamplePlayer(document.getString("firstPlayer"), UUID.randomUUID())
            val second = SamplePlayer(document.getString("secondPlayer"), UUID.randomUUID())
            val third = SamplePlayer(document.getString("thirdPlayer"), UUID.randomUUID())
            val fourth = SamplePlayer(document.getString("fourthPlayer"), UUID.randomUUID())
            val fifth = SamplePlayer(document.getString("fifthPlayer"), UUID.randomUUID())
            val sixth = SamplePlayer(document.getString("sixthPlayer"), UUID.randomUUID())
            val seventh = SamplePlayer(document.getString("seventhPlayer"), UUID.randomUUID())
            val eighth = SamplePlayer(document.getString("eighthPlayer"), UUID.randomUUID())
            val ninth = SamplePlayer(document.getString("ninthPlayer"), UUID.randomUUID())

            ping.samplePlayers(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth)

            it.ping = ping.build()
        }
    }

}