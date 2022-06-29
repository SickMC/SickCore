package net.sickmc.sickcore

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import java.time.Duration
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.collections.LinkedHashSet

fun main() {
    embeddedServer(Netty, port = 8084) {
        install(WebSockets) {
            pingPeriod = Duration.ofSeconds(15)
            timeout = Duration.ofSeconds(15)
            maxFrameSize = Long.MAX_VALUE
            masking = false
        }

        routing {
            val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())
            webSocket("/event") {
                val thisConnection = Connection(this)
                connections += thisConnection

                for (frame in incoming)
                    send(frame)

            }
        }
    }.start(wait = true)
}

class Connection(val session: DefaultWebSocketServerSession) {
    companion object {
        var lastId = AtomicInteger(0)
    }

    val name = "user${lastId.getAndIncrement()}"
}