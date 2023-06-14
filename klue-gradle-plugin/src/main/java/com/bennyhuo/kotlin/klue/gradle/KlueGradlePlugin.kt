@file:Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")

package com.bennyhuo.kotlin.klue.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.multiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.cocoapods.KotlinCocoapodsPlugin
import org.jetbrains.kotlin.gradle.plugin.cocoapods.supportedTargets
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import java.io.File

class KlueGradlePlugin : Plugin<Project> {
    override fun apply(target: Project) {

        target.extensions.create("klue", KlueExtension::class.java)

        target.withAllPlugins("com.google.devtools.ksp", "org.jetbrains.kotlin.native.cocoapods") {

            val task = target.tasks.register("podGenWrapper", PodGenWrapperTask::class.java)
            target.tasks.getByName("podInstall").dependsOn(task)

            target.afterEvaluate {
                val klueExtension = target.extensions.getByType(KlueExtension::class.java)
                val baseName = getFrameworkBaseName(target)
                    ?: throw IllegalArgumentException("Base name of framework should not be null.")

                val wrapperSourceDir = klueExtension.wrapperSourceDir ?: File(target.buildDir, "generated").absolutePath
                val wrapperFrameworkName = klueExtension.wrapperFrameworkName ?: "${baseName}Wrapper"

                target.ksp.apply {
                    arg("frameworkName", baseName)
                    arg("wrapperFrameworkName", wrapperFrameworkName)
                    arg("wrapperSourceDir", wrapperSourceDir)
                    arg("projectDir", target.projectDir.absolutePath)
                }

                task.configure {
                    it.frameworkName.set(baseName)
                    it.wrapperSourceDir.set(File(wrapperSourceDir))
                    it.wrapperFrameworkName.set(wrapperFrameworkName)
                    it.projectDir.set(target.projectDir)
                }
            }
        }
    }

    private fun getFrameworkBaseName(project: Project): String? {
        return project.multiplatformExtension.supportedTargets().firstNotNullOfOrNull { target ->
            target.binaries
                .matching { it.name.startsWith(KotlinCocoapodsPlugin.POD_FRAMEWORK_PREFIX) }
                .withType(Framework::class.java).firstOrNull()?.baseName
        }
    }

}