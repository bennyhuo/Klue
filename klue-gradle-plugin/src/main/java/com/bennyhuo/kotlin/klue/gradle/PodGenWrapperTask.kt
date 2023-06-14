package com.bennyhuo.kotlin.klue.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class PodGenWrapperTask : DefaultTask() {

    @get:Input
    abstract val wrapperFrameworkName: Property<String>

    @get:Input
    abstract val frameworkName: Property<String>

    @get:OutputDirectory
    abstract val wrapperSourceDir: DirectoryProperty

    @get:OutputDirectory
    abstract val projectDir: DirectoryProperty

    @TaskAction
    fun generate() {
        var klueModuleText = this.javaClass.classLoader.getResourceAsStream("template/KlueModule.tpl")
            ?.bufferedReader()?.use {
                it.readText()
            } ?: return

        klueModuleText = klueModuleText.replace("{{frameworkName}}", frameworkName.get())
        File(wrapperSourceDir.asFile.get(), "KlueModule.swift").writeText(klueModuleText)

        var podSpecText = this.javaClass.classLoader.getResourceAsStream("template/podspec.tpl")
            ?.bufferedReader()?.use {
                it.readText()
            } ?: return

        podSpecText = podSpecText
            .replace("{{frameworkName}}", frameworkName.get())
            .replace("{{wrapperFrameworkName}}", wrapperFrameworkName.get())
            .replace("{{wrapperSourceDir}}", "${wrapperSourceDir.asFile.get().relativeTo(projectDir.asFile.get()).path}/**/*.swift")
        File(projectDir.asFile.get(), "${wrapperFrameworkName.get()}.podspec").writeText(podSpecText)
    }

}