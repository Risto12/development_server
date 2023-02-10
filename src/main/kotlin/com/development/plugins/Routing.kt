package com.development.plugins

import com.development.routing.alienNews
import io.ktor.server.routing.routing
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        alienNews()
    }
}
