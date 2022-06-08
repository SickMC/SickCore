import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins{
    kotlin("jvm")
    id("fabric-loom")
    id("io.github.juuxel.loom-quiltflower")
    id("org.quiltmc.quilt-mappings-on-loom")
}

repositories{
    mavenCentral()
}

dependencies {
    val minecraftVersion = "1.19"
    val quiltMappingsVersion = "1.19+build.1:v2"
    val fabricAPIVersion = "0.55.2+1.19"
    val fabricLoaderVersion = "0.14.7"
    val fabricLanguageKotlinVersion = "1.7.4+kotlin.1.6.21"

    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings(loom.layered {
        addLayer(quiltMappings.mappings("org.quiltmc:quilt-mappings:$quiltMappingsVersion"))
        officialMojangMappings()
    })

    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricAPIVersion")
    modImplementation("net.fabricmc:fabric-loader:$fabricLoaderVersion")
    modImplementation("net.fabricmc:fabric-language-kotlin:$fabricLanguageKotlinVersion")

    val fabrikVersion = "1.8.0"
    modImplementation("net.axay:fabrikmc-core:$fabrikVersion")
    modImplementation("net.axay:fabrikmc-commands:$fabrikVersion")
    modImplementation("net.axay:fabrikmc-igui:$fabrikVersion")
    modImplementation("net.axay:fabrikmc-persistence:$fabrikVersion")
    modImplementation("net.axay:fabrikmc-nbt:$fabrikVersion")
}

tasks{
    processResources {
        val props = mapOf(
            "version" to project.version,
            "description" to project.description,
            "mc_version" to "1.19"
        )

        inputs.properties(props)

        filesMatching("fabric.mod.json") {
            expand(props)
        }
    }
    withType<JavaCompile>{
        options.apply{
            release.set(17)
            encoding = "UTF-8"
        }
    }
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}
