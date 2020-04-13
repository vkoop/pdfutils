import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.3.71"

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

    implementation("com.github.librepdf:openpdf:1.3.12")
    implementation("info.picocli:picocli:4.1.4")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "de.vkoop.pdfutils.AppKt"
}
