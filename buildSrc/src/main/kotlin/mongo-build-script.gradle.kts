import gradle.kotlin.dsl.accessors._b2136506e763a2adcd0912a5d2026a22.compileJava
import gradle.kotlin.dsl.accessors._b2136506e763a2adcd0912a5d2026a22.compileKotlin

plugins{
    kotlin("jvm")
}

dependencies{
    implementation("org.litote.kmongo:kmongo-coroutine-serialization:4.6.0")
}

kotlin.sourceSets.all{
    languageSettings.optIn("kotlin.requiresOptIn")
}

tasks{
    compileJava{
        options.encoding = "UTF-8"
        options.release.set(17)
    }
    compileKotlin{
        kotlinOptions.jvmTarget = "11"
    }
}
