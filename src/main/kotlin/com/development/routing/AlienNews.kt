package com.development.routing

import com.development.exceptions.NoContentException
import com.development.models.Endpoints
import com.development.plugins.BASIC_AUTH
import com.development.plugins.JWT_TOKEN_AUTH
import com.development.resources.*
import com.development.resources.newsChannel.NewsChannelResourceBuilder
import com.development.utility.EndPointInfoGenerator
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

const val CHANNEL_ID_URL_RESOURCE = "channel_name"
const val VERSION_ONE = "v1"
const val ALIEN_NEWS_ROOT_PATH = "/alien/news"
const val ALIEN_NEWS_CHANNELS = "$ALIEN_NEWS_ROOT_PATH/$VERSION_ONE/channels"
const val ALIEN_NEWS_CHANNEL = "$ALIEN_NEWS_ROOT_PATH/$VERSION_ONE/channel/{$CHANNEL_ID_URL_RESOURCE}"
const val ALIEN_NEWS_BASIC_AUTHENTICATED_CHANNELS = "$ALIEN_NEWS_ROOT_PATH/$VERSION_ONE/authenticated/basic/channels"
const val ALIEN_NEWS_BASIC_AUTHENTICATED_CHANNEL = "$ALIEN_NEWS_ROOT_PATH/$VERSION_ONE/authenticated/basic/channel/{$CHANNEL_ID_URL_RESOURCE}"
const val ALIEN_NEWS_TOKEN_AUTHENTICATED_LOGIN = "$ALIEN_NEWS_ROOT_PATH/$VERSION_ONE/authenticated/token/login"
const val ALIEN_NEWS_TOKEN_AUTHENTICATED_CHANNELS = "$ALIEN_NEWS_ROOT_PATH/$VERSION_ONE/authenticated/token/channels"
const val ALIEN_NEWS_TOKEN_AUTHENTICATED_CHANNEL = "$ALIEN_NEWS_ROOT_PATH/$VERSION_ONE/authenticated/token/channel/{$CHANNEL_ID_URL_RESOURCE}"

fun YAMLResourceFactory.getNewsChannelResource() = factoryMethod(NewsChannelResourceBuilder())

private fun getChannelAsJson(channel: String) = Json.encodeToJsonElement(
    YAMLResourceFactory.getNewsChannelResource().getChannel(channel)
        ?: throw NoContentException(message = "No such channel")
)

private fun getChannelsAsJson() = Json.encodeToJsonElement(YAMLResourceFactory.getNewsChannelResource().getChannels())

fun Route.alienNews() {
    route(ALIEN_NEWS_ROOT_PATH) {
        get {
            val endpoints = Json.encodeToJsonElement(EndPointInfoGenerator.getAlienNews())
            call.respond(endpoints)
        }
    }

    // Authenticated and non authenticated routing could have been made as sub routes
    route(ALIEN_NEWS_CHANNELS) {
        get {
            call.respond(getChannelsAsJson())
        }
    }

    route(ALIEN_NEWS_CHANNEL) {
        get {
            val channel = getChannelAsJson(call.parameters[CHANNEL_ID_URL_RESOURCE]!!)
            call.respond(channel)

        }
    }

    route(ALIEN_NEWS_BASIC_AUTHENTICATED_CHANNELS) {
        authenticate(BASIC_AUTH) {
            get {
                call.respond(getChannelsAsJson())
            }
        }
    }

    route(ALIEN_NEWS_BASIC_AUTHENTICATED_CHANNEL) {
        authenticate(BASIC_AUTH) {
            get {
                val channel = getChannelAsJson(call.parameters[CHANNEL_ID_URL_RESOURCE]!!)
                call.respond(channel)
            }
        }
    }



    route(ALIEN_NEWS_BASIC_AUTHENTICATED_CHANNELS) {
        authenticate(JWT_TOKEN_AUTH) {
            get {
                call.respond(getChannelsAsJson())
            }
        }
    }

    route(ALIEN_NEWS_BASIC_AUTHENTICATED_CHANNEL) {
        authenticate(JWT_TOKEN_AUTH) {
            get {
                val channel = getChannelAsJson(call.parameters[CHANNEL_ID_URL_RESOURCE]!!)
                call.respond(channel)
            }
        }
    }
}