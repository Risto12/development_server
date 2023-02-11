package com.development.configurations

import kotlinx.serialization.Serializable

@Serializable
data class Channel(
    val name: String,
    val latestUpdate: String,
    val brakingNews: Boolean
)

@Serializable
data class Content(
    val text: String,
    val date: String,
    val brakingNews: Boolean
    )

@Serializable
data class News(val news: Content)

data class NewsChannels(private val channels: Map<String, List<News>>) {

    private fun getLatestUpdateForChannel(news: List<News>): String = news.maxOf { it.news.date }

    fun getChannels(): List<Channel>? {
        // TODO need more specific timing to sort this correctly
        if(channels.isEmpty()) return null
        return channels.map {
            val name = it.key // TODO simplify this
            val latestUpdate = getLatestUpdateForChannel(it.value)
            it.value.first { news -> news.news.date == latestUpdate }.let {
                    news ->
                Channel(
                    name = name,
                    latestUpdate = news.news.date,
                    brakingNews = news.news.brakingNews
                )
            }
        }
    }

    fun getChannel(channel: String) = channels[channel]
}

class NewsChannelsResource : YAMLApplicationResource<NewsChannels> {
    override val resourceFile: String = "alien_news.yml"
    override fun getResourceClassModel(): Class<NewsChannels> = NewsChannels::class.java
}
