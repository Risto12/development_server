package com.development.routing

import com.development.configurations.*
import com.development.utility.EndPointStringResponseCreator
import io.ktor.http.*

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

const val ALIEN_NEWS_ROOT_PATH = "/alien/news"
const val ALIEN_NEWS_CHANNELS = "$ALIEN_NEWS_ROOT_PATH/channels"
const val ALIEN_NEWS_CHANNEL = "$ALIEN_NEWS_ROOT_PATH/channel/{id?}"


fun ResourceFactory.getNewsChannelResource() = factoryMethod(NewsChannelsResource())

/**
 * Basic API endpoint
 */
fun Route.alienNews() {
    route(ALIEN_NEWS_ROOT_PATH) {
        get {
            val endpoints = EndPointStringResponseCreator.endsPointsToString(
                ALIEN_NEWS_CHANNELS,
                ALIEN_NEWS_CHANNEL
            )
            call.respondText(endpoints, status = HttpStatusCode.OK)
        }
    }

    route(ALIEN_NEWS_CHANNELS) {
        get {
            val resource = ResourceFactory.getNewsChannelResource()
            call.respond(Json.encodeToJsonElement(resource.getChannels()))
        }
    }

    route(ALIEN_NEWS_CHANNEL) {
        get {
            // TODO create ktor validation for this
            val channel = ResourceFactory.getNewsChannelResource().getChannel(call.parameters["id"]!!)
            call.respond(Json.encodeToJsonElement(channel))
        }
    }
}