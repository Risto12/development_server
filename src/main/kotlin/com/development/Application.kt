package com.development

import io.ktor.server.application.*
import com.development.plugins.*


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    configureSerialization()
    configureResources()
    configureAuthentication()
    configureSockets()
    configStatusPages()
    configureRouting()
}
