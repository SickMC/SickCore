val projectName = "sickcore"
rootProject.name = projectName

pluginManagement{
    repositories{
        gradlePluginPortal()
    }

    val kotlinVersion = "1.7.0"
    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion
    }
}

include("$projectName-proxy")
include("$projectName-api")
include("$projectName-modules")
include("$projectName-mod")
include("$projectName-server")
