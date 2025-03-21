plugins {
    val kotlinVersion = "2.1.20"

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

val picoliVersion : String by project
val openpdfVersion : String by project

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("com.github.librepdf:openpdf:$openpdfVersion")
    implementation("info.picocli:picocli:$picoliVersion")
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set( "de.vkoop.pdfutils.AppKt")
}

tasks.test {
    // Use the built-in JUnit support of Gradle.
    useJUnitPlatform()
}