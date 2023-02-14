package com.development.models

import kotlinx.serialization.Serializable

// Many more but they will be added when required
enum class HTTPType {
    GET,
    POST
}

@Serializable
data class Endpoints(
    val info: String? = null,
    val path: String,
    val type: HTTPType? = null,
    val returns: String? = null,
    val subPaths: List<Endpoints>? = null,
)
