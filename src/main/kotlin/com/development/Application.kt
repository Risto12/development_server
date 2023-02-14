package com.development

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.development.plugins.*
import io.ktor.server.websocket.*
import java.time.Duration


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    configureSerialization()
    configureAuthentication()
    configureSockets()
    configStatusPages()
    configureRouting()
    configureResources()
}
