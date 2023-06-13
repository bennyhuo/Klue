package com.bennyhuo.kotlin.klue.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

internal fun Project.withAllPlugins(vararg pluginIds: String, action: (List<Plugin<*>>) -> Unit) {
    return withAllPlugins(pluginIds.toList(), action)
}

internal fun Project.withAllPlugins(pluginIds: List<String>, action: (List<Plugin<*>>) -> Unit) {
    when {
        pluginIds.size > 1 -> {
            val left = pluginIds.subList(0, pluginIds.size - 1)
            val right = pluginIds.last()
            plugins.withId(right) { plugin ->
                withAllPlugins(left) { plugins ->
                    action(plugins + plugin)
                }
            }
        }
        pluginIds.size == 1 -> {
            plugins.withId(pluginIds[0]) {
                action(listOf(it))
            }
        }
        else -> {
            // empty pluginIds, do noting.
        }
    }
}