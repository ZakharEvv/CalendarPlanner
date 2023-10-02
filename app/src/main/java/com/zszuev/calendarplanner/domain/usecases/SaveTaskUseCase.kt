package com.zszuev.calendarplanner.domain.usecases

import com.zszuev.calendarplanner.domain.entities.Task
import com.zszuev.calendarplanner.domain.repository.Repository
import javax.inject.Inject

class SaveTaskUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(task: Task) {
        repository.saveTask(task)
    }
}