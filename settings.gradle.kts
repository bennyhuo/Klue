pluginManagement {
    repositories {
        maven("https://mirrors.tencent.com/nexus/repository/maven-public/")
        maven("https://mirrors.tencent.com/nexus/repository/gradle-plugins")
    }
}

rootProject.name = "klue"
include(":androidApp")
include(":klue-runtime-js")
include(":klue-runtime-common")
include(":klue-runtime-webview")
include(":klue-runtime-reactnative")
include(":klue-compiler")
include(":klue-annotations")
include(":sample-bridge")

includeBuild("klue-gradle-plugin")