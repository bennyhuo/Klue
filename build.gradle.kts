plugins {
    id("com.android.application") version "7.1.2" apply false
    id("com.android.library") version "7.1.2" apply false
    kotlin("multiplatform") version "1.7.20-Beta" apply false
    kotlin("plugin.serialization") version "1.7.20-Beta" apply false
    id("com.google.devtools.ksp") version "1.7.20-Beta-1.0.6" apply false
    id("com.vanniktech.maven.publish") version "0.22.0" apply false
}

allprojects {
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


    group = property("GROUP").toString()
    version =  property("VERSION_NAME").toString()

}