import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins{
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    `velocity-build-script`
    `mongo-build-script`
    `websocket-client-script`
}
dependencies {
    implementation(modProject(":${rootProject.name}-api"))
    implementation(modProject(":${rootProject.name}-modules"))

    shadow(kotlin("stdlib"))
    shadow("org.litote.kmongo:kmongo-coroutine-serialization:4.6.0")

    val ktorVersion = "2.0.3"
    shadow("io.ktor:ktor-client-core:$ktorVersion")
    shadow("io.ktor:ktor-client-cio:$ktorVersion")
    shadow("io.ktor:ktor-client-websockets:$ktorVersion")
    shadow("org.slf4j:slf4j-simple:2.0.0-alpha7")
    implementation("org.slf4j:slf4j-simple:2.0.0-alpha7")

    shadow(modProject(":${rootProject.name}-api"))
    shadow(modProject(":${rootProject.name}-modules"))
}
repositories {
    mavenCentral()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "17"
}