plugins{
    kotlin("jvm")
    kotlin("plugin.serialization")
    `redis-build-script`
    `mongo-build-script`
    `mod-build-script`
}

dependencies {
    implementation(modProject(":${rootProject.name}-api"))
    implementation(modProject(":${rootProject.name}-modules"))
}