import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("com.vanniktech.maven.publish")
}

dependencies {
    implementation("com.google.devtools.ksp:symbol-processing-api:1.7.0-1.0.6")
    implementation("com.squareup:kotlinpoet:1.12.0")
    implementation("com.squareup:kotlinpoet-ksp:1.12.0")
    
    implementation(project(":klue-annotations"))
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs = listOf(
            "-Xopt-in=com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview",
            "-Xopt-in=com.google.devtools.ksp.KspExperimental"
        )
    }
}