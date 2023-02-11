package com.development.configurations


import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File


const val RESOURCE_FOLDER_ROOT = "src/main/resources/"

interface YAMLApplicationResource<T> {
    val resourceFile: String
    fun getResourcePath(): String = "$RESOURCE_FOLDER_ROOT$resourceFile"
    fun getResourceClassModel(): Class<T>
}

object ResourceFactory {
    fun <T>factoryMethod(resource:YAMLApplicationResource<T>): T = resourceBuilder(resource)

    private fun <T> resourceBuilder(resource:YAMLApplicationResource<T>): T {
        return ObjectMapper(YAMLFactory())
            .registerKotlinModule()
            .readValue(File(resource.getResourcePath()), resource.getResourceClassModel())
    }
}
