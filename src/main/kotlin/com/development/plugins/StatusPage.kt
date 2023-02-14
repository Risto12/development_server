package com.development.plugins

import com.development.exceptions.NoContentException
import com.development.exceptions.TokenAuthenticationException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configStatusPages() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            when(cause) {
                is NoContentException -> call.respondText(
                    text = cause.message ?: "No content",
                    status = HttpStatusCode.NoContent
                )
                is TokenAuthenticationException -> call.respondText(
                    text = cause.message ?: "Authentication failure",
                    status = HttpStatusCode.Unauthorized
                )
                else -> call.respondText(text = "Internal server error", status = HttpStatusCode.InternalServerError)
            }
        }
    }
}