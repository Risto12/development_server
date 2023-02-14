package com.development.resources.newsChannel

import kotlinx.serialization.Serializable

@Serializable
data class NewsContentResource(
    val text: String,
    val date: String,
    val imageIds: List<String>?,
    val brakingNews: Boolean,
)
