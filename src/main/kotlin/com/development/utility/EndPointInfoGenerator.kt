package com.development.utility

import com.development.models.Endpoints
import com.development.models.HTTPType
import com.development.routing.*

object EndPointInfoGenerator {
    fun getAlienNews(): Array<Endpoints> = arrayOf(
        Endpoints(
            path = ALIEN_NEWS_ROOT_PATH,
            info = "Alien news android application rest api",
            type = HTTPType.GET,
            subPaths = listOf(
                Endpoints(
                    path = ALIEN_NEWS_CHANNEL,
                    info = "Get channel info",
                    type = HTTPType.GET,
                ),
                Endpoints(
                    path = ALIEN_NEWS_CHANNELS,
                    info = "Get all available channels",
                    type = HTTPType.GET
                ),
                Endpoints(
                    path = ALIEN_NEWS_IMAGE,
                    info = "Get image related to news by id",
                    type = HTTPType.GET
                ),
                Endpoints(
                    path = ALIEN_NEWS_BASIC_AUTHENTICATED_CHANNEL,
                    info = "Same as channel with basic authentication",
                    type = HTTPType.GET
                ),
                Endpoints(
                    path = ALIEN_NEWS_BASIC_AUTHENTICATED_CHANNELS,
                    info = "Same as channels with basic authentication",
                    type = HTTPType.GET
                ),
                Endpoints(
                    path = ALIEN_NEWS_BASIC_AUTHENTICATED_IMAGE,
                    info = "Get image related to news by id with basic authentication",
                    type = HTTPType.GET
                ),
                Endpoints(
                    path = ALIEN_NEWS_TOKEN_AUTHENTICATED_CHANNEL,
                    info = "Same as channel but requires valid token in bearer parameters. Fetch token from token endpoint",
                    type = HTTPType.GET
                ),
                Endpoints(
                    path = ALIEN_NEWS_TOKEN_AUTHENTICATED_CHANNELS,
                    info = "Same as channels but requires valid token in bearer parameters. Fetch token from token endpoint",
                    type = HTTPType.GET
                ),
                Endpoints(
                    path = ALIEN_NEWS_TOKEN_AUTHENTICATED_IMAGE,
                    info = "Get image related to news by id with token authentication",
                    type = HTTPType.GET
                ),
                Endpoints(
                    path = ALIEN_NEWS_TOKEN_AUTHENTICATED_LOGIN,
                    info = "Acquire token by sending username and password fields in post form data",
                    type = HTTPType.POST
                )
        )))

    private fun getSocket(): Array<Endpoints> = arrayOf(
        Endpoints(path = CHAT_PATH),
        Endpoints(path = ECHO_PATH)
    )

    private fun getDevelopment() = arrayOf(Endpoints(
        path = DEVELOPMENT_ROOT_PATH,
        info = "Testing ktor framework related stuff. Might also do nothing but return string",
        type = HTTPType.GET
    ))

    fun getAllEndPoints() = arrayOf(*getAlienNews(), *getDevelopment(), *getSocket())
}
