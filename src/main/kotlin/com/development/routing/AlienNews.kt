package com.development.routing

import com.development.configurations.NewsChannels
import com.development.configurations.NewsChannelsResource
import com.development.configurations.ResourceFactory
import com.development.utility.EndPointStringResponseCreator
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

const val ALIEN_NEWS_ROOT_PATH = "/alien/news"
const val ALIEN_NEWS_CHANNELS = "$ALIEN_NEWS_ROOT_PATH/channels"
const val ALIEN_NEWS_CHANNEL = "$ALIEN_NEWS_ROOT_PATH/channel/{id?}"

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
            val resource = ResourceFactory.factoryMethod(NewsChannelsResource())
            println(resource)
            call.respondText(endpoints, status = HttpStatusCode.OK)
        }
    }

    route(ALIEN_NEWS_CHANNELS) {
        get {
            call.respondText("TODO", status = HttpStatusCode.OK)
        }
    }

    route(ALIEN_NEWS_CHANNEL) {
        get {
            call.respondText("TODO", status = HttpStatusCode.OK)
        }
    }
}