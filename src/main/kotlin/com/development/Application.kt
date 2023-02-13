package com.development

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.development.plugins.*
import io.ktor.server.websocket.*
import java.time.Duration

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureSockets()
    configureRouting()
    configureResources()
    configStatusPages()
}
