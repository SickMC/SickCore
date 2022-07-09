package net.sickmc.sickcore

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.util.collections.*
import java.util.*

fun main() {
    embeddedServer(
        Netty, port = System.getenv("WEBSOCKET_PORT").toInt(), host = System.getenv("WEBSOCKET_SERVER_ADDRESS")
    ) {
        install(WebSockets) {
            timeoutMillis = Long.MAX_VALUE
        }

        routing {
            val eventConnections = Collections.synchronizedSet<DefaultWebSocketServerSession>(ConcurrentSet())
            val verifyConnections = Collections.synchronizedSet<DefaultWebSocketServerSession>(ConcurrentSet())

            webSocket("/event") {
                eventConnections.add(this)
                for (frame in incoming) {
                    eventConnections.forEach { it.send(frame) }
                }
            }
            webSocket("/verify") {
                verifyConnections.add(this)
                for (frame in incoming) {
                    println(frame)
                    for (connection in verifyConnections) {
                        connection.send(frame)
                        println("Sent!!")
                    }
                }
            }
        }
    }.start(wait = true)
}