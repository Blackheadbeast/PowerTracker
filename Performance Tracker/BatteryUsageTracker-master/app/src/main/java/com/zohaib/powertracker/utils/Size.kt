package com.zohaib.powertracker.utils

import java.text.DecimalFormat
import kotlin.math.log10

fun fileSize(size2: Long): String {
    val size = size2.toLong()
    if (size <= 0) return "0"
    val units = arrayOf("B", "kB", "MB", "GB", "TB")
    val digitGroups = (log10(size.toDouble()) / log10(1024.0)).toInt()
    return DecimalFormat("#,##0.#").format(
        size / Math.pow(
            1024.0,
            digitGroups.toDouble()
        )
    ) + " " + units[digitGroups]
}