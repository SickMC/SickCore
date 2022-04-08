import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    id("io.papermc.paperweight.userdev") version "1.3.5"
    id("xyz.jpenilla.run-paper") version "1.0.6"
    kotlin("plugin.serialization") version "1.6.10"
}

repositories{
    mavenCentral()
    maven ("https://jitpack.io")
    maven ("https://nexus.velocitypowered.com/repository/maven-public/")
}

dependencies{
    paperDevBundle("1.18.2-R0.1-SNAPSHOT")
    implementation("net.axay:kspigot:1.18.2")
    implementation("org.slf4j:slf4j-simple:1.7.30")
    implementation("org.litote.kmongo:kmongo-coroutine-serialization:4.5.1")
    implementation("io.github.crackthecodeabhi:kreds:0.7")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    compileOnly("com.arcaniax:HeadDatabase-API:1.3.1")
    compileOnly("com.velocitypowered:velocity-api:3.0.1")
    annotationProcessor("com.velocitypowered:velocity-api:3.0.1")
}

tasks{
    build{
        dependsOn(reobfJar)
    }
    compileJava{
        options.encoding = "UTF-8"
        options.release.set(17)
    }
    compileKotlin{
        kotlinOptions.jvmTarget = "17"
    }
    runServer {
        minecraftVersion("1.18.2")
    }
}