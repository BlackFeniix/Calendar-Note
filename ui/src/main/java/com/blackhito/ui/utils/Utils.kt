package com.blackhito.ui.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

fun convertMillisToHour(millis: Long): String {
    val formatter = SimpleDateFormat("HH:mm",
        Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return formatter.format(Date(millis))
}

fun getEndOfDay(startDayMillis: Long): Long = startDayMillis + 24 * 60 * 60 * 1000