package com.zszuev.calendarplanner.domain.entities

data class Month(
    val index: Int,
    val name: String,
    val days: Int,
    var isSelected: Boolean
)