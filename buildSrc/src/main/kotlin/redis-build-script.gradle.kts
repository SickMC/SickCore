plugins{
    kotlin("jvm")
}

dependencies{
    implementation("io.github.crackthecodeabhi:kreds:0.7")
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