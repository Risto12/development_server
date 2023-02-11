package com.development.configurations


data class Content(val text: String, val date: String, val brakingNews: String)

data class News(val news: Content)

data class NewsChannels(val channels: Map<String, List<News>>)

class NewsChannelsResource : YAMLApplicationResource<NewsChannels> {
    override val resourceFile: String = "alien_news.yml"
    override fun getResourceClassModel(): Class<NewsChannels> = NewsChannels::class.java
}
