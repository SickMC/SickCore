import java.net.URI

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    `velocity-build-script`
    `mongo-build-script`
    `mod-build-script`
    `websocket-client-script`
    `maven-publish`
}

publishing {

    repositories{
        maven {
            name = "SickCore-API"
            url = URI("https://maven.pkg.github.com/sickmc/sickcore")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }

}

