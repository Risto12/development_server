package com.development.utility

import io.ktor.server.config.*

object PropertyHelper {
    fun getProperty(name: String, config: ApplicationConfig) = config.property(name).getString()
}