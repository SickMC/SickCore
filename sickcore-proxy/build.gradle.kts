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
    implementation(kotlin("stdlib"))
    shadow("org.jetbrains.kotlin:kotlin-stdlib:1.7.0")
    shadow("org.litote.kmongo:kmongo-coroutine-serialization:4.6.0")
    shadow("io.github.crackthecodeabhi:kreds:0.7")
    shadow(modProject(":${rootProject.name}-api"))
    shadow(modProject(":${rootProject.name}-modules"))
}
repositories {
    mavenCentral()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}