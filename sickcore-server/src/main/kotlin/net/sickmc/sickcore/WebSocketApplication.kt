package net.sickmc.sickcore

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.util.collections.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.util.*

fun main() {
    embeddedServer(
        Netty, port = System.getenv("WEBSOCKET_PORT").toInt(), host = System.getenv("WEBSOCKET_SERVER_ADDRESS")
    ) {
        install(WebSockets) {
            timeoutMillis = Long.MAX_VALUE
        }

        routing {
            val eventFlow = MutableSharedFlow<String>()

            webSocket("/sickmc") {
                launch {
                    eventFlow.collect(::send)
                }
                for (frame in incoming) {
                    if (frame !is Frame.Text) continue
                    launch {
                        eventFlow.emit(frame.readText())
                    }
                }
            }
            
        }
    }.start(wait = true)
}