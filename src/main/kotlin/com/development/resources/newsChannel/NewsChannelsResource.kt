package com.development.resources.newsChannel

import com.development.models.NewsChannel
import com.development.utility.DateHelper
import java.util.*


fun String.toDate(): Date = DateHelper.toDate(this)

data class NewsChannelsResource(private val channels: Map<String, List<NewsResource>>) {

    private fun getLatestUpdateForChannel(news: List<NewsResource>) = news.maxOf { it.news.date.toDate() }

    fun getChannels(): List<NewsChannel> {
        if(channels.isEmpty()) return listOf()
        return channels.map { entry ->
            val latestUpdate = getLatestUpdateForChannel(entry.value)
            entry.value.first { it.news.date.toDate() == latestUpdate }.let {
                    channel ->
                NewsChannel(
                    name = entry.key,
                    header = channel.news.header,
                    latestUpdate = channel.news.date,
                    brakingNews = channel.news.brakingNews
                )
            }
        }
    }

    fun getChannel(channel: String) =
        channels.mapKeys { it.key.lowercase() }[channel.lowercase()]?.sortedByDescending { it.news.date.toDate() }

}