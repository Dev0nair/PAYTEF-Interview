package com.ismaelgr.core.domain.utils

import java.text.DateFormat
import java.util.Calendar

fun Calendar.formatToString(): String {
    return DateFormat.getDateInstance(DateFormat.LONG).format(this.time)
}