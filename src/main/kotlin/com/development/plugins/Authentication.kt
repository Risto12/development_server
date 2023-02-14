package com.development.plugins




import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.development.resources.YAMLResourceFactory
import com.development.resources.authentication.AuthenticationResourceBuilder
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
) {
    companion object {
        fun fromConfigs(config: ApplicationConfig) = AlienRealmJwtConfiguration(
            secret = config.property("jwt.secret").getString(),
            issuer = config.property("jwt.issuer").getString(),
            audience = config.property("jwt.audience").getString(),
            realm = config.property("jwt.realm").getString()
        )
    }
}


fun Application.configureAuthentication() {

    val (alienRealm, alienSecret, issuer, alienAudience) = AlienRealmJwtConfiguration.fromConfigs(
        config = this.environment.config
    )

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