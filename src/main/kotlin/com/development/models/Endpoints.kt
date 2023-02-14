package com.development.models

import kotlinx.serialization.Serializable

@Serializable
data class Endpoints(
    val path: String,
    val info: String? = null,
    val subPaths: List<Endpoints>? = null
)
