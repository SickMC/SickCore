import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("fabric-loom")
    id("io.github.juuxel.loom-quiltflower")
    id("org.quiltmc.quilt-mappings-on-loom")
}

repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net/")
    maven("https://server.bbkr.space/artifactory/libs-release/")
    maven("https://maven.quiltmc.org/repository/release/")
}

dependencies {
    minecraft("com.mojang:minecraft:${BuildConstants.minecraftVersion}")
    mappings(loom.layered {
        //addLayer(quiltMappings.mappings("org.quiltmc:quilt-mappings:${BuildConstants.quiltMappingsVersion}"))
        officialMojangMappings()
    })
    modImplementation("net.fabricmc:fabric-loader:${BuildConstants.fabricLoaderVersion}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${BuildConstants.fabricAPIVersion}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${BuildConstants.fabricLanguageKotlinVersion}")

    modImplementation("net.silkmc:silk-core:${BuildConstants.silkVersion}")
    modImplementation("net.silkmc:silk-commands:${BuildConstants.silkVersion}")
    modImplementation("net.silkmc:silk-game:${BuildConstants.silkVersion}")

    include("net.sickmc:sickapi:${BuildConstants.sickAPIVersion}")
    include("org.litote.kmongo:kmongo-coroutine-serialization:${BuildConstants.kmongoVersion}")
    include("org.litote.kmongo:kmongo-shared:${BuildConstants.kmongoVersion}")
    include("org.litote.kmongo:kmongo-data:${BuildConstants.kmongoVersion}")
    include("org.litote.kmongo:kmongo-id:${BuildConstants.kmongoVersion}")
    include("org.litote.kmongo:kmongo-property:${BuildConstants.kmongoVersion}")
    include("org.litote.kmongo:kmongo-serialization-mapping:${BuildConstants.kmongoVersion}")
    include("org.litote.kmongo:kmongo-coroutine-core:${BuildConstants.kmongoVersion}")
    include("org.litote.kmongo:kmongo-async-shared:${BuildConstants.kmongoVersion}")
    include("org.mongodb:bson:4.7.1")
    include("org.mongodb:bson-record-codec:4.7.1")
    include("org.mongodb:mongodb-driver-core:4.7.1")
    include("org.mongodb:mongodb-driver-reactivestreams:4.7.1")
    include("com.github.jershell:kbson:0.4.5")
    include("io.projectreactor:reactor-core:3.2.22.RELEASE")
    include("org.reactivestreams:reactive-streams:1.0.3")
    include("org.litote:kreflect:1.0.0")
}

@Serializable
data class FabricModConfiguration(
    val schemaVersion: Int,
    val id: String,
    val version: String,
    val name: String,
    val description: String,
    val authors: List<String>,
    val contributors: List<String>,
    val entrypoints: LinkedHashMap<String, List<Entrypoint>> = linkedMapOf(),
    val mixins: List<String> = emptyList(),
    val depends: LinkedHashMap<String, String>,
    val contact: Contact,
    val license: String,
    val icon: String? = null
) {
    @Serializable
    data class Contact(
        val homepage: String,
        val issues: String,
        val sources: String,
        val discord: String,
    )

    @Serializable
    data class Entrypoint(
        val adapter: String,
        val value: String,
    )
}

val modName: String by extra
val modID: String by extra(project.name)
val modEntrypoints: LinkedHashMap<String, List<String>>? by extra(null)
val modMixinFiles: List<String>? by extra(null)
val modDepends: LinkedHashMap<String, String>? by extra(null)

tasks {
    val modDotJsonTask = register("modDotJson") {
        val modConfig = FabricModConfiguration(
            1,
            modID,
            rootProject.version.toString(),
            modName,
            project.description.toString(),
            BuildConstants.authors,
            BuildConstants.contributors,
            modEntrypoints?.mapValuesTo(LinkedHashMap()) {
                it.value.map { target -> FabricModConfiguration.Entrypoint("kotlin", target) }
            } ?: linkedMapOf(),
            modMixinFiles ?: emptyList(),
            linkedMapOf(
                "fabric-api" to ">=${BuildConstants.fabricAPIVersion}",
                "fabric-language-kotlin" to ">=${BuildConstants.fabricLanguageKotlinVersion}",
                "minecraft" to ">=${BuildConstants.minecraftVersion}",
                "silk-all" to ">=${BuildConstants.silkVersion}"
            ).apply { putAll(modDepends ?: emptyMap()) },
            FabricModConfiguration.Contact(
                "https://github.com/${BuildConstants.githubRepo}",
                "https://github.com/${BuildConstants.githubRepo}/issues",
                "https://github.com/${BuildConstants.githubRepo}",
                "https://discord.gg/emTX78nhnz"
            ),
            "GPL-3.0"
        )

        val modDotJson = buildDir.resolve("resources/main/fabric.mod.json")

        inputs.property("modConfig", modConfig.toString())
        outputs.file(modDotJson)

        doFirst {
            val prettyJson = Json { prettyPrint = true }

            if (!modDotJson.exists()) {
                modDotJson.parentFile.mkdirs()
                modDotJson.createNewFile()
            }

            modDotJson.writeText(prettyJson.encodeToString(modConfig))
        }
    }

    processResources {
        dependsOn(modDotJsonTask)
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
            freeCompilerArgs += "-Xskip-prerelease-check"
        }
    }

    withType<JavaCompile> {
        options.release.set(17)
    }
}