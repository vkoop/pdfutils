import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.3.61"

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

    compile("com.github.librepdf:openpdf:1.3.11")
    compile("info.picocli:picocli:4.0.4")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "de.vkoop.pdfutils.AppKt"
}
