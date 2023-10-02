package com.zszuev.calendarplanner.domain.usecases

import androidx.lifecycle.LiveData
import com.zszuev.calendarplanner.domain.entities.Task
import com.zszuev.calendarplanner.domain.repository.Repository
import javax.inject.Inject

class GetTasksByDayUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(date: String): List<Task> {
        return repository.getTasksByDay(date)
    }
}