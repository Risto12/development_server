package com.development.routing

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.development.exceptions.NoContentException
import com.development.exceptions.TokenAuthenticationException
import com.development.models.Endpoints
import com.development.plugins.BASIC_AUTH
import com.development.plugins.JWT_TOKEN_AUTH
import com.development.plugins.getAlienRealmJwtConfiguration
import com.development.resources.*
import com.development.resources.newsChannel.NewsChannelResourceBuilder
import com.development.utility.EndPointInfoGenerator
import io.ktor.http.ContentDisposition.Companion.File
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import java.io.File
import java.util.*

const val CHANNEL_ID_URL_RESOURCE = "channel_name"
const val IMAGE_ID_URL_RESOURCE = "image_id"

const val VERSION_ONE = "v1"
const val ALIEN_NEWS_ROOT_PATH = "/alien/news"

const val ALIEN_NEWS_CHANNELS = "$ALIEN_NEWS_ROOT_PATH/$VERSION_ONE/channels"
const val ALIEN_NEWS_CHANNEL = "$ALIEN_NEWS_ROOT_PATH/$VERSION_ONE/channel/{$CHANNEL_ID_URL_RESOURCE}"
const val ALIEN_NEWS_IMAGE = "$ALIEN_NEWS_ROOT_PATH/$VERSION_ONE/image"

const val ALIEN_NEWS_BASIC_AUTHENTICATED_CHANNELS = "$ALIEN_NEWS_ROOT_PATH/$VERSION_ONE/authenticated/basic/channels"
const val ALIEN_NEWS_BASIC_AUTHENTICATED_CHANNEL =
    "$ALIEN_NEWS_ROOT_PATH/$VERSION_ONE/authenticated/basic/channel/{$CHANNEL_ID_URL_RESOURCE}"
const val ALIEN_NEWS_BASIC_AUTHENTICATED_IMAGE = "$ALIEN_NEWS_ROOT_PATH/$VERSION_ONE/authenticated/basic/image/{image_id}"

const val ALIEN_NEWS_TOKEN_AUTHENTICATED_LOGIN = "$ALIEN_NEWS_ROOT_PATH/$VERSION_ONE/authenticated/token/login"
const val ALIEN_NEWS_TOKEN_AUTHENTICATED_CHANNELS = "$ALIEN_NEWS_ROOT_PATH/$VERSION_ONE/authenticated/token/channels"
const val ALIEN_NEWS_TOKEN_AUTHENTICATED_CHANNEL =
    "$ALIEN_NEWS_ROOT_PATH/$VERSION_ONE/authenticated/token/channel/{$CHANNEL_ID_URL_RESOURCE}"
const val ALIEN_NEWS_TOKEN_AUTHENTICATED_IMAGE = "$ALIEN_NEWS_ROOT_PATH/$VERSION_ONE/authenticated/token/image/{image_id}"

fun YAMLResourceFactory.getNewsChannelResource() = factoryMethod(NewsChannelResourceBuilder())

private fun getChannelAsJson(channel: String) = Json.encodeToJsonElement(
    YAMLResourceFactory.getNewsChannelResource().getChannel(channel)
        ?: throw NoContentException(message = "No such channel")
)

private fun getChannelsAsJson() = Json.encodeToJsonElement(YAMLResourceFactory.getNewsChannelResource().getChannels())

fun Route.alienNews() {

    val newsChannels: suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit = {
        call.respond(getChannelsAsJson())
    }

    val newsChannel: suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit = {
        val channel = getChannelAsJson(call.parameters[CHANNEL_ID_URL_RESOURCE]!!)
        call.respond(channel)
    }

    route(ALIEN_NEWS_ROOT_PATH) {
        get {
            val endpoints = Json.encodeToJsonElement(EndPointInfoGenerator.getAlienNews())
            call.respond(endpoints)
        }
    }

    // Authenticated and non authenticated routing could have been made as sub routes
    route(ALIEN_NEWS_CHANNELS) { get(newsChannels) }

    route(ALIEN_NEWS_CHANNEL) { get(newsChannel) }

    route(ALIEN_NEWS_BASIC_AUTHENTICATED_CHANNELS) {
        authenticate(BASIC_AUTH) { get(newsChannels) }
    }

    route(ALIEN_NEWS_BASIC_AUTHENTICATED_CHANNEL) {
        authenticate(BASIC_AUTH) { get(newsChannel) }
    }


    route(ALIEN_NEWS_TOKEN_AUTHENTICATED_LOGIN) {
        post {
            val configs = application.getAlienRealmJwtConfiguration()
            val parameters = call.receiveParameters()
            val username = parameters["username"] ?: throw TokenAuthenticationException()
            val password = parameters["password"] ?: throw TokenAuthenticationException()

            if(password != "todo") throw TokenAuthenticationException() // TODO authentication credentials from database

            val token = JWT.create()
                .withAudience(configs.audience)
                .withIssuer(configs.issuer)
                .withClaim("username", username)
                .withExpiresAt(Date(System.currentTimeMillis() + configs.validTimeAsMillis()))
                .sign(Algorithm.HMAC256(configs.secret))

            call.respond(hashMapOf("token" to token))
        }
    }

    route(ALIEN_NEWS_TOKEN_AUTHENTICATED_CHANNELS) {
        authenticate(JWT_TOKEN_AUTH) { get(newsChannels) }
    }

    route(ALIEN_NEWS_TOKEN_AUTHENTICATED_CHANNEL) {
        authenticate(JWT_TOKEN_AUTH) { get(newsChannel) }
    }

    staticBasePackage = "images"
    static(ALIEN_NEWS_IMAGE) {  // TODO when time create own implementation of the file version of this I don't like resource folder used for this
        // reason for this is that default file version of this doesn't take "." as an argument...
        resources(".")
    }
}
