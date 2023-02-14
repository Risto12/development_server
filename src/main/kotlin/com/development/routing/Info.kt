package com.development.routing

import com.development.utility.EndPointInfoGenerator
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement


fun Route.info() {
    route("") {
        get {
            val endpoints = Json.encodeToJsonElement(
                EndPointInfoGenerator.getAllEndPoints()
            )
            call.respond(endpoints)
        }
    }
}