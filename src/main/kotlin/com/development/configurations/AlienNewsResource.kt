package com.development.configurations

import com.development.utility.DateHelper
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*


fun String.toDate(): Date = DateHelper.toDate(this)


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
    ) {

    fun isSameDate(date: Date): Boolean = date == this.date.toDate()
}

@Serializable
data class News(val news: Content)

data class NewsChannels(private val channels: Map<String, List<News>>) {

    private fun getLatestUpdateForChannel(news: List<News>) = news.maxOf { it.news.date.toDate() }

    fun getChannels(): List<Channel>? {
        // TODO need more specific timing to sort this correctly
        if(channels.isEmpty()) return null
        return channels.map { entry ->
            val latestUpdate = getLatestUpdateForChannel(entry.value)
            entry.value.first { it.news.isSameDate(latestUpdate) }.let {
                    channel ->
                Channel(
                    name = entry.key,
                    latestUpdate = channel.news.date,
                    brakingNews = channel.news.brakingNews
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
