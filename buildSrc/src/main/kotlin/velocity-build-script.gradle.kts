import gradle.kotlin.dsl.accessors._b2136506e763a2adcd0912a5d2026a22.compileJava
import gradle.kotlin.dsl.accessors._b2136506e763a2adcd0912a5d2026a22.compileKotlin

plugins{
    kotlin("jvm")
}

repositories {
    maven {
        name = "papermc"
        setUrl("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies{
    compileOnly("com.velocitypowered:velocity-api:3.0.1")
    implementation("net.kyori:adventure-text-minimessage:4.10.1")
}

tasks{
    compileJava{
        options.encoding = "UTF-8"
        options.release.set(17)
    }
    compileKotlin{
        kotlinOptions.jvmTarget = "11"
    }
}