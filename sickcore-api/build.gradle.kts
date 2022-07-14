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

    publications {
        create<MavenPublication>("maven") {
            groupId = "net.sickmc.sickcore"
            artifactId = "sickcore-api"
            version = "2.0.0"

            from(components["java"])
        }
    }

}



