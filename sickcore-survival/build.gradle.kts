plugins {
    kotlin("jvm")
    `project-script`
    `fabric-script`
}

dependencies {
    api(modProject(":${rootProject.name}-api:fabric"))
}

val modName by extra("Survival")
val modEntrypoints by extra(linkedMapOf("main" to listOf("net.sickmc.sickcore.survival.MainKt")))
val modMixinFiles by extra(listOf("sickcore.mixins.json"))