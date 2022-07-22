plugins {
    id("com.android.application")
    kotlin("android")
}

repositories {
    // for react native libraries
    maven(url = "$rootDir/reactNativeApp/node_modules/react-native/android")
    // for android jsc
    maven(url = "$rootDir/reactNativeApp/node_modules/jsc-android/dist")

    maven("https://mirrors.tencent.com/nexus/repository/maven-public/") {
        content {
            excludeGroup("com.facebook.react")
        }
    }
}

android {
    compileSdk = 32
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

dependencies {
    implementation(project(":sample-bridge"))
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")

    implementation("com.facebook.react:react-native:0.69.1")
    implementation("org.webkit:android-jsc:+")
}