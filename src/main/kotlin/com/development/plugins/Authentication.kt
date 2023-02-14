package com.development.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.development.utility.PropertyHelper.getProperty
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import io.ktor.server.response.*

const val BASIC_AUTH = "basic-auth"
const val JWT_TOKEN_AUTH = "auth-jwt"

data class AlienRealmJwtConfiguration(
    val realm: String,
    val secret: String,
    val issuer: String,
    val audience: String,
    private val validTimeMinutes: Int
) {
    fun validTimeAsMillis(): Int = (validTimeMinutes * 60) * 1000

    companion object {
        fun fromConfigs(config: ApplicationConfig) = AlienRealmJwtConfiguration(
            secret = getProperty("jwt.secret", config),
            issuer = getProperty("jwt.issuer", config),
            audience = getProperty("jwt.audience", config),
            realm = getProperty("jwt.realm", config),
            validTimeMinutes = getProperty("jwt.valid_time_minutes", config).toInt()
        )
    }
}

data class AlienRealmBasicAuthConfigurations(
    val username: String,
    val password: String,
) {
    companion object {
        fun fromConfigs(config: ApplicationConfig) = AlienRealmBasicAuthConfigurations(
            username = getProperty("basic_auth.username", config),
            password = getProperty("basic_auth.password", config),
        )
    }
}

fun Application.getAlienRealmJwtConfiguration() = AlienRealmJwtConfiguration.fromConfigs(
    this.environment.config
)

fun Application.getBasicAuthConfiguration() = AlienRealmBasicAuthConfigurations.fromConfigs(
    this.environment.config
)

fun Application.configureAuthentication() {

    val (alienRealm, alienSecret, issuer, alienAudience) = getAlienRealmJwtConfiguration()
    val (basicAuthUsername, basicAuthPassword) = getBasicAuthConfiguration()

    install(Authentication) {
        basic(BASIC_AUTH) {
            realm = alienRealm
            validate { credentials ->
                if (credentials.name == basicAuthUsername && credentials.password == basicAuthPassword) {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }

        jwt(JWT_TOKEN_AUTH) {
            realm = alienRealm

            verifier(
                JWT
                .require(Algorithm.HMAC256(alienSecret))
                .withAudience(alienAudience)
                .withIssuer(issuer)
                .build())

            validate { credential ->
                    if (credential.payload.getClaim("username").asString() != "") {
                        JWTPrincipal(credential.payload)
                    } else {
                        null
                    }
                }

            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Not valid Token")
            }
        }
    }
}