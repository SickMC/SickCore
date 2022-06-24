plugins{
    kotlin("jvm")
}

dependencies{
    implementation("org.litote.kmongo:kmongo-coroutine-serialization:4.6.1")
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
