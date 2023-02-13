package com.development.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*

const val BASIC_AUTH = "basic-auth"

fun Application.configureAuthentication() {
    install(Authentication) {
        basic(BASIC_AUTH) {
            realm = "Access to the '/alien/news/*/authenticated/*' paths"
            validate { credentials ->
                if (credentials.name == "test" && credentials.password == "test") {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }
    }
}