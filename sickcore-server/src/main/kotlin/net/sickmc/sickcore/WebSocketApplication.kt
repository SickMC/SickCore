package net.sickmc.sickcore

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.util.collections.*
import io.ktor.websocket.*
import java.time.Duration
import java.util.*

fun main() {
    embeddedServer(
        Netty, port = System.getenv("WEBSOCKET_PORT").toInt(), host = System.getenv("WEBSOCKET_SERVER_ADDRESS")
    ) {
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
                for (frame in incoming) {
                    if ((frame as Frame.Text).readText() == "jo") eventConnections.add(this)
                    eventConnections.forEach { it.send(frame) }
                }
            }
            webSocket("/verify") {
                for (frame in incoming) {
                    if ((frame as Frame.Text).readText() == "jo") verifyConnections.add(this)
                    verifyConnections.forEach { it.send(frame) }
                }
            }
        }
    }.start(wait = true)
}