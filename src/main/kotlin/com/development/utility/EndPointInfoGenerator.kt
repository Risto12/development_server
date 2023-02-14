package com.development.utility

import com.development.models.Endpoints
import com.development.routing.*

object EndPointInfoGenerator {
    fun getAlienNews(): Array<Endpoints> = arrayOf(
        Endpoints(ALIEN_NEWS_ROOT_PATH,
        info = "Alien news android application rest api",
        subPaths = listOf(
            Endpoints(ALIEN_NEWS_CHANNEL),
            Endpoints(ALIEN_NEWS_CHANNELS),
            Endpoints(ALIEN_NEWS_BASIC_AUTHENTICATED_CHANNEL),
            Endpoints(ALIEN_NEWS_BASIC_AUTHENTICATED_CHANNELS),
            Endpoints(ALIEN_NEWS_TOKEN_AUTHENTICATED_CHANNEL),
            Endpoints(ALIEN_NEWS_TOKEN_AUTHENTICATED_CHANNELS),
            Endpoints(ALIEN_NEWS_TOKEN_AUTHENTICATED_LOGIN)
        )))

    fun getSocket(): Array<Endpoints> = arrayOf(Endpoints(SOCKET_ROOT_PATH,
            info = "Socket testing endpoints",
            subPaths = listOf(
                Endpoints(CHAT_PATH),
                Endpoints(ECHO_PATH)
            )
        ))

    fun getDevelopment() = arrayOf(Endpoints(
            DEVELOPMENT_ROOT_PATH,
            info = "Testing ktor framework related stuff"
    ))

    fun getAllEndPoints() = arrayOf(*getAlienNews(), *getDevelopment(), *getSocket())
}
