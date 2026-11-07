plugins {
    `java-library`
    `maven-publish`
    id("net.neoforged.gradle.userdev") version "7.1.38"
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
}

version = "1.0.0"
group = "com.tlgd.assault"

base {
    archivesName.set("tlgdassault")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

repositories {
    mavenCentral()
}

val embeddedKotlin by configurations.creating

dependencies {
    implementation("net.neoforged:neoforge:21.1.0")
    implementation(kotlin("stdlib"))
    embeddedKotlin(kotlin("stdlib"))
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
    // Bundle Kotlin's standard library so players do not need a separate Kotlin mod.
    from(embeddedKotlin.map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
