plugins {
    kotlin("multiplatform")
    id("com.vanniktech.maven.publish")
}

kotlin {
    js(IR) {
        binaries.library()
        nodejs()
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":klue-runtime-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}