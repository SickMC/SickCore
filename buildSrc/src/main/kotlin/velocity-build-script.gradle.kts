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