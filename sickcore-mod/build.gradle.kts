plugins{
    kotlin("jvm")
    kotlin("plugin.serialization")
    `redis-build-script`
    `mongo-build-script`
    `mod-build-script`
}

dependencies {
    include(implementation(modProject(":${rootProject.name}-api"))!!)
    include(implementation(modProject(":${rootProject.name}-modules"))!!)

    include(implementation("org.litote.kmongo:kmongo-coroutine-serialization:4.6.0")!!)
    include(implementation("io.github.crackthecodeabhi:kreds:0.7")!!)
}