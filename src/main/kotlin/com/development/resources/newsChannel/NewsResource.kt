package com.development.resources.newsChannel

import kotlinx.serialization.Serializable

@Serializable
data class NewsResource(val news: NewsContentResource)
