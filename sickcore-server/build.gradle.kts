plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    `websocket-server-script`
    application
}

application {
    mainClass.set("net.sickmc.sickcore.WebSocketApplicationKt")
}

dependencies {
    implementation(modProject(":${rootProject.name}-api"))
    implementation("org.slf4j:slf4j-simple:2.0.0-alpha7")
}