package com.zszuev.calendarplanner.data

import android.content.SharedPreferences
import com.zszuev.calendarplanner.data.mappers.TaskMapper
import com.zszuev.calendarplanner.domain.entities.Task
import com.zszuev.calendarplanner.domain.repository.Repository
import com.zszuev.taskmanager.data.database.TasksDao
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val taskMapper: TaskMapper,
    private val dao: TasksDao,
    private val sharedPreferences: SharedPreferences
) : Repository{

    private val NAME = "name_key"

    override suspend fun getTasksByDay(date: String): List<Task> =
        dao.getTasksByDate(date).map { taskMapper.mapDBModelToEntity(it) }

    override suspend fun saveTask(task: Task) {
        dao.addTask(taskMapper.mapEntityToDBModel(task))
    }

    override suspend fun deleteTask(id: Long) {
        dao.deleteTask(id)
    }

    override fun saveUserName(name: String) {
        val editor = sharedPreferences.edit()
        editor.putString(NAME, name).apply()
    }

    override fun getUserName(): String {
        return sharedPreferences.getString(NAME, "User")!!
    }
}