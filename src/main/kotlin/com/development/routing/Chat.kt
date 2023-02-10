package com.development.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

const val CHAT_ROOT_PATH = "/chat"

/***
 * Socket based route
 */
fun Route.chat() {
    route(CHAT_ROOT_PATH) {
        get {
            call.respondText("TODO", status = HttpStatusCode.OK)
        }
    }
}