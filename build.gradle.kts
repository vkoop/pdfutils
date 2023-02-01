import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.8.0"

    id("application")

    kotlin("jvm") version kotlinVersion

    id("com.github.ben-manes.versions") version "0.45.0"
}

group = "de.vkoop"
version = "v1.0.16"

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

    implementation("com.github.librepdf:openpdf:1.3.30")
    implementation("info.picocli:picocli:4.7.1")
}

tasks.withType<KotlinCompile> {

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

application {
    mainClass.set( "de.vkoop.pdfutils.AppKt")
}
