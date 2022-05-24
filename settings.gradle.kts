rootProject.name = "SickCore"

pluginManagement{
    repositories{
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.quiltmc.org/repository/release/")
        maven("https://server.bbkr.space/artifactory/libs-release/")
    }
}
include("src:main:java")
findProject(":src:main:java")?.name = "java"
