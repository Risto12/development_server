package com.development.routing

import com.development.models.Endpoints
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement


fun Route.info() {
    route("") {
        get {
            val endpoints = Json.encodeToJsonElement(
                listOf(
                    Endpoints(ALIEN_NEWS_ROOT_PATH,
                        info = "Alien news android application rest api",
                        subPaths = listOf(
                            Endpoints(ALIEN_NEWS_CHANNEL),
                            Endpoints(ALIEN_NEWS_CHANNELS),
                            Endpoints(ALIEN_NEWS_AUTHENTICATED_CHANNEL),
                            Endpoints(ALIEN_NEWS_AUTHENTICATED_CHANNELS)
                    )),
                    Endpoints(
                        DEVELOPMENT_ROOT_PATH,
                        info = "Testing ktor framework related stuff",
                    ),
                    Endpoints(SOCKET_ROOT_PATH,
                        info = "Socket testing endpoints",
                        subPaths = listOf(
                        Endpoints(CHAT_PATH),
                        Endpoints(ECHO_PATH)
                    )
                    )
                )
            )
            call.respond(endpoints)
        }
    }
}