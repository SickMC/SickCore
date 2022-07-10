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
            val connections = Collections.synchronizedMap(HashMap(ConcurrentMap<String, DefaultWebSocketServerSession>()))

            val webSockets = listOf("event", "verify", "reward", "message")
            webSockets.forEach {
                webSocket("/$it"){
                    connections[it] = this
                    for (frame in incoming)
                        connections[it]?.send(frame)
                }
            }
        }
    }.start(wait = true)
}