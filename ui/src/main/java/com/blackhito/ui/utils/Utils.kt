package com.blackhito.ui.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return formatter.format(Date(millis))
}

fun convertMillisToDateWithHour(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return formatter.format(Date(millis))
}

fun convertMillisToHour(millis: Long): String {
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return formatter.format(Date(millis))
}

fun LocalDateTime.toMillis(): Long = atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

fun convertMillisToDateTime(millis: Long): LocalDateTime =
    Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDateTime()

fun convertTimeToMillis(hours: Int, minutes: Int): Long {
    return hours * 60 * 60 * 1000L + minutes * 60 * 1000L
}


fun getEndOfDay(startDayMillis: Long): Long = startDayMillis + 24 * 60 * 60 * 1000