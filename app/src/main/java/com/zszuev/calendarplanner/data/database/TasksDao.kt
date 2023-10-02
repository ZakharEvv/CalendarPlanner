package com.zszuev.taskmanager.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zszuev.calendarplanner.data.dbmodels.TaskDBModel

@Dao
interface TasksDao {

    @Query("SELECT * FROM tasks WHERE date=:date")
    suspend fun getTasksByDate(date: String): List<TaskDBModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(task: TaskDBModel)

    @Query("DELETE FROM tasks WHERE id=:taskId")
    suspend fun deleteTask(taskId: Long)

    fun selectionSort(arr: IntArray) {
        val n = arr.size
        for (i in 0 until n - 1) {
            var minIndex = i
            for (j in i + 1 until n) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j
                }
            }
            val temp = arr[minIndex]
            arr[minIndex] = arr[i]
            arr[i] = temp
        }
    }
}