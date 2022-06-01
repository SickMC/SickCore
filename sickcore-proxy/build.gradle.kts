import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins{
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    `velocity-build-script`
    `redis-build-script`
    `mongo-build-script`
}
dependencies {
    implementation(modProject(":${rootProject.name}-api"))
    implementation(modProject(":${rootProject.name}-modules"))
    shadow(kotlin("stdlib"))
    shadow("org.litote.kmongo:kmongo-coroutine-serialization:4.6.0")
    shadow("io.github.crackthecodeabhi:kreds:0.7")
}
repositories {
    mavenCentral()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}