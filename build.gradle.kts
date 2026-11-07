plugins {
    id("java")
    id("net.neoforged.gradle.userdev") version "7.0.165"
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
}

base {
    archivesName.set("tlgdassault")
}

version = "1.0.0"
group = "com.tlgd.assault"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
    maven("https://maven.neoforged.net/releases")
}

dependencies {
    implementation("net.neoforged:neoforge:1.21.1-47.0.0")
    implementation(kotlin("stdlib"))
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        freeCompilerArgs.add("-Xjsr305=strict")
    }
}
