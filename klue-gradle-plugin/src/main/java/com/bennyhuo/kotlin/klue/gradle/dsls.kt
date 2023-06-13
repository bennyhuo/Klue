package com.bennyhuo.kotlin.klue.gradle

import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.cocoapods.CocoapodsExtension

val KotlinMultiplatformExtension.cocoapods: CocoapodsExtension get() =
    (this as ExtensionAware).extensions.getByName("cocoapods") as CocoapodsExtension



/**
 * Retrieves the [ksp][com.google.devtools.ksp.gradle.KspExtension] extension.
 */
val Project.ksp: KspExtension get() =
    (this as ExtensionAware).extensions.getByName("ksp") as KspExtension

/**
 * Configures the [ksp][com.google.devtools.ksp.gradle.KspExtension] extension.
 */
fun Project.ksp(configure: Action<KspExtension>): Unit =
    (this as ExtensionAware).extensions.configure("ksp", configure)



