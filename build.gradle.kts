plugins {
    id("java")
    id("net.neoforged.gradle.userdev") version "7.0.165"
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
}

group = "com.tlgd.assault"
version = "1.0.0"

base {
    archivesName.set("tlgdassault")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
    maven("https://maven.neoforged.net/releases")
}

val embeddedKotlin by configurations.creating

dependencies {
    implementation("net.neoforged:neoforge:21.1.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.1.0")
    embeddedKotlin("org.jetbrains.kotlin:kotlin-stdlib:2.1.0")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(listOf("-Xlint:unchecked", "-Xlint:deprecation", "-parameters"))
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        freeCompilerArgs.add("-Xjsr305=strict")
    }
}

tasks.jar {
    // Bundle Kotlin's standard library so players only need this mod JAR.
    from(embeddedKotlin.map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["MixinConfigs"] = ""
    }
}
