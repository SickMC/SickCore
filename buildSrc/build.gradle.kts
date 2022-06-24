plugins {
    `kotlin-dsl`
    kotlin("plugin.serialization") version embeddedKotlinVersion
    kotlin("jvm") version "1.7.0"
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://maven.fabricmc.net/")
    maven("https://maven.quiltmc.org/repository/release")
    maven("https://server.bbkr.space/artifactory/libs-release/")
    maven ("https://repo.papermc.io/repository/maven-public/")
}

dependencies{
    fun pluginDep(id: String, version: String) = "${id}:${id}.gradle.plugin:${version}"

    val kotlinVersion = "1.7.0"

    compileOnly(kotlin("gradle-plugin", embeddedKotlinVersion))
    runtimeOnly(kotlin("gradle-plugin", kotlinVersion))
    compileOnly(pluginDep("org.jetbrains.kotlin.plugin.serialization", embeddedKotlinVersion))
    runtimeOnly(pluginDep("org.jetbrains.kotlin.plugin.serialization", kotlinVersion))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")


    implementation(pluginDep("fabric-loom", "0.12-SNAPSHOT"))
    implementation(pluginDep("io.github.juuxel.loom-quiltflower", "1.7.3"))
    implementation(pluginDep("org.quiltmc.quilt-mappings-on-loom", "4.2.0"))

    compileOnly("com.velocitypowered:velocity-api:3.0.1")
    implementation("net.kyori:adventure-text-minimessage:4.10.1")

    implementation("org.litote.kmongo:kmongo:4.6.1")
}
