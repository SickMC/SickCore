plugins {
    java
}

repositories{
    mavenCentral()
    maven ("https://jitpack.io")
    maven ("https://papermc.io/repo/repository/maven-public/")
    maven ("https://nexus.velocitypowered.com/repository/maven-public/")
    maven ( "https://repo.aikar.co/content/groups/aikar/")
    maven ("https://haoshoku.xyz:1234/repository/public/")
    maven("https://repo.thesimplecloud.eu/artifactory/list/gradle-release-local/")
}

dependencies{
    implementation("net.axay:kspigot:1.18.0")
    implementation("net.dv8tion:JDA:5.0.0-alpha.9")
    compileOnly("eu.thesimplecloud.simplecloud:simplecloud-api:2.3.0")
    compileOnly("eu.thesimplecloud.simplecloud:simplecloud-plugin:2.3.0")
    compileOnly("eu.thesimplecloud.simplecloud:simplecloud-base:2.3.0")
    compileOnly("xyz.haoshoku.nick:nickapi:6.3.3-SNAPSHOT")
    compileOnly("com.arcaniax:HeadDatabase-API:1.3.1")
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
    compileOnly("com.velocitypowered:velocity-api:3.0.1")
    compileOnly("co.aikar:acf-paper:0.5.1-SNAPSHOT")
    compileOnly("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
    annotationProcessor("com.velocitypowered:velocity-api:3.0.1")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}