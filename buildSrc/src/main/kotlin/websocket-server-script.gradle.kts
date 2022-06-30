plugins{
    kotlin("jvm")
}

repositories{
    mavenCentral()
}

dependencies{
    val ktorVersion = "2.0.2"
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-websockets:$ktorVersion")
}