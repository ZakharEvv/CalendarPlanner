package com.zszuev.calendarplanner.data.dbmodels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskDBModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val time: String,
    val date: String,
    val title: String,
    val text: String
)