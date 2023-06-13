@file:Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")
package com.bennyhuo.kotlin.klue.gradle

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.multiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.cocoapods.CocoapodsExtension
import org.jetbrains.kotlin.gradle.plugin.cocoapods.KotlinCocoapodsPlugin
import org.jetbrains.kotlin.gradle.plugin.cocoapods.supportedTargets
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework

class KlueGradlePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.withAllPlugins("com.google.devtools.ksp", "org.jetbrains.kotlin.native.cocoapods") {
            target.afterEvaluate {
                val cocoapodsExtension = target.extensions.getByType(KotlinMultiplatformExtension::class.java).cocoapods

                println("-- baseName: ${getFrameworkBaseName(target)}")
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