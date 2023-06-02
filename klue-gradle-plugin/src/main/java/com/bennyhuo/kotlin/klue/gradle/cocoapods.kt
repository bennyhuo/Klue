package com.bennyhuo.kotlin.klue.gradle

import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.cocoapods.CocoapodsExtension

val KotlinMultiplatformExtension.cocoapods: CocoapodsExtension get() =
    (this as ExtensionAware).extensions.getByName("cocoapods") as CocoapodsExtension