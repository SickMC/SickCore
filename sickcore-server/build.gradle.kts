plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    `websocket-server-script`
    application
}

application {
    mainClass.set("net.sickmc.sickcore.WebSocketApplicationKt")
}