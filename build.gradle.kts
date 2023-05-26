plugins {
    id("com.android.application") version "7.4.2" apply false
    id("com.android.library") version "7.4.2" apply false
    kotlin("multiplatform") version "1.8.20" apply false
    kotlin("plugin.serialization") version "1.8.20" apply false
    id("com.google.devtools.ksp") version "1.8.20-1.0.11" apply false
    id("com.vanniktech.maven.publish") version "0.22.0" apply false
}

allprojects {
    repositories {
        // for android jsc
        maven(url = "$rootDir/reactNativeApp/node_modules/jsc-android/dist")

        maven("https://mirrors.tencent.com/nexus/repository/maven-public/") {
            content {
                excludeModule("org.webkit", "android-jsc")
            }
        }
    }

    group = property("GROUP").toString()
    version =  property("VERSION_NAME").toString()
}