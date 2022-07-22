plugins {
    kotlin("multiplatform")
}

version = "1.0"

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

