package com.zszuev.calendarplanner.domain.usecases

import com.zszuev.calendarplanner.domain.repository.Repository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(id: Long){
        repository.deleteTask(id)
    }
}