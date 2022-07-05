package net.sickmc.sickcore

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.util.collections.*
import java.time.Duration
import java.util.*

fun main() {
    embeddedServer(Netty, port = System.getenv("WEBSOCKET_PORT").toInt()) {
        install(WebSockets) {
            pingPeriod = Duration.ofSeconds(15)
            timeout = Duration.ofSeconds(15)
            maxFrameSize = Long.MAX_VALUE
            masking = false
        }

        routing {
            val eventConnections = Collections.synchronizedSet<DefaultWebSocketServerSession>(ConcurrentSet())
            val verifyConnections = Collections.synchronizedSet<DefaultWebSocketServerSession>(ConcurrentSet())

            webSocket("/event") {
                eventConnections.add(this)
                for (frame in incoming){
                    eventConnections.forEach { it.send(frame) }
                }
            }
            webSocket("/verify") {
                verifyConnections.add(this)
                for (frame in incoming){
                    verifyConnections.forEach { it.send(frame) }
                }
            }
        }
    }.start(wait = true)
}