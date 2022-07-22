pluginManagement {
    repositories {
        maven("https://mirrors.tencent.com/nexus/repository/maven-public/")
        gradlePluginPortal()
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