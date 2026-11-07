plugins {
    id("java")
    id("com.github.jengelman.gradle.plugins.shadow") version "8.1.1"
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
}

group = "com.tlgd"
version = "1.0.0"
java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))

repositories {
    mavenCentral()
    maven("https://maven.neoforged.net/releases")
    maven("https://maven.minecraftforge.net/releases")
    maven("https://jitpack.io")
    gradlePluginPortal()
}

dependencies {
    // NeoForge
    minecraft "net.neoforged:neoforge:1.21.1-21.1.0"
    mappings "net.neoforged:neoforge:1.21.1-21.1.0:mappings"

    // Kotlin stdlib
    implementation(kotlin("stdlib-jdk8", "2.1.0"))

    // Config library (Cloth Config para GUIs de config)
    modImplementation "me.shedaniel:cloth-config:15.0.127-1.21.1"

    // Optional: Architectury para cross-platform
    modImplementation "dev.architectury:architectury:11.0.0"

    // Optional: ModMenu para aparecer no menu de mods
    modRuntimeOnly "me.shedaniel:modmenu:8.0.0"
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs << "-Xlint:unchecked" << "-Xlint:deprecation" << "-parameters"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "21"
        freeCompilerArgs += "-Xjsr305=strict"
    }
}

// Shadow jar para desenvolvimento
tasks.shadowJar {
    archiveClassifier.set("dev")
    manifest {
        attributes["Main-Class"] = "com.tlgd.assault.TLGAssaultMod"
    }
}

// Run configurations
tasks.named("runClient") {
    doLast {
        // Configurações de debug se necessário
    }
}
tasks.named("runServer") {
    doLast {
    }
}

// Publishing (opcional)
publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}