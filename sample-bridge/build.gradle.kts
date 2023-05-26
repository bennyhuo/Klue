import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.plugin.KotlinTarget
import org.jetbrains.kotlin.gradle.targets.js.npm.npmProject
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackOutput

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    kotlin("plugin.serialization")
    id("com.google.devtools.ksp")
}

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    js(IR) {
        moduleName = "SampleBridge"
        binaries.executable()
        browser {
            commonWebpackConfig {
                output = KotlinWebpackOutput(
                    library = "SampleBridge",
                    libraryTarget = KotlinWebpackOutput.Target.UMD,
                    globalObject = "this"
                )
                outputFileName = "SampleBridge.js"
            }
        }
        nodejs()
        generateTypeScriptDefinitions()

        println("-----> ${compilations.getByName("main").npmProject.dir}")

        tasks.register<Copy>("copyReactNativeOutput") {
            group = "browser"
            dependsOn("jsDevelopmentExecutableCompileSync")
            dependsOn("jsPackageJson")

            from(compilations.getByName("main").npmProject.dir)
            into(File(rootDir, "reactNativeApp/node_modules/SampleBridge"))
        }

        tasks.register<Copy>("copyWebOutput") {
            group = "browser"
            dependsOn("jsDevelopmentExecutableCompileSync")
            from(File(compilations.getByName("main").npmProject.dir, "kotlin"))
            into(File(rootDir, "webApp/static"))
        }
    }

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "SampleBridge"
            export(project(":klue-runtime-webview"))
            export(project(":klue-runtime-reactnative"))
            export(project(":klue-runtime-common"))
        }
        name = "SampleBridge"
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.3")
                api(project(":klue-runtime-webview"))
                api(project(":klue-runtime-reactnative"))
                api(project(":klue-runtime-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(kotlin("reflect"))
            }
        }

        val jsMain by getting
        jsMain.kotlin.srcDir("build/generated/ksp/js/jsMain/kotlin")

        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}


android {
    compileSdk = 32
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 32
    }

    libraryVariants.all {
        sourceSets.forEach {
            it.kotlinDirectories
        }
    }
}

fun addKspSourceSetToCompilation(target: KotlinTarget, compilation: KotlinCompilation<*>) {
    compilation.defaultSourceSet.kotlin.srcDir(
        "${buildDir}/generated/ksp/${target.name}/${compilation.defaultSourceSetName}/kotlin"
    )
}

kotlin.targets.forEach { target ->
    if (target.platformType == KotlinPlatformType.androidJvm) {
        android.libraryVariants.all {
            val compilation = target.compilations.getByName(name)
            addKspSourceSetToCompilation(target, compilation)
        }
    } else {
        target.compilations.forEach {
            addKspSourceSetToCompilation(target, it)
        }
    }
}


dependencies {
    add("kspCommonMainMetadata", project(":klue-compiler"))
    add("kspAndroid", project(":klue-compiler"))
    add("kspIosArm64", project(":klue-compiler"))
    add("kspIosSimulatorArm64", project(":klue-compiler"))
    add("kspIosX64", project(":klue-compiler"))
    add("kspJs", project(":klue-compiler"))
}

tasks.register<Copy>("copyJsOutput") {
    group = "browser"
    val jsBrowserDistribution = tasks.named("jsBrowserDistribution")
    from(jsBrowserDistribution)
    into(File(rootDir, "web"))
}

rootProject.extensions.configure<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension> {
    versions.webpackCli.version = "4.10.0"
}