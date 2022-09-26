plugins {
    kotlin("jvm")
    `project-script`
    `fabric-script`
}

val modName by extra("SickCore API")
val modID by extra("sickcore-api")
val modEntrypoints by extra(linkedMapOf("main" to listOf("net.sickmc.sickcore.api.fabric.MainKt")))
//val modMixinFiles by extra(listOf("sickcore.mixins.json"))