package com.development.plugins

import com.development.resources.YAMLResourceFactory
import com.development.resources.authentication.AuthenticationResourceBuilder
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

const val BASIC_AUTH = "basic-auth"
const val JWT_TOKEN_AUTH = "auth-jwt"

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
        jwt(JWT_TOKEN_AUTH) {

        }
    }
}