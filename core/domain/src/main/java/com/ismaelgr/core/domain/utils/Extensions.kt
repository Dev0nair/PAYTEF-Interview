package com.ismaelgr.core.domain.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun Calendar.formatToString(): String {
    return SimpleDateFormat(
        "yyyy-MM-dd",
        Locale.getDefault()
    ).format(time)
}