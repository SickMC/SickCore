package net.sickmc.sickcore.core.modules.appereance

import com.velocitypowered.api.event.proxy.ProxyPingEvent
import com.velocitypowered.api.proxy.server.ServerPing.SamplePlayer
import net.sickmc.sickcore.core.VelocityCore
import net.sickmc.sickcore.core.listenVelocity
import net.kyori.adventure.text.minimessage.MiniMessage
import java.util.*

class MOTDHandler {

    suspend fun handleMOTD(){
        val document = VelocityCore.instance!!.configCollection.getDocument("type", "motd")!!

        listenVelocity<ProxyPingEvent> {
            val ping = it.ping.asBuilder()
            val firstLine = MiniMessage.miniMessage().deserialize(document.document.getString("firstLine") + "<newline>")
            val secondLine = MiniMessage.miniMessage().deserialize(document.document.getString("secondLine"))
            val description = firstLine.append(secondLine)
            ping.description(description)
            ping.maximumPlayers(document.document.getInteger("maxPlayers"))
            val first = SamplePlayer(document.document.getString("firstPlayer"), UUID.randomUUID())
            val second = SamplePlayer(document.document.getString("secondPlayer"), UUID.randomUUID())
            val third = SamplePlayer(document.document.getString("thirdPlayer"), UUID.randomUUID())
            val fourth = SamplePlayer(document.document.getString("fourthPlayer"), UUID.randomUUID())
            val fifth = SamplePlayer(document.document.getString("fifthPlayer"), UUID.randomUUID())
            val sixth = SamplePlayer(document.document.getString("sixthPlayer"), UUID.randomUUID())
            val seventh = SamplePlayer(document.document.getString("seventhPlayer"), UUID.randomUUID())
            val eighth = SamplePlayer(document.document.getString("eighthPlayer"), UUID.randomUUID())
            val ninth = SamplePlayer(document.document.getString("ninthPlayer"), UUID.randomUUID())

            ping.samplePlayers(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth)

            it.ping = ping.build()
        }
    }

}