package com.zszuev.calendarplanner.domain.usecases

import com.zszuev.calendarplanner.domain.repository.Repository
import javax.inject.Inject

class GetUserNameUseCase @Inject constructor(private val repository: Repository) {

    operator fun invoke() : String {
        return repository.getUserName()
    }
}