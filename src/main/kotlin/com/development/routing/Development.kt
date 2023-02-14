package com.development.routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

const val DEVELOPMENT_ROOT_PATH = "/ktor/development"

/***
 * This endpoint is for testing ktor framework functionality. Example testing getting resources from resource file.
 */
fun Route.development() {
    route(DEVELOPMENT_ROOT_PATH) {
        get {
            val output = ""
            call.respondText(output)
        }
    }
}