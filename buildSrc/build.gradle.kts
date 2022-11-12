plugins {
    `kotlin-dsl`
    kotlin("plugin.serialization") version embeddedKotlinVersion
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://maven.fabricmc.net/")
    maven("https://server.bbkr.space/artifactory/libs-release/")
    maven("https://maven.quiltmc.org/repository/release/")
}

dependencies {
    fun pluginDep(id: String, version: String) = "${id}:${id}.gradle.plugin:${version}"

    val kotlinVersion = "1.7.21"
    val loomVersion = "1.0-SNAPSHOT"
    val quiltFlowerVersion = "1.7.4"
    val quiltMappingsPluginVersion = "4.2.1"

    //Kotlin
    compileOnly(kotlin("gradle-plugin", embeddedKotlinVersion))
    runtimeOnly(kotlin("gradle-plugin", kotlinVersion))
    compileOnly(pluginDep("org.jetbrains.kotlin.plugin.serialization", embeddedKotlinVersion))
    runtimeOnly(pluginDep("org.jetbrains.kotlin.plugin.serialization", kotlinVersion))

    //Fabric
    implementation(pluginDep("fabric-loom", loomVersion))
    implementation(pluginDep("io.github.juuxel.loom-quiltflower", quiltFlowerVersion))
    implementation(pluginDep("org.quiltmc.quilt-mappings-on-loom", quiltMappingsPluginVersion))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
}