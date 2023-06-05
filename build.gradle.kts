plugins {
    val kotlinVersion = "1.8.21"

    id("application")

    kotlin("jvm") version kotlinVersion

    id("com.github.ben-manes.versions") version "0.46.0"
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
    implementation("info.picocli:picocli:4.7.4")
}

kotlin {

    // For example:
    jvmToolchain(8)
}

application {
    mainClass.set( "de.vkoop.pdfutils.AppKt")
}

tasks.test {
    // Use the built-in JUnit support of Gradle.
    useJUnitPlatform()
}