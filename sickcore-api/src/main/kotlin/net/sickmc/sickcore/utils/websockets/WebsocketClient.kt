package net.sickmc.sickcore.utils.websockets

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*

val webClient = HttpClient(CIO) {
    install(WebSockets)
}

suspend inline fun listenChannel(
    channel: String, crossinline session: suspend DefaultClientWebSocketSession.() -> Unit
) {
    webClient.webSocket(
        method = HttpMethod.Get,
        host = System.getenv("WEBSOCKET_CLIENT_ADDRESS"),
        port = System.getenv("WEBSOCKET_PORT").toInt(),
        path = "/$channel"
    ) {
        send(Frame.Text("jo"))
        session.invoke(this)
        println("Websocket connected!")
    }
}

suspend fun sendChannel(channel: String, message: Frame) {
    webClient.webSocket(
        method = HttpMethod.Get,
        host = System.getenv("WEBSOCKET_CLIENT_ADDRESS"),
        port = System.getenv("WEBSOCKET_PORT").toInt(),
        path = "/$channel"
    ) {
        send(message)
    }
}

suspend fun sendMessage(message: String) = sendChannel("sickmc", Frame.Text(message))

suspend inline fun listen(crossinline session: suspend DefaultClientWebSocketSession.() -> Unit) = listenChannel("sickmc", session)