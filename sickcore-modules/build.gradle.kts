plugins{
    kotlin("jvm")
    kotlin("plugin.serialization")
    `velocity-build-script`
    `redis-build-script`
    `mongo-build-script`
    `mod-build-script`
}

dependencies{
    implementation(modProject(":${rootProject.name}-api"))
}