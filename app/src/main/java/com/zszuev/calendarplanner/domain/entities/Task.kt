package com.zszuev.calendarplanner.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    val id: Long = UNDEFINED_ID,
    val time: String,
    val date: String,
    val title: String,
    val text: String
) : Parcelable {
    companion object {
        const val UNDEFINED_ID: Long = 0
    }
}