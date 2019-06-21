import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.3.40"

    id("application")

    kotlin("jvm") version kotlinVersion
}

group = "de.vkoop"
version = "1.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    compile("com.github.librepdf:openpdf:1.2.20")
    compile("info.picocli:picocli:3.9.6")

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "de.vkoop.pdfutils.AppKt"
}
