plugins {
    kotlin("multiplatform")
    id("com.android.application")
}

repositories {
    // for android jsc
    maven(url = "$rootDir/reactNativeApp/node_modules/jsc-android/dist")

    maven("https://mirrors.tencent.com/nexus/repository/maven-public/") {
        content {
            excludeModule("org.webkit", "android-jsc")
        }
    }
}

kotlin {
    android()
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(project(":sample-bridge"))
                implementation("com.google.android.material:material:1.4.0")
                implementation("androidx.appcompat:appcompat:1.3.1")
                implementation("androidx.constraintlayout:constraintlayout:2.1.0")

                implementation("com.facebook.react:react-android:0.71.8")
                implementation("org.webkit:android-jsc:+")
            }
        }
    }
}

android {
    compileSdk = 32
    namespace = "com.bennyhuo.kotlin.klue.android"
    defaultConfig {
        applicationId = "com.bennyhuo.kotlin.klue.android"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        viewBinding = true
    }
}