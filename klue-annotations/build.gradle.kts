plugins {
    kotlin("multiplatform")
    id("com.vanniktech.maven.publish")
}

kotlin {
    jvm()
    js(IR) {
        binaries.library()
        nodejs()
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {

    }
}

