plugins{
    kotlin("jvm")
    kotlin("plugin.serialization")
    `websocket-build-script`
    application
}

application{
    mainClass.set("net.sickmc.sickcore.Application")
}