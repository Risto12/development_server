package com.development.routing

import com.development.exceptions.NoContentException
import com.development.resources.*
import com.development.resources.newsChannel.NewsChannelResourceBuilder
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

const val CHANNEL_ID_URL_RESOURCE = "channel_name"
const val VERSION_ONE = "v1"
const val ALIEN_NEWS_ROOT_PATH = "/alien/news"
const val ALIEN_NEWS_CHANNELS = "$ALIEN_NEWS_ROOT_PATH/$VERSION_ONE/channels"
const val ALIEN_NEWS_CHANNEL = "$ALIEN_NEWS_ROOT_PATH/$VERSION_ONE/channel/{$CHANNEL_ID_URL_RESOURCE}"

fun YAMLResourceFactory.getNewsChannelResource() = factoryMethod(NewsChannelResourceBuilder())

@Serializable
data class EndPoint(val path: String)
fun Route.alienNews() {

    route(ALIEN_NEWS_ROOT_PATH) {
        get {
            val endpoints = Json.encodeToJsonElement(
                listOf(
                    EndPoint(ALIEN_NEWS_CHANNEL),
                    EndPoint(ALIEN_NEWS_CHANNELS)
                )
            )
            call.respond(endpoints)
        }
    }

    route(ALIEN_NEWS_CHANNELS) {
        get {
            val resource = YAMLResourceFactory.getNewsChannelResource()
            call.respond(Json.encodeToJsonElement(resource.getChannels()))
        }
    }

    route(ALIEN_NEWS_CHANNEL) {
        get {
            val channel = YAMLResourceFactory
                .getNewsChannelResource()
                .getChannel(call.parameters[CHANNEL_ID_URL_RESOURCE]!!)
                ?: throw NoContentException(message = "No such channel")
            call.respond(Json.encodeToJsonElement(channel))
        }
    }
}