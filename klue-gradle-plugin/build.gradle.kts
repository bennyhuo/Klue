import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-gradle-plugin`
    kotlin("jvm") version "1.8.20"
    id("com.vanniktech.maven.publish") version "0.22.0"
}

repositories {
    maven("https://mirrors.tencent.com/nexus/repository/maven-public/")
}

group = property("GROUP").toString()
version =  property("VERSION_NAME").toString()

gradlePlugin {
    plugins {
        create("KlueGradlePlugin") {
            id = "com.bennyhuo.klue"
            implementationClass = "com.bennyhuo.kotlin.klue.gradle.KlueGradlePlugin"
        }
    }
}

dependencies {
    implementation("com.google.devtools.ksp:symbol-processing-gradle-plugin:1.8.20-1.0.11")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.20")
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs = listOf(
            "-Xopt-in=com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview",
            "-Xopt-in=com.google.devtools.ksp.KspExperimental"
        )
    }
}