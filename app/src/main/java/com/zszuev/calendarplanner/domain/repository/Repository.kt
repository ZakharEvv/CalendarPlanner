package com.zszuev.calendarplanner.domain.repository

import androidx.lifecycle.LiveData
import com.zszuev.calendarplanner.domain.entities.Task

interface Repository {

    suspend fun getTasksByDay(date: String): List<Task>

    suspend fun saveTask(task: Task)

    suspend fun deleteTask(id: Long)

    fun saveUserName(name: String)

    fun getUserName(): String
}