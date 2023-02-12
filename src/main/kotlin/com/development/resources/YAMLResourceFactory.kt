package com.development.resources


import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File

const val RESOURCE_FOLDER_ROOT = "src/main/resources/"

object YAMLResourceFactory {
    fun <T>factoryMethod(resource:YAMLResourceBuilder<T>): T = build(resource)

    private fun <T> build(resource:YAMLResourceBuilder<T>): T {
        return ObjectMapper(YAMLFactory())
            .registerKotlinModule()
            .readValue(File(resource.getResourcePath()), resource.getResourceClassModel())
    }
}
