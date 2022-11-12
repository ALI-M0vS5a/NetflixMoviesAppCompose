package com.example.movieappcompose.util

import kotlin.math.ln
import kotlin.math.pow

fun withSuffix(count: Long): String {
    if (count < 1000) return "" + count
    val exp = (ln(count.toDouble()) / ln(1000.0)).toInt()
    return String.format(
        "%.1f %c",
        count / 1000.0.pow(exp.toDouble()),
        "kMGTPE"[exp - 1]
    )
}

fun toHourMinutesSeconds(input: Int): String {
    val days = input / 86400
    val hours = (input % 86400 ) / 3600
    val minutes = ((input % 86400 ) % 3600 ) / 60
    val seconds = ((input % 86400 ) % 3600 ) % 60
    if(minutes < 1) {
        return "$seconds s"
    }
    if(hours < 1) {
        return "$minutes m"
    }
    if(days < 1) {
        return "$hours h $minutes m"
    }
    return "$days d $hours h $minutes m"
}