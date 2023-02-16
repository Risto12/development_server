package com.development.models

import kotlinx.serialization.Serializable

@Serializable
data class NewsChannel(
    val name: String,
    val header: String,
    val latestUpdate: String,
    val brakingNews: Boolean
)
