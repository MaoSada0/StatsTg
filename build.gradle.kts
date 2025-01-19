plugins {
    kotlin("jvm") version "2.0.20"
    kotlin("plugin.serialization") version "1.9.10"
}

group = "ru.qq"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("org.springframework.boot:spring-boot-starter:3.3.0")
    implementation("org.springframework.boot:spring-boot-starter-web:3.3.0")
    implementation("org.telegram:telegrambots-spring-boot-starter:6.1.0")
}
tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}