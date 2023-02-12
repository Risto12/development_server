package com.development.resources.newsChannel

import com.development.resources.YAMLResourceBuilder


class NewsChannelResourceBuilder : YAMLResourceBuilder<NewsChannelsResource> {
    override val resourceFile: String = "alien_news.yml"
    override fun getResourceClassModel(): Class<NewsChannelsResource> = NewsChannelsResource::class.java
}
