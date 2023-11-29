plugins {
    val kotlinVersion = "1.9.21"

    id("application")

    kotlin("jvm") version kotlinVersion
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

    implementation("com.github.librepdf:openpdf:1.3.33")
    implementation("info.picocli:picocli:4.7.5")
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set( "de.vkoop.pdfutils.AppKt")
}

tasks.test {
    // Use the built-in JUnit support of Gradle.
    useJUnitPlatform()
}