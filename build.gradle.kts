plugins {
    kotlin("jvm") version "1.6.20"
    id("io.papermc.paperweight.userdev") version "1.3.5"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    kotlin("plugin.serialization") version "1.6.20"
}

version = "1.0.0"

repositories{
    mavenCentral()
    maven ("https://jitpack.io")
    maven ("https://nexus.velocitypowered.com/repository/maven-public/")
}

dependencies{
    paperDevBundle("1.18.2-R0.1-SNAPSHOT")
    implementation("net.axay:kspigot:1.18.2")
    implementation("org.litote.kmongo:kmongo-coroutine-serialization:4.5.1")
    implementation("io.github.crackthecodeabhi:kreds:0.7")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    shadow("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    shadow("org.litote.kmongo:kmongo-coroutine-serialization:4.5.1")
    shadow("io.github.crackthecodeabhi:kreds:0.7")
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
    register<Exec>("pushToTesting"){
        dependsOn(build)
        group = "push"
        commandLine("wsl", "rsync", "/mnt/c/Users/anton/Desktop/Ordner/Development/SickNetwork/SickCore/build/libs/SickCore-1.0.0-dev-all.jar", "node1:/home/sickmc/network/Testing/paper/plugins/SickCore.jar")
        commandLine("wsl", "rsync", "/mnt/c/Users/anton/Desktop/Ordner/Development/SickNetwork/SickCore/build/libs/SickCore-1.0.0-dev-all.jar", "node1:/home/sickmc/network/Testing/velocity/plugins/SickCore.jar")
    }
}