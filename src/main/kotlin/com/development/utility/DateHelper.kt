package com.development.utility

import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
    private val configuredDateFormat = SimpleDateFormat("dd-MM-yyyy hh:mm:ss") // Should be with seconds but this is just development api

    init {
        configuredDateFormat.timeZone = TimeZone.getTimeZone("UTC")
    }

    fun toDate(date: String): Date = configuredDateFormat.parse(date)
}