plugins{
    kotlin("jvm")
    kotlin("plugin.serialization")
    `mongo-build-script`
    `mod-build-script`
}

dependencies {
    include(implementation(modProject(":${rootProject.name}-api"))!!)
    include(implementation(modProject(":${rootProject.name}-modules"))!!)

    val modInclude: Configuration by configurations.creating
    modInclude(implementation("org.litote.kmongo:kmongo-coroutine-serialization:4.6.0")!!)

    modInclude.resolvedConfiguration.resolvedArtifacts.forEach {
        include(it.moduleVersion.id.toString())
    }
}

kotlin.sourceSets.all {
    languageSettings.optIn("kotlin.RequiresOptIn")
}
