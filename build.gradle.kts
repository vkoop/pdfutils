import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.5.31"

    id("application")

    kotlin("jvm") version kotlinVersion

    id("com.github.ben-manes.versions") version "0.39.0"
}

group = "de.vkoop"
version = "v1.0.9"

repositories {
    mavenCentral()
}

distributions {
    main {
        version = ""
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("com.github.librepdf:openpdf:1.3.26")
    implementation("info.picocli:picocli:4.6.1")
}

tasks.withType<KotlinCompile> {

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

application {
    mainClassName = "de.vkoop.pdfutils.AppKt"
}
