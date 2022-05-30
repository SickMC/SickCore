plugins {
    kotlin("jvm") version "1.5.31"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.github.juuxel.loom-quiltflower") version "1.7.1"
    id("org.quiltmc.quilt-mappings-on-loom") version "4.2.0"
    kotlin("plugin.serialization") version "1.5.31"
    id("fabric-loom") version "0.12-SNAPSHOT"
    `maven-publish`
}

group = "net.sickmc"
version = "1.0.0"
description = "Network Core for the SickNetwork"

repositories{
    mavenCentral()
    maven ("https://nexus.velocitypowered.com/repository/maven-public/")
    maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots/") {
        name = "sonatype-oss-snapshots1"
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/SickMC/SickCore")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}

dependencies{
    minecraft("com.mojang:minecraft:1.18.2")
    mappings(loom.layered {
        addLayer(quiltMappings.mappings("org.quiltmc:quilt-mappings:1.18.2+build.22:v2"))
        officialMojangMappings()
    })
    modImplementation("net.fabricmc:fabric-loader:0.13.3")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.51.1+1.18.2")
    modImplementation("net.fabricmc:fabric-language-kotlin:1.7.4+kotlin.1.6.21")

    val fabrikVersion = "1.7.4"
    modImplementation("net.axay:fabrikmc-core:$fabrikVersion")
    modImplementation("net.axay:fabrikmc-commands:$fabrikVersion")
    modImplementation("net.axay:fabrikmc-igui:$fabrikVersion")
    modImplementation("net.axay:fabrikmc-persistence:$fabrikVersion")
    modImplementation("net.axay:fabrikmc-nbt:$fabrikVersion")

    implementation("net.kyori:adventure-text-minimessage:4.10.1")
    compileOnly("com.velocitypowered:velocity-api:3.0.1")

    implementation("org.litote.kmongo:kmongo-coroutine-serialization:4.6.0")
    implementation("io.github.crackthecodeabhi:kreds:0.7")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
    shadow("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
    shadow("io.github.crackthecodeabhi:kreds:0.7")
    shadow(kotlin("stdlib"))
}

kotlin.sourceSets.all{
    languageSettings.optIn("kotlin.requiresOptIn")
}

tasks{
    compileJava{
        options.encoding = "UTF-8"
        options.release.set(17)
    }
    compileKotlin{
        kotlinOptions.jvmTarget = "11"
    }
    processResources {
        val props = mapOf(
            "version" to project.version,
            "description" to project.description,
            "mc_version" to "1.18"
        )

        inputs.properties(props)

        filesMatching("fabric.mod.json") {
            expand(props)
        }
    }
    val pushToStorage by registering(Exec::class){
        dependsOn(build)
        group = "push"
        commandLine("wsl", "rsync", "/mnt/c/Users/anton/Documents/Development/Forks/SickCore/build/libs/SickCore-1.0.0-all.jar", "node1:/home/sickmc/network/Testing/storage/SickCore.jar")
    }
}