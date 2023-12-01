package com.zohaib.powertracker.utils

import android.util.Log
import java.util.Calendar

object CalendarUtils {
    private val TAG = "##@@@CalendarUtils"

    fun getCustomAdjustedSystemTimeMillis(filter: String): Long {
        val cal = Calendar.getInstance()

        when (filter) {
            "Past Hour" -> cal.add(Calendar.HOUR_OF_DAY, -1)
            "Past Day" -> cal.add(Calendar.DAY_OF_YEAR, -1)
            "Past Week" -> cal.add(Calendar.WEEK_OF_YEAR, -1)
            "Past Month" -> cal.add(Calendar.MONTH, -1)
            "Past Year" -> cal.add(Calendar.YEAR, -1)
            else -> {
                cal.add(Calendar.HOUR_OF_DAY, -1)
                Log.w(TAG, "filter: $filter")
            }
        }

        return cal.timeInMillis
    }

    fun formatDuration(milliseconds: Long): String {
        val seconds = milliseconds / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val weeks = days / 7
        val years = days / 365

        val res = when {
            years > 0 -> "$years years"
            weeks > 0 -> "$weeks weeks"
            days > 0 -> "$days days"
            hours > 0 -> "$hours hours"
            minutes > 0 -> "$minutes minutes"
            else -> "$seconds seconds"
        }
        if (years > 10)//Workaround for glitch: Some apps show 53 years back date due to unidentified cause so just return - when date exceeds 10 years
            return "-"

        return res
    }
}