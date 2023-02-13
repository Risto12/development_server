package com.development.plugins

import com.development.resources.YAMLResourceFactory
import com.development.resources.authentication.AuthenticationResourceBuilder
import io.ktor.server.application.*
import io.ktor.server.auth.*

const val BASIC_AUTH = "basic-auth"

fun Application.configureAuthentication() {
    install(Authentication) {
        basic(BASIC_AUTH) {
            realm = "Access to the '/alien/news/*/authenticated/*' paths"
            val authentication = YAMLResourceFactory.factoryMethod(AuthenticationResourceBuilder())
            validate { credentials ->
                if (credentials.name == authentication.username && credentials.password == authentication.password) {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }
    }
}