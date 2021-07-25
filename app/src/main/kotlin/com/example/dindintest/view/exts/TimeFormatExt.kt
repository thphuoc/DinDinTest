package com.example.dindintest.view.exts

import java.text.SimpleDateFormat
import java.util.*

fun String.parseToMilis(): Long? {
    return try {
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(this)?.time
    } catch (ex: Exception) {
        null
    }
}

fun Long.formatHHMM(): String {
    return SimpleDateFormat(
        "HH:mm",
        Locale.getDefault()
    ).format(Date(this))
}

fun String.formatHHMM(): String {
    return this.parseToMilis()?.formatHHMM() ?: this
}

fun now() = Calendar.getInstance().timeInMillis

fun Long.convertMsToTimeLeft(): String {
    val seconds = this / 1000
    if (seconds < 60) {
        return "00:$seconds".plus("s")
    }
    val mins = seconds / 60
    val secondLeft = mins % 60
    return "$mins:$secondLeft"
}

fun Long.toTimeString(): String {
    return SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss",
        Locale.getDefault()
    ).format(Date(this))
}