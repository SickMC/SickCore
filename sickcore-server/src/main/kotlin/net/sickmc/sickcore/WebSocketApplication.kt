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
    embeddedServer(Netty, port = System.getenv("WEBSOCKET_PORT").toInt()) {
        install(WebSockets) {
            pingPeriod = Duration.ofSeconds(15)
            timeout = Duration.ofSeconds(15)
            maxFrameSize = Long.MAX_VALUE
            masking = false
        }

        routing {
            val connections = Collections.synchronizedSet<Connection?>(ConcurrentSet())
            webSocket("/event") {
                for (frame in incoming) {
                    connections.forEach {
                        it.session.send(frame)
                    }
                }
            }

            webSocket("/verify") {
                for (frame in incoming) {
                    connections.filter { it.type == ClientType.DISCORD || it.type == ClientType.VELOCITY }.forEach {
                        it.session.send(frame)
                    }
                }
            }

            webSocket("/register") {
                for (frame in incoming) {
                    when (frame) {
                        is Frame.Text -> {
                            val components = frame.components()
                            connections += Connection(ClientType.valueOf(components[1]), components[2], this@webSocket)
                        }
                        else -> {}
                    }
                }
            }
        }
    }.start(wait = true)
}

data class Connection(val type: ClientType, val name: String, val session: DefaultWebSocketServerSession) {}

fun Frame.components(): List<String> {
    val textFrame = this as? Frame.Text ?: return listOf()
    return textFrame.readText().split("/")
}